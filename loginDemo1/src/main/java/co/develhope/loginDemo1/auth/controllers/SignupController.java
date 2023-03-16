package co.develhope.loginDemo1.auth.controllers;

import co.develhope.loginDemo1.auth.entities.SignupActivationDTO;
import co.develhope.loginDemo1.auth.entities.SignupDTO;
import co.develhope.loginDemo1.auth.services.SignupService;
import co.develhope.loginDemo1.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class SignupController {

    @Autowired
    SignupService signupService;

    @PostMapping("/signup")
    public User signUp(@RequestBody SignupDTO signupDTO ) throws Exception {
        return  signupService.signUp(signupDTO);
    }

    @PostMapping("/signup/activation")
    public User activation(@RequestBody  SignupActivationDTO signupActivationDTO) throws Exception {
        return signupService.activate(signupActivationDTO);
    }
}
