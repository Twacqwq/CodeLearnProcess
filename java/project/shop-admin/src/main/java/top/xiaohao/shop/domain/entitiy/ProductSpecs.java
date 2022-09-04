package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品规格表(ProductSpecs)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecs implements Serializable {
    //商品id
    @TableId
    private Integer productId;
    //规格id
    private Integer specsId;

}

