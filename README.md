docker run -d \
      -p 3306:3306 \
     --name mysql-docker-container \
     -e MYSQL_ROOT_PASSWORD=root123 \
     -e MYSQL_DATABASE=dbname-ikc-005 \
     -e MYSQL_USER=root-user \
     -e MYSQL_PASSWORD=root@321 \
        mysql:8

docker build -t niteenjava/ikc-api-001 .

docker run -t --name spring-app-jpa-container --link mysql-docker-container:mysql -p 8080:8080 niteenjava/ikc-api-001