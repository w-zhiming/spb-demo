package com.demo.spb.mq;

import java.util.List;

public class sender {

    public static void sendMsg(MsgTypeEnum type, Object msgObj) throws Exception {
        MqSender sender = new MqSender();
        // 连接到JMS提供者（服务器）
        sender.initialize();
        sender.getConn().start();

        sender.sendMessage(type, msgObj);
        sender.close();
    }

    public static void main(String[] args) throws Exception {
        sendMsg(MsgTypeEnum.TEXT, "this is for you!");
        sendMsg(MsgTypeEnum.OBJECT, List.of(1,2,3));
    }

//    public static void main(String[] args) throws Exception {
//        new MqAccept().startReceiveMessage();
//    }

}
