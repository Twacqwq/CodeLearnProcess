package top.xiaohao.shop.domain.vo;

import lombok.Data;

@Data
public class SupplierVO {

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
    private boolean status;
}
