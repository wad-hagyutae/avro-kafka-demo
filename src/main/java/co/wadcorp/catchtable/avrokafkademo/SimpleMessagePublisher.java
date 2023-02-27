package co.wadcorp.catchtable.avrokafkademo;

import co.wadcorp.catchtable.event.SimpleMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleMessagePublisher {

  private final KafkaTemplate<String, SimpleMessage> kafkaTemplate;

  @Value(value = "${spring.kafka.topics.simple-message.name}")
  private String simpleMessageTopicName;

  public SimpleMessagePublisher(KafkaTemplate<String, SimpleMessage> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publish(String key, String message) {
    SimpleMessage eventMessage = SimpleMessage.newBuilder()
        .setMessage(message)
        .build();
    kafkaTemplate.send(simpleMessageTopicName, key, eventMessage);
  }
}
