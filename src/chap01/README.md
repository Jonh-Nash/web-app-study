# memo

## Javaの話
FileOutputStream: イメージ・データなどのrawバイトのストリームを書き込むときに使用する。
write(): 指定されたバイトをこのファイル出力ストリームに書き込みます。

FileInputStream: イメージ・データなどのrawバイトのストリームを読み込む時に使用する。
read(): この入力ストリームからデータのバイトを読み込みます。

* OutputStream と write(): ファイルの書き込み
* InputStream と read(): ファイルの読み込み

## 大事なこと
TcpServer は8001ポートで ServerSocket インスタンスを作り、以下のように待ち受ける。
```
Socket socket = server.accept();
```

TcpClient は localhost:8001 で Server インスタンスを作る。
このとき TcpServer は、acceptメソッドを抜けて Socket クラスのインスタンスを作る。

TcpServer と TcpClient の両方で Socket インスタンスができると接続が完了する。
あとは、Socketインスタンスからストリームを取得して write なり read なりを行うと、相手への送信、取得を行える。

* OutputStream と write(): 相手への送信
* InputStream と read(): 相手からの受信

## Apache を手軽に試す方法
Docker Desktop で Apache を立ち上げて、そこに TcpClient.java から接続することで行える。
devcontainer からは `localhost` にはつなげない（公開されていない）ため、`host.docker.internal` を使用する。

以下は、src/ でのコマンド

```
docker container run --name myapache -d -it -p 80:80 -v /Users/justy/dev/web-app-study/src/chap01/docs:/usr/local/apache2/htdocs httpd 
```

実行中のコンテナに入りたい時
```
docker exec -it myapache /bin/bash
```

コンテナのログを見たい時
```
docker logs myapache
```

コマンドで動かしたコンテナを消したい時
```
docker rm -f <containr ID>
```

## 1.3.4 での注意点
### Apacheから400が返ってくる場合
もし、VSCodeなどでclient_send.txtを編集しているとすると、改行の形式がLFになっているから400が返ってくる可能性が高い。
CRLFにしてから送ってみよう。

### Apacheから408が返ってくる場合
TcpClient.javaがclient_send.txtの最終行である空白行を送れていない可能性がある。
body部分を適当に書いて、空白行を送るようにしてみよう。
