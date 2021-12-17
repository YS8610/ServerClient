# ServerClient
Server/Client Java with thread so multiple client can be served at the same time. Multiple Clients are being handled by CookieClientHandler class. Client can end connection by typing `close`<br><br>
Server will send a random text from cookies.txt in the root directory when client send `get-cookies`

## Default setting
Default port for server is 3000.  
Default ip for client is 127.0.0.1 and default port is 3000  

## Jar operation
Can be compiled to 1 jar file. The command for running the jar file is as below<br><br>
>Client = `java -cp .\clientserver.jar com.day4.Client ip:portNo`  
>Server = `java -cp .\clientserver.jar com.day4.Server portNo PathofTextFile`
<br>

>Client example, `java -cp .\clientserver.jar com.day4.Client 127.0.0.1:3000`<br>
>Server example, `java -cp .\clientserver.jar com.day4.Server 3000 cookies.txt`
