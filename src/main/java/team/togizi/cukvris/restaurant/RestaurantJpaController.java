package team.togizi.cukvris.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vegan-res")
public class RestaurantJpaController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    // 음식점 한 개 조회
    @GetMapping("/restaurant/{id}")
    public Restaurant retrieveRes(@PathVariable int id){
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        return restaurant.get();
    }


    // 음식점 이름으로 검색
    //@GetMapping("/restaurant/{resName}")



}
