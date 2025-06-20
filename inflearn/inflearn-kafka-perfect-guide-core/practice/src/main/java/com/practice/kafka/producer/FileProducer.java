package com.practice.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileProducer {
    public static final Logger logger = LoggerFactory.getLogger(FileProducer.class.getName());

    public static void main(String[] args) {
        String topicName = "file-topic";
        String filePath = "C:\\study\\kafka\\inflearn-kafka-perfect-guide-core\\practice\\src\\main\\resources\\pizza_sample.txt";

        Properties props = new Properties();

        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.133:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // KafkaProducer 객체 생성
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props)) {
            sendFileMessages(kafkaProducer, topicName, filePath);
        }
    }

    private static void sendFileMessages(KafkaProducer<String, String> kafkaProducer, String topicName, String filePath) {
        String line = "";
        final String delimiter = ",";
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(delimiter);
                String key = tokens[0];
                String value = Stream.of(tokens)
                        .filter(token -> !token.equals(key))
                        .collect(Collectors.joining(","));

                sendMessage(kafkaProducer, topicName, key, value);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }


    }

    private static void sendMessage(KafkaProducer<String, String> kafkaProducer, String topicName, String key, String value) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, key, value);

        logger.info("key: {}, value: {}", key, value);

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
}
