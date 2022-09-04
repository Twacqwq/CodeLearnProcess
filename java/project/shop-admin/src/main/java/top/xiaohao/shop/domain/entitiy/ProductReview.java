package top.xiaohao.shop.domain.entitiy;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品评价(ProductReview)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview implements Serializable {
    //用户评论id
    @TableId
    private Integer reviewId;
    //用户帐号
    private String accountNumber;
    //商品编号
    private String productNo;
    //商品规格
    private String productSpecs;
    //订单编号
    private String orderNo;
    //评论时间
    private Date reviewTime;
    //商品评星
    private Object starLevel;
    //商品评价
    private String productReview;

}

