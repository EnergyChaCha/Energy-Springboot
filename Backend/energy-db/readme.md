# 실행 방법

- windows인 경우 git bash에서 실행시켜야 합니다.
- 현재 디렉터리에 .env 파일이 있어야 합니다.
  - .env 형식
    ``` properties
    # POSTGRES
    POSTGRES_PASSWORD=비밀번호
    POSTGRES_PORT=포트
    ```

- 현재 디렉터리에서 다음 명령어 실행시키세요. </br>
  ``` shell
  chmod +x db-start.sh
  ./db-start.sh
  ```

