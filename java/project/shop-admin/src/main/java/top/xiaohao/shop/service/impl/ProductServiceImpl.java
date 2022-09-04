package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.ProductMapper;
import top.xiaohao.shop.domain.entitiy.Product;
import top.xiaohao.shop.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * 商品表(Product)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}

