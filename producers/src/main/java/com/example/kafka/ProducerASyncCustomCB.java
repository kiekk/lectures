package com.example.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerASyncCustomCB {

    public static final Logger logger = LoggerFactory.getLogger(ProducerASyncCustomCB.class.getName());

    public static void main(String[] args) {
        String topicName = "multipart-topic";

        // KafkaProducer configuration setting

        Properties props = new Properties();

        // bootstrap.servers, key.serializer.class, value.serializer.class
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.133:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // KafkaProducer 객체 생성
        try (KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(props)) {

            for (int seq = 0; seq < 20; seq++) {
                // ProducerRecord 객체 생성
                ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(topicName, seq, "hello world " + seq);
                // KafkaProducer 메시지 전송 (비동기, callback 사용)
                Callback callback = new CustomCallback(seq);
                kafkaProducer.send(producerRecord, callback);
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
