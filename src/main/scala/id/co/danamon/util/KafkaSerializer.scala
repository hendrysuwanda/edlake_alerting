package id.co.danamon.util

import id.co.danamon.schema.AlertNotification
import org.apache.avro.Schema
import org.apache.avro.io.DecoderFactory
import org.apache.avro.specific.SpecificDatumReader
import org.apache.log4j.Logger

/**
  * Created by welly on 11/30/2017.
  */
object KafkaSerializer {
  val logger = Logger.getLogger(KafkaSerializer.getClass)

  def convertToObject(message: Array[Byte]): AlertNotification = {
    val schemaString = scala.io.Source.fromFile("src/main/avro/AlertNotification.avsc").mkString
    val schema: Schema = new Schema.Parser().parse(schemaString)
    // Deserialize and create generic record
    val reader = new SpecificDatumReader[AlertNotification](schema)
    logger.info("reading schema is done...")
    val decoder = DecoderFactory.get().binaryDecoder(message, null)
    logger.info("decoding message is done..." + decoder)
    val record = reader.read(null, decoder)

    record
  }
}
