package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退货原因表(ReturnReason)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnReason implements Serializable {
    //退货id
    @TableId
    private Integer reasonId;
    //退货理由
    private String reasonName;
    //是否启用
    private Integer status;

}

