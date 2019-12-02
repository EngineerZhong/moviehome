package com.dalididilo.kafka.esinhai.kafkainit.impl;

import com.esinhai.kafkainit.IKafkaConsumer;
import com.esinhai.kafkainit.entity.ConsumerYmlBean;
import com.esinhai.kafkainit.init.KafkaInit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

/**
 * @author  zwb_dev
 */
@Component
@Data
@Slf4j
public class KafkaConsumerImpl implements IKafkaConsumer<String,Object> {

    @Autowired
    ConsumerYmlBean prop;

    @Autowired
    KafkaInit kafkaClient;

    /**
     * 获取消费者，未指定分区的情况。
     * @return
     */
    @Override
    public KafkaConsumer<String, Object> getConsumer() {
        return new KafkaConsumer<>(prop.initProps());
    }

    /**
     * 获取消费者并且指定了消费的分区。
     * @param topic
     * @return
     */
    @Override
    public KafkaConsumer<String, Object> getConsumer(List topic) {
        KafkaConsumer<String, Object> consumer = getConsumer();
        consumer.subscribe(topic);
        return consumer;
    }

    /**
     * 消费多条数据。
     * @param consumer 指定了消费具体的Topic的consumer
     * @return
     */

    @Override
    public void readRecords(KafkaConsumer<String, Object> consumer) {
        readRecordsAsync(consumer);
    }

    @Async
    public void readRecordsAsync(KafkaConsumer<String, Object> consumer){
        while (true){
            /**
             * 消费者消费数据。
             */
            ConsumerRecords<String, Object> records
                    = consumer.poll(Duration.ofMillis(Long.valueOf(prop.getAutoCommitIntervalMs())));
            for (ConsumerRecord<String, Object> record : records) {
                log.info(record.topic() + ":::" + record.partition() + ":::" + record.value());
            }
        }

    }
}