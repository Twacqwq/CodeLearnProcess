package top.xiaohao.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import top.xiaohao.shop.domain.entitiy.User;
import top.xiaohao.shop.domain.enums.AppHttpCodeEnum;
import top.xiaohao.shop.domain.vo.RegisUserVO;
import top.xiaohao.shop.domain.entitiy.UserRole;
import top.xiaohao.shop.service.LoginService;
import top.xiaohao.shop.service.UserRoleService;
import top.xiaohao.shop.service.UserService;
import top.xiaohao.shop.utils.Result;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        return loginService.login(user);
    }

    @GetMapping("/logout")
    public Result<?> logout() {
        return loginService.logout();
    }

    @PostMapping("/admin/register")
    public Result<?> register(@RequestBody RegisUserVO regisUserVO) {
        if (!Objects.equals(regisUserVO.getPassword(), regisUserVO.getCheckPassword())) {
            return new Result<>(500, "两次密码输入不正确");
        }
        User user = new User();
        user.setCreatedAt(new Date());
        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        user.setUsername(regisUserVO.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(regisUserVO.getPassword()));
        user.setUserId(String.valueOf(userService.count() + 1));
        boolean status = userService.save(user);
        if (status) {
            UserRole userRole = new UserRole();
            userRole.setUserId(Integer.valueOf(user.getUserId()));
            userRole.setRoleId(1);
            userRoleService.save(userRole);
            return new Result<>(200, "注册成功");
        }
        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
    }
}
