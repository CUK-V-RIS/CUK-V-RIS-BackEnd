package team.togizi.cukvris.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    //List<Restaurant> findByResNameContains(String resName);

    // 음식점 이름으로 검색
    @Query("SELECT r FROM restaurant r WHERE r.resName LIKE %:resName%")
    List<Restaurant> findByResNameContains(@Param("resName") String resName);

    // 음식점 필터로 검색 (지역)
    // 수정해야됨
    @Query("select r from restaurant r where r.resAddress in (:area)")
    List<Restaurant> findRestaurantByFilterArea(@Param("area") List<String> resFilterListArea);

}
