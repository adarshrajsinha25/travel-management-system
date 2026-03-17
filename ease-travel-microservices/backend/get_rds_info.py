import boto3

client = boto3.client('rds', region_name='us-east-1')
response = client.describe_db_instances()
for db in response['DBInstances']:
    print(f"DBInstanceIdentifier: {db['DBInstanceIdentifier']}")
    print(f"Endpoint: {db.get('Endpoint', {}).get('Address', 'N/A')}")
    print(f"Port: {db.get('Endpoint', {}).get('Port', 'N/A')}")
    print(f"MasterUsername: {db.get('MasterUsername', 'N/A')}")
    print(f"DBName: {db.get('DBName', 'N/A')}")
    print("---")

