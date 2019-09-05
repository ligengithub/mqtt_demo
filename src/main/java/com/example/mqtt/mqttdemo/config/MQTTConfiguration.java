package com.example.mqtt.mqttdemo.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;


/**
 * MQTT 配置
 *
 * @author Leaves
 * @version 1.0.5
 * @date 2017/10/26
 */
@Configuration
@EnableConfigurationProperties(value = {MQTTSettings.class})  //配置类生效配置
public class MQTTConfiguration {

    @Autowired
    private MQTTSettings mqttSettings;


    /**
     * MQTT连接配置
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setPassword(mqttSettings.getPassword()!=null? mqttSettings.getPassword().toCharArray():null);
        options.setUserName(mqttSettings.getUserName()!=null? mqttSettings.getUserName():null);
        String[] broker = new String[]{mqttSettings.getBroker()};
        options.setServerURIs(broker);
        factory.setConnectionOptions(options);
        //If set to false both the client and server will maintain state across restarts of the client, the server and the connection
        //If set to true the client and server will not maintain state across restarts of the client, the server or the connection
        factory.setCleanSession(true);
        //the persistence class to use to store in-flight message. If null then the default persistence mechanism is used
        factory.setPersistence(new MemoryPersistence());
        return factory;
    }


    /**
     * MQTT消息出站处理器
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MqttPahoMessageHandler mqttOutbound() {
        String outboundClientId = "mqtt-outbound" + System.currentTimeMillis();
        //String outboundClientId = "SmartApp" + RandomStringUtil.getUpperAlphabetDigital(4) + System.currentTimeMillis();
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(outboundClientId, mqttClientFactory());  // 创建一个客户端
        handler.setAsync(true);
        //RabbitMQ MQTT Qos1
        handler.setDefaultQos(0);
        handler.setCompletionTimeout(30000);
        //handler.setConverter(new MqttOutboundMessageConverter());
        //handler.setDefaultTopic(mqttSettings.getPubTopic());
        return handler;
    }

    /**
     * MQTT消息出站通道
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT消息转发通道
     */
    @Bean
    public MessageChannel mqttMessageTransformChannel() {
        return new DirectChannel();
    }


    /**
     * MQTT消息入站处理器
     */
    @Bean
    public MessageProducer mqttInbound() {
        String inboundClientId = "mqtt-inbound" + System.currentTimeMillis();
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(inboundClientId, mqttClientFactory(), mqttSettings.getSubTopic());
        adapter.setQos(0);
        adapter.setAutoStartup(true);
        adapter.setSendTimeout(30000);
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    /**
     * MQTT消息入站通道
     */
    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

//    /**
//     * MQTT入站消息格式转换器 => 将消息转换成byte[]并输出
//     */
//    @Bean
//    public MqttMessageConverter mqttInboundMessageConverter() {
//        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter("UTF8");
//        converter.setPayloadAsBytes(true);
//        return converter;
//    }

//    /**
//     * SSL工具
//     */
//    private SocketFactory getSslSocketFactory(String password, String caPath, String clientCrtPath, String clientKeyPath) {
//        try {
//            Security.addProvider(new BouncyCastleProvider());
//            // load CA certificate
//            //"classpath:sslFiles/sslCAFiles/ca.crt"
//            PEMReader reader = new PEMReader(new InputStreamReader(new DefaultResourceLoader().getResource(caPath).getInputStream()));
//            X509Certificate caCert = (X509Certificate) reader.readObject();
//            reader.close();
//
//            // load client certificate
//            reader = new PEMReader(new InputStreamReader(new DefaultResourceLoader().getResource(clientCrtPath).getInputStream()));
//            X509Certificate cert = (X509Certificate) reader.readObject();
//            reader.close();
//
//            // load client private key
//            reader = new PEMReader(
//                    new InputStreamReader(new DefaultResourceLoader().getResource(clientKeyPath).getInputStream()),
//                    new PasswordFinder() {
//                        @Override
//                        public char[] getPassword() {
//                            return password.toCharArray();
//                        }
//                    }
//            );
//            KeyPair key = (KeyPair) reader.readObject();
//            reader.close();
//
//            // CA certificate is used to authenticate server
//            KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
//            caKs.load(null, null);
//            caKs.setCertificateEntry("ca-certificate", caCert);
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            tmf.init(caKs);
//
//            // client key and certificates are sent to server so it can authenticate us
//            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//            ks.load(null, null);
//            ks.setCertificateEntry("certificate", cert);
//            ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new java.security.cert.Certificate[]{cert});
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            kmf.init(ks, password.toCharArray());
//
//            // finally, create SSL socket factory
//            SSLContext context = SSLContext.getInstance("TLSv1");
//            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//            return context.getSocketFactory();
//        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
