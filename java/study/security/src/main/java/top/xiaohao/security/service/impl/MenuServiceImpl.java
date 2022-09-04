package top.xiaohao.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaohao.security.entity.Menu;
import top.xiaohao.security.mapper.MenuMapper;
import top.xiaohao.security.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-03-16 16:22:12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermById(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        return menuMapper.selectPermByUserId(userId);
    }
}

