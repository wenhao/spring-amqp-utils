# spring-amqp-utils

### event 

```java
@Event(exchange = EXCHANGE_ORDER, routingKey = ROUTING_ORDER_CREATED)
public class OrderCreatedEvent extends OrderEvent {
    
}
```

### dispatcher

```java
@Service
@ConditionalOnProperty(prefix = "application.mq", name = "enabled", havingValue = "true")
public class OrderEventDispatcher implements EventDispatcher<OrderEvent> {

    private final AmqpTemplate amqpTemplate;

    public OrderEventDispatcher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void dispatch(OrderEvent event) {
        Event annotation = AnnotationUtils.findAnnotation(event.getClass(), Event.class);
        amqpTemplate.convertAndSend(annotation.exchange(), annotation.routingKey(), event);
    }
}
```

### listener

```java
@Slf4j
@Service
@ConditionalOnProperty(prefix = "application.mq", name = "enabled", havingValue = "true")
public class OrderEventListener {

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(QUEUE_ORDER_CREATED), exchange = @Exchange(value = EXCHANGE_ORDER, type = ExchangeTypes.TOPIC), key = ROUTING_ORDER_CREATED)})
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("order: {}, date: {}", event.getOrderId(), event.getDate());
    }
}
```

### Enable MQ 

```yaml
application:
  mq:
    enbaled: true
```
