package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品类别表(ProductType)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductType implements Serializable {
    //类别id
    @TableId
    private Integer typeId;
    //类别名称
    private String typeName;
    //类别描述
    private String typeDescribe;
    //左侧宣传图
    private String typeUrlLeft;
    //横幅宣传图
    private String typeUrlTop;

}

