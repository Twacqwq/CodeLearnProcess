package top.xiaohao.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.xiaohao.shop.domain.entitiy.Role;

import java.util.List;

/**
 * (Role)表服务接口
 *
 * @author Twac
 * @since 2022-05-15 14:33:59
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleByUserId(String userId);

}

