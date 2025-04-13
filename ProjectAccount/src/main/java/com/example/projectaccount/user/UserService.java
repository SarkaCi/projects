package com.example.projectaccount.user;

import com.example.projectaccount.auditLog.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;

    private final AuditLogService auditLogService;

    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
        auditLogService.saveUser(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any user with ID" + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Optional<User> user = repo.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Could not find any user with ID" + id);

        }
        repo.deleteById(id);
        auditLogService.deleteUser(user.get());
    }

    public List<User> getUsersForAccount(Integer accountId) {
        return repo.findByAccountId(accountId);
    }


}
