package top.xiaohao.shop.service;

import org.springframework.transaction.annotation.Transactional;
import top.xiaohao.shop.domain.entitiy.User;
import top.xiaohao.shop.utils.Result;


public interface LoginService {
    Result<?> login(User user);

    Result<?> logout();
}
