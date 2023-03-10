package co.wadcorp.catchtable.avrokafkademo.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Value(value = "${spring.kafka.numPartitions}")
  private int numPartitions;
  @Value(value = "${spring.kafka.replicationFactor}")
  private short replicationFactor;

  @Value(value = "${spring.kafka.topics.simple-message.name}")
  private String simpleMessageTopicName;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic simpleMessageTopic() {
    // 실행 시에 토픽이 존재하지 않는다면 생성
    return new NewTopic(simpleMessageTopicName, numPartitions, replicationFactor);
  }
}
