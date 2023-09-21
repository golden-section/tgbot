package tld.petbot.tgbot.currency;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public Currency createCurrency(String code) {
        Currency currency = currencyRepository.findByCurrencyCode(code);
        if (currency == null) {
            currency = new Currency();
            currency.setCode(code);
            return currencyRepository.save(currency);
        }
        return currency;
    }
}
