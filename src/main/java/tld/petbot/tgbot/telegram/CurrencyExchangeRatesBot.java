package tld.petbot.tgbot.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CurrencyExchangeRatesBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.name}")
    private String botName;
    public CurrencyExchangeRatesBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
    /* The following code is added for testing purposes only.
    * It should be changed */
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("You sent " + text);
                sendMessage.setChatId(message.getChatId());
                execute(sendMessage);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}