package co.develhope.loginDemo1.auth.services;

import co.develhope.loginDemo1.auth.entities.RequestPasswordDTO;
import co.develhope.loginDemo1.auth.entities.RestorePasswordDTO;
import co.develhope.loginDemo1.notification.services.NotificationService;
import co.develhope.loginDemo1.user.entities.User;
import co.develhope.loginDemo1.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void request(RequestPasswordDTO requestPasswordDTO) throws Exception {

        User userFromDB=userRepository.findByEmail(requestPasswordDTO.getEmail());
        if (userFromDB == null) throw new Exception("user is null");
        userFromDB.setPasswordResetCode(UUID.randomUUID().toString());

        notificationService.sendPasswordResetMail(userFromDB);
        userRepository.save(userFromDB);
    }

    public void restore(RestorePasswordDTO restorePasswordDTO) throws Exception {
      User userFromDB = userRepository.findByPasswordResetCode(restorePasswordDTO.getResetPasswordCode());
        if (userFromDB == null) throw new Exception("user is null");

        userFromDB.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userFromDB.setPasswordResetCode(null);

        userFromDB.setActive(true);
        userFromDB.setActivationCode(null);

        userRepository.save(userFromDB);

    }
}
