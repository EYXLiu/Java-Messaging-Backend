# Java Messaging
Tech Stack: Java, Spring Boot, Websockets (STOMP), Multithreading, Kafka, Redis, MongoDB, Spring Security, Spring Data

# About
Implemented a real-time messaging backend using Spring Boot WebSocket (STOMP) to enable instant client-server communication, more details below <br/>
Used Apache Kafka for asynchronous, decoupled message processing between producers and consumers <br/>
Leveraged @Async and a custom `ThreadPoolTaskExecutor` to enable multithreaded MongoDB writes and Kafka publishing <br/>
Persisted messages in MongoDB using Spring Data, saving to a NoSQL database for multi-message types <br/>
Implemented Redis cacheing for active and inactive users, reducing latency and auto-updating without MongoDB read overheads <br/>

# Websocket Endpoints
To access the Kafka topic, first, create a SockJS websocket subscription to `HOST:PORT/ws` <br/>
Then, subscripe to '/topic/chat' to start receiving messages <br/>
Messages should be sent as a JSON package to '/app/chat' <br/>
Sample Code <br/>
`npm install @stomp/stompjs sockjs-client` <br/>
```
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const stompClient = new Client({
  webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
  reconnectDelay: 5000,
  onConnect: () => {
    console.log("Connected");
    stompClient.subscribe('/topic/chat', (message) => {
      const chatMessage = JSON.parse(message.body);
      //do whatever with chatMessage
      console.log(chatMessage);
    });
  },
});
stompClient.activate();

const sendMessage = (text, sender) => {
  const message = {
    content: text,
    senderId: sender
  }

  stompClient.publish({
    destination: '/app/chat',
    body: JSON.stringify(message),
  });
};
```

# Kafka Commands
For personal reference but also useful in general, I've found setting up kafka locally is a pain sometimes. There's id-issues and problems when restarting kafka sessions. 
To start, make sure to clear logs using `rm -rf /tmp/kraft-combined-logs/*`
Then create a new location to store logs with `bin/kafka-storage.sh format -t <random-key> -c config/server.properties`
* If this doesn't work, edit the server.properties file to include the line "controller.quorum.voters=1@HOST:PORT", that might be the issue. Make sure it aligns with the number of node.id's you've set, so more than one means more than one port for the controller.quorum.voters. 
Finally start Kafka using `bin/kafka-server-start.sh config/server.properties`
