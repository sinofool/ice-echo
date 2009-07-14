
SERVER_CXX_BIN=target/EchoServer
CLIENT_JAVA_BIN=target/echo-client-1.0-SNAPSHOT.jar
CLIENT_CXX_BIN=target/EchoClient

all: $(SERVER_CXX_BIN) $(CLIENT_JAVA_BIN) $(CLIENT_CXX_BIN)

$(SERVER_CXX_BIN):
	mkdir -p target/generated/cpp
	slice2cpp -I/opt/Ice-3.3/slice slice/*.ice --output-dir=target/generated/cpp
	g++ -g -o target/EchoServer -Iserver/cpp -Itarget/generated/cpp -I/opt/Ice-3.3/include -L/opt/Ice-3.3/lib64 -lIce -Wl,-rpath=/opt/Ice-3.3/lib64 server/cpp/*.cpp target/generated/cpp/*.cpp

$(CLIENT_JAVA_BIN): pom.xml
	mvn package

$(CLIENT_CXX_BIN) : client/cpp/main.cpp
	mkdir -p target/generated/cpp
	slice2cpp -I/opt/Ice-3.3/slice slice/*.ice --output-dir=target/generated/cpp
	g++ -g -o target/EchoClient -Iclient/cpp -Itarget/generated/cpp -I/opt/Ice-3.3/include -L/opt/Ice-3.3/lib64 -lIce -Wl,-rpath=/opt/Ice-3.3/lib64 client/cpp/*.cpp target/generated/cpp/*.cpp
	

clean:
	rm -rf target
