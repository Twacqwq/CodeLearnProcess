package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.ProductsFeaturedMapper;
import top.xiaohao.shop.domain.entitiy.ProductsFeatured;
import top.xiaohao.shop.service.ProductsFeaturedService;
import org.springframework.stereotype.Service;

/**
 * (ProductsFeatured)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:33
 */
@Service("productsFeaturedService")
public class ProductsFeaturedServiceImpl extends ServiceImpl<ProductsFeaturedMapper, ProductsFeatured> implements ProductsFeaturedService {

}

