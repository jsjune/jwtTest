package com.sparta.jwtproject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLEncoder;

public class TestApi {

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static void main(String[] args) {
        try{
                StringBuilder urlBuilder = new StringBuilder("http://openapi.forest.go.kr/openapi/service/cultureInfoService/gdTrailInfoOpenAPI"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + "CUnaloj7dqGa0E7B1yinNSBe8arlqRx4Vz9sWrfACPuZk2RnkhAo1wSYHxnZB0q2ephaL1YaanF%2BzpKUF%2FQcIg%3D%3D"); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*페이지당 표시 항목 수*/
                String url = new URL(urlBuilder.toString()).toString();

                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                // root tag
                doc.getDocumentElement().normalize();
                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = nList.item(temp);
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){

                        Element eElement = (Element) nNode;
                        System.out.println(temp+1);
                        System.out.println("산명 : " + getTagValue("mntnm", eElement));
                        System.out.println("100대명산 선정이유 : " + getTagValue("aeatreason", eElement));
                        System.out.println("산정보내용 : " + getTagValue("details", eElement));
                        System.out.println("산정보높이 : " + getTagValue("mntheight", eElement));
                        System.out.println("산정보소재지 : " + getTagValue("areanm", eElement));
                        System.out.println("산정보부제 : " + getTagValue("subnm", eElement));
                        System.out.println("코스 : " + getTagValue("etccourse", eElement));

                    }	// for end
                }	// if end

        } catch (Exception e){
            e.printStackTrace();
        }	// try~catch end
    }	// main end


}
