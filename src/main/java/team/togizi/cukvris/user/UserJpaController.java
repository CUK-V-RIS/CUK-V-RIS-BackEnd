package team.togizi.cukvris.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    // 사용자 추가
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
}
