package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.BannerMapper;
import top.xiaohao.shop.domain.entitiy.Banner;
import top.xiaohao.shop.service.BannerService;
import org.springframework.stereotype.Service;

/**
 * 商品广告轮播图(Banner)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:31
 */
@Service("bannerService")
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

}

