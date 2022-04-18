package team.togizi.cukvris.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    @Autowired
    private final UserRepository userRepository;

    public Optional<User> login(User user) {
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());
        return findUser;
    }
};
