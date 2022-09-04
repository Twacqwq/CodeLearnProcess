package top.xiaohao.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('system:test:list')") //需要test标识符的权限才能访问
    public String hello() {
        return "hello security";
    }
}
