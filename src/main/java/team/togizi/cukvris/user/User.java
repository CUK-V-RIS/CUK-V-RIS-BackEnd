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

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date joinDate;

    //@Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer veganLevel;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

     */
    @Id
    @GeneratedValue
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
