package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";
    private static long id = 0;

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password, String passwordConfirmation) throws ValidationException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Password Confirmation wrong");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Login is already in use");
        }
        if (!user.getEmail().matches("^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(?:\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+" +
                ")*@(?:[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(" +
                "?:aero|arpa|asia|biz|cat|com|coop|edu" +
                "|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$")) {
            throw new ValidationException("Email is incorrect");
        }
    }

    public void register(User user, String password) {
        user.setPasswordSha1(Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString());
        user.setId(id++);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public long findCount() {
        return userRepository.findCount();
    }

    public User validateUser(User user, String password) throws ValidationException {
        String login = user.getLogin();
        String passwordSha = Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString();
        List<User> users = findAll();
        for (User u : users) {
            if (u.getLogin().equals(login)) {
                if (passwordSha.equals(u.getPasswordSha1())) {
                    return u;
                } else {
                    throw new ValidationException("Incorrect password");
                }
            }
        }
        throw new ValidationException("Cannot find user with name " + login);
    }

    public String find(long id) {
        return userRepository.find(id);
    }
}
