package team.togizi.cukvris.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void login(@RequestParam String code) {
        String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
    }
}
