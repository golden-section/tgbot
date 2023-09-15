package tld.petbot.tgbot.telegram.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import tld.petbot.tgbot.telegram.CurrencyExchangeRatesBot;

@Configuration
public class BotInitializer {
    @SneakyThrows
    @Bean
    public TelegramBotsApi telegramBotsApi(CurrencyExchangeRatesBot currencyExchangeRatesBot) {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(currencyExchangeRatesBot);
        return api;
    }
}