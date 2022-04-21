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
    
    @Query("SELECT r FROM restaurant r WHERE r.resName LIKE %:resName%")
    List<Restaurant> findByResNameContains(@Param("resName") String resName);
}
