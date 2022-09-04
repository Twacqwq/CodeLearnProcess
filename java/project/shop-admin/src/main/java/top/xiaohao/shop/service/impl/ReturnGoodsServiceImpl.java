package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.ReturnGoodsMapper;
import top.xiaohao.shop.domain.entitiy.ReturnGoods;
import top.xiaohao.shop.service.ReturnGoodsService;
import org.springframework.stereotype.Service;

/**
 * 商品退货表(ReturnGoods)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:34
 */
@Service("returnGoodsService")
public class ReturnGoodsServiceImpl extends ServiceImpl<ReturnGoodsMapper, ReturnGoods> implements ReturnGoodsService {

}

