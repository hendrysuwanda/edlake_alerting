package samples

import java.io.ByteArrayOutputStream
import java.util.Properties

import org.junit._
import Assert._
import id.co.danamon.schema.AlertNotification
import org.apache.avro.Schema.Parser
import org.apache.avro.io.EncoderFactory
import org.apache.avro.specific.SpecificDatumWriter
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

@Test
class AppTest {

    @Test
    def testOK(): Unit = {
      val props = new Properties()
      props.put("bootstrap.servers", "localhost:9092")
      props.put("acks", "all")
      props.put("retries", "0")
      props.put("batch.size", "16384")
      props.put("linger.ms", "1")
      props.put("buffer.memory", "33554432")
      props.put("security.protocol", "PLAINTEXT")
      props.put("sasl.kerberos.service.name", "kafka")
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
      val producer = new KafkaProducer[String, Array[Byte]](props)

      val schema = new Parser().parse(scala.io.Source.fromFile("D:\\sprinboot\\edlake_alerting\\src\\main\\avro\\AlertNotification.avsc").mkString)

      val alert = new AlertNotification("source", "Alert in Cloudera", "Warning", "timestamp")

      val writer = new SpecificDatumWriter[AlertNotification](schema)
      val out = new ByteArrayOutputStream
      val encoder = EncoderFactory.get.binaryEncoder(out, null);
      writer.write(alert, encoder)
      encoder.flush()
      out.close()
      val serializedBytes: Array[Byte] = out.toByteArray()

      producer.send(new ProducerRecord[String, Array[Byte]]("notifier", serializedBytes))
    }

//    @Test
//    def testKO() = assertTrue(false)

}


