package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品规格表(Specs)表实体类
 *
 * @author Twac
 * @since 2022-05-26 15:34:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specs implements Serializable {
    //规格id
    @TableId
    private Integer specsId;
    //规格类型
    private String specsName;
    //商品类别
    private String productType;

}

