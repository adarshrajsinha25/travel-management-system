import boto3

rds = boto3.client('rds', region_name='us-east-1')
ec2 = boto3.client('ec2', region_name='us-east-1')

# Get RDS instance details
dbs = rds.describe_db_instances(DBInstanceIdentifier='dev-ecom-rds-mysql-authdb')
db = dbs['DBInstances'][0]

print(f"DB:                {db['DBInstanceIdentifier']}")
print(f"Endpoint:          {db['Endpoint']['Address']}")
print(f"Port:              {db['Endpoint']['Port']}")
print(f"PubliclyAccessible: {db['PubliclyAccessible']}")
print(f"VPC:               {db.get('DBSubnetGroup', {}).get('VpcId', 'N/A')}")
print(f"Subnet Group:      {db.get('DBSubnetGroup', {}).get('DBSubnetGroupName', 'N/A')}")
print(f"AZ:                {db.get('AvailabilityZone', 'N/A')}")
print()

# Check subnets in the DB subnet group
subnet_group = db.get('DBSubnetGroup', {})
subnets = subnet_group.get('Subnets', [])
print(f"=== DB Subnet Group: {subnet_group.get('DBSubnetGroupName')} ===")
subnet_ids = []
for s in subnets:
    sid = s['SubnetIdentifier']
    az = s['SubnetAvailabilityZone']['Name']
    status = s['SubnetStatus']
    subnet_ids.append(sid)
    print(f"  Subnet: {sid}, AZ: {az}, Status: {status}")
print()

# Check route tables for each subnet to see if there's an internet gateway
vpc_id = subnet_group.get('VpcId')
if vpc_id and subnet_ids:
    # Get all route tables in the VPC
    rts = ec2.describe_route_tables(Filters=[{'Name': 'vpc-id', 'Values': [vpc_id]}])
    
    print(f"=== Route Tables in VPC {vpc_id} ===")
    for rt in rts['RouteTables']:
        rt_id = rt['RouteTableId']
        assoc_subnets = [a.get('SubnetId', 'main') for a in rt['Associations']]
        is_main = any(a.get('Main', False) for a in rt['Associations'])
        
        has_igw = False
        for route in rt['Routes']:
            gw = route.get('GatewayId', '')
            if gw.startswith('igw-'):
                has_igw = True
                
        relevant = any(s in assoc_subnets for s in subnet_ids) or is_main
        if relevant:
            print(f"  RT: {rt_id}, Main: {is_main}, HasIGW: {has_igw}")
            print(f"    Associated subnets: {assoc_subnets}")
            for route in rt['Routes']:
                dest = route.get('DestinationCidrBlock', route.get('DestinationPrefixListId', '?'))
                target = route.get('GatewayId', route.get('NatGatewayId', route.get('TransitGatewayId', '?')))
                print(f"    Route: {dest} -> {target}")
            print()

# Check Network ACLs
print(f"=== Network ACLs for subnets ===")
nacls = ec2.describe_network_acls(Filters=[{'Name': 'vpc-id', 'Values': [vpc_id]}])
for nacl in nacls['NetworkAcls']:
    assoc_subnets = [a['SubnetId'] for a in nacl['Associations']]
    relevant = any(s in assoc_subnets for s in subnet_ids)
    if relevant:
        print(f"  NACL: {nacl['NetworkAclId']}")
        print(f"    Subnets: {assoc_subnets}")
        for entry in sorted(nacl['Entries'], key=lambda x: (not x['Egress'], x['RuleNumber'])):
            direction = "OUTBOUND" if entry['Egress'] else "INBOUND"
            action = entry['RuleAction']
            cidr = entry.get('CidrBlock', '?')
            rule_num = entry['RuleNumber']
            proto = entry.get('Protocol', '?')
            port_range = entry.get('PortRange', {})
            from_port = port_range.get('From', '*')
            to_port = port_range.get('To', '*')
            print(f"    {direction} Rule#{rule_num}: {action} {cidr} proto={proto} ports={from_port}-{to_port}")
        print()

