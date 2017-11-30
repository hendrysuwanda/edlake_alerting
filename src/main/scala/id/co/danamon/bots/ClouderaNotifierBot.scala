package id.co.danamon.bots

import id.co.danamon.model.Pic
import id.co.danamon.repo.PicRepository
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

/**
  * Created by welly on 11/29/2017.
  */
class ClouderaNotifierBot extends TelegramLongPollingBot {
  private val repository = new PicRepository

  override def onUpdateReceived(update: Update): Unit = {
    if (!update.hasMessage() || !update.getMessage().hasText()) return

    val message_text = update.getMessage().getText

    if (!message_text.startsWith("/register")) return

    val chat_id = update.getMessage().getChatId

    repository.save(Pic(chat_id, "name"))

    val pic = new Pic(chat_id, message_text)

    val message = new SendMessage()
      .setChatId(chat_id)
      .setText(message_text)
    //
    //      execute(message) // Sending our message object to user
    sendMessage(message)
    println(message_text)
  }

  override def getBotToken: String = {
    "462337554:AAGuL1gqLT5rX7O32DSSed9iu_qEZTBXZHE"
  }

  override def getBotUsername: String = {
    "cloudera_police_bot"
  }
}
