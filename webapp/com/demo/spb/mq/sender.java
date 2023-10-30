package com.demo.spb.mq;

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
        sendMsg(MsgTypeEnum.TEXT, "这是测试哈123...");
    }

//    public static void main(String[] args) throws Exception {
//        new MqAccept().startReceiveMessage();
//    }

}
