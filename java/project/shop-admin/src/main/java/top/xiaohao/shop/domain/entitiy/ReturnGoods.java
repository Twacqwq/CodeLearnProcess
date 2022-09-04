package top.xiaohao.shop.domain.entitiy;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品退货表(ReturnGoods)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnGoods implements Serializable {
    //退货id
    @TableId
    private Integer returnId;
    //申请时间
    private Date applyTime;
    //订单编号
    private String orderNo;
    //用户账号
    private String userNumber;
    //用户名称
    private String userName;
    //退款金额
    private Object returnPrice;
    //操作员账号
    private String operatorNumber;
    //操作员名称
    private String operatorName;
    //处理时间
    private Date dealTime;
    //退货原因
    private String returnReason;
    //退货状态
    private String returnState;

}

