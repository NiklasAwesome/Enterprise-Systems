package se.liu.ida.tdp024.account.utils.impl.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import se.liu.ida.tdp024.account.utils.api.logger.AccountLogger;


public class AccountLoggerKafka implements AccountLogger {
    private String topic;
    private Producer<String, String> producer;

    public AccountLoggerKafka(String topic) {
        this.topic = topic;
        this.producer = createProducer();
    }

    public void log(String message) {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        producer.send(new ProducerRecord<String, String>(this.topic, String.format("[%s]: ", now) + message));
    }

    private Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(props);

    }
}
