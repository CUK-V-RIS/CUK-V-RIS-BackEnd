package team.togizi.cukvris.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCategory {
    private Integer review1_fresh;
    private Integer review2_amount;
    private Integer review3_taste;
    private Integer review4_price;
    private Integer review5_next;
    private Integer review6_costPerform;
    private Integer review7_option;
    private Integer review8_withNone;
    private Integer review9_similar;
}
