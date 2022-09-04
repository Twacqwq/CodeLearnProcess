package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.ProductBrandMapper;
import top.xiaohao.shop.domain.entitiy.ProductBrand;
import top.xiaohao.shop.service.ProductBrandService;
import org.springframework.stereotype.Service;

/**
 * 商品品牌表(ProductBrand)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Service("productBrandService")
public class ProductBrandServiceImpl extends ServiceImpl<ProductBrandMapper, ProductBrand> implements ProductBrandService {

}

