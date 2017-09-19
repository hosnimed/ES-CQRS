package com.yetanotherdevblog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableBinding(Source.class)
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

}

@Component
class UserListener extends AbstractMongoEventListener<User> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserListener.class);

	private MessageChannel output;

	public UserListener(MessageChannel output) {
		this.output = output;
	}

	@Override
	public void onAfterSave(AfterSaveEvent<User> event) {
		LOGGER.info("onAfterSave {}", event);
		output.send(
				MessageBuilder.withPayload(event.getSource())
						.build());
	}

	@Override
	public void onAfterDelete(AfterDeleteEvent<User> event) {
		LOGGER.info("onAfterDelete {}", event);
	}

}

@Document
class User {

	@Id
	private String id;

	private String username;

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