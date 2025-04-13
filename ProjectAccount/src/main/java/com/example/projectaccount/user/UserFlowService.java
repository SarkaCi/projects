package com.example.projectaccount.user;

import org.springframework.ui.Model;
import com.example.projectaccount.SessionService;
import com.example.projectaccount.account.Account;
import com.example.projectaccount.account.AccountService;
import com.example.projectaccount.auditLog.AuditLogService;
import com.example.projectaccount.userAddress.UserAddress;
import com.example.projectaccount.userAddress.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.projectaccount.account.AccountNotFoundException;

import java.time.LocalDateTime;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@RequiredArgsConstructor
public class UserFlowService {

    private final UserService userService;

    private final AccountService accountService;

    private final UserDaoMapper userDaoMapper;

    private final UserAddressService userAddressService;

    private final SessionService sessionService;

    private final AuditLogService auditLogService;

    public String saveUser(UserDto userDto, Integer accountId, RedirectAttributes ra, Model model) {
        try {
            User user = null;
            UserAddress userAddress = userAddressService.save(userDto.getUserAddress());
            Account account = null;
            if (userDto.getId() == null) {
                account = accountService.getAccountById(accountId);
                user = userDaoMapper.toEntity(userDto, account, userAddress, sessionService.getSessionUserId(), LocalDateTime.now());
            } else {
                user = userService.get(userDto.getId());

                String password = (userDto.getPassword() == null) ? user.getPassword() : userDto.getPassword();
                userDaoMapper.toUpdateEntity(userDto, password, user, sessionService.getSessionUserId(), LocalDateTime.now(), userAddress);
            }

            auditLogService.saveUserAddress(userAddress, user);
            userService.save(user);

            ra.addFlashAttribute("message", "The user has been saved successfully.");
            return "success";

        } catch (AccountNotFoundException | UserNotFoundException e) {
            ra.addFlashAttribute("message", "Error: " + e.getMessage());
            return "error";
        }
    }
}
