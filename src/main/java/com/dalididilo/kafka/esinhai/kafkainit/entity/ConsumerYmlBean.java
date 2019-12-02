package com.dalididilo.kafka.esinhai.kafkainit.entity;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Kafka 消费者配置实体类。
 * @author zwb_dev
 */
@Component
@Profile(value = "server")
@ConfigurationProperties("kafka-consumer-config")
@Data
public class ConsumerYmlBean {
    /**
     * Zookeeper集群地址
     */
    private String bootstrapServers;
    /**
     *  消费者组ID
     */
    private String groupId;
    /**
     * 是否允许自动commit
     */
    private String enableAutoCommit;
    /**
     * 提交延时
     */
    private String AutoCommitIntervalMs;
    /**
     *  Key反序列化
     */
    private String KeyDeSerializer;
    /**
     *  Value反序列化
     */
    private String ValueDeSerializer;

    /**
     * 初始化消费者需要的配置信息。
     * @return
     */
    public Properties initProps(){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,AutoCommitIntervalMs);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,KeyDeSerializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,ValueDeSerializer);
        return props;
    }
}
