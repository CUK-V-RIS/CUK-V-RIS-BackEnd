package team.togizi.cukvris.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vegan-res")
public class RestaurantJpaController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    // 0 ~ 24 : 지역
    // 25 ~ 31 : 비건단계
    // 32 ~ 41 : 음식점 타입
    String resFilterList[]=new String[]{"강남구", "강동구", "강서구", "강북구",
    "관악구", "광진구", "구로구", "금천구", "노원구", "동대문구", "도봉구", "동작구", "마포구",
    "서대문구", "성동구", "성북구", "서초구", "송파구", "영등포구", "용산구", "양천구",
    "은평구", "종로구", "중구", "중랑구",
    "비건", "락토", "오보", "락토 오보", "페스코", "폴로", "플렉시테리언",
    "한식", "분식", "카페", "베이커리", "양식", "술집", "인도", "중식", "동남아", "일식"};

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
    // 프론트에서 데이터 받기 Test(JSON)
    // 현재 진행상황 : 프론트에서 배열로 정수들 받아서 문자열로 반환까지
    // 이제 저 리스트 크기만큼 반복문 돌려서 쿼리문에 추가하면 됨 - 근데 동적쿼리 개어려움ㅜ
    // 문자열 리스트 1개 -> 지역, 비건단계, 음식점종류 3개로 나눔
    // 음식점 필터로 조회
    @GetMapping("/restaurant/filter")
    public List<String> searchByFilter(@RequestBody List<Integer> filteredValue){

        List<String> resFilterListArea = new ArrayList<>();
        List<String> resFilterListLevel = new ArrayList<>();
        List<String> resFilterListType = new ArrayList<>();
        for(Integer i : filteredValue){
            if(i <= 24)
                resFilterListArea.add(resFilterList[i]);
            else if(i <= 31)
                resFilterListLevel.add(resFilterList[i]);
            else if(i <= 41)
                resFilterListType.add(resFilterList[i]);
        }

        return resFilterListLevel;
    }
*/

}
