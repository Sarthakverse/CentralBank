package org.sarthak.accounts.repository;

import jakarta.transaction.Transactional;
import org.sarthak.accounts.entity.Accounts;
import org.sarthak.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByCustomer(Customer customer);

    @Transactional
    @Modifying
    void deleteByCustomer(Customer customer);

}