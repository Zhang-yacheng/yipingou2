package cn.yanfa.model.enumPackage;

public enum BigScreenDataType {
    //监测点
    MONITOR(1),
    //设备
    SENSOR(2),
    //设备异常
    ABNORMAL(3),
    //实时预警
    REALALARM(4),
    //设备在线折线统计
    SENSERSTATUS_CHART(5),
    //实时预警按照级别统计
    REALALARMCOUNT(6),
    //实时预警折线统计
    REALALARMCOUNT_CHART(7),
    //预警柱状图统计
    REALALARMCOUNT_HISTOGRAM(8),
    //设备在线折线统计
    SENSERSTATUS_PIE_CHART(9),
    //实时预警详情
    CURRENTALARM_MESSAGE(10);
    private Integer code;

    BigScreenDataType() {
    }

    BigScreenDataType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
