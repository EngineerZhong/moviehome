package com.dalididilo.kafka.esinhai.kafkainit.opensdk;

import com.esinhai.kafkainit.entity.KafkaServerYmlBean;
import com.esinhai.kafkainit.init.KafkaInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaSdk {
    /**
     * kafka 服务配置实体类。
     */
    @Autowired
    KafkaServerYmlBean prop;

    @Autowired
    KafkaInit init;

    public void test() throws Exception {
        for (String queryTopic : init.getInstance(prop).queryTopics()) {
            log.info("Topic:" + queryTopic);
        }
    }

}
