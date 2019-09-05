package com.example.mqtt.mqttdemo.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQTT的外部文件（application-dev.yml）配置读取类
 * @Author LuDash
 * @Date 2017-10-18 20:09
 */
@ConfigurationProperties(prefix = "mqtt")
public class MQTTSettings {

    // 服务器地址 ip:端口 形式
    private String broker;

    //  mqtt服务器
    // 服务器账号
    private String userName;
    // 密码
    private String password;


    // 订阅主题  在本例中，我使用自己订阅自己。。
    private String subTopic;
    // 发布主题前缀
    private String basePubTopic;


    // ssl连接方式配置。 如果不用ssl可以不用配置
    private String caCrtFile;
    // 证书客户端
    private String clientCrtFile;
    // key
    private String clientKeyFile;
    // ssl密码
    private String sslPassword;



    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public String getBasePubTopic() {
        return basePubTopic;
    }

    public void setBasePubTopic(String basePubTopic) {
        this.basePubTopic = basePubTopic;
    }

    public String getCaCrtFile() {
        return caCrtFile;
    }

    public void setCaCrtFile(String caCrtFile) {
        this.caCrtFile = caCrtFile;
    }

    public String getClientCrtFile() {
        return clientCrtFile;
    }

    public void setClientCrtFile(String clientCrtFile) {
        this.clientCrtFile = clientCrtFile;
    }

    public String getClientKeyFile() {
        return clientKeyFile;
    }

    public void setClientKeyFile(String clientKeyFile) {
        this.clientKeyFile = clientKeyFile;
    }

    public String getSslPassword() {
        return sslPassword;
    }

    public void setSslPassword(String sslPassword) {
        this.sslPassword = sslPassword;
    }
}
