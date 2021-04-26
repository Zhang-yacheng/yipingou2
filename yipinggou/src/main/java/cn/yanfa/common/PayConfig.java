package cn.yanfa.common;

public class PayConfig {
    // 支付宝支付appId
    private String aliAppid;

    // 支付宝支付商户ID
    private String aliMchid;

    // 支付宝支付回调地址
    private String aliNotifyurl;

    // 支付宝包名
    private String aliPkg;

    // 支付宝支付秘钥
    private String aliAppSecretKey;

    // 公钥
    private String alipayPublicKey;

    // 微信支付appid
    private String wxAppid;

    // 微信支付商户ID
    private String wxMchid;

    // 微信支付回调
    private String wxNotifyurl;

    // 微信支付AppSecret
    private String wxAppSecret;

    // 微信支付秘钥
    private String wxSecretKey;

    // 微信包名
    private String wxPkg;

    private static PayConfig payConfig;

    private PayConfig()
    {
        wxPkg = "";
        wxMchid = "";
        wxAppid = "";
        wxAppSecret = "";
        wxSecretKey = "";
        wxNotifyurl = "";
        aliAppid = "";
        aliMchid = "";
        aliNotifyurl = "";
        aliAppSecretKey = "";
        aliPkg = "";
        alipayPublicKey = "";
    }

    public static synchronized PayConfig getInstance()
    {
        if (payConfig == null)
        {
            payConfig = new PayConfig();
        }
        return payConfig;
    }

    public String getAliAppid()
    {
        return aliAppid;
    }

    public void setAliAppid(String aliAppid)
    {
        this.aliAppid = aliAppid;
    }

    public String getAliMchid()
    {
        return aliMchid;
    }

    public void setAliMchid(String aliMchid)
    {
        this.aliMchid = aliMchid;
    }

    public String getAliNotifyurl()
    {
        return aliNotifyurl;
    }

    public void setAliNotifyurl(String aliNotifyurl)
    {
        this.aliNotifyurl = aliNotifyurl;
    }

    public String getAliAppSecretKey()
    {
        return aliAppSecretKey;
    }

    public void setAliAppSecretKey(String aliAppSecretKey)
    {
        this.aliAppSecretKey = aliAppSecretKey;
    }

    public String getWxAppid()
    {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid)
    {
        this.wxAppid = wxAppid;
    }

    public String getWxMchid()
    {
        return wxMchid;
    }

    public void setWxMchid(String wxMchid)
    {
        this.wxMchid = wxMchid;
    }

    public String getWxNotifyurl()
    {
        return wxNotifyurl;
    }

    public void setWxNotifyurl(String wxNotifyurl)
    {
        this.wxNotifyurl = wxNotifyurl;
    }

    public String getWxAppSecret()
    {
        return wxAppSecret;
    }

    public void setWxAppSecret(String wxAppSecret)
    {
        this.wxAppSecret = wxAppSecret;
    }

    public String getWxSecretKey()
    {
        return wxSecretKey;
    }

    public void setWxSecretKey(String wxSecretKey)
    {
        this.wxSecretKey = wxSecretKey;
    }

    public String getWxPkg()
    {
        return wxPkg;
    }

    public void setWxPkg(String wxPkg)
    {
        this.wxPkg = wxPkg;
    }

    public String getAliPkg()
    {
        return aliPkg;
    }

    public void setAliPkg(String aliPkg)
    {
        this.aliPkg = aliPkg;
    }

    public String getAlipayPublicKey()
    {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey)
    {
        this.alipayPublicKey = alipayPublicKey;
    }

    @Override
    public String toString()
    {
        return "PayConfig [aliAppid=" + aliAppid + ", aliMchid=" + aliMchid + ", aliNotifyurl="
                + aliNotifyurl + ", aliPkg=" + aliPkg + ", aliAppSecretKey=" + aliAppSecretKey
                + ", alipayPublicKey=" + alipayPublicKey + ", wxAppid=" + wxAppid + ", wxMchid="
                + wxMchid + ", wxNotifyurl=" + wxNotifyurl + ", wxAppSecret=" + wxAppSecret
                + ", wxSecretKey=" + wxSecretKey + ", wxPkg=" + wxPkg + "]";
    }
}
