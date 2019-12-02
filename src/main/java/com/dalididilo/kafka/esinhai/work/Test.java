package com.dalididilo.kafka.esinhai.work;

import com.esinhai.kafkainit.opensdk.KafkaSdk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Test implements CommandLineRunner {

    @Autowired
    KafkaSdk kafkaSdk;


    @Override
    public void run(String... args) throws Exception {
        kafkaSdk.test();
    }
}
