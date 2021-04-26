package cn.yanfa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * (UserInfo)表实体类
 *
 * @author makejava
 * @since 2021-04-26 15:07:44
 */
@Data
@Entity
@Table(name="user_info")
public class UserInfo implements Serializable {

    /**
     * 主键id
     */
    @Id
    private String id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 介绍人id
     */
    private String introducer;
    /**
     * 头像URL
     */
    private String headPortrait;

    @Transient
    private String token;
}