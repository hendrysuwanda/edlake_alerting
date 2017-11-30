package id.co.danamon.util

/**
  * Created by welly on 11/29/2017.
  */
import scala.io.Source
import java.util.Properties
import org.apache.log4j.Logger
import java.io.FileNotFoundException


object PropertyHandler extends Serializable {
  val logger = Logger.getLogger(PropertyHandler.getClass)
  var props: Properties = null;
  def loadProperties() = {
    props = new Properties()
    try {
      val source = Source.fromFile("src/main/resources/application.properties").bufferedReader()
      props.load(source)
    } catch {
      case e: Exception => logger.error("Error Occured while loading properties file...Reason :" + e.getMessage)
    }
  }

  def getValue(key: String): String = {
    if (props != null) {
      val value = props.getProperty(key)
      logger.info("Key :" + key + ", Value:" + value)
      return value
    }
    return null
  }

}