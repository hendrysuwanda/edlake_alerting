package id.co.danamon.bots

import id.co.danamon.repo.PicRepository
import id.co.danamon.schema.AlertNotification
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.{ApiContextInitializer, TelegramBotsApi}

/**
  * Created by welly on 11/29/2017.
  */
object ClouderaAlertBot {

  val botsApi = new TelegramBotsApi()
  val bot = new ClouderaNotifierBot()
  val picRepository = new PicRepository()

  botsApi.registerBot(bot)

  def send(x: AlertNotification) = {
    val pic = picRepository.getPicBySeverity(x.getSeverity)
    val message = new SendMessage()
      .setChatId(pic.chatId)
      .setText(x.getMessage.toString)

    bot.sendMessage(message)
  }
}
