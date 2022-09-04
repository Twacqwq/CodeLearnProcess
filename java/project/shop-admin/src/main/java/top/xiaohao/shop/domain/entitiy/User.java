package top.xiaohao.shop.domain.entitiy;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (User)表实体类
 *
 * @author Twac
 * @since 2022-05-12 17:30:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    //用户名
    private String username;
    //用户密码
    private String password;

    private String phone;
    //逻辑删除 0是1否
    private Integer delFlag;
    //用户表id
    @TableId
    private String userId;
    //创建时间
    private Date createdAt;
    //更新时间
    private Date updatedAt;
    //用户头像
    private String avatar;

    private String sex;

    private String summary;

    private String address;

}

