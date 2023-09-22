package tld.petbot.tgbot.bankServices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tld.petbot.tgbot.rate.CurrencyRate;

import java.util.List;

@Service
@AllArgsConstructor
public class MonoBankService implements BankInterface {

    private static final String MONOBANK_URL = "https://api.monobank.ua/bank/currency";

    @Override
    public void saveCurrencyRateDaily() {
        saveCurrencyRateFromJson(MONOBANK_URL);
    }

    @Override
    public List<CurrencyRate> saveCurrencyRateFromJson(String strURL) {
        //todo service need to write currency code like "Code -> USD" mb can find library formatter for this
        /*
        "currencyCodeA": 840,
        "currencyCodeB": 980,
        "date": 1694984473,
        "rateBuy": 36.65,
        "rateCross": 0,
        "rateSell": 37.4406
         */
        return null;
    }
}
