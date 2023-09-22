package tld.petbot.tgbot.bankServices;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tld.petbot.tgbot.Date.DateFormat;
import tld.petbot.tgbot.bank.BankService;
import tld.petbot.tgbot.currency.CurrencyService;
import tld.petbot.tgbot.rate.CurrencyRate;
import tld.petbot.tgbot.rate.CurrencyRateService;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NbuService implements BankInterface {
    private CurrencyService currencyService;
    private BankService bankService;
    private CurrencyRateService currencyRateService;

    private static final String NBU_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void saveCurrencyRateDaily() {
        saveCurrencyRateFromJson(NBU_URL);
    }

    @Override
    @SneakyThrows
    public List<CurrencyRate> saveCurrencyRateFromJson(String strURL) {
        List<CurrencyRate> currencyRateList = new ArrayList<>();

        URL url = URI.create(strURL).toURL();
        JSONArray jsonArray = new JSONArray(IOUtils.toString(url, StandardCharsets.UTF_8));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);

            CurrencyRate currencyRate = currencyRateService.createCurrencyRate(
                    bankService.createBank("NBUBank"),
                    currencyService.createCurrency(json.getString("currency")),
                    new BigDecimal(json.getString("rate")),
                    new BigDecimal(json.getString("rate")),
                    DateFormat.parseDate(json.getString("exchangedate")));

            currencyRateList.add(currencyRate);
        }
        return currencyRateList;
    }
}


