# Kubernetes cluster config:

Tag all images and push to docker hub


General ConfigMap (current folder):

```shell script
kubectl apply -f shopping-configmap.yaml
```


Database deployment:
```shell script
cd database
kubectl apply -f shopping-database-secret.yaml
kubectl apply -f shopping-database-claim0-persistentvolume.yaml
kubectl apply -f shopping-database-claim0-persistentvolumeclaim.yaml
kubectl apply -f shopping-database-initial-data-configmap.yaml
kubectl apply -f shopping-database-deployment.yaml
kubectl apply -f shopping-database-ingress.yml

# Port forward if you want to access the database using DBeaver
kubectl port-forward pod/postgresql-99bcfd89b-bgnpj 5432:5432
```

Kafka deployment (kafka folder):
```shell script
cd kafka
kubectl apply -f zookeeper-deployment.yaml
kubectl apply -f kafka-deployment.yaml

# Access the kafka pod to create the topics used by the application:
kubectl exec -it pod/zookeeper-deployment-75f44db564-25sfh -- /bin/bash

kafka-topics --create --topic payment --bootstrap-server kafka-service:9092
kafka-topics --create --topic order-update --bootstrap-server kafka-service:9092
kafka-topics --create --topic product-update --bootstrap-server kafka-service:9092
kafka-topics --create --topic product --bootstrap-server kafka-service:9092
kafka-topics --create --topic payment-update --bootstrap-server kafka-service:9092

#Watching topics to see the messages received:
kafka-console-consumer --bootstrap-server localhost:29092 --topic payment

#List all topics
kafka-topics --list  --bootstrap-server kafka-service:9092
```

Microservices:
```shell script
cd services
kubectl apply -f order-deployment.yaml
kubectl apply -f payment-deployment.yaml
kubectl apply -f product-deployment.yaml
```


kubectl useful commands:

```shell script
# watch pod logs 
kubectl logs -f pod/order-deployment-55747fdf98-mbrmz
kubectl logs pod/order-deployment-55747fdf98-mbrmz --tail=100
```