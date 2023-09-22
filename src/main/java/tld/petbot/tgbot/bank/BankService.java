package tld.petbot.tgbot.bank;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankService {
    private final BankRepository bankRepository;

    public Bank createBank(String name) {
        Bank bank = bankRepository.findByName(name);
        if (bank == null) {
            bank = new Bank();
            bank.setName(name);
            return bankRepository.save(bank);
        }
        return bank;
    }
}
