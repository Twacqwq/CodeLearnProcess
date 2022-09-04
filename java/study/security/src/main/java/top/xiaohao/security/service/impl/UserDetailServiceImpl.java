package top.xiaohao.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.xiaohao.security.entity.LoginUser;
import top.xiaohao.security.entity.User;
import top.xiaohao.security.service.MenuService;
import top.xiaohao.security.service.UserService;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * 自定义登录逻辑
     * @param username 用户名
     * @return UserDetail
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username));
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误qwq");
        }
        //TODO 根据用户查询权限信息
//        List<String> permissionList = new ArrayList<>(Arrays.asList("test", "admin"));
        List<String> perms = menuService.selectPermById(user.getId());
        return new LoginUser(user, perms);
    }
}
