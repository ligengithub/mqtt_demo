package com.example.mqtt.mqttdemo.OutBound;

import com.alibaba.fastjson.JSONObject;
import com.example.mqtt.mqttdemo.config.MQTTSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * MQTT出站消息转换器
 *
 * @Author LuDash
 * @Date 2017-10-18 20:21
 */
@Component
public class MqttOutboundHandler {

    private Logger logger = LoggerFactory.getLogger(MqttOutboundHandler.class);


    @Autowired
    private MQTTSettings mqttSettings;

    @Transformer(inputChannel = "mqttMessageTransformChannel", outputChannel = "mqttOutboundChannel")
    public Message<?> transform(Message<?> message) {

        try {
            JSONObject data = JSONObject.parseObject(message.getPayload().toString());

             if (Objects.isNull(data) || Objects.isNull(data.get("topic"))){
                 throw new Exception("消息主题topic不能为null");
             }
            //发布消息完整主题PubTopic=basePubTopic+topic（topic由应用服务程序指定）
            String publishTopic = mqttSettings.getBasePubTopic() + data.get("topic").toString();

            //发送数据到broker的时候设置发布消息的主题的方法：使用MessageBuilder，添加mqtt_topic头部信息(header)
            Message<?> msgBuild = MessageBuilder
                    .fromMessage(message)
                    .setHeaderIfAbsent(data.get("headerName").toString(), publishTopic)
                    .build();
            logger.info("MQTT Outbound : " + message.getPayload().toString());
            return msgBuild;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

}
