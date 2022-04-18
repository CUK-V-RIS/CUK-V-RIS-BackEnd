package team.togizi.cukvris.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/vegan-res")
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public User loginId(@RequestBody User user) {

        Optional<User> findUser = loginService.login(user);

        if(!findUser.isPresent()){
            throw new UserNotFoundException(String.format("Check Your ID", user.getUserId()));
        }
        if(!findUser.get().getUserPwd().equals(user.getUserPwd())){
            throw new UserNotFoundException(String.format("Check Your Password", user.getUserPwd()));
        }

        return findUser.get();
    }

};