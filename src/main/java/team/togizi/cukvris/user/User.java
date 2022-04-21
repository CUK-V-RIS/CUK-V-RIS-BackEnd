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
}
