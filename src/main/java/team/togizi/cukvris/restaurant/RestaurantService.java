package team.togizi.cukvris.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    @Autowired
    private final RestaurantRepository restaurantRepository;

    public Optional<Restaurant> searchByName(String resName){

        Optional<Restaurant> resList=restaurantRepository.findByResNameContains(resName);
        return resList;
    }
}
