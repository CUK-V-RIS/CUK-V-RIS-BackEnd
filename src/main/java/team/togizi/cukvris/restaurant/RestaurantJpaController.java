package team.togizi.cukvris.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/restaurantName/{resName}")
    public List<Restaurant> searchByResName(@PathVariable String resName){
        //List<Restaurant> restaurants=restaurantRepository.findByResNameContains(resName);

        List<Restaurant> resList=restaurantRepository.findByResNameContains(resName);
        if(resList.isEmpty()){
            throw new ResNotFoundException(String.format("[%s] not found", resName));
        }
        return resList;
    }



    // 음식점 필터로 조회
    @GetMapping("/restaurant/filter")
    public List<Restaurant> searchByFilter(@RequestBody List<Integer> filteredValue){

        // ***findByResNameContains 부분 수정하기***
        List<Restaurant> resFilterList=restaurantRepository.findByResNameContains("");

        if(resFilterList.isEmpty()){
            throw new ResNotFoundException(String.format("[%s] not found", filteredValue));
        }

        return resFilterList;
    }


    /*
    프론트에서 데이터 받기 (JSON)
    // 음식점 필터로 조회
    @GetMapping("/restaurant/filter")
    public List<Integer> searchByFilter(@RequestBody List<Integer> filteredValue){

        List<Integer> resFilterList=filteredValue;
        return resFilterList;
    }
    */

}
