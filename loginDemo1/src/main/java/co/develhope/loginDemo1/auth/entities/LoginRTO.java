package co.develhope.loginDemo1.auth.entities;

import co.develhope.loginDemo1.user.entities.User;
import lombok.Data;

@Data
public class LoginRTO {
    User user;
    String JWT;
}
