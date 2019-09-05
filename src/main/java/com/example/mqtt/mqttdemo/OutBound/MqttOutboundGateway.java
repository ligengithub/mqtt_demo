package com.example.mqtt.mqttdemo.OutBound;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

/**`
 * MQTT消息出站网关
 *
 * @Author LuDash
 * @Date 2017-10-18 20:19
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttMessageTransformChannel")
public interface MqttOutboundGateway {
    /**
     * 发布消息到MQTT服务器
     */
    void publish(String message);

}
