package org.sarthak.accounts.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.sarthak.accounts.constants.AccountsConstants;
import org.sarthak.accounts.dto.AccountsDto;
import org.sarthak.accounts.dto.CustomerDto;
import org.sarthak.accounts.entity.Accounts;
import org.sarthak.accounts.entity.Customer;
import org.sarthak.accounts.exception.CustomerAlreadyExistsException;
import org.sarthak.accounts.exception.ResourceNotFoundException;
import org.sarthak.accounts.mapper.AccountsMapper;
import org.sarthak.accounts.mapper.CustomerMapper;
import org.sarthak.accounts.repository.AccountsRepository;
import org.sarthak.accounts.repository.CustomerRepository;
import org.sarthak.accounts.service.IAccountsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl  implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomer(customer);
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        String[] accountTypes = {AccountsConstants.SAVINGS, AccountsConstants.CURRENT, AccountsConstants.FIXED_DEPOSIT};
        String randomAccountType = accountTypes[new Random().nextInt(accountTypes.length)];
        newAccount.setAccountType(randomAccountType);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomer(customer).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomer().getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomer(customer);
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
