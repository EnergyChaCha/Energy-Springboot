# 실행 방법

- windows인 경우 git bash에서 실행시켜야 합니다.
- 현재 디렉터리에 .env 파일이 있어야 합니다.
  - .env 형식
    ``` properties
    # MySQL
    MYSQL_ROOT_PASSWORD=your_root_password
    MYSQL_DATABASE=your_database_name
    MYSQL_USER=your_mysql_user
    MYSQL_PASSWORD=your_mysql_password
    HOST_PORT=3306
    
    # Redis
    REDIS_PASSWORD=your_redis_password
    ```

- 현재 디렉터리에서 다음 명령어 실행시키세요. </br>
  ``` shell
  chmod +x docker-compose.sh
  ./docker-compose.sh
  ```

