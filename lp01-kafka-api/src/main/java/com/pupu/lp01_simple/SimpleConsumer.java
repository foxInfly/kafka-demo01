package com.pupu.lp01_simple;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * 简单消费者
 *
 * @author lp
 * @since 2021/2/11 13:51
 */
public class SimpleConsumer {
    public static void main(String[] args) {
        //1. 加载配置文件
        Properties props = new Properties();
        //props.put("bootstrap.servers","192.168.44.161:9093,192.168.44.161:9094,192.168.44.161:9095");
        props.put("bootstrap.servers", "121.37.227.17:9092");
        //指定消费者组；同一时间，不会出现两个消费者消费同一个partition的情况
        props.put("group.id", "lp-test-group");
        // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
        props.put("enable.auto.lp03_commit", "true");
        // 消费者自动提交的间隔
        props.put("auto.lp03_commit.interval.ms", "1000");
        // 从最早的数据开始消费 earliest | latest | none
        props.put("auto.offset.reset", "earliest");
        // 序列化方式，这里是字符串
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //2. 初始化消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        //3. 订阅topic
        consumer.subscribe(Collections.singletonList("mytopic"));

        try {
            while (true) {
                //4. poll拉取，kafka只用这一种方式；定义拉取的最大最阻塞时间，超过时间，会返回空；一次拉取的条数，默认是500条
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                //5. 解析拉取到的数据
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d ,key =%s, value= %s, partition= %s%n", record.offset(), record.key(), record.value(), record.partition());
                }
            }
        } finally {
            consumer.close();
        }
    }
}
