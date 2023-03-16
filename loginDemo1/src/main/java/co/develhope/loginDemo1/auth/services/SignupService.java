package co.develhope.loginDemo1.auth.services;

import co.develhope.loginDemo1.auth.entities.SignupActivationDTO;
import co.develhope.loginDemo1.auth.entities.SignupDTO;
import co.develhope.loginDemo1.notification.services.NotificationService;
import co.develhope.loginDemo1.user.entities.Role;
import co.develhope.loginDemo1.user.entities.User;
import co.develhope.loginDemo1.user.repositories.RoleRepository;
import co.develhope.loginDemo1.user.repositories.UserRepository;
import co.develhope.loginDemo1.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;



    public User signUp(SignupDTO signupDTO) throws Exception {

        User user = new User();
        user.setName(signupDTO.getName());
        user.setSurname(signupDTO.getSurname());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
      Optional<Role> userRole = roleRepository.findByName(Roles.REGISTERED);
       if (!userRole.isPresent()) throw new Exception("Can not set user role");
       roles.add(userRole.get());
      user.setRoles(roles);

        notificationService.sendActivationMail(user);
        return userRepository.save(user);

    }

    public User activate(SignupActivationDTO signupActivationDTO) throws Exception {

        User user = userRepository.getByActivationCode(signupActivationDTO.getActivationCode());

        if (user == null) throw new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);

        return userRepository.save(user);

    }
}
