package top.xiaohao.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.xiaohao.shop.domain.entitiy.LoginUser;
import top.xiaohao.shop.service.UserRoleService;
import top.xiaohao.shop.service.UserService;
import top.xiaohao.shop.utils.Result;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 获取所有管理员列表
     * @return result
     */
    @GetMapping("/adminList")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> getAllAdmin() {
        return userService.getAllAdmin();
    }

    @GetMapping("/getAdminInfo")
    public Result<?> getAdminInfo() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Result<>(200, "查询成功", loginUser.getUser());
    }

//    @PostMapping("/register")
//    public Result<?> register(@RequestBody RegisUserVO regisUserVO) {
//        if (!Objects.equals(regisUserVO.getPassword(), regisUserVO.getCheckPassword())) {
//            return new Result<>(500, "两次密码输入不正确");
//        }
//        User user = new User();
//        user.setCreatedAt(new Date());
//        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
//        user.setUsername(regisUserVO.getUsername());
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode(regisUserVO.getPassword()));
//        user.setUserId(String.valueOf(userService.count() + 1));
//        boolean status = userService.save(user);
//        if (status) {
//            UserRole userRole = new UserRole();
//            userRole.setUserId(Integer.valueOf(user.getUserId()));
//            userRole.setRoleId(1);
//            userRoleService.save(userRole);
//            return new Result<>(200, "注册成功");
//        }
//        return new Result<>(500, AppHttpCodeEnum.SYSTEM_ERROR.toString());
//    }


}
