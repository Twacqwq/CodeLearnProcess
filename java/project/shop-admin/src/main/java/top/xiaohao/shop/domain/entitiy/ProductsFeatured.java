package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ProductsFeatured)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsFeatured implements Serializable {
    //记录id
    @TableId
    private Integer recordId;
    //用户id
    private Integer userId;
    //商品id
    private Integer productId;
    //推荐分
    private Integer recommend;

}

