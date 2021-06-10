# spring-amqp-utils

1. Declarative message queue event. 
2. Provide dummy implementation by `application.yml` configuration.

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

application.yml

```yaml
application:
  mq:
    enbaled: true
```

### Best Practise

每一个聚合根下发布的所有类型的事件对应一个exchange，exchange设置为topic，queue可以配置接收某一种类型的事件，也可以配置接收所有某种聚合相关的事件，还可以配置接收所有事件。exchange的命名格式为“限界上下文.聚合名”，routing key为“聚合名.事件名”，比如对于Order上下文有Product聚合根的“已创建”事件，那么exchange为"order.product"，routing key为'product.created'。
