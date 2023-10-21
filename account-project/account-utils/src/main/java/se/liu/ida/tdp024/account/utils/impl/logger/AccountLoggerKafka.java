package se.liu.ida.tdp024.account.utils.impl.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import se.liu.ida.tdp024.account.utils.api.exception.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.utils.api.logger.AccountLogger;


public class AccountLoggerKafka implements AccountLogger {
    private String topic;
    private Producer<String, String> producer;

    public AccountLoggerKafka(String topic) {
        this.topic = topic;
        this.producer = createProducer();
    }

    public void log(String message) throws AccountServiceConfigurationException {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            producer.send(new ProducerRecord<String, String>(this.topic, String.format("[%s]: ", now) + message)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new AccountServiceConfigurationException("Kafka not available: " + e.getMessage());
        }
    }

    private Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("linger.ms", 1);
        props.put("max.block.ms", 5000);
        props.put("delivery.timeout.ms", 5000);
        props.put("request.timeout.ms", 3000);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(props);

    }
}
