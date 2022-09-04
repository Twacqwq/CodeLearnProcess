package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.xiaohao.shop.mapper.RoleMapper;
import top.xiaohao.shop.domain.entitiy.Role;
import top.xiaohao.shop.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Role)表服务实现类
 *
 * @author Twac
 * @since 2022-05-15 14:34:00
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleByUserId(String userId) {
        return roleMapper.selectRoleByUserId(userId);
    }
}

