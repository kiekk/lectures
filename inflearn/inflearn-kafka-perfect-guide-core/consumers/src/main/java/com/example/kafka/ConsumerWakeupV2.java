package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerWakeupV2 {

    public static final Logger logger = LoggerFactory.getLogger(ConsumerWakeupV2.class.getName());

    public static void main(String[] args) {

        String topicName = "pizza-topic";

        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.133:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_02");
        props.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "60000");

        try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props)) {

            // main thread
            Thread mainThread = Thread.currentThread();

            // main thread. 종료 시 별도의 thread 로 kafkaConsumer wakeup() 메서드를 호출하게 함
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("main program starts to exit by calling wakeup");
                kafkaConsumer.wakeup();

                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));

            kafkaConsumer.subscribe(List.of(topicName));

            kafkaConsumer.wakeup();

            int loopCount = 0;
            while (true) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));

                logger.info(" ###### logCount : {} consumerRecords count: {}", loopCount++, consumerRecords.count());

                consumerRecords.forEach(record -> {
                    logger.info("record key: {}, record value: {}, partition : {}, record offset: {}",
                            record.key(), record.value(), record.partition(), record.offset());
                });

                try {
                    logger.info("main thread is sleeping {} ms during while loop", loopCount * 10_000);
                    Thread.sleep(loopCount * 10_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (WakeupException e) {
            logger.error("wakeup exception has been called");
        } finally {
            logger.info("finally consumer is closing");
        }
    }
}
