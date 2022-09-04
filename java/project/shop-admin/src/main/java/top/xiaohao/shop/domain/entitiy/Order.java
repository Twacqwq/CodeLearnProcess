package top.xiaohao.shop.domain.entitiy;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单表(Order)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    //订单id
    @TableId
    private Integer orderId;
    //订单编号
    private String orderNo;
    //下单时间
    private Date orderTime;
    //商品编号
    private String productNo;
    //商品规格
    private String productSpecs;
    //用户账号
    private String userAccount;
    //用户名称
    private String userName;
    //联系方式
    private String contactWay;
    //商品金额
    private Object payPrice;
    //购买数量
    private Integer payAmount;
    //支付方式
    private String payType;
    //订单来源
    private String orderFrom;
    //订单状态
    private String orderState;
    //收货地址
    private String acceptAddress;
    //退货状态
    private Integer returnState;

}

