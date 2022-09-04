package top.xiaohao.security.mp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.xiaohao.security.entity.User;
import top.xiaohao.security.service.MenuService;
import top.xiaohao.security.service.UserService;

import java.util.List;

@SpringBootTest
public class MpTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Test
    public void testMP() {
        List<User> userList = userService.list();
        System.out.println(userList);
    }

    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.matches("123", "$2a$10$Abz.gy3zX0L0MINpfXRXBuljPIUnvYatQNbLGnVdxTdtCChT4zbdG"));
    }

    @Test
    public void testSelectPermsByUserId() {
        List<String> perms = menuService.selectPermById(2L);
        System.out.println(perms);
    }
}