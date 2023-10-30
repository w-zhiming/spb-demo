package com.demo.spb.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;

/**
 * 发送mq消息
 */
public class MqSender {
    private Destination destination = null;
    private Connection conn = null;
    private Session session = null;
    private MessageProducer producer = null;

    /**
     * 获取conn
     *
     * @return conn conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * 初始化
     *
     * @throws Exception
     */
    public void initialize() throws Exception {
        // 连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constant.USER, Constant.PASSWORD, Constant.URL);
        conn = connectionFactory.createConnection();
        // 事务性会话，自动确认消息
        session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 消息的目的地（Queue/Topic）
        destination = session.createQueue(Constant.SUBJECT);
        // destination = session.createTopic(SUBJECT);
        // 消息的提供者（生产者）
        producer = session.createProducer(destination);
        // 不持久化消息
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public void sendMessage(MsgTypeEnum msgTypeEnum, Object msgObject) throws Exception {
        initialize();
        // 连接到JMS提供者（服务器）
        conn.start();
        switch (msgTypeEnum) {
            // 发送字节消息
            case BYTES:
                BytesMessage msg2 = session.createBytesMessage();
                msg2.writeBytes(msgObject.toString().getBytes());
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                producer.send(msg2);
                break;
            // 发送Map消息
//            case MAP:
//                MapMessage msg = session.createMapMessage();
//                msg.setBoolean("boolean", true);
//                msg.setShort("short", (short) 0);
//                msg.setLong("long", 123456);
//                msg.setString("MapMessage", "ActiveMQ Map Message!");
//                producer.send(msg);
//                break;
            // 发送对象消息
            case OBJECT:
                ObjectMessage msg12 = session.createObjectMessage();
                msg12.setObject((Serializable) msgObject);
                producer.send(msg12);
                break;
            // 发送流消息
//            case STREAM:
//                StreamMessage msg1 = session.createStreamMessage();
//                msg1.writeBoolean(false);
//                msg1.writeLong(1234567890);
//                producer.send((StreamMessage) msg1);
//                break;
            // 发送文本消息
            case TEXT:
                TextMessage msg11 = session.createTextMessage();
                msg11.setText((String) msgObject);
                producer.send(msg11);
                break;
            default:
                break;
        }
        close();
    }

    /**
     * 关闭连接
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        if (producer != null) {
            producer.close();
        }
        if (session != null) {
            session.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

}