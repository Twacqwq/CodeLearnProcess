package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.xiaohao.shop.domain.entitiy.User;
import top.xiaohao.shop.domain.vo.AdminListVO;
import top.xiaohao.shop.mapper.UserMapper;
import top.xiaohao.shop.service.UserService;
import org.springframework.stereotype.Service;
import top.xiaohao.shop.utils.BeanCopyUtils;
import top.xiaohao.shop.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * (User)表服务实现类
 *
 * @author Twac
 * @since 2022-05-12 17:30:55
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<?> getAllAdmin() {
        List<User> userList = userMapper.getAllAdmin();
        List<AdminListVO> adminListVOList = BeanCopyUtils.copyBeanList(userList, AdminListVO.class);
        return new Result<>(200, "查询成功", adminListVOList);
    }
}

