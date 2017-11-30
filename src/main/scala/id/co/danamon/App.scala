package id.co.danamon

import id.co.danamon.bots.ClouderaAlertBot
import id.co.danamon.util.{InternalKafkaUtils, KafkaSerializer, PropertyHandler}
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{KafkaUtils, _}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.telegram.telegrambots.ApiContextInitializer

/**
 * @author ${user.name}
 */
object App {
  val logger = Logger.getLogger(App.getClass)

  def main(args : Array[String]) {
    PropertyHandler.loadProperties()

    ApiContextInitializer.init()

    val spark = SparkSession.builder.master("local[2]").appName("Campaign App").getOrCreate()
    val sc  = spark.sparkContext
    val streamingContext = new StreamingContext(sc, Seconds(2))

    val topic = "notifier"

    val stream = KafkaUtils.createDirectStream[String, Array[Byte]](streamingContext, PreferConsistent, Subscribe[String, Array[Byte]](Array(topic), InternalKafkaUtils.getKafkaParams()))

    stream.foreachRDD(rdd => {
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      logger.info("Reading current offset Range..." + offsetRanges.mkString)

      rdd.foreachPartition(records => {

//        val alertBot = new ClouderaAlertBot()

        records
          .map(x => KafkaSerializer.convertToObject(x.value()))
          .foreach(ClouderaAlertBot.send)

      })

    })

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
