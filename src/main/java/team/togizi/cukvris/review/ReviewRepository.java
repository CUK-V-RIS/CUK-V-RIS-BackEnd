package team.togizi.cukvris.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query(value = "Select restaurant.res_name, review.review_category" +
            " From restaurant, review " +
            "where review.res_idx=restaurant.res_idx and review.user_idx= :userIdx", nativeQuery = true)
    List<Object[]> findByUserIdx(@Param("userIdx") Integer userIdx);
}
