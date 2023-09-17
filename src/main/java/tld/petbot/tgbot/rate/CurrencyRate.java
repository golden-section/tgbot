package tld.petbot.tgbot.rate;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tld.petbot.tgbot.bank.Bank;
import tld.petbot.tgbot.currency.Currency;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency_rate")
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "currency_buy", nullable = false)
    private BigDecimal buy;

    @Column(name = "currency_sale", nullable = false)
    private BigDecimal sale;

    @Column(name = "exchange_date", nullable = false)
    private Date date;
}
