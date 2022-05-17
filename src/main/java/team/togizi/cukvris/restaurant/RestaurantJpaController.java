package team.togizi.cukvris.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
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
    public HashSet<Restaurant> searchByFilter(@RequestBody List<Integer> filteredValue){

        List<Restaurant> resFilterListFinal=new ArrayList<>();

        List<String> resFilterListArea = new ArrayList<>();
        List<String> resFilterListLevel = new ArrayList<>();
        List<String> resFilterListType = new ArrayList<>();

        HashSet<Restaurant> hashListArea=new HashSet<>();
        HashSet<Restaurant> hashListLevel=new HashSet<>();
        HashSet<Restaurant> hashListType=new HashSet<>();


        // List<Integer>에 있는 값들 분류해서 String으로 변환
        for(Integer i : filteredValue){
            if(i <= 24)
                resFilterListArea.add(resFilterList[i]);
            else if(i <= 31)
                resFilterListLevel.add(resFilterList[i]);
            else if(i <= 41)
                resFilterListType.add(resFilterList[i]);
        }


        // List<String> 비어있으면 모든 값 넣기
        if(resFilterListArea.isEmpty()){
            for(int i=0 ; i<=24 ; i++)
                resFilterListArea.add(resFilterList[i]);
        }
        if(resFilterListLevel.isEmpty()){
            for(int i=25 ; i<=31 ; i++)
                resFilterListLevel.add(resFilterList[i]);
        }
        if(resFilterListType.isEmpty()){
            for(int i=32 ; i<=41 ; i++)
                resFilterListType.add(resFilterList[i]);
        }


        // List에 있는 값들 하나씩 검색해서 HashSet에 합집합
        for(String str : resFilterListArea){
            HashSet<Restaurant> hashTmp=restaurantRepository.findRestaurantByFilterArea(str);
            hashListArea.addAll(hashTmp);
        }
        for(String str : resFilterListLevel){
            HashSet<Restaurant> hashTmp=restaurantRepository.findRestaurantByFilterLevel(str);
            hashListLevel.addAll(hashTmp);
        }
        for(String str : resFilterListType){
            HashSet<Restaurant> hashTmp=restaurantRepository.findRestaurantByFilterType(str);
            hashListType.addAll(hashTmp);
        }


        // 3개의 HashSet 교집합
        hashListArea.retainAll(hashListLevel);
        hashListArea.retainAll(hashListType);

        return hashListArea;
    }



}
