package com.dalididilo.kafka.test;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Properties;

/**
 * Kafka 服务器配置连接实体类。
 * @author zwb_dev
 */
@Component
@Profile(value = "server")
@ConfigurationProperties("kafka-servers-config")
public class KafkaYmlBean {
    /**
     * 集群地址
     */
    private String bootstrapServers;
    /**
     *  应答级别：ack模式，all是最慢但是最安全的
     */
    private String acks;
    /**
     * 发送失败，重试次数
     */
    private String retries;
    /**
     * 批量大小
     */
    private String batchSize;
    /**
     * 提交延时。
     */
    private String lingerMs;
    /**
     * 缓存,生产者。
     */
    private String bufferMemory;
    /**
     *  Key序列化
     */
    private String KeySerializer;
    /**
     *  Value序列化
     */
    private String ValueSerializer;

    /**
     * 初始化Kafka集群配置Props
     * @return
     */
    public Properties initProps(){
        Properties props = null;
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.155:9091,192.168.1.155:9092,192.168.1.155:9093");
        props.put(ProducerConfig.ACKS_CONFIG,acks);
        props.put(ProducerConfig.RETRIES_CONFIG,retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG,lingerMs);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,KeySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,ValueSerializer);
        /**
         * 服务器查询KAFKA集群地址。
         */
//        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("code","KAFKA");
//        List<SysConfig> zkServers = sysConfigService.list(queryWrapper);
//        if(zkServers != null && zkServers.size() > 0) {
//            props = new Properties();
//            String bootstrapServer = zkServers.get(0).getValue();
            /**
             * 初始化完成后存储到系统常量中。
             */
//            ConstantsContext.putConstant("KAFKA_SERVER_URL",bootstrapServer);
            /**
             * 连接配置信息。
             */
//            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
//            props.put(ProducerConfig.ACKS_CONFIG,acks);
//            props.put(ProducerConfig.RETRIES_CONFIG,retries);
//            props.put(ProducerConfig.BATCH_SIZE_CONFIG,batchSize);
//            props.put(ProducerConfig.LINGER_MS_CONFIG,lingerMs);
//            props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,bufferMemory);
//            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,KeySerializer);
//            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,ValueSerializer);
//        }

        return props;
    }
}
