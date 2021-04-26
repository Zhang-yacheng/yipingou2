package cn.yanfa.dao;

import cn.yanfa.config.jpa.BaseRepository;
import cn.yanfa.entity.UserInfo;

/**
 * (UserInfo)表数据库访问层
 *
 * @author zry
 * @since 2021-04-26 13:22:36
 */
public interface UserInfoDao extends BaseRepository<UserInfo, String> {

   UserInfo findFirstByUsernameEquals(String username);
}