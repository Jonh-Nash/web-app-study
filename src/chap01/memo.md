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
