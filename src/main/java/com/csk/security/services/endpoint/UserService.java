package com.csk.security.services.endpoint;

import com.csk.security.services.entity.User;
import com.csk.security.services.exception.BusinessValidationException;
import com.csk.security.services.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean registerUser(User user) {

        var userRecord = userRepository.findByUserName(user.getUserName());

        if (userRecord.isPresent()) {

            log.error("User already exists with userName ", user.getUserName());
            throw new BusinessValidationException("User already exists", "email");
        }

        var encodedPassword = passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPassword);
        user.getAuthorities().forEach(authority -> authority.setUser(user));

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {

            log.error("Exception occurred while user registration ", e.getMessage());
        }
        return false;
    }
}
