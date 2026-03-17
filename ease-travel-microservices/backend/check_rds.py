import boto3, socket

client = boto3.client('rds', region_name='us-east-1')
dbs = client.describe_db_instances()

for d in dbs['DBInstances']:
    host = d['Endpoint']['Address']
    port = d['Endpoint']['Port']
    user = d['MasterUsername']
    sgs = [sg['VpcSecurityGroupId'] for sg in d['VpcSecurityGroups']]
    public = d.get('PubliclyAccessible', False)
    print(f"DB:       {d['DBInstanceIdentifier']}")
    print(f"Endpoint: {host}")
    print(f"Port:     {port}")
    print(f"Master:   {user}")
    print(f"Public:   {public}")
    print(f"SGs:      {sgs}")
    print()

    # Quick TCP connectivity test
    print(f"Testing TCP connection to {host}:{port}...")
    try:
        sock = socket.create_connection((host, port), timeout=5)
        sock.close()
        print("✅ TCP connection SUCCESSFUL!")
    except Exception as e:
        print(f"❌ TCP connection FAILED: {e}")
    print()

# Try to get the secret (password)
try:
    sm = boto3.client('secretsmanager', region_name='us-east-1')
    resp = sm.get_secret_value(SecretId='rds!db-ac3131f7-1993-4580-8828-1622ea790e84-ZF7csy')
    import json
    secret = json.loads(resp['SecretString'])
    print(f"Secret username: {secret.get('username')}")
    print(f"Secret password: {'*' * len(secret.get('password', ''))}")
    print("✅ Secrets Manager access works!")
except Exception as e:
    print(f"❌ Secrets Manager: {e}")

