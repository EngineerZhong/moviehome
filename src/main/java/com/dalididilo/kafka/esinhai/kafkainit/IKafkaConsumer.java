package com.dalididilo.kafka.esinhai.kafkainit;


import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;

/**
 * @author zwb_dev
 */
public interface IKafkaConsumer<K,V> {

    /**
     * 获取K,V消费者。
     * @return
     */
    KafkaConsumer<K,V> getConsumer();

    /**
     * 获取K,V生产者。
     * @return
     */
    KafkaConsumer<K,V> getConsumer(List topic);
    /**
     * 读取Kafka ConsumerRecords
     *
     */
    void readRecords(KafkaConsumer<K, V> consumer);
}
