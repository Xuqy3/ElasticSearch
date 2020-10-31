package com.xuqy.elasticsearch.enums;

public enum ScenarioCodeEnum {

    CODE_10111011001("移动用户集团直充","10111011001"),//
    CODE_11111014002("欠费停机提醒","11111014002"),//
    CODE_11111014001("流量阈值提醒","11111014001"),//
    CODE_11000011(   "4G用户可选包","10111010002"),
    CODE_10111010001("4G业务新装受理开通","10111010001"),//
    CODE_11111010004("翼支付功能开通","11111010004"),
    CODE_11111110001("宽带产品新装",  "11111110001"),
    CODE_11111011004("集团11888充值","11111011004");

    private String scenarioName;   //场景名

    private String value;           //场景号

    public static String getScenarioNameByValue(String value) {
        ScenarioCodeEnum[] values = ScenarioCodeEnum.values();
        for (ScenarioCodeEnum scenarioCodeEnum : values) {

            if (scenarioCodeEnum.getValue().equals(value)) {
                return scenarioCodeEnum.getScenarioName();
            }
        }
        return value;
    }

    public static String getValueByScenarioName(String ScenarioName) {
        ScenarioCodeEnum[] values = ScenarioCodeEnum.values();
        for (ScenarioCodeEnum scenarioCodeEnum : values) {
            if (scenarioCodeEnum.getScenarioName().equals(ScenarioName)) {
                return scenarioCodeEnum.getValue();
            }
        }
        return ScenarioName;
    }

    ScenarioCodeEnum(String ScenarioName, String value) {
        this.scenarioName = ScenarioName;
        this.value = value;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String ScenarioName) {
        this.scenarioName = ScenarioName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
