package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.PurchaseMapper;
import top.xiaohao.shop.domain.entitiy.Purchase;
import top.xiaohao.shop.service.PurchaseService;
import org.springframework.stereotype.Service;

/**
 * 采购表(Purchase)表服务实现类
 *
 * @author Twac
 * @since 2022-05-19 23:28:33
 */
@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

}

