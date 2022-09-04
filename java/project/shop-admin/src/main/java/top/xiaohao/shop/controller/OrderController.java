package top.xiaohao.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xiaohao.shop.service.OrderService;
import top.xiaohao.shop.utils.Result;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/findCount")
    public Result<?> count() {
        return new Result<>(200, "查询成功", orderService.count());
    }

    @GetMapping("/findAll")
    public Result<?> findAll() {
        return new Result<>(200, "查询成功", orderService.list());
    }
}

