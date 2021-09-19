**LOCAL BUILD:**
docker build -t niteenjava/praveen-traders-service-001 .
docker build -t niteenjava/praveen-traders-ui-001 .

**LOCAL PUSH:**
docker push niteenjava/praveen-traders-service-001
docker push niteenjava/praveen-traders-ui-001

**UI:**
docker run -d -p 4200:80 niteenjava/praveen-traders-ui-001

**MYSQL RUN:**
docker run -d -p 3306:3306 --name mysql-docker-container -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=praveen-traders-db-001 -e MYSQL_USER=root-user -e MYSQL_PASSWORD=root@321 mysql:8

**MYSQL START:**
docker exec -it mysql-docker-container bash
mysql -uroot-user -p
root@321

Server -> Data Import

LOCAL:
brew install mysql
brew services start mysql
brew services list
brew services stop mysql

**LINK & RUN APPLCATION:**
docker run -t --name spring-app-jpa-container --link mysql-docker-container:mysql -p 8080:8080 niteenjava/praveen-traders-service-001


**USEFUL DOCKER COMMANDS:**
docker images;
docker container ls;
docker ps;

docker rmi -f image_name;
docker rm -f container_name;
docker system prune -a

docker logs -f ps_id

H2-database:
backup to '/Users/niteenchougula/Documents/back.zip'

//System configuration
ipconfig/all
ifconfig


###start mysql in command
mysql.server start