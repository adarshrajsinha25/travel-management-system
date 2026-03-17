import boto3
import json
import urllib3
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

# Create IAM client with SSL verification disabled (corporate proxy/cert issue)
iam = boto3.client('iam', region_name='us-east-1', verify=False)

# Policy to allow Secrets Manager and RDS access
policy_document = {
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "secretsmanager:GetSecretValue",
                "secretsmanager:DescribeSecret"
            ],
            "Resource": "arn:aws:secretsmanager:us-east-1:959713282924:secret:rds!db-ac3131f7-1993-4580-8828-1622ea790e84-*"
        },
        {
            "Effect": "Allow",
            "Action": [
                "rds:DescribeDBInstances"
            ],
            "Resource": "*"
        }
    ]
}

try:
    # Try to put inline policy on the user
    response = iam.put_user_policy(
        UserName='adarshrajsinha',
        PolicyName='EaseTravelRDSSecretsAccess',
        PolicyDocument=json.dumps(policy_document)
    )
    print("SUCCESS: Attached inline policy 'EaseTravelRDSSecretsAccess' to user 'adarshrajsinha'")
except Exception as e:
    print(f"Failed to attach policy: {e}")
    
    # Try to get the RDS secret directly
    print("\nTrying to get RDS secret directly...")
    try:
        sm = boto3.client('secretsmanager', region_name='us-east-1', verify=False)
        response = sm.get_secret_value(SecretId='rds!db-ac3131f7-1993-4580-8828-1622ea790e84-ZF7csy')
        secret = json.loads(response['SecretString'])
        print(f"Host: {secret.get('host')}")
        print(f"Port: {secret.get('port')}")
        print(f"Username: {secret.get('username')}")
    except Exception as e2:
        print(f"Also failed: {e2}")
        
    # Try to describe RDS
    print("\nTrying to describe RDS instances...")
    try:
        rds = boto3.client('rds', region_name='us-east-1', verify=False)
        response = rds.describe_db_instances()
        for db in response['DBInstances']:
            print(f"DB: {db['DBInstanceIdentifier']}, Endpoint: {db.get('Endpoint', {}).get('Address', 'N/A')}")
    except Exception as e3:
        print(f"Also failed: {e3}")
