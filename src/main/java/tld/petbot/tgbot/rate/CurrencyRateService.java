package tld.petbot.tgbot.rate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tld.petbot.tgbot.bank.Bank;
import tld.petbot.tgbot.bank.BankRepository;
import tld.petbot.tgbot.currency.Currency;
import tld.petbot.tgbot.currency.CurrencyRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyRateService {
    private final CurrencyRepository currencyRepository;
    private final BankRepository bankRepository;
    private final CurrencyRateRepository currencyRateRepository;

    public CurrencyRate createCurrencyRate(Bank bank, Currency currency,
                                           BigDecimal setBuy, BigDecimal setSale,
                                           Date date) {
        CurrencyRate currencyRate = new CurrencyRate();

        currencyRate.setBank(bank);
        currencyRate.setCurrency(currency);
        currencyRate.setBuy(setBuy);
        currencyRate.setSale(setSale);
        currencyRate.setDate(date);

        return currencyRateRepository.save(currencyRate);
    }

    public List<Object[]> getExchangeRatesByCurrencyAndBank(String currencyCode, String bankName) {
        Currency currency = currencyRepository.findByCurrencyCode(currencyCode);
        Bank bank;
        bank = bankRepository.findByName(bankName);
        if (currency != null && bank != null) {
            return currencyRateRepository.findByCurrencyAndBank(currencyCode, bankName);
        }
        return Collections.emptyList();
    }
}

