package com.example.mqtt.mqttdemo.entry;

import java.util.Date;

/**
 * MQTT 消息体
 *
 * @author Leaves
 * @version 1.0.0
 * @date 2017/11/13
 */
public class DownLinkBody {

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 消息头，默认“mqtt_topic”
     */
    private String headerName;

    /**
     * 消息体
     */
    private Object message;

    /**
     * 消息创建时间
     */
    private Date gmtCreate;

    public DownLinkBody() {
    }

    public DownLinkBody(String topic, Object message) {
        this.topic = topic;
        this.headerName = "mqtt_topic";
        this.message = message;
        this.gmtCreate = new Date();
    }

    public DownLinkBody(String topic, String headerName, Object message, Date gmtCreate) {
        this.topic = topic;
        this.headerName = headerName;
        this.message = message;
        this.gmtCreate = gmtCreate;
    }

    public String getTopic() {
        return topic;
    }

    public DownLinkBody setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public String getHeaderName() {
        return headerName;
    }

    public DownLinkBody setHeaderName(String headerName) {
        this.headerName = headerName;
        return this;
    }

    public Object getMessage() {
        return message;
    }

    public DownLinkBody setMessage(Object message) {
        this.message = message;
        return this;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public DownLinkBody setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    @Override
    public String toString() {
        return "DownLinkBody{" +
                "topic='" + topic + '\'' +
                ", headerName='" + headerName + '\'' +
                ", message='" + message + '\'' +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
