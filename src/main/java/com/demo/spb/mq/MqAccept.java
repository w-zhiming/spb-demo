package com.demo.spb.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Enumeration;

/**
 * 接收mq消息
 */
public class MqAccept implements MessageListener {
    private Destination dest = null;
    private Connection conn = null;
    private Session session = null;
    private MessageConsumer consumer = null;

    private boolean stop = false;

    /**
     * 初始化
     *
     * @throws Exception
     */
    private void initialize() throws Exception {
        // 连接工厂是用户创建连接的对象.
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constant.USER, Constant.PASSWORD, Constant.URL);
        // 连接工厂创建一个jms connection
        conn = connectionFactory.createConnection();
        // 是生产和消费的一个单线程上下文。会话用于创建消息的生产者，消费者和消息。会话提供了一个事务性的上下文。
        session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE); // 不支持事务
        // 目的地是客户用来指定他生产消息的目标还有他消费消息的来源的对象.
        dest = session.createQueue(Constant.SUBJECT);
        // dest = session.createTopic(SUBJECT);
        // 会话创建消息的生产者将消息发送到目的地
        consumer = session.createConsumer(dest);
    }

    /**
     * 消费消息
     *
     * @throws JMSException
     * @throws Exception
     */
    public void startReceiveMessage() throws JMSException, Exception {
        initialize();
        conn.start();
        consumer.setMessageListener(this);
        // 等待接收消息
        while (!stop) {
            Thread.sleep(5000);
        }

    }

    @SuppressWarnings("rawtypes")
    @Override
    public void onMessage(Message msg) {
        try {
            if (msg instanceof TextMessage) {
                TextMessage message = (TextMessage) msg;
                System.out.println("------Received TextMessage------");
                System.out.println(message.getText());
            } else if (msg instanceof MapMessage) {
                MapMessage message = (MapMessage) msg;
                System.out.println("------Received MapMessage------");
                System.out.println(message.getLong("long"));
                System.out.println(message.getBoolean("boolean"));
                System.out.println(message.getShort("short"));
                System.out.println(message.getString("MapMessage"));
                System.out.println("------Received MapMessage for while------");
                Enumeration enumer = message.getMapNames();
                while (enumer.hasMoreElements()) {
                    Object obj = enumer.nextElement();
                    System.out.println(message.getObject(obj.toString()));
                }
            } else if (msg instanceof StreamMessage) {
                StreamMessage message = (StreamMessage) msg;
                System.out.println("------Received StreamMessage------");
                System.out.println(message.readString());
                System.out.println(message.readBoolean());
                System.out.println(message.readLong());
            } else if (msg instanceof ObjectMessage) {
                System.out.println("------Received ObjectMessage------");
                ObjectMessage message = (ObjectMessage) msg;
               // System.out.println(message.getObject().);
               // ActiveMQMessage mqMessage = (ActiveMQMessage) message.getObject();
               // MqMessage mqMessage = (MqMessage) message.getObject();
                //System.out.println("name: " + mqMessage.getName());
            } else if (msg instanceof BytesMessage) {
                System.out.println("------Received BytesMessage------");
                BytesMessage message = (BytesMessage) msg;
                byte[] byteContent = new byte[1024];
                int length = -1;
                StringBuffer content = new StringBuffer();
                while ((length = message.readBytes(byteContent)) != -1) {
                    content.append(new String(byteContent, 0, length));
                }
                System.out.println(content.toString());
            } else {
                System.out.println(msg);
            }
            stop = true;
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        System.out.println("Consumer:->Closing connection");
        if (consumer != null) {
            consumer.close();
        }
        if (session != null) {
            session.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}