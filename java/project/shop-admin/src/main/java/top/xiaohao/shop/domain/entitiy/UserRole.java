package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (UserRole)表实体类
 *
 * @author Twac
 * @since 2022-05-19 23:28:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
    //用户ID
    @TableId
    private Integer userId;
    //角色ID
    private Integer roleId;

}

