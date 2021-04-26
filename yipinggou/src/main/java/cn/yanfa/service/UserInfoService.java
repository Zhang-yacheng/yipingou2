package cn.yanfa.service;

import cn.yanfa.entity.UserInfo;
import cn.yanfa.model.response.Result;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * (UserInfo)表服务接口
 *
 * @author zry
 * @since 2021-04-26 13:22:34
 */
public interface UserInfoService {
    UserInfo findById(String id);

    Page<UserInfo> findAllByPage(int offset, int limit);

    UserInfo save(UserInfo userInfo);

    UserInfo update(UserInfo userInfo);

    boolean deleteById(String id);

    List<UserInfo> getall();

    Result login(UserInfo userInfo);
}