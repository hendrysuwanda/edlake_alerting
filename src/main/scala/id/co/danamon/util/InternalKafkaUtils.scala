package id.co.danamon.util

/**
  * Created by welly on 11/30/2017.
  */
object InternalKafkaUtils {
  def getKafkaParams(): scala.collection.immutable.Map[String, String] = {

    val kafkaParams: scala.collection.immutable.Map[String, String] = scala.collection.immutable.Map("bootstrap.servers" -> PropertyHandler.getValue("bootstrap.servers"),
      "group.id" -> PropertyHandler.getValue("group.id"),
      "enable.auto.commit" -> PropertyHandler.getValue("enable.auto.commit"),
      "security.protocol" -> PropertyHandler.getValue("security.protocol"),
      "sasl.kerberos.service.name" -> PropertyHandler.getValue("sasl.kerberos.service.name"),
      "session.timeout.ms" -> PropertyHandler.getValue("session.timeout.ms"),
      "key.deserializer" -> PropertyHandler.getValue("key.deserializer"),
      "value.deserializer" -> PropertyHandler.getValue("value.deserializer"),
      "auto.offset.reset" -> PropertyHandler.getValue("auto.offset.reset"))
    kafkaParams
  }

}
