package tld.petbot.tgbot.rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, CurrencyRateService> {
    @Query(value = "SELECT currency_buy, currency_sale " +
            "FROM currency_rate AS cr " +
            "JOIN currency AS c ON cr.currency_id = c.id " +
            "JOIN bank AS b ON cr.bank_id = b.id " +
            "WHERE c.currency_code = ?1 " +
            "AND b.bank_name = ?2", nativeQuery = true)
    List<Object[]> findByCurrencyAndBank(String currencyCode, String bankName);
}
