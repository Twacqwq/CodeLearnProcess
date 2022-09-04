package top.xiaohao.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.xiaohao.shop.domain.entitiy.User;
import top.xiaohao.shop.service.RoleService;
import top.xiaohao.shop.service.UserService;
import top.xiaohao.shop.utils.Result;

import java.util.List;

@SpringBootTest
class ShopApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @Test
    public void testRedisConn() {

    }

    @Test
    public void testMP() {
        List<User> userList = userService.list();
        userList.forEach(System.out::println);
    }

    @Test
    public void testBC() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    public void testRole() {
        List<String> list = roleService.selectRoleByUserId("1");
        list.forEach(System.out::println);
    }

}
