package team.togizi.cukvris.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    //List<Restaurant> findByResNameContains(String resName);

    // 음식점 이름으로 검색
    @Query("SELECT r FROM restaurant r WHERE r.resName LIKE %:resName%")
    List<Restaurant> findByResNameContains(@Param("resName") String resName);

    // 음식점 필터로 검색 (지역)
    @Query("Select r From restaurant r where r.resAddress LIKE %:area%")
    HashSet<Restaurant> findRestaurantByFilterArea(@Param("area") String area);

    // 음식점 필터로 검색 (비건레벨)
    @Query("select r from restaurant r where r.resMenu like %:level%")
    HashSet<Restaurant> findRestaurantByFilterLevel(@Param("level") String level);

    // 음식점 필터로 검색 (타입)
    @Query("select r from restaurant r where r.resCategory like %:type%")
    HashSet<Restaurant> findRestaurantByFilterType(@Param("type") String type);
}
