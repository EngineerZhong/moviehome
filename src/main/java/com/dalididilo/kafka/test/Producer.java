package com.dalididilo.kafka.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Producer {




    public static void main(String[] args){
        Properties props = new Properties();
        // 设置集群地址
        props.put("bootstrap.servers", "192.168.123.83:9091,192.168.123.83:9092,192.168.123.83:9093");
        //ack模式，all是最慢但是最安全的
        props.put("acks", "all");
        // 序列化器
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

//        List<String> servers = new ArrayList<>();
//        servers.add("192.168.123.83:9091");
//        servers.add("192.168.123.83:9092");
//        servers.add("192.168.123.83:9093");
//        KafkaProperties props = new KafkaProperties();
//        props.setBootstrapServers(servers);
//        props.getTemplate()

        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 100000; i++)
            producer.send(new ProducerRecord<String, String>("first",Integer.toString(i), "value--" + i ));
        producer.close();
        System.out.println("producer close");

    }



}
