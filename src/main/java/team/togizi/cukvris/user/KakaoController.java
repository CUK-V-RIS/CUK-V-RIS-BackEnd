package team.togizi.cukvris.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/vegan-res")

public class KakaoController {
    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private UserRepository userRepository;

    private static String access_Token = "";
    /*@ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
    }

    */


    @ResponseBody
    @GetMapping("/kakao")
    public User createUserByKakao(@RequestParam String code, HttpSession session) {

        if(access_Token == "")
            access_Token = kakaoService.getKaKaoAccessToken(code);

        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }
        System.out.println("session_userId: " + session.getAttribute("userId"));
        System.out.println("session_access_Token: " + session.getAttribute("access_Token"));

        System.out.println(session);

        String userId = userInfo.get("nickname").toString() + userInfo.get("email").toString();

        User user = new User();
        user.setUserId(userId);
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

    }
    @ResponseBody
    @GetMapping("/logout")
    public Optional<User> access(HttpSession session) throws IOException {

        System.out.println(session);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);

        String userId = userInfo.get("nickname").toString() + userInfo.get("email").toString();
        Optional<User> findUser = userRepository.findByUserId(userId);

        String access_token = (String)session.getAttribute("access_Token");
        System.out.println("access_Token: " + access_token);
        Map<String, String> map = new HashMap<String, String>();
        map.put("Authorization", "Bearer "+ access_token);

        String result = kakaoService.HttpPostConnection("https://kapi.kakao.com/v1/user/logout", map).toString();
        System.out.println(result);

        access_Token = "";

        return findUser;
    }
    @GetMapping("/kakao/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }
}
