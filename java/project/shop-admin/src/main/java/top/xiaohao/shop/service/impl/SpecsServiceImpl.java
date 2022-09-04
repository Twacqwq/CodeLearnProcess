package top.xiaohao.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.shop.mapper.SpecsMapper;
import top.xiaohao.shop.domain.entitiy.Specs;
import top.xiaohao.shop.service.SpecsService;
import org.springframework.stereotype.Service;

/**
 * 商品规格表(Specs)表服务实现类
 *
 * @author Twac
 * @since 2022-05-26 15:34:33
 */
@Service("specsService")
public class SpecsServiceImpl extends ServiceImpl<SpecsMapper, Specs> implements SpecsService {

}

