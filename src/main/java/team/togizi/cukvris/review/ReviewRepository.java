package team.togizi.cukvris.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 최근 나의 리뷰 조회
    @Query(value = "Select new team.togizi.cukvris.review.MyReview(res.resName, rev.reviewCategory) From restaurant res, review rev where rev.resIdx=res.resIdx and rev.userIdx= :userIdx")
    List<MyReview> findByUserIdx(@Param("userIdx") Integer userIdx);
}
