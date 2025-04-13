package com.example.projectaccount.account;

import com.example.projectaccount.auditLog.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repo;

    private final AuditLogService auditLogService;

    public List<Account> listAll() {
        return (List<Account>) repo.findAll();
    }

    public void save(Account account) {
        repo.save(account);
        auditLogService.saveAccount(account);
    }

    public Account get(Integer id) throws AccountNotFoundException {
        Optional<Account> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AccountNotFoundException("Could not find any account with ID" + id);
    }

    public void delete(Integer id) throws AccountNotFoundException {
        Optional<Account> account = repo.findById(id);
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Could not find any account with ID" + id);

        }
        repo.deleteById(id);
        auditLogService.deleteAccount(account.get());
    }

    public Account getAccountById(Integer id) throws AccountNotFoundException {
        Optional<Account> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AccountNotFoundException("Could not find any account with ID" + id);
    }

}
