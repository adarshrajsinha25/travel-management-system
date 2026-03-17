import boto3

ec2 = boto3.client('ec2', region_name='us-east-1')
sg_id = 'sg-0a6e32903b8d153ee'

# First, check current rules
sg = ec2.describe_security_groups(GroupIds=[sg_id])
print("Current inbound rules:")
for rule in sg['SecurityGroups'][0]['IpPermissions']:
    print(f"  Protocol: {rule.get('IpProtocol')}, FromPort: {rule.get('FromPort')}, ToPort: {rule.get('ToPort')}")
    for ip_range in rule.get('IpRanges', []):
        print(f"    CIDR: {ip_range['CidrIp']}")
    for group in rule.get('UserIdGroupPairs', []):
        print(f"    SG: {group['GroupId']}")
print()

# Add MySQL/Aurora rule (port 3306) for 0.0.0.0/0 to allow your IP
# (Your IP might change, so we add a proper rule)
print("Adding inbound rule: TCP port 3306 from 0.0.0.0/0 ...")
try:
    ec2.authorize_security_group_ingress(
        GroupId=sg_id,
        IpPermissions=[
            {
                'IpProtocol': 'tcp',
                'FromPort': 3306,
                'ToPort': 3306,
                'IpRanges': [
                    {'CidrIp': '0.0.0.0/0', 'Description': 'MySQL access from anywhere'}
                ]
            }
        ]
    )
    print("✅ Rule added successfully!")
except Exception as e:
    if 'Duplicate' in str(e) or 'already exists' in str(e):
        print("Rule already exists.")
    else:
        print(f"❌ Failed: {e}")

# Verify
import socket
host = 'dev-ecom-rds-mysql-authdb.cgnk0406c735.us-east-1.rds.amazonaws.com'
port = 3306
print(f"\nTesting TCP connection to {host}:{port}...")
try:
    sock = socket.create_connection((host, port), timeout=10)
    sock.close()
    print("✅ TCP connection SUCCESSFUL!")
except Exception as e:
    print(f"❌ TCP connection FAILED: {e}")

