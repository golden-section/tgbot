package tld.petbot.tgbot.bankServices;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import tld.petbot.tgbot.Date.DateFormat;
import tld.petbot.tgbot.bank.BankService;
import tld.petbot.tgbot.currency.CurrencyService;
import tld.petbot.tgbot.rate.CurrencyRate;
import tld.petbot.tgbot.rate.CurrencyRateService;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PrivatBankService implements BankInterface {
    private BankService bankService;
    private CurrencyService currencyService;
    private CurrencyRateService currencyRateService;

    private static final String PRIVAT_URL_1 = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";
    private static final String PRIVAT_URL_2 = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=12";

    @Scheduled(cron = "0 0 0 * * ?")
    public void saveCurrencyRateDaily() {
        saveCurrencyRateFromJson(PRIVAT_URL_1);
        saveCurrencyRateFromJson(PRIVAT_URL_2);
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
                    bankService.createBank("PrivatBank"),
                    currencyService.createCurrency(json.getString("ccy")),
                    new BigDecimal(json.getString("buy")),
                    new BigDecimal(json.getString("sale")),
                    DateFormat.parseDate(String.valueOf(LocalDateTime.now())));

            currencyRateList.add(currencyRate);
        }
        return currencyRateList;
    }
}
