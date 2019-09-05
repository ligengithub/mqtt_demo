package com.example.mqtt.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mqtt.mqttdemo.OutBound.MqttOutboundGateway;
import com.example.mqtt.mqttdemo.entry.DownLinkBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ligen
 * @title: TestController
 * @projectName mqtt-demo
 * @description:
 * @date 2019/9/413:57
 */



@RestController
public class TestController {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    MqttOutboundGateway mqttOutboundGateway;

    @GetMapping("/publish")
    public void publishMessage(@RequestParam(value = "topic") String topic, @RequestParam(value = "message") String message){
      logger.debug("debug");
        mqttOutboundGateway.publish(JSONObject.toJSONString(new DownLinkBody(topic,message)));
    }

}
