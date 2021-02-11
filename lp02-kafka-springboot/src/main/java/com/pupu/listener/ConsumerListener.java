package com.pupu.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author lp
 * @since  2021/2/11 18:39
 */
@Component
public class ConsumerListener {
    //groupId是消费者组Id，保证同一组的消费者不会重复消费
    @KafkaListener(topics = "springboottopic",groupId = "springboottopic-group")
    public void onMessage(String msg){
        System.out.println("----收到消息："+msg+"----");
    }
}
