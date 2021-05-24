docker build -t niteenjava/ikc-api-010 .

docker run -d -p 3306:3306 --name mysql-docker-container -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=dbname-ikc-010 -e MYSQL_USER=root-user -e MYSQL_PASSWORD=root@321 mysql:8

docker run -t --name spring-app-jpa-container --link mysql-docker-container:mysql -p 8080:8080 niteenjava/ikc-api-010

docker exec -it mysql-docker-container bash
mysql -uroot-user -proot@321
brew cask install mysqlworkbench

