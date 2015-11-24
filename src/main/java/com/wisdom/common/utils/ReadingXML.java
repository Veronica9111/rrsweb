package com.wisdom.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.sf.json.JSONArray;

public class ReadingXML {
	
	public String read(String path){
		  Element element = null;
		  String data="";
		  List<Map<String,String>>list=new ArrayList<>();
		  File f = new File(path);
		  DocumentBuilder db = null;
		  DocumentBuilderFactory dbf = null;
		   dbf = DocumentBuilderFactory.newInstance();
		   try {
			db = dbf.newDocumentBuilder();
			Document dt = db.parse(f);
			element = dt.getDocumentElement();
			 // 获得根节点
			  System.out.println("根元素：" + element.getNodeName());
			  NodeList childNodes = element.getChildNodes();
			  for (int i = 0; i < childNodes.getLength(); i++) {
				    Node node1 = childNodes.item(i);
				    Map<String,String> map=new HashMap();
				    //data------------------------------------------
				    if ("data".equals(node1.getNodeName())) {
				     NodeList nodeDetail = node1.getChildNodes();
				     for (int j = 0; j < nodeDetail.getLength(); j++) {
				      Node detail = nodeDetail.item(j);
				      if ("supplier".equals(detail.getNodeName()))
				      {
				    	  map.put("supplier", detail.getNodeName());
				       System.out.println("supplier: " + detail.getTextContent());}
				      else if ("description".equals(detail.getNodeName())) 
				      {
				       System.out.println("description: " + detail.getTextContent());
				       map.put("description", detail.getTextContent());
				      }
				      else if ("amount".equals(detail.getNodeName())) {
				       System.out.println("amount: " + detail.getTextContent());
				       map.put("amount", detail.getTextContent());
				       list.add(map);
				      }
				     
				     }
				     
				    }
				    
				    if ("fa".equals(node1.getNodeName())) {
					     NodeList nodeDetail = node1.getChildNodes();
					     
					      Node detail = nodeDetail.item(0);
					      if ("fa".equals(detail.getNodeName())) 
					       System.out.println("fa: " + detail.getTextContent());
					      if(detail.getTextContent().equals("\n\t\t")){
					    	  map.put("fa", "");
					      }else{
					    	  map.put("fa", detail.getTextContent());
					      }
					      
					     list.add(map);
					    }
				    if ("id".equals(node1.getNodeName())) {
					     NodeList nodeDetail = node1.getChildNodes();
					   
					      Node detail = nodeDetail.item(0);
					      if ("id".equals(detail.getNodeName())) 
					       System.out.println("id: " + detail.getTextContent());
					      if(detail.getTextContent().equals("\n\t\t")){
					    	  map.put("id", "");
					      }else{
					    	  map.put("id", detail.getTextContent());
					      }
					      list.add(map);
					    }
				   }
			   data = JSONArray.fromObject(list).toString();
			  System.out.println(data);
			 System.out.println(list.size());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		   return data;
	}
//	public static void main(String[] args) {
//		ReadingXML rx=new ReadingXML();
//		rx.read();
//	}

}
