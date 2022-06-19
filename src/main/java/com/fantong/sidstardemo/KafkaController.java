package com.fantong.sidstardemo;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.Properties;

@RestController
public class KafkaController {

    @PostMapping("/api/postSIDSTARKafkaMessage")
    public JSONObject postSIDSTARKafkaMessage(@RequestParam String topic, @RequestParam String jsonData, @RequestParam String username, @RequestParam String password){
        //KafkaController kCtrl = new KafkaController("broker1:9094,broker2:9094,broker3:9094","iz7cq1h1","uCee74gc0ahPnrfEuuJCKuvoWj9pEueb",topicName);

        String brokers = "moped-01.srvs.cloudkafka.com:9094,moped-02.srvs.cloudkafka.com:9094,moped-03.srvs.cloudkafka.com:9094";

        try {
            KafkaManager kMgr = new KafkaManager(brokers, username, password, topic);
            kMgr.produce(jsonData);
        }catch(Exception e){
            JSONObject toRet = new JSONObject();
            toRet.put("Error","kafka message was not posted");
            return toRet;
        }
        JSONObject toRet = new JSONObject();
        toRet.put("success","kafka message posted");
        return toRet;
    }
}
