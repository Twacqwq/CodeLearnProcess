package top.xiaohao.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.xiaohao.shop.domain.entitiy.Order;

/**
 * 订单表(Order)表数据库访问层
 *
 * @author Twac
 * @since 2022-05-19 23:28:31
 */
public interface OrderMapper extends BaseMapper<Order> {

    Integer getCount();
}

