package co.wadcorp.catchtable.avrokafkademo.controller;

import co.wadcorp.catchtable.avrokafkademo.SimpleMessageDto;
import co.wadcorp.catchtable.avrokafkademo.SimpleMessagePublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleMessageController {

  private final SimpleMessagePublisher simpleMessagePublisher;

  public SimpleMessageController(SimpleMessagePublisher simpleMessagePublisher) {
    this.simpleMessagePublisher = simpleMessagePublisher;
  }

  @PostMapping("publish")
  private ResponseEntity<String> publish(@RequestBody SimpleMessageDto simpleMessageDto) {
    simpleMessagePublisher.publish(simpleMessageDto.key(), simpleMessageDto.message());
    return ResponseEntity.ok("published");
  }
}
