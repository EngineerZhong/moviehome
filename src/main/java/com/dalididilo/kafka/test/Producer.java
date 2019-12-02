package com.dalididilo.kafka.test;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Producer {




    public static void main(String[] args){
        Properties props = new Properties();
        // 设置集群地址
        props.put("bootstrap.servers", "192.168.1.155:9091,192.168.1.155:9092,192.168.1.155:9093");
        //ack模式，all是最慢但是最安全的
        props.put("acks", "all");
        // 序列化器
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String,String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 9; i++){
            producer.send(new ProducerRecord<>("zwb_dev", "value--" + i)
                    , (metadata, exception) -> {
                if(exception == null){
                    System.out.println(metadata.topic() + ":::::" + metadata.partition());
                    System.out.println(metadata.offset());
                }
            });
        }
        producer.close();
        System.out.println("producer close");

    }



}
