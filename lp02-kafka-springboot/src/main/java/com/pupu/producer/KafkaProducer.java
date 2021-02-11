package com.pupu.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 *
 * @author lp
 * @since  2021/2/11 18:40
 */
@Component
public class KafkaProducer {
    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate;

    public String send(@RequestParam String msg){
        kafkaTemplate.send("springboottopic", msg);
        return "ok";
    }
}
