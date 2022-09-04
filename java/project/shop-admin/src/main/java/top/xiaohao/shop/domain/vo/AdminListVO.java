package top.xiaohao.shop.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 管理员列表VO类
 * @author Twac
 */
@Data
public class AdminListVO {
    //用户名
    private String username;

    private String phone;
    //用户表id
    private String userId;

    private String sex;

    private String address;



}
