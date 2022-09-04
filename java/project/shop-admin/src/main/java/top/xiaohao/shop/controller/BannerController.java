package top.xiaohao.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xiaohao.shop.domain.entitiy.Banner;
import top.xiaohao.shop.service.BannerService;
import top.xiaohao.shop.utils.Result;

import java.util.Collection;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/findAll")
    public Result<?> findAll() {
        Collection<Banner> banners = bannerService.list();
        return new Result<>(200, "查询成功", banners);
    }
}
