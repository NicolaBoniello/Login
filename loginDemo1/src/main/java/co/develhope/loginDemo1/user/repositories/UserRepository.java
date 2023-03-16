package co.develhope.loginDemo1.user.repositories;

import co.develhope.loginDemo1.auth.entities.SignupActivationDTO;
import co.develhope.loginDemo1.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User getByActivationCode(String  activationCode);

    User findByPasswordResetCode(String resetPasswordCode);

}
