package top.xiaohao.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import top.xiaohao.shop.domain.entitiy.User;
import top.xiaohao.shop.domain.vo.AdminListVO;
import top.xiaohao.shop.utils.Result;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author Twac
 * @since 2022-05-12 17:30:55
 */
public interface UserService extends IService<User> {

    Result<?> getAllAdmin();

}

