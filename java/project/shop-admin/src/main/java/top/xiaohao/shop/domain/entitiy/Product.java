package top.xiaohao.shop.domain.entitiy;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品表(Product)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    //商品id
    @TableId
    private Integer productId;
    //商品编号
    private String productNo;
    //商品名称
    private String productName;
    //商品类别
    private String productType;
    //副标题
    private String productDescribe;
    //品牌
    private String productBrand;
    //进价
    private Object inPrice;
    //售价
    private Object outPrice;
    //库存
    private Integer productStock;
    //最低库存
    private Integer lowestStock;
    //是否缺货
    private Integer isStockout;
    //是否新品
    private Integer isNew;
    //是否上架
    private Integer isSale;
    //上架时间
    private Date saleTime;
    //商品图片
    private String productUrl;

}

