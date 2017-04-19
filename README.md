ParentTree API
=

ParentTree API for retreiving a parent tree for a set of concepts 


# Quick REST test

```
docker-compose up -d
```

## For swagger documentation
```
http://localhost:9105/swagger
```

# Integration Tests

## Running Integration Test

'''
mvn clean verify -P integration-test
'''

# Docker

## Build

```
docker build .
```
  
## Tag
### Get the image id

```
docker images
```

## Push to quay

### Login

```
docker login -e="." -u="ontotext+ontotext" -p="XXXX" quay.io
```

### tag
```
docker tag ${IMAGE} parent-tree 

docker tag ${IMAGE} quay.io/ontotext/parent-tree

```

### push to quay
```
docker push quay.io/ontotext/parent-tree

```

## Run Interactive
```
docker run --name parent-tree -it parent-tree /bin/bash
```   

## Run Daemon
```
docker run --name parent-tree -d parent-tree 
```

## Shell to docker container



### Get container ids
```
docker ps -a
```

```
docker exec -i -t ${CONTAINER_ID} /bin/bash
```



## Invoke

## Run via docker-compose

### Environment

Create a .env file with the correct environment settings

```
SOME_THING=XXX

```

### Interactive
```
docker-compose up
```

### Daemon
```
docker-compose up -d
