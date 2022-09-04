package top.xiaohao.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.xiaohao.shop.domain.entitiy.Role;

import java.util.List;

/**
 * (Role)表数据库访问层
 *
 * @author Twac
 * @since 2022-05-15 14:33:59
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleByUserId(String userId);
}

