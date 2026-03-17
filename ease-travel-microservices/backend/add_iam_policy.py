import boto3
import json

iam = boto3.client('iam', region_name='us-east-1')

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
    print("Successfully attached inline policy 'EaseTravelRDSSecretsAccess' to user 'adarshrajsinha'")
except Exception as e:
    print(f"Failed to attach policy: {e}")
    print("\nTrying to list user policies to see what exists...")
    try:
        policies = iam.list_user_policies(UserName='adarshrajsinha')
        print(f"Inline policies: {policies['PolicyNames']}")
        attached = iam.list_attached_user_policies(UserName='adarshrajsinha')
        print(f"Attached policies: {[p['PolicyName'] for p in attached['AttachedPolicies']]}")
    except Exception as e2:
        print(f"Also failed to list policies: {e2}")

