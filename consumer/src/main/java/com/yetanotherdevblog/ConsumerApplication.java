package com.yetanotherdevblog;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@SpringBootApplication
@EnableBinding(Sink.class)
public class ConsumerApplication {

	static class User {
		private String id, username;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}

	@Autowired
	private Logger logger;

	@StreamListener(Sink.INPUT)
	public void loggerSink(Message<User> message) {
		logger.info("Received: " + message.getPayload().getUsername());

		// Save to READ datastore

	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}

