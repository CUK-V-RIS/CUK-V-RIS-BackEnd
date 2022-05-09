package team.togizi.cukvris.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/vegan-res")

public class KakaoLoginController {
    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @Autowired
    private UserRepository userRepository;

    /*@ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
    }

    */


    @ResponseBody
    @GetMapping("/kakao")
    public User createUserByKakao(@RequestParam String code) {

        String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        HashMap<String, Object> userInfo = kakaoLoginService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);

        User user = new User();
        user.setUserId(userInfo.get("nickname").toString());
        user.setEmail(userInfo.get("email").toString());

        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        if(!findUser.isPresent())
        {
            user.setUserRole(1); // user 권한 부여
            User savedUser = userRepository.save(user);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUser.getUserIdx()) // savedUser의 id를 path로 지정한다 뭐 그런거 같음,,
                    .toUri(); // Uri화 함

            ResponseEntity.created(location).build();

            return savedUser;
        }
        else
            return findUser.get();
        /*//    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }*/
    }
    @GetMapping("/kakao/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }
}
