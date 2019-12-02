package com.dalididilo.kafka.esinhai.kafkainit.entity;

import lombok.Data;
import org.apache.kafka.clients.admin.AdminClientConfig;
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
@ConfigurationProperties("kafka-server-config")
@Data
public class KafkaServerYmlBean {

    /**
     * 集群地址。
     */
    private String bootstrapServers;
    /**
     * 连接管理中央的Id
     */
    private String clientId;
    /**
     *  最大的存活毫秒数。
     */
    private String metaMaxAgeMs;
    /**
     * 连接失败的重试次数。
     */
    private String retries;


    /**
     * Kafka环境参数。
     * @return
     */
    public Properties initProp(){
        Properties prop = new Properties();
        prop.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        prop.put(AdminClientConfig.METADATA_MAX_AGE_CONFIG,metaMaxAgeMs);
        prop.put(AdminClientConfig.RETRIES_CONFIG,retries);
        return prop;
    }


}
