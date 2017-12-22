

```
./mvnw clean package -DskipTests=true
```


### Run frontend uploader

```
java -Xmx128m -Xms128m -jar target/demo-uploader-bridge-0.0.1-SNAPSHOT.jar 
```

### Run backend uploader

```
java -Xmx128m -Xms128m -jar target/demo-uploader-bridge-0.0.1-SNAPSHOT.jar --server.port=9090 
```

### Upload a large file

Upload files with file size **greater than heap size**.

```
$ ls -lh ~/Downloads/jdk-9.0.1_osx-x64_bin.dmg 
-rw-r--r--@ 1 makit  720748206   382M 12 14 03:25 /Users/makit/Downloads/jdk-9.0.1_osx-x64_bin.dmg

$ curl -v localhost:8080/bridge -F file=@/Users/makit/Downloads/jdk-9.0.1_osx-x64_bin.dmg
> POST /bridge HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Length: 400670057
> Expect: 100-continue
> Content-Type: multipart/form-data; boundary=------------------------1d6b15e4f1355edd
> 
< HTTP/1.1 100 
< HTTP/1.1 200 
< X-Application-Context: application
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 2
< Date: Fri, 22 Dec 2017 14:37:46 GMT
< 
OK
```

