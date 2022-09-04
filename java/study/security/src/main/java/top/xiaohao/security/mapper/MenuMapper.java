package top.xiaohao.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.xiaohao.security.entity.Menu;

import java.util.List;

/**
 * 菜单表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-16 16:22:12
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermByUserId(Long userId);
}

