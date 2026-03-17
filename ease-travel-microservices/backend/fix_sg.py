import boto3
import socket

ec2 = boto3.client('ec2', region_name='us-east-1')
sg_id = 'sg-0a6e32903b8d153ee'
current_ip = '203.200.34.50/32'

# Step 1: Show current rules
sg = ec2.describe_security_groups(GroupIds=[sg_id])
print("=== Current inbound rules ===")
for rule in sg['SecurityGroups'][0]['IpPermissions']:
    print(f"  Protocol: {rule.get('IpProtocol')}, FromPort: {rule.get('FromPort')}, ToPort: {rule.get('ToPort')}")
    for ip_range in rule.get('IpRanges', []):
        print(f"    CIDR: {ip_range['CidrIp']}")
    for group in rule.get('UserIdGroupPairs', []):
        print(f"    SG: {group['GroupId']}")
print()

# Step 2: Remove old rule for 136.226.233.109/32 (port 3306)
print("Removing old rule (136.226.233.109/32 on port 3306)...")
try:
    ec2.revoke_security_group_ingress(
        GroupId=sg_id,
        IpPermissions=[{
            'IpProtocol': 'tcp',
            'FromPort': 3306,
            'ToPort': 3306,
            'IpRanges': [{'CidrIp': '136.226.233.109/32'}]
        }]
    )
    print("  Old rule removed.")
except Exception as e:
    print(f"  Skip (may not exist): {e}")

# Step 3: Add correct rule for current IP
print(f"Adding rule: TCP 3306 from {current_ip}...")
try:
    ec2.authorize_security_group_ingress(
        GroupId=sg_id,
        IpPermissions=[{
            'IpProtocol': 'tcp',
            'FromPort': 3306,
            'ToPort': 3306,
            'IpRanges': [{'CidrIp': current_ip, 'Description': 'MySQL from my current IP'}]
        }]
    )
    print("  ✅ Rule added!")
except Exception as e:
    if 'Duplicate' in str(e):
        print("  Rule already exists.")
    else:
        print(f"  ❌ Failed: {e}")

# Step 4: Verify rules
print("\n=== Updated inbound rules ===")
sg = ec2.describe_security_groups(GroupIds=[sg_id])
for rule in sg['SecurityGroups'][0]['IpPermissions']:
    print(f"  Protocol: {rule.get('IpProtocol')}, FromPort: {rule.get('FromPort')}, ToPort: {rule.get('ToPort')}")
    for ip_range in rule.get('IpRanges', []):
        print(f"    CIDR: {ip_range['CidrIp']}")
    for group in rule.get('UserIdGroupPairs', []):
        print(f"    SG: {group['GroupId']}")
print()

# Step 5: Test TCP connectivity
host = 'dev-ecom-rds-mysql-authdb.cgnk0406c735.us-east-1.rds.amazonaws.com'
port = 3306
print(f"Testing TCP connection to {host}:{port}...")
try:
    sock = socket.create_connection((host, port), timeout=10)
    sock.close()
    print("✅ TCP connection SUCCESSFUL!")
except Exception as e:
    print(f"❌ TCP connection FAILED: {e}")
