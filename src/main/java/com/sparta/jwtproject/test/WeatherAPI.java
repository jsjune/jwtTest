package com.sparta.jwtproject.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


public class WeatherAPI {
    public static void main(String[] args) throws IOException, ParseException {

        GpsTransfer gpsTransfer = new GpsTransfer();
        gpsTransfer.setLat(37.556671);
        gpsTransfer.setLng(127.198374);
        gpsTransfer.transfer(gpsTransfer,0);

        LocalDate seoulNow = LocalDate.now(ZoneId.of("Asia/Seoul"));
        String month;
        String day;
        if (seoulNow.getMonthValue() < 10) {
            month = "0"+seoulNow.getMonthValue();
        }else{
            month = String.valueOf(seoulNow.getMonthValue());
        }
        if (seoulNow.getDayOfMonth() < 10) {
            day = "0"+seoulNow.getDayOfMonth();
        }else{
            day = String.valueOf(seoulNow.getDayOfMonth());
        }
        String localDate = String.valueOf(seoulNow.getYear())+ month +day;
        System.out.println(localDate);

        LocalTime now = LocalTime.now(ZoneId.of("Asia/Seoul"));
        String hour = String.valueOf(now).split(":")[0];
        String minute = String.valueOf(now).split(":")[1];
        String time = hour+minute;
        System.out.println(time);

        // 초단기예보조회
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" +
                "CUnaloj7dqGa0E7B1yinNSBe8arlqRx4Vz9sWrfACPuZk2RnkhAo1wSYHxnZB0q2ephaL1YaanF%2BzpKUF%2FQcIg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(localDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf((int)gpsTransfer.getxLat()), "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf((int)gpsTransfer.getyLng()), "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        String data= sb.toString();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(data);
        // response 키를 가지고 데이터를 파싱
        JSONObject parse_response = (JSONObject) obj.get("response");
        // response 로 부터 body 찾기
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        // body 로 부터 items 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items");

        // items로 부터 itemlist 를 받기
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        JSONObject weatherInfo = (JSONObject) parse_item.get(0);

//        System.out.println(parse_item);
        List<String> T1H = new ArrayList<>(); // 기온
        List<String> PTY = new ArrayList<>(); // 강수형태
        List<String> SKY = new ArrayList<>(); // 하늘형태

        for(int i = 0; i < parse_item.size(); i++){
            weatherInfo = (JSONObject) parse_item.get(i);

            if(weatherInfo.get("category").equals("T1H")){
                T1H.add((String) weatherInfo.get("fcstValue"));
            }
            if(weatherInfo.get("category").equals("PTY")){
                PTY.add((String) weatherInfo.get("fcstValue"));
            }
            if(weatherInfo.get("category").equals("SKY")){
                SKY.add((String) weatherInfo.get("fcstValue"));
            }

        }
        System.out.println("기온 : " +T1H.get(0));
        System.out.println("강수 형태 : " + PTY.get(0));
        System.out.println("하늘 형태 : " + SKY.get(0));
//        LocalDate seoulNow = LocalDate.now(ZoneId.of("Asia/Seoul"));
//        System.out.println(String.valueOf(seoulNow.getYear())+String.valueOf(seoulNow.getMonthValue())+String.valueOf(seoulNow.getDayOfMonth()));



    }
}
