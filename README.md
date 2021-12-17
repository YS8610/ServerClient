# ServerClient
Server/Client Java which serves only 1 client at any time due to blocking IO. Client can end connection by typing close
Server will send a random text from cookies.txt in the root directory when client send "get-cookies

## Default setting
Default port for server is 3000.
Default ip for client is 127.0.0.1 and default port is 3000

## Jar operation
Can be compiled to 1 jar file. The command for running the jar file is as below
Client = java -cp .\clientserver.jar com.day4.Client ip:portNo
Server = java -cp .\clientserver.jar com.day4.Server portNo PathofTextFile

For client end, java -cp .\clientserver.jar com.day4.Client 127.0.0.1:3000
For server end, java -cp .\clientserver.jar com.day4.Server 3000 cookies.txt
