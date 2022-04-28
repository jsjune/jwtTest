package com.sparta.jwtproject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class MountainApi {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B553662/peakPoiInfoService/getPeakPoiInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + "CUnaloj7dqGa0E7B1yinNSBe8arlqRx4Vz9sWrfACPuZk2RnkhAo1wSYHxnZB0q2ephaL1YaanF%2BzpKUF%2FQcIg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답 결과의 출력 방식을 xml, json 형태로 변환 제공될 수 있도록 함*/
        urlBuilder.append("&" + URLEncoder.encode("srchFrtrlNm","UTF-8") + "=" + URLEncoder.encode("관악산", "UTF-8")); /*검색하고자 하는 숲길명 “TEXT”*/
        urlBuilder.append("&" + URLEncoder.encode("srchPlaceTpeCd","UTF-8") + "=" + URLEncoder.encode("등산로입구", "UTF-8")); /*검색하고자 하는 장소유형코드 “TEXT” PEAK:봉우리 SPRING:샘터 SCENERY:경관자원 TOILET:화장실 REST:쉼터 CAMP:야영장 CULTURAL:문화자원 DANGER:위험지역 ENTRY:등산로입구 INFO:안내소 PHOTO:포토존 SHELTER:대피소 VIEW:조망점 WILD:야생동물 SIGN:갈림길 STORE:편의점 FOOD:음식점 PARK:주차장 TRANS:대중교통*/
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
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}