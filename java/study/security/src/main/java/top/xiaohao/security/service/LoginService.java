package top.xiaohao.security.service;

import top.xiaohao.security.entity.ResponseResult;
import top.xiaohao.security.entity.User;

public interface LoginService {

    ResponseResult<?> login(User user);

    ResponseResult<?> logout();
}
