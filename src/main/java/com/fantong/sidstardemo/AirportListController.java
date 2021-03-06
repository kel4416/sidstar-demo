package com.fantong.sidstardemo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

@RestController
public class AirportListController {

    @PostMapping("/api/getAirports")
    public JSONObject getAirports(@RequestParam String apiKey){
        System.out.println("Start Handling get Airports request");
        JSONObject toRet = new JSONObject();
        try {
            URL url = new URL("https://open-atms.airlab.aero/api/v1/airac/airports");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("api-key", apiKey);
            conn.connect();
            int responsecode = conn.getResponseCode();
            System.out.println(responsecode);
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(conn.getInputStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                Object obj = parse.parse(inline);

                if (obj instanceof JSONObject) {
                    JSONObject jo = (JSONObject) obj;
                    toRet.put("data",jo);
                    return toRet;
                } else {
                    JSONArray ja = (JSONArray) obj;
                    toRet.put("data", ja);
                    return toRet;
                }
            }
        }catch(Exception e){
            System.out.print("Connection Error");
            System.out.println(e.toString());
            toRet.put("Error", "Unsuccessful in retrieving airports");
            return toRet;
        }
    }
}
