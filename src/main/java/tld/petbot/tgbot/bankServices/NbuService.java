package tld.petbot.tgbot.bankServices;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tld.petbot.tgbot.Date.DateFormat;
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
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NbuService implements BankService {
    private final CurrencyRepository currencyRepository;
    private final BankRepository bankRepository;
    private final CurrencyRateRepository currencyRateRepository;

    private static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void saveCurrencyRateDaily() {
        saveCurrencyRateFromJson(NBU_URL);
    }

    @Override
    public void saveCurrencyRateFromJson(String strURL) {
        try {
            URL url = URI.create(strURL).toURL();
            JSONArray jsonArray = new JSONArray(IOUtils.toString(url, StandardCharsets.UTF_8));

            Bank bank = bankRepository.findBankByName("NBUBank");

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                Currency currency = new Currency();
                currency.setCode(json.getString("currency"));
                currencyRepository.save(currency);

                CurrencyRate currencyRate = new CurrencyRate();
                currencyRate.setBank(bank);
                currencyRate.setCurrency(currency);
                currencyRate.setBuy(new BigDecimal(json.getString("rate")));
                currencyRate.setSale(new BigDecimal(json.getString("rate")));
                currencyRate.setDate(DateFormat.parseDate(json.getString("exchangedate"), sdf));
                currencyRateRepository.save(currencyRate);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
