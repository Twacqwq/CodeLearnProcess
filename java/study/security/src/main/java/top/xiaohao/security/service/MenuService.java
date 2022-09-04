package top.xiaohao.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.xiaohao.security.entity.Menu;

import java.util.List;

/**
 * 菜单表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2022-03-16 16:22:12
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermById(Long userId);
}

