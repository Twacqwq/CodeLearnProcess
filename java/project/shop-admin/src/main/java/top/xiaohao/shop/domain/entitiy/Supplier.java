package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 供应商表(Supplier)表实体类
 *
 * @author Twac
 * @since 2022-05-20 01:42:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements Serializable {
    //供应商id
    @TableId
    private Integer supplierId;
    //供应商编号
    private String supplierNo;
    //供应商名称
    private String supplierName;
    //商品类别
    private String productType;
    //负责人
    private String principal;
    //联系方式
    private String contactWay;
    //是否可用
    private Integer status;

}

