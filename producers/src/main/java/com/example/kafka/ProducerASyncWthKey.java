package com.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerASyncWthKey {

    public static final Logger logger = LoggerFactory.getLogger(ProducerASyncWthKey.class.getName());

    public static void main(String[] args) {
        String topicName = "multipart-topic";

        // KafkaProducer configuration setting

        Properties props = new Properties();

        // bootstrap.servers, key.serializer.class, value.serializer.class
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.133:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // KafkaProducer 객체 생성
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props)) {

            for (int seq = 0; seq < 20; seq++) {
                // ProducerRecord 객체 생성
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, String.valueOf(seq), "hello world " + seq);

                // KafkaProducer 메시지 전송 (비동기, callback 사용)
                kafkaProducer.send(producerRecord, (metadata, exception) -> {
                    if (exception == null) {
                        logger.info("\n ##### record metadata received ##### \n");
                        logger.info("partition : {}", metadata.partition());
                        logger.info("offset : {}", metadata.offset());
                        logger.info("timestamp : {}", metadata.timestamp());
                    } else {
                        logger.error("exception error from broker {}", exception.getMessage());
                    }
                });
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            kafkaProducer.flush();
        }
    }
}
