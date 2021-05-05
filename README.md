docker login

docker images
docker ps

docker rm -f container
docker rmi -f images

#######################

docker build -t ikc0505v1

docker tag ikc0505v1 niteenjava/ikc0505v1
docker push niteenjava/ikc0505v1

docker pull niteenjava/ikc0505v1   
docker run -p 8080:8080 niteenjava/ikc0505v1
