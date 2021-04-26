package cn.yanfa.model.enumPackage;

public enum UrlEnum {
    // 获取令牌
    GETTOKEN("http://openapi.ecois.info/v3/token?appid=OSql_yI2DK3l058i&secret=E3JhKqFZlP*8pgsF5TLKDNWI$RY8JsCW"),
    // 设置推送地址
    SET_PUSH_ADDRESS("http://openapi.ecois.info/v3/device/webhook"),
    // 报警喇叭
    SET_ALARM_HORN("http://service.northdoo.com:8080/ytyserver"),
    //和舆图报警器
    HTU_ALARM_HORN("http://120.77.219.179:9136/gxhy/agreement/SendCallingMessage"),
    //查看数据推送地址
    FIND_PUSHDATA_ADDRESS("http://openapi.ecois.info/v3/device/webhook");


    private String url;

    private UrlEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
