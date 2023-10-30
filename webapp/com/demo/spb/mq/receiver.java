package com.demo.spb.mq;

public class receiver {
    public static void main(String[] args) throws Exception {
        new MqAccept().startReceiveMessage();
    }

}
