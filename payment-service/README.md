# payment-service

This microservice is focused on tasks related to orders.

```shell script
# Build native image and deploy container in Docker
mvn clean package -D quarkus.container-image.build=true -Pnative -D quarkus.native.container-build=true -D skipTests

#Tag image and push to Docker Hub
docker image tag eliasf/payment-service:1.0.0-SNAPSHOT lfelipelias/payment-service:1.0.0-SNAPSHOT
docker image push lfelipelias/payment-service:1.0.0-SNAPSHOT
```