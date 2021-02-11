package com.pupu.lp03_commit;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author lp
 * @since  2021/2/11 17:52
 */
public class CommitConsumer {
    public static void main(String[] args) {
        Properties props= new Properties();
        //props.put("bootstrap.servers","192.168.44.161:9093,192.168.44.161:9094,192.168.44.161:9095");
        props.put("bootstrap.servers","121.37.227.17:9092");
        props.put("group.id","gp-test-group1");
        // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
        props.put("enable.auto.lp03_commit","true");
        // 消费者自动提交的间隔，则是间隔多久提交一次
        props.put("auto.lp03_commit.interval.ms","1000");
        // 从最早的数据开始消费 earliest | latest | none（如果服务端没有保存消费者和partition的关系，则会报错）
        props.put("auto.offset.reset","earliest");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> consumer=new KafkaConsumer<>(props);

        // 订阅 topic
        consumer.subscribe(Arrays.asList("lp03_commit-test"));

        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        // 手动提交
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d ,key =%s, value= %s, partition= %s%n" ,record.offset(),record.key(),record.value(),record.partition());
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                // 同步提交,然后服务端的offset才会更新
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

}
