package com.practice.kafka.event;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class FileEventHandler implements EventHandler {
    public static final Logger logger = LoggerFactory.getLogger(FileEventHandler.class.getName());

    private KafkaProducer<String, String> kafkaProducer;
    private String topicName;
    private boolean sync;

    public FileEventHandler(KafkaProducer<String, String> kafkaProducer, String topicName, boolean sync) {
        this.kafkaProducer = kafkaProducer;
        this.topicName = topicName;
        this.sync = sync;
    }

    @Override
    public void onMessage(MessageEvent messageEvent) throws InterruptedException, ExecutionException {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(this.topicName, messageEvent.getKey(), messageEvent.getValue());

        if (this.sync) {
            RecordMetadata recordMetadata = kafkaProducer.send(producerRecord).get();
            logger.info("\n ##### record metadata received ##### \n");
            logger.info("partition : {}", recordMetadata.partition());
            logger.info("offset : {}", recordMetadata.offset());
            logger.info("timestamp : {}", recordMetadata.timestamp());
        } else {
            this.kafkaProducer.send(producerRecord, (metadata, exception) -> {
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

    //FileEventHandler가 제대로 생성되었는지 확인을 위해 직접 수행.
    public static void main(String[] args) throws Exception {
        String topicName = "file-topic";

        Properties props  = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);
        boolean sync = true;

        FileEventHandler fileEventHandler = new FileEventHandler(kafkaProducer, topicName, sync);
        MessageEvent messageEvent = new MessageEvent("key00001", "this is test message");
        fileEventHandler.onMessage(messageEvent);
    }
}
