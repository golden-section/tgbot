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
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PrivatBankService implements BankService {
    private final CurrencyRepository currencyRepository;
    private final BankRepository bankRepository;
    private final CurrencyRateRepository currencyRateRepository;

    private static final String PRIVAT_URL_1 = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";
    private static final String PRIVAT_URL_2 = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=12";

    @Scheduled(cron = "0 0 0 * * ?")
    public void saveCurrencyRateDaily() {
        saveCurrencyRateFromJson(PRIVAT_URL_1);
        saveCurrencyRateFromJson(PRIVAT_URL_2);
    }

    @Override
    public void saveCurrencyRateFromJson(String strURL) {
        try {
            URL url = URI.create(strURL).toURL();
            JSONArray jsonArray = new JSONArray(IOUtils.toString(url, StandardCharsets.UTF_8));

            Bank bank = bankRepository.findBankByName("PrivatBank");

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                Currency currency = new Currency();
                currency.setCode(json.getString("ccy"));
                currencyRepository.save(currency);

                CurrencyRate currencyRate = new CurrencyRate();
                currencyRate.setBank(bank);
                currencyRate.setCurrency(currency);
                currencyRate.setBuy(new BigDecimal(json.getString("buy")));
                currencyRate.setSale(new BigDecimal(json.getString("sale")));
                currencyRate.setDate(DateFormat.parseDate(String.valueOf(LocalDateTime.now()), sdf));
                currencyRateRepository.save(currencyRate);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
