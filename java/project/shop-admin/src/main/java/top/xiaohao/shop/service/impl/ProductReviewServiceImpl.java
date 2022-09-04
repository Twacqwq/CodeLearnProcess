package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.ProductReviewMapper;
import top.xiaohao.shop.domain.entitiy.ProductReview;
import top.xiaohao.shop.service.ProductReviewService;
import org.springframework.stereotype.Service;

/**
 * 商品评价(ProductReview)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Service("productReviewService")
public class ProductReviewServiceImpl extends ServiceImpl<ProductReviewMapper, ProductReview> implements ProductReviewService {

}

