package tld.petbot.tgbot.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {
    @Autowired
    private BankRepository bankRepository;


}
