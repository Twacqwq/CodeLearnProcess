package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.ProductSpecsMapper;
import top.xiaohao.shop.domain.entitiy.ProductSpecs;
import top.xiaohao.shop.service.ProductSpecsService;
import org.springframework.stereotype.Service;

/**
 * 商品规格表(ProductSpecs)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Service("productSpecsService")
public class ProductSpecsServiceImpl extends ServiceImpl<ProductSpecsMapper, ProductSpecs> implements ProductSpecsService {

}

