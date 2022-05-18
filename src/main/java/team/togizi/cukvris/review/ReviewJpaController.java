package team.togizi.cukvris.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vegan-res")
public class ReviewJpaController {
    @Autowired
    private ReviewRepository reviewRepository;

    // 공통 (1~6)        : 재료신선, 양 많, 맛있, 가격 착, 다음에도, 가성비 좋
    // 비건 (7~8)        : 선택지다양, 논비건과 함께
    // 논비건 (9)        :  논비건과 비슷


    // 최근 나의 리뷰 조회
    @GetMapping("/{userIdx}/myReview")
    public List<MyReview> checkMyReview(@PathVariable int userIdx){

        List<MyReview> reviews=reviewRepository.findByUserIdx(userIdx);

        if(reviews.isEmpty()){
            throw new ReviewNotFoundException(String.format("작성한 리뷰가 없습니다."));
        }
        return reviews;
    }

    // 키워드 리뷰 생성
    /*
    {
        "userIdx" : 1,
        "resIdx" : 100,
        "reviewCategory" : 8
    }
    */
    // @Valid 이용 유효성 검사
    @PostMapping("/review")
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review){
        Review savedReview=reviewRepository.save(review);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{reviewIdx}")
                .buildAndExpand(savedReview.getReviewIdx())
                .toUri();

        // 새로운 리뷰 생성되면 상태 코드에 200 OK 아닌 201 Created
        return ResponseEntity.created(location).build();

    }


    // 키워드 리뷰 조회
    @GetMapping("/{resIdx}/review")
    public ReviewCategory retrieveReview(@PathVariable int resIdx){

        ReviewCategory reviewCategory=new ReviewCategory();

        reviewCategory.setReview1_fresh(reviewRepository.retrieveAllReviews(resIdx, 1));
        reviewCategory.setReview2_amount(reviewRepository.retrieveAllReviews(resIdx, 2));
        reviewCategory.setReview3_taste(reviewRepository.retrieveAllReviews(resIdx, 3));
        reviewCategory.setReview4_price(reviewRepository.retrieveAllReviews(resIdx, 4));
        reviewCategory.setReview5_next(reviewRepository.retrieveAllReviews(resIdx, 5));
        reviewCategory.setReview6_costPerform(reviewRepository.retrieveAllReviews(resIdx, 6));
        reviewCategory.setReview7_option(reviewRepository.retrieveAllReviews(resIdx, 7));
        reviewCategory.setReview8_withNone(reviewRepository.retrieveAllReviews(resIdx, 8));
        reviewCategory.setReview9_similar(reviewRepository.retrieveAllReviews(resIdx, 9));

        return reviewCategory;
    }

    // 키워드 리뷰 삭제


}
