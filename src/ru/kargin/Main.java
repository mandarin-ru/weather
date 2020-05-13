package ru.kargin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.*;
import java.io.*;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws ParseException {
        Settings settings = new Settings();
        String keyServiceCity = settings.keyServiceCity;
        String keyServiceWeather = settings.keyServiceWeather;
        String publicIp = "", temperature = "", nameCity = "";
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));
            publicIp = sc.readLine().trim();
        }
        catch (Exception e) {
            System.exit(1);
        }

        try {
            URL url_name = new URL("http://api.ipstack.com/"+publicIp+"?access_key="+keyServiceCity);
            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));
            publicIp = sc.readLine().trim();
        }
        catch (Exception e) {
            System.exit(2);
        }

        Object obj = new JSONParser().parse(publicIp);
        JSONObject jo = (JSONObject) obj;
        nameCity = jo.get("city").toString();
        String string1 = "http://api.weatherstack.com/current?access_key="+keyServiceWeather+"&query="+nameCity ;
        string1 = string1.replaceAll(" ", "%20");

        try {

            URL url_name = new URL(string1);
            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));
            publicIp = sc.readLine().trim();
        }
        catch (Exception e) {
            System.exit(3);
        }

        try {
            Object obj1 = new JSONParser().parse(publicIp);

            JSONObject jo1 = (JSONObject) obj1;

            JSONArray array = new JSONArray();
            array.add(jo1.get("current"));

            for(int i = 0; i < array.size(); i++){
                JSONObject values2 = (JSONObject) array.get(i);
                //System.out.println(values2);
                temperature = values2.get("temperature").toString();
            }
        }  catch (ParseException e) {
            System.exit(4);
        }
        System.out.println(nameCity + " "+ temperature);

       /* {
            "request":{
            "type":"City", "query":"Saint Petersburg, Russia", "language":"en", "unit":"m"
        },"location":{
            "name":"Saint Petersburg", "country":"Russia", "region":"Saint Petersburg City", "lat":"59.894", "lon":
            "30.264", "timezone_id":"Europe\/Moscow", "localtime":"2020-05-09 20:24", "localtime_epoch":
            1589055840, "utc_offset":"3.0"
        },"current":{
            "observation_time":"05:24 PM", "temperature":11, "weather_code":113, "weather_icons":[
            "https:\/\/assets.weatherstack.com\/images\/wsymbols01_png_64\/wsymbol_0001_sunny.png"],
            "weather_descriptions":["Sunny"],"wind_speed":19, "wind_degree":310, "wind_dir":"NW", "pressure":
            1011, "precip":0, "humidity":43, "cloudcover":0, "feelslike":9, "uv_index":4, "visibility":10, "is_day":
            "yes"
        }
        }*/
    }

}
