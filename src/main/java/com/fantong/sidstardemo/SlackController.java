package com.fantong.sidstardemo;

import com.slack.api.webhook.Payload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import com.slack.api.webhook.WebhookPayloads;


@RestController
public class SlackController {

    @PostMapping("/api/sendSlackNotif")
    public JSONObject sendSlackNotif(@RequestParam String jsonData){
        JSONObject toRet = new JSONObject();
        try {
            String webhookUrl = "https://hooks.slack.com/services/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";
            JSONParser parser = new JSONParser();
            System.out.println(jsonData);

            JSONObject receivedData = (JSONObject) parser.parse(jsonData);

            JSONArray fieldsForWaypoints = new JSONArray();

            JSONArray waypointsData = (JSONArray)receivedData.get("topWaypoints");
            for(int i=0; i< waypointsData.size() ; i++){
                JSONObject waypointD = (JSONObject)waypointsData.get(i);
                JSONObject field = new JSONObject();
                field.put("type","plain_text");
                field.put("text",waypointD.get("name").toString() + "," + waypointD.get("count").toString());
                fieldsForWaypoints.add(field);
                if(i==9){
                    //Slack do no allow for more than 10 fields in the block. Can consider sending another notif for next iteration
                    break;
                }

            }

            JSONObject waypointsBlock = new JSONObject();
            waypointsBlock.put("type","section");
            waypointsBlock.put("fields",fieldsForWaypoints);

            JSONArray blocksArray = new JSONArray();

            JSONObject titleText = new JSONObject();
            titleText.put("type","section");

            JSONObject titleTextContent = new JSONObject();
            titleTextContent.put("type","plain_text");
            titleTextContent.put("text","Please look at the new top waypoints association count for airport " + receivedData.get("airport").toString() + "'s " + receivedData.get("stdInstru").toString() + "(s)");

            titleText.put("text",titleTextContent);
            blocksArray.add(titleText);
            blocksArray.add(waypointsBlock);

            JSONObject slackPayload = new JSONObject();

            slackPayload.put("blocks",blocksArray);

            System.out.println(slackPayload.toString());

            Slack slack = Slack.getInstance();
            WebhookResponse response = slack.send(webhookUrl, slackPayload.toString());
            System.out.println(response);
            toRet.put("success","Slack notif is posted successfully");
            return toRet;

        }catch(Exception e){
            System.out.println(e);
            toRet.put("Error","kafka message was not posted");
            return toRet;
        }


    }
}
