package co.develhope.loginDemo1.auth.services;

import co.develhope.loginDemo1.auth.entities.LoginDTO;
import co.develhope.loginDemo1.auth.entities.LoginRTO;
import co.develhope.loginDemo1.user.entities.Role;
import co.develhope.loginDemo1.user.entities.User;
import co.develhope.loginDemo1.user.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class LoginService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    public static final String JWT_SECRET = "1f0b5c8e-bc4b-4d20-bc8f-f7d3aebd5d31";


    public LoginRTO login(LoginDTO loginDTO) throws Exception {
      User userFromDB = userRepository.findByEmail(loginDTO.getEmail());
         if (userFromDB == null || !userFromDB.isActive()) return null;

         boolean canLogin = this.canUserLogin(userFromDB, loginDTO.getPassword());
         if (!canLogin) return null;

         String JWT = getJWT(userFromDB);
         userFromDB.setJwtCreatedOn(LocalDateTime.now());
         userRepository.save(userFromDB);

         userFromDB.setPassword(null);
         LoginRTO out = new LoginRTO();
         out.setJWT(JWT);
         out.setUser(userFromDB);

         return out;
    }

    public  boolean canUserLogin(User user, String password){
      return passwordEncoder.matches(password, user.getPassword());

    }

    public  String getJWT(User user) throws UnsupportedEncodingException {

        Date expireDate = Date.from(LocalDateTime.now().plusDays(15)
                .atZone(ZoneId.systemDefault())
                .toInstant());

       return JWT.create()
               .withIssuer("Nicola")
               .withIssuedAt(new Date())
               .withExpiresAt(expireDate)
               .withClaim("id", user.getId())
               .withClaim("roles", String.join(",",user.getRoles().stream().map(Role::getName).toArray(String[]::new)))
               .sign(Algorithm.HMAC512(JWT_SECRET));
    }
}
