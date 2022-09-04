package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品广告轮播图(Banner)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner implements Serializable {
    //商品广告牌id
    @TableId
    private Integer bannerId;
    //商品名称
    private String productName;
    //商品链接
    private String productUrl;
    //广告宣传栏链接
    private String bannerUrl;

}

