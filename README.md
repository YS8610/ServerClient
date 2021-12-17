# ServerClient
Server/Client Java which serves only 1 client at any time due to blocking IO. Client can end connection by typing close<br>
Server will send a random text from cookies.txt in the root directory when client send "get-cookies

## Default setting
Default port for server is 3000.<br>
Default ip for client is 127.0.0.1 and default port is 3000<br>

## Jar operation
Can be compiled to 1 jar file. The command for running the jar file is as below<br>
Client = java -cp .\clientserver.jar com.day4.Client ip:portNo<br>
Server = java -cp .\clientserver.jar com.day4.Server portNo PathofTextFile<br>

For client end, java -cp .\clientserver.jar com.day4.Client 127.0.0.1:3000<br>
For server end, java -cp .\clientserver.jar com.day4.Server 3000 cookies.txt<br>
