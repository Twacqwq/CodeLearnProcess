package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.OrderMapper;
import top.xiaohao.shop.domain.entitiy.Order;
import top.xiaohao.shop.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * 订单表(Order)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:32
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}

