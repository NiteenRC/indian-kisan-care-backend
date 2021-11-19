**LOCAL BUILD:**
docker build -t niteenjava/smart-accounting-book .
docker build -t niteenjava/smart-accounting-book-ui-001 .

**LOCAL PUSH:**
docker push niteenjava/smart-accounting-book
docker push niteenjava/smart-accounting-book-ui-001

**UI:**
docker run -d -p 4200:80 niteenjava/smart-accounting-book-ui-001

**MYSQL RUN:**
docker run -d -p 3306:3306 --name mysql-docker-container -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=smart_accounting_book_db -e MYSQL_USER=root-user -e MYSQL_PASSWORD=root@321 mysql:8

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
docker run -t --name spring-app-jpa-container --link mysql-docker-container:mysql -p 8080:8080 niteenjava/smart-accounting-book
docker run -t --name spring-app-jpa-container -p 8080:8080 niteenjava/smart-accounting-book

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
GitHub token: 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
docker run -e MYSQL_ROOT_PASSWORD=password mysql_image


#### Postgres
brew instal postgresql
brew services list
brew services start postgresql
pqsl	postgres
create role app_user with login password 'app_user';
create database book_db;
\du
psql
\list
\q