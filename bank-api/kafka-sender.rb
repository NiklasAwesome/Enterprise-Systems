require 'kafka'
k = Kafka.new(["localhost:9092"])
k.deliver_message("RUBY LIST", topic: "rest")

class Kafkasender
    def initialize
        @kaf = Kafka.new(["localhost:9092"])
        @topic = "rest"
    end
    def log(message)
        msg_to_send = "[%s]: RUBY: %s" % [Time.now.strftime("%Y-%m-%d %k:%M:%S"), message]
        @kaf.deliver_message(msg_to_send, topic: @topic)
    end
end