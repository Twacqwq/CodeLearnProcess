package top.xiaohao.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.xiaohao.shop.domain.entitiy.User;
import top.xiaohao.shop.domain.vo.AdminListVO;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author Twac
 * @since 2022-05-12 17:30:54
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> getAllAdmin();

}

