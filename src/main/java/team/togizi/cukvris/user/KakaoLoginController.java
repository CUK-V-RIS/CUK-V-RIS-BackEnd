package team.togizi.cukvris.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

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

    /*@ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
    }*/
    @ResponseBody
    @GetMapping("/kakao")
    public void login(@RequestParam String code, HttpSession session) {
        /*String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);*/

        String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        HashMap<String, Object> userInfo = kakaoLoginService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }
    }
}
