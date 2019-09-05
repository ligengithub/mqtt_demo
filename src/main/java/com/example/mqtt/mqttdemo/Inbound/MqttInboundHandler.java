package com.example.mqtt.mqttdemo.Inbound;//package com.senthink.www.mqtt.inbound;

import com.alibaba.fastjson.JSONObject;
import com.example.mqtt.mqttdemo.entry.DownLinkBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * MQTT入站消息处理器 => 处理设备平台推送上来的设备消息   SubScribe的角色
 * @Author LuDash
 * @Date 2017-10-18 20:17
 */
@Component
public class MqttInboundHandler {

    private Logger logger = LoggerFactory.getLogger(MqttInboundHandler.class);

    @ServiceActivator(inputChannel="mqttInboundChannel")
    public void handleMessage(Message<?> message) {
        try {
            logger.info(message.toString());
            DownLinkBody body = JSONObject.parseObject(message.getPayload().toString(), DownLinkBody.class);
            logger.info("MQTT Inbound : " + body.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
