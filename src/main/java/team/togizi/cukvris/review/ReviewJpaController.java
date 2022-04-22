package team.togizi.cukvris.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vegan-res")
public class ReviewJpaController {
    @Autowired
    private ReviewRepository reviewRepository;

    // 최근 나의 리뷰 조회
    @GetMapping("/{userIdx}/myReview")
    public List<Review> checkMyReview(@PathVariable int userIdx){

        List<Review> reviews=reviewRepository.findAll();

        if(reviews.isEmpty()){
            throw new ReviewNotFoundException(String.format("작성한 리뷰가 없습니다."));
        }
        return reviews;



    }
}
