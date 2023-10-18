use kafka::producer::{Producer, Record, RequiredAcks};
use std::time::Duration;
use chrono::prelude::*;

pub fn send_to_kafka(message: String) {
    let mut producer = Producer::from_hosts(vec!["localhost:9092".to_owned()])
    .with_ack_timeout(Duration::from_secs(1))
    .with_required_acks(RequiredAcks::One)
    .create()
    .unwrap();

    let date_as_string = format!("{}", Local::now().format("%Y-%m-%d %H:%M:%S"));
    let msg_to_send = format!("[{}]: RUST: {}", date_as_string, message);    
    producer
        .send(&Record::from_value("rest", msg_to_send.as_bytes()))
        .unwrap();
}
