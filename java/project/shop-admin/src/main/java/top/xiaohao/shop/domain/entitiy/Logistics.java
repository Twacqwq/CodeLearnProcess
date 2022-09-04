package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物流表(Logistics)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logistics implements Serializable {
    //物流id
    @TableId
    private Integer logisticId;
    //订单编号
    private String orderNo;
    //发货人
    private String sender;
    //发货人联系方式
    private String senderTel;
    //发货人联系地址
    private String senderAdd;
    //收货人
    private String receiver;
    //收货人联系方式
    private String receiverTel;
    //收货人联系地址
    private String receiverAdd;
    //物流公司
    private String parcelName;

}

