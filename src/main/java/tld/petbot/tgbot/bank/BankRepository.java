package tld.petbot.tgbot.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankRepository extends JpaRepository<Bank, UUID> {
    @Query(value = "SELECT * FROM bank WHERE bank_name = ?1", nativeQuery = true)
    Bank findByName(String name);
}

