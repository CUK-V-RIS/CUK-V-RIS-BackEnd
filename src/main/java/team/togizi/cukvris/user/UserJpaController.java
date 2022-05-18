package team.togizi.cukvris.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vegan-res")
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    // 전체 사용자 조회
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }


    // 개별 사용자 조회
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user.get(); // .get()은 Optional에서 가져옴
    }

    // 사용자 추가(회원 가입)
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)
    {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getUserIdx()) // savedUser의 id를 path로 지정한다 뭐 그런거 같음,,
                .toUri(); // Uri화 함

        return ResponseEntity.created(location).build();
    }
    // 회원 정보 수정 (비건 레벨) + 로그인 이후 이거로 비건레벨 받으면 됨, 프론트에서 User로 묶어서 @RequestBody로 주면 됨
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        User storedUser = optionalUser.get();
        ///수정할 정보 입력
        //storedUser.setUserId(user.getUserId()); // 불변값
        //storedUser.setUserPwd(user.getUserPwd()); // null
        storedUser.setVeganLevel(user.getVeganLevel());
        //storedUser.setEmail(user.getEmail()); // 불변값

        User updatedUser = userRepository.save(storedUser);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedUser.getUserIdx())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    //회원 탈퇴
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}
