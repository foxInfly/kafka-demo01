package com.pupu.lp01_simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 简单生产者
 *
 * @author lp
 * @since 2021/2/11 14:04
 */
public class SimpleProducer {

    public static void main(String[] args) {

        //1. 配置参数
        Properties pros = new Properties();
        //pros.put("bootstrap.servers","192.168.44.161:9093,192.168.44.161:9094,192.168.44.161:9095");
        pros.put("bootstrap.servers", "121.37.227.17:9092");
        pros.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pros.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 0 发出去就确认 | 1 leader 落盘就确认| all(-1) 所有Follower同步完才确认
        pros.put("acks", "1");
        // 异常自动重试次数
        pros.put("retries", 3);
        // 多少条数据发送一次，默认16K
        pros.put("batch.size", 16384);
        // 批量发送的等待时间，哪怕没到16K，超过5秒也发送
        pros.put("linger.ms", 5);
        // 客户端缓冲区大小，默认32M，满了也会触发消息发送
        pros.put("buffer.memory", 33554432);
        // 获取元数据时生产者的阻塞时间，超时后抛出异常
        pros.put("max.block.ms", 3000);

        //2. 创建Sender线程
        Producer<String, String> producer = new KafkaProducer<>(pros);

        //如果topic不存在，会默认创建一个这个topic，其副本也是1个；生产中一般是禁止的，
        producer.send(new ProducerRecord<>("mytopic", "name", "lp"));
        producer.send(new ProducerRecord<>("mytopic", "sex", "男"));

        /*for (int i = 0; i < 5; i++) {
            //3. 发送消息给kafka
            producer.send(new ProducerRecord<>("mytopic", Integer.toString(i), Integer.toString(i)));
             System.out.println("发送:"+i);
        }*/

        //producer.send(new ProducerRecord<String,String>("mytopic","1","1"));
        //producer.send(new ProducerRecord<String,String>("mytopic","2","2"));

        producer.close();
    }
}
