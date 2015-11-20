package com.wisdom.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdom.common.model.Model;
public class WriteXML {
	public static void WriteXML(String date,String fa,String id){
		
		ObjectMapper mapper = new ObjectMapper();
		Model model=new Model();
		try {
			List<Model>list= (List) mapper.readValue(date, new TypeReference<List<Model>>() {});
			for(int i=0; i<list.size();i++){
				System.out.println(list.get(i).getAmount() +list.get(i).getDescription()+list.get(i).getSupplier());
			}
			Element ele = new Element("information"); 
			Document Doc = new Document(ele);   
			
			  for (int i=0; i<list.size();i++) { 
				  Element elements = new Element("data");  
				   //在生成的名称为information的跟元素下生成下一级元素标签名称为data				 
				  elements.addContent(new Element("supplier").setText(list.get(i).getSupplier()));
				      elements.addContent(new Element("description").setText(list.get(i).getDescription()));  
				      elements.addContent(new Element("amount").setText(list.get(i).getAmount()));  
				    //将已经设置好值的elements赋给root
				      ele.addContent(elements);  
				      
				     }
			  
			  ele.addContent(new Element("fa").setText(fa));
		
			  ele.addContent(new Element("id").setText(id));
			 
			  XMLOutputter XMLOut = new XMLOutputter();  
			//将生成的xml文档Doc输出到c盘的test.xml文档中
			   XMLOut.output(Doc, new FileOutputStream(System.getProperty("user.dir")+File.separator+"src"
						+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"files"+File.separator+"test.xml"));
			  
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir")+File.separator+"src"
				+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"files");
		String date="[{\"supplier\":\"supplier\",\"description\":\"des\",\"amount\":\"11\"},{\"supplier\":\"supplier\",\"description\":\"des2\",\"amount\":\"22\"}]";
		WriteXML(date, "fa", "11");
	}

}
