package co.develhope.loginDemo1.user.controllers;

import co.develhope.loginDemo1.auth.entities.LoginRTO;
import co.develhope.loginDemo1.auth.services.LoginService;
import co.develhope.loginDemo1.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    LoginService loginService;

    @GetMapping("/profile")
    public LoginRTO getProfile(Principal principal) throws UnsupportedEncodingException {
        User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        LoginRTO rto = new LoginRTO();
        rto.setUser(user);
        rto.setJWT(loginService.getJWT(user));
        return rto;
    }

}
