package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.ReturnReasonMapper;
import top.xiaohao.shop.domain.entitiy.ReturnReason;
import top.xiaohao.shop.service.ReturnReasonService;
import org.springframework.stereotype.Service;

/**
 * 退货原因表(ReturnReason)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:34
 */
@Service("returnReasonService")
public class ReturnReasonServiceImpl extends ServiceImpl<ReturnReasonMapper, ReturnReason> implements ReturnReasonService {

}

