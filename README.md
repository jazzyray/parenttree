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

## Curl Requests

### JSON
``` 
curl -X GET --header 'Accept: application/vnd.ontotext.parenttree+json' --header 'X-Request-ID: 123123' 'http://localhost:9105/parenttree?conceptId=http%3A%2F%2Fontology.ontotext.com%2Fresource%2Ftsk9f4r0xn3c&conceptId=http%3A%2F%2Fontology.ontotext.com%2Fresource%2Ftsk9f4r0xn4c'
```

#### Response
```
 { "tree": { "leaf": { "id": "http://ontology.ontotext.com/resource/tsk9f4r0xn1c" }, "children": [ { "leaf": { "id": "http://ontology.ontotext.com/resource/tsk9f4r0xn2c" }, "children": [ { "leaf": { "id": "http://ontology.ontotext.com/resource/tsk9f4r0xn4c" }, "children": null } ] }, { "leaf": { "id": "http://ontology.ontotext.com/resource/tsk9f4r0xn3c" }, "children": null } ] }, "nodes": [ { "id": "http://ontology.ontotext.com/resource/tsk9f4r0xn1c", "preferredLabel": "Well drilling", "label": "well-drilling" }, { "id": "http://ontology.ontotext.com/resource/tsk9f4r0xn2c", "preferredLabel": "Well planning", "label": "well-planning" }, { "id": "http://ontology.ontotext.com/resource/tsk9f4r0xn3c", "preferredLabel": "Well site preparation", "label": "well-site-preparation" }, { "id": "http://ontology.ontotext.com/resource/tsk9f4r0xn4c", "preferredLabel": "Wellbore design", "label": "well-bore-design" } ] }
```

### XML

``` 
curl -X GET --header 'Accept: application/vnd.ontotext.parenttree+xml' --header 'X-Request-ID: 123123' 'http://localhost:9105/parenttree?conceptId=http%3A%2F%2Fontology.ontotext.com%2Fresource%2Ftsk9f4r0xn3c&conceptId=http%3A%2F%2Fontology.ontotext.com%2Fresource%2Ftsk9f4r0xn4c'
```

#### Response
``` 
curl -X GET --header 'Accept: application/vnd.ontotext.parenttree+xml' --header 'X-Request-ID: 123123' 'http://localhost:9105/parenttree?conceptId=http%3A%2F%2Fontology.ontotext.com%2Fresource%2Ftsk9f4r0xn3c&conceptId=http%3A%2F%2Fontology.ontotext.com%2Fresource%2Ftsk9f4r0xn4c'
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
