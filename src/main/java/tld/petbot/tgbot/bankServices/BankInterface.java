package tld.petbot.tgbot.bankServices;

import tld.petbot.tgbot.rate.CurrencyRate;

import java.util.List;

public interface BankInterface {
    void saveCurrencyRateDaily();
    List<CurrencyRate> saveCurrencyRateFromJson(String strURL);
}
