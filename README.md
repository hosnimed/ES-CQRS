# CQRS + Event sourcing using Spring Cloud Stream #

CQRS stands for Command Query Responsibility segregation, which means that you can use a different model to update information and a different model to read information. Typically by different model we mean actual object model and probably on different machines.

I this example I am using 
* Kafka as event store (for event sourcing)
* MongoDB for Write store
* Could use Elasticsearch for Read store (full text capabilities)
* Spring Cloud Stream to abstract the event store and the publish/subscribe mechanism

This is actually a precursor project to applying Spring Data Flow which will handle instance count, partitioning between consumer/producers and ad-hoc creation of data micro-services using familiar Java API
and known semantics from Spring Integration like Sink, Source, Transformer
  
# Producer
On the producer side, a small app backed by mongodb is created. Spring Data provides special hooks like afterSave, afterDelete that are overridden in the application to apply event sourcing.

# Consumer
Consumer handles the event and from the producer and just logs it but could eventually save it to elasticsearch for full text querying 

```
class UserListener extends AbstractMongoEventListener<User> {

	......

	@Override
	public void onAfterSave(AfterSaveEvent<User> event) {
		...
	}

	@Override
	public void onAfterDelete(AfterDeleteEvent<User> event) {
		...
	}

}
```

# How do I set this up?

# Run Kafka, Zookeeper, Kafka manager, MongoDB
`docker-compose up -d`

# Build Producer/Consumer
`mvn -f producer/pom.xml clean package`
`mvn -f consumer/pom.xml clean package`

# Run Producer
`java -jar producer/target/producer.jar`

# Run Consumer
`java -jar consumer/target/consumer.jar`

# Do a GET request on producer
curl http://localhost:8080/
