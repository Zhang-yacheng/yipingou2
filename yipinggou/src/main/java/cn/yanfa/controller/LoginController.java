package cn.yanfa.controller;

import cn.yanfa.entity.UserInfo;
import cn.yanfa.jwt.ShiroUtil;
import cn.yanfa.model.response.Result;
import cn.yanfa.service.UserInfoService;
import cn.yanfa.service.impl.UserInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoServiceImpl userInfoServiceImpl;

    @RequestMapping("user/login")
    public Result login(@RequestBody UserInfo userInfo) {
        return userInfoServiceImpl.login(userInfo);
    }

    /***
     * 用户退出
     *
     */
    @PostMapping("user/logout")
    public Result logout() {
        try {
            String uid = ShiroUtil.getUserId();
            if (StringUtils.isEmpty(uid)) {
                log.error("用户退出失败-用户信息获取失败!");
                return Result.paramsErr("用户退出失败-用户信息获取失败!");
            }
            return Result.succeed();
        } catch (Exception ex) {
            throw new NullPointerException(ex.toString());
        }
    }


}
