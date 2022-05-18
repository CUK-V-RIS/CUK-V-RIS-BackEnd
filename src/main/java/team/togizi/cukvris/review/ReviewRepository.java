package team.togizi.cukvris.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 최근 나의 리뷰 조회
    @Query(value = "Select new team.togizi.cukvris.review.MyReview(res.resName, rev.reviewCategory) From restaurant res, review rev where rev.resIdx=res.resIdx and rev.userIdx= :userIdx")
    List<MyReview> findByUserIdx(@Param("userIdx") Integer userIdx);

    // 음식점 전체 리뷰 조회
    @Query(value="select count(rev) from review rev where rev.resIdx= :resIdx and rev.reviewCategory= :reviewCategory")
    Integer retrieveAllReviews(Integer resIdx, Integer reviewCategory);

    /*
    // del
    @Query(value="delete from review rev where rev.resIdx= :resIdx and rev.userIdx= :userIdx")
    void deleteReviewByResIdxAndUserIdx(Integer resIdx, Integer userIdx);
    */

    // resIdx, userIdx로 리뷰 찾기(return Review)
    @Query(value="select rev from review rev where rev.resIdx= :resIdx and rev.userIdx= :userIdx")
    Review findReviewByResIdxAndUserIdx(Integer resIdx, Integer userIdx);
}
