package tld.petbot.tgbot.service;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tld.petbot.tgbot.bank.Bank;
import tld.petbot.tgbot.bank.BankRepository;
import tld.petbot.tgbot.currency.Currency;
import tld.petbot.tgbot.currency.CurrencyRepository;
import tld.petbot.tgbot.rate.CurrencyRate;
import tld.petbot.tgbot.rate.CurrencyRateRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
public class NbuService {
    private final CurrencyRepository currencyRepository;
    private final BankRepository bankRepository;
    private final CurrencyRateRepository currencyRateRepository;

    private static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Scheduled(cron = "0 0 0 * * ?")
    public void saveCurrencyRateDaily() {
        saveCurrencyRateFromJson();
    }

    private void saveCurrencyRateFromJson() {
        try {
            URL url = URI.create(NBU_URL).toURL();
            JSONArray jsonArray = new JSONArray(IOUtils.toString(url, StandardCharsets.UTF_8));

            Bank bank = new Bank();
            bank.setName("NBU");
            bankRepository.save(bank);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                Currency currency = new Currency();
                currency.setName(json.getString("txt"));
                currency.setCode(json.getString("cc"));
                currencyRepository.save(currency);

                CurrencyRate currencyRate = new CurrencyRate();
                currencyRate.setBank(bank);
                currencyRate.setCurrency(currency);
                currencyRate.setRate(new BigDecimal(json.getString("rate")));
                currencyRate.setDate(parseDate(json.getString("exchangedate"), sdf));
                currencyRateRepository.save(currencyRate);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Date parseDate(String dateStr, SimpleDateFormat sdf) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
