package cn.yanfa.service.impl;

import cn.yanfa.common.AESUtil;
import cn.yanfa.common.RedisUtil;
import cn.yanfa.common.SystemConstant;
import cn.yanfa.dao.UserInfoDao;
import cn.yanfa.entity.UserInfo;
import cn.yanfa.jwt.JWTService;
import cn.yanfa.model.response.Result;
import cn.yanfa.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * (UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-26 13:22:34
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private RedisUtil redisUtils;
    @Autowired
    private JWTService jWTService;

    @Override
    public UserInfo findById(String id) {
        Optional<UserInfo> byId = userInfoDao.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public List<UserInfo> getall() {
        return this.userInfoDao.findAll();

    }

    @Override
    public Result login(UserInfo userInfo) {
        if (null == userInfo) {
            log.error("用户登录失败-参数为空!");
            return Result.paramsErr("用户登录失败-参数为空!");
        }
        UserInfo uInfo = userInfoDao.findFirstByUsernameEquals(userInfo.getUsername());
        if (uInfo == null) {
            log.error("用户登录失败-账号信息不存在!");
            return Result.failed("用户登录失败-账号信息不存在!");
        }

        if (!userInfo.getPassword().equals(uInfo.getPassword())) {
            log.error("用户登录失败-密码错误!");
            return Result.failed("用户登录失败-密码错误!");
        }
        String token = "";
        try {
            token = generateToken(uInfo.getId());
        } catch (Exception ex) {
            return Result.failed("登录失败:系统异常:" + ex);
        }
        uInfo.setToken(token);

        return Result.succeed(uInfo);
    }

    /**
     * 根据用户ID生成token
     *
     * @param userId
     * @return token字符串
     */
    public String generateToken(String userId) {
        String key = String.format(SystemConstant.HASH_KEY_USERINFO, userId);
        String hashKey = SystemConstant.LOGIN_TOKEN;
        String token = null;
        try {
            String secret = (String) redisUtils.hget(key, hashKey);
            if (secret == null || secret.equals("")) {
                secret = AESUtil.getRandomCode(8);
                redisUtils.hset(key, hashKey, secret);
            }
            if (secret == null || secret.equals("")) {
                return null;
            }
            token = jWTService.generateToken(userId, secret);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return token;
    }

    @Override
    public Page<UserInfo> findAllByPage(int offset, int limit) {
        return this.userInfoDao.findAll(PageRequest.of((offset - 1)
                * limit, limit));
    }

    @Override
    public UserInfo save(UserInfo userInfo) {

        return this.userInfoDao.save(userInfo);
    }


    @Override
    public UserInfo update(UserInfo userInfo) {

        return this.userInfoDao.save(userInfo);
    }

    @Override
    public boolean deleteById(String id) {

        try {
            this.userInfoDao.deleteById(id);
        } catch (Exception ex) {
            return false;
        }
        return true;

    }
}