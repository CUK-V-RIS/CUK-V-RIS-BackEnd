package team.togizi.cukvris.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="user")
public class User {

    @Id     // primary key 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // 자동 증가
    private Integer userIdx;

    @Column(name = "userId")
    private String userId;

    @Column(name = "userPwd")
    private String userPwd;

    @Column(name = "email")
    private String email;

    @Column(name = "veganLevel")
    private Integer veganLevel;

    @Column(name = "userRole") // enum 쓰지말래서 0이면 접근 권한 x, 1이면 user, 2면 admin 뭐 이런식..
    private Integer userRole;
}
