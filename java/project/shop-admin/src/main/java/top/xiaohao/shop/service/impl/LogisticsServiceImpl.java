package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.LogisticsMapper;
import top.xiaohao.shop.domain.entitiy.Logistics;
import top.xiaohao.shop.service.LogisticsService;
import org.springframework.stereotype.Service;

/**
 * 物流表(Logistics)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:31
 */
@Service("logisticsService")
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

}

