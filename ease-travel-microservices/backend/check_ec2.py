import boto3

ec2 = boto3.client('ec2', region_name='us-east-1')
instances = ec2.describe_instances(Filters=[{'Name': 'instance-state-name', 'Values': ['running', 'stopped']}])

print("=== EC2 Instances ===")
found = False
for r in instances['Reservations']:
    for i in r['Instances']:
        found = True
        name = ''
        for tag in i.get('Tags', []):
            if tag['Key'] == 'Name':
                name = tag['Value']
        print(f"  Name: {name}")
        print(f"  ID: {i['InstanceId']}")
        print(f"  State: {i['State']['Name']}")
        print(f"  Type: {i['InstanceType']}")
        print(f"  PublicIP: {i.get('PublicIpAddress', 'N/A')}")
        print(f"  Key: {i.get('KeyName', 'N/A')}")
        print(f"  VPC: {i.get('VpcId', 'N/A')}")
        print()

if not found:
    print("  No EC2 instances found.")
    print()
    print("OPTIONS for local dev:")
    print("  1. Create an EC2 instance in the same VPC for SSH tunneling")
    print("  2. Use AWS Systems Manager Session Manager port forwarding")
    print("  3. Deploy services directly to AWS (ECS/EC2)")
    print("  4. Use a local MySQL instead for development")

