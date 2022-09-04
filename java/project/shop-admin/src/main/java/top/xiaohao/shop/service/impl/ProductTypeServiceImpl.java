package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.ProductTypeMapper;
import top.xiaohao.shop.domain.entitiy.ProductType;
import top.xiaohao.shop.service.ProductTypeService;
import org.springframework.stereotype.Service;

/**
 * 商品类别表(ProductType)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:33
 */
@Service("productTypeService")
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

}

