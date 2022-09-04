package top.xiaohao.shop.domain.entitiy;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Role)表实体类
 *
 * @author Twac
 * @since 2022-05-15 14:33:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    private Integer id;
    //角色名称
    private String name;
    //角色key
    private String roleKey;
    //是否启用 0禁用1启用
    private Integer roleState;

}

