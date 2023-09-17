package tld.petbot.tgbot.bankServices;

public interface BankService {
    void saveCurrencyRateDaily();
    void saveCurrencyRateFromJson(String strURL);
}
