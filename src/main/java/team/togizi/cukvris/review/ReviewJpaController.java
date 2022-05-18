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

    // 공통 (0~5)        : 재료신선, 양 많, 맛있, 가격 착, 다음에도, 가성비 좋
    // 비건 (6~7)        : 선택지다양, 논비건과 함께
    // 논비건 (8)        :  논비건과 비슷


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
    @PostMapping("/Review")
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review){
        Review savedReview=reviewRepository.save(review);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{reviewIdx}")
                .buildAndExpand(savedReview.getReviewIdx())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    // 키워드 리뷰 조회


    // 키워드 리뷰 삭제


}
