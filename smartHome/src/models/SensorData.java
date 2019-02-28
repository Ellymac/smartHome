package models;



import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SensorData {


   public static JSONObject getDataFromSensor() {
        String apiKey = "8c66466252bb175015cef5632d3f50a1";
        double latitude = 37.8267;
        double longitude = -122.4233;
        String forcastUrl = "https://api.darksky.net/forecast/"+ apiKey +"/"+latitude+","+longitude;
        URL url = null;
        try {
            url = new URL(forcastUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection con = null;
        try {
            con = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        try {
            in = con.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = null;
        try {
            body = IOUtils.toString(in, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) JSONValue.parse(body);
        JSONObject currently = (JSONObject) jsonObject.get("currently");

        return  currently;
    }

}
