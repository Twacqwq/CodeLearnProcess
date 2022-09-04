package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品品牌表(ProductBrand)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBrand implements Serializable {
    //品牌id
    @TableId
    private Integer brandId;
    //品牌名称
    private String brandName;
    //品牌描述
    private String brandDescribe;

}

