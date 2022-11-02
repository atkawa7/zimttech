package com.example.demo.services;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Constraint;
import am.ik.yavi.core.Validator;
import com.example.demo.api.AccountService;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.dto.CreateAccount;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SuccessOrFailure;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demo.utils.RandomUtils.randomBytes;
import static java.util.Base64.getUrlEncoder;
import static java.util.regex.Pattern.compile;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private final Validator<CreateAccount> validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public AccountServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.validator = ValidatorBuilder
                .<CreateAccount>of()
                .constraint(CreateAccount::getFirstName, "firstName", c -> c.notBlank().greaterThan(2).lessThanOrEqual(50))
                .constraint(CreateAccount::getLastName, "lastName", c -> c.notBlank().greaterThan(2).lessThanOrEqual(50))
                .constraint(CreateAccount::getUsername, "username", c -> c.notBlank().greaterThan(2).lessThanOrEqual(50).pattern(compile("[A-Za-z0-9]+")))
                .constraint(CreateAccount::getPassword, "password", c -> c.notBlank().greaterThanOrEqual(8).lessThanOrEqual(64))
                .constraintOnObject(CreateAccount::getRole, "role", Constraint::notNull)
                .build();
    }

    @Override
    public SuccessOrFailure createToken(LoginRequest loginRequest) {
        var user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new SuccessOrFailure().setResult(user.getToken());
        }
        return new SuccessOrFailure().setSuccess(false).setMessage("Failed to authenticate user");
    }

    @Override
    public SuccessOrFailure createAccount(CreateAccount createAccount) {
        var violations = validator.validate(createAccount);
        if (violations.isEmpty()) {
            var result = userRepository.findByUsername(createAccount.getUsername());
            if (result != null) {
                return new SuccessOrFailure()
                        .setSuccess(false)
                        .setMessage("User exists");
            }

            var user = new User();
            user.setFirstName(createAccount.getFirstName());
            user.setLastName(createAccount.getLastName());
            user.setUsername(createAccount.getUsername());
            user.setPassword(passwordEncoder.encode(createAccount.getPassword()));
            user.setRole(createAccount.getRole());
            user.setToken(getUrlEncoder().encodeToString(randomBytes(64)));

            user = userRepository.save(user);

            return new SuccessOrFailure().setResult(user);
        }
        return new SuccessOrFailure()
                .setSuccess(false)
                .setMessage("Failed to validate create account");
    }
}
