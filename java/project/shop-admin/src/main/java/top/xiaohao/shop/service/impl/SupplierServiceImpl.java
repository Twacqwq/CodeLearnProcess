package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.SupplierMapper;
import top.xiaohao.shop.domain.entitiy.Supplier;
import top.xiaohao.shop.service.SupplierService;
import org.springframework.stereotype.Service;

/**
 * 供应商表(Supplier)表服务实现类
 *
 * @author Twac
 * @since 2022-05-20 01:42:54
 */
@Service("supplierService")
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

}

