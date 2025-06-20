package com.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class SimpleProducerSync {

    public static final Logger logger = LoggerFactory.getLogger(SimpleProducerSync.class.getName());

    public static void main(String[] args) {
        String topicName = "simple-topic";

        // KafkaProducer configuration setting

        Properties props = new Properties();

        // bootstrap.servers, key.serializer.class, value.serializer.class
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.133:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // KafkaProducer 객체 생성
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props)) {

            // ProducerRecord 객체 생성
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "hello world");

            // KafkaProducer 메시지 전송
            try {
                RecordMetadata recordMetadata = kafkaProducer.send(producerRecord).get();
                logger.info("\n ##### record metadata received ##### \n");
                logger.info("partition : {}", recordMetadata.partition());
                logger.info("offset : {}", recordMetadata.offset());
                logger.info("timestamp : {}", recordMetadata.timestamp());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            kafkaProducer.flush();
        }
    }
}
