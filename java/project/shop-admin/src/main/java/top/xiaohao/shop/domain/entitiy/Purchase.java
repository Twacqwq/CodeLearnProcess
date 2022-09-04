package top.xiaohao.shop.domain.entitiy;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 采购表(Purchase)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase implements Serializable {
    //采购id
    @TableId
    private Integer purchaseId;
    //采购编号
    private String purchaseNo;
    //进货数量
    private Integer purchaseNumber;
    //进货时间
    private Date purchaseTime;
    //收货时间
    private Date receiptTime;
    //商品编号
    private String productNo;
    //商品名称
    private String productName;
    //供应商编号
    private String supplierNo;
    //供应商名称
    private String supplierName;
    //操作员编号
    private String accountNumber;
    //操作员名称
    private String userName;
    //收货状态
    private Integer receiptStatus;

}

