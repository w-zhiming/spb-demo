package com.demo.spb.mq;

public enum MsgTypeEnum {

    TEXT("text", "文本信息"),
    //    MAP("map", "Map信息"),
//    STREAM("stream", "流信息"),
    OBJECT("object", "对象信息"),
    BYTES("byte", "字节信息");

    public String key;

    public String value;

    /**
     * 创建一个新的实例MsgTypeEnum.
     *
     * @param key
     * @param value
     */
    MsgTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

}