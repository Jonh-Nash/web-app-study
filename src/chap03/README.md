# chap03
## Tomcatをデプロイする
1. devcontainer の中で行う
  * `javac -cp /usr/local/tomcat/lib/servlet-api.jar -d ./WEB-INF/classes *.java`
2. ローカルで行う
  * `docker build -t myapp .`
  * `docker run --name myapp -p 8080:8080 -d myapp:latest`
3. 画面表示
  * `http://localhost:8080/chap03/ShowBBS`
4. 削除
  * `docker stop myapp`
  * `docker rm myapp`

## jsp版のTomcatをデプロイする
1. devcontainer の中で行う
  * `javac -cp /usr/local/tomcat/lib/servlet-api.jar -d ./testbbs_jsp/WEB-INF/classes/ bbs/*.java`
2. ローカルで行う
  * `docker build -t myapp .`
  * `docker run --name myapp -p 8080:8080 -d myapp:latest`
3. 画面表示
  * `http://localhost:8080/testbbs_jsb/ShowBBS`
4. 削除
  * `docker stop myapp`
  * `docker rm myapp`