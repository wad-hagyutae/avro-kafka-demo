package co.wadcorp.catchtable.avrokafkademo.config;

import co.wadcorp.catchtable.event.SimpleMessage;
import io.apicurio.registry.serde.SerdeConfig;
import io.apicurio.registry.serde.avro.AvroKafkaSerializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Value(value = "${schema-registry.host}")
  private String registryHost;

  @Value(value = "${schema-registry.uri}")
  private String registryUri;

  @Bean
  public ProducerFactory<String, SimpleMessage> producerFactory() {
    // 상세 내용 관련해서는 다음 링크 참고
    // https://www.apicur.io/registry/docs/apicurio-registry/2.3.x/getting-started/assembly-using-kafka-client-serdes.html#registry-serdes-config-producer_registry
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroKafkaSerializer.class.getName());
    configProps.put(SerdeConfig.REGISTRY_URL, registryHost + registryUri);
    // The lookup strategy to find the global ID for the schema.
    configProps.put(SerdeConfig.FIND_LATEST_ARTIFACT, Boolean.TRUE);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, SimpleMessage> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
