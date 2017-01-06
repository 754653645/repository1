package com.WheatherSpider;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.net.URL;



import org.apache.commons.httpclient.Header;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.NameValuePair;  
import org.apache.commons.httpclient.methods.PostMethod;  

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WheatherSpider extends Application
{
	 CheckBox cb = null;
	 ListView<String> listView = null;
	 ObservableList<String> strList = null;
	 ToggleGroup group =null;
	 TextField position = null;
	 String URL = null;
	 String pattern = null;
	 String getTime = null;
	 String[] time = new String[4];
	 String[] weather = new String[4];
	 String[] temperature = new String[4];
	 String[] windDir = new String[4];
	 String[] windStrength = new String[4];
	 Button autoPositionButton = null;
	       static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	 
	 		public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		     // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");
		    }
		  }
	 
	 public static void main(String[] args) 
	{

		launch(args);
	}

	
	
	
	// javafx UI thread
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Wheather Spider");
		stage.setResizable(false);
		// use stackpane as root to combine the self-defined image as background
		StackPane root = new StackPane();
		AnchorPane rootPane = new AnchorPane();
		
		
		// add a image as my application background
		//Image backgroudImage = new Image(this.getClass().getResourceAsStream("c.jpg"));
		//ImageView backgroundView = new ImageView();
	//	backgroundView.setImage(backgroudImage);
	//	backgroundView.resize(20.0, 20.0);
	//	backgroundView.setFitHeight(500);
	//	backgroundView.setFitWidth(500);
		
		
		// button for getting position
		autoPositionButton = new Button("自动获取地址");
		AnchorPane.setRightAnchor(autoPositionButton, 5.0);
		AnchorPane.setTopAnchor(autoPositionButton, 5.0);
	    position = new TextField("please text your phone number");
	    position.setFont(new Font("Segoe UI",11));
	    position.setStyle("-fx-text-fill: ORANGE");
		AnchorPane.setTopAnchor(position, 5.0);
		AnchorPane.setRightAnchor(position, 200.0);
		autoPositionButton.setOnMouseClicked( 
									new EventHandler<MouseEvent>()
									{

										@Override
										public void handle(MouseEvent arg0) 
										{
											// TODO Auto-generated method stub
											//get IP and use the HTTPGET to construct URL to get information from the Baidu API
											JSONObject json;
											try {
											
												json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=jFPNCFMwONGgb2tA2yKHjXiy");
												System.out.println(json.toString());
												String temp = ((JSONObject) json.get("content")).get("address").toString();
												autoPositionButton.setText(temp.substring(0, temp.length()-1));
											} catch (IOException
													| JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
											
									});
		
		 MenuButton menuBtn = new MenuButton("place");
		 MenuItem item1 = new MenuItem("北京");
		 item1.setOnAction( 
				new EventHandler<ActionEvent>()
				{
					public void handle(ActionEvent arg0) 
					{	
						autoPositionButton.setText("北京");
						// TODO Auto-generated method stub
						//get IP and use the HTTPGET to construct URL to get information from the Baidu API
						
					}
						
				});
		
		MenuItem item2 = new MenuItem("天津");
		 item2.setOnAction( 
					new EventHandler<ActionEvent>()
					{
						public void handle(ActionEvent arg0) 
						{	
							autoPositionButton.setText("天津");
							// TODO Auto-generated method stub
							//get IP and use the HTTPGET to construct URL to get information from the Baidu API
							
						}
							
					});
		MenuItem item3 = new MenuItem("上海");
		 item3.setOnAction( 
					new EventHandler<ActionEvent>()
					{
						public void handle(ActionEvent arg0) 
						{	
							autoPositionButton.setText("上海");
							// TODO Auto-generated method stub
							//get IP and use the HTTPGET to construct URL to get information from the Baidu API
							
						}
							
					});
		MenuItem item4 = new MenuItem("重庆");
		 item4.setOnAction( 
					new EventHandler<ActionEvent>()
					{
						public void handle(ActionEvent arg0) 
						{	
							autoPositionButton.setText("重庆");
						}
							
					});
		menuBtn.getItems().addAll(item1,item2,item3,item4);
		AnchorPane.setTopAnchor(menuBtn, 5.0);
		AnchorPane.setLeftAnchor(menuBtn, 5.0);
		
		
		
		
		//define radio button 
		group = new ToggleGroup();
		
		RadioButton button1 = new RadioButton("手动模式");
		button1.setToggleGroup(group);
		button1.setSelected(true);
		button1.setUserData("manual");
		AnchorPane.setTopAnchor(button1, 30.0);
		AnchorPane.setLeftAnchor(button1, 5.0);
		
		RadioButton button2 = new RadioButton("监控模式");
	   	button2.setToggleGroup(group);
	   	button2.setUserData("monitor");
	   	AnchorPane.setTopAnchor(button2, 30.0);
	   	AnchorPane.setLeftAnchor(button2, 105.0);
	   	group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	        public void changed(ObservableValue<? extends Toggle> ov,
	            Toggle old_toggle, Toggle new_toggle) {
	          if (group.getSelectedToggle() != null) {
	        	  	pattern = group.getSelectedToggle().getUserData().toString();
	          }
	        }
	      });
	   	
	   	
	   	
	   	
	   	////////////////////////list view  to show update information
		 strList = FXCollections.observableArrayList();
		 listView = new ListView<>(strList);
		 listView.setItems(strList);
		 listView.setPrefSize(200, 300);
		 AnchorPane.setLeftAnchor(listView, 50.0);
		 AnchorPane.setBottomAnchor(listView, 110.0);
		 AnchorPane.setRightAnchor(listView, 50.0);
		 rootPane.setStyle(" -fx-background-color: #1d1d1d");
		 
		 
	   	////////////////////button for starting  working    
		Button startBtn = new Button("start");
		AnchorPane.setBottomAnchor(startBtn, 30.0);
		AnchorPane.setLeftAnchor(startBtn, 245.0);
		startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, 
									new EventHandler<MouseEvent>(){

										@Override
										public void handle(MouseEvent arg0) {
											// TODO Auto-generated method stub
											checkURL();
											if(pattern == "manual")
											{
												System.out.println(URL);
												//System.out.println(getInformation());
												strList.add(getInformation());
												writeToExcel();
												if(cb.isSelected())
												sendMessage();
											}
											if(pattern == "monitor")
											{
												final long timeInterval = 1000;
												Task<Void> task = new Task<Void>(){
													public Void call(){
														 while(true){
															 Calendar c = Calendar.getInstance();
															 int hour = c.get(Calendar.HOUR_OF_DAY);
															 int minute = c.get(Calendar.MINUTE);
															 System.out.println(hour+"   "+minute);

															// if((hour==20 && minute == 17) || (hour==22 && minute == 0)){
																 String s1 = getInformation();
																 strList.add(s1);
																 writeToExcel();
												              if(cb.isSelected())
												              {
												            	  sendMessage();
												              }
														//	 }
														 try{
															 Thread.sleep(timeInterval);
														 }catch(InterruptedException e){
															// System.out.println("wrong");

															e.printStackTrace();				
														 }
														 }
													}
												};
												Thread thread = new Thread(task);
												thread.start();
											}
										}
										
			
								});
		 
		 cb = new CheckBox("send message to your phone");
		 cb.setStyle( "-fx-border-color: white; "  
				  + "-fx-font-size: 11;"  
				  + "-fx-border-insets: -5; "  
				  + "-fx-border-radius: 5;"  
				  + "-fx-border-style: dotted;"  
				  + "-fx-border-width: 2;" 
				 );
		AnchorPane.setBottomAnchor(cb, 70.0); 
		AnchorPane.setLeftAnchor(cb, 200.0);
		
		
		 
		rootPane.getChildren().addAll(autoPositionButton,menuBtn,position,button1,button2,listView,startBtn,cb);
		
		root.getChildren().addAll( rootPane);
		Scene s = new Scene(root, 500, 500);
		s.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setScene(s);
		stage.show();
		
		
	}
		
	
	void checkURL()
	{
		switch(autoPositionButton.getText().toString())
		{
		case ("北京"):{ URL = "http://www.weather.com.cn/weather1d/101010100.shtml#dingzhi1"; break;}
		case ("天津"):{ URL = "http://www.weather.com.cn/weather1d/102010100.shtml#dingzhi1"; break;}
		case ("上海"):{ URL = "http://www.weather.com.cn/weather1d/103010100.shtml#dingzhi1"; break;}
		case ("重庆"):{ URL = "http://www.weather.com.cn/weather1d/104010100.shtml#dingzhi1"; break;}
		}
	}
	
	String getInformation()
	{
		int i=0, j=0,k=0, l=0, m=0;
		try{
		HttpGet hGet = new HttpGet(URL);
		org.apache.http.client.HttpClient hClient = new DefaultHttpClient();
		HttpResponse hResponse = hClient.execute(hGet);
		System.out.println(hResponse.getStatusLine().getStatusCode());
		HttpEntity hEntity = hResponse.getEntity();
		InputStream is = hEntity.getContent();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
//		StringBuilder sBuilder = new StringBuilder();		
        String line = null;
		while((line = bReader.readLine()) != null ){
			if(line.contains("<!-- 嵌套7天3-6小时精细化预报模块 开始-->"))
			  break;
		}
		//////获取所需字段
		///////////
		//////////
		//////////
		////////////
		
		while((line = bReader.readLine()) != null ){
			 if(line.contains("<input type=\"hidden\""))
				   getTime = line.substring(line.indexOf("value")+7,line.indexOf("更新"));;
			if(m==4)
				break;
			if(line.startsWith("<i>") && i<=3)
				time[i++] = line.substring("<i>".length(), "<i>".length()+7);
			if(line.startsWith("<em>") && j<=3)
				weather[j++] = line.substring("<em>".length(), line.indexOf("</em>"));	
			if(line.startsWith("<span>")  && k<=3)
				temperature[k++] = line.substring("<span>".length(), line.indexOf("</span>"));	
			if(line.startsWith("<b>")  && l<=3)
				windDir[l++] = line.substring("<b>".length(), line.indexOf("</b>"));
			if(line.startsWith("<strong>"))
				windStrength[m++] = line.substring("<strong>".length(), line.indexOf("</strong>"));
			
		}
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return (getTime+" "+position.getText()+"气象更新成功");
	}
	
	void writeToExcel()
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("WheatherSpider");
		sheet.setDefaultColumnWidth((short)15);
		HSSFCellStyle style = wb.createCellStyle();
		HSSFRow row =sheet.createRow(0);
		HSSFCell cell = row.createCell((short)0);
		cell.setCellValue("time");
		cell.setCellStyle(style);
		
		cell = row.createCell((short)1);
		cell.setCellValue("weather");
		cell.setCellStyle(style);
		
		cell = row.createCell((short)2);
		cell.setCellValue("temperature");
		cell.setCellStyle(style);
		
		cell = row.createCell((short)3);
		cell.setCellValue("windDir");
		cell.setCellStyle(style);
		
		cell = row.createCell((short)4);
		cell.setCellValue("windStrength");
		cell.setCellStyle(style);
		
		cell = row.createCell((short)5);
		cell.setCellValue("positon");
		cell.setCellStyle(style);
		short count = 0;
		for (; count < 4; count++) {  
	        row = sheet.createRow(count + 1);  
	        row.createCell(0).setCellValue(time[count]);  
	        row.createCell(1).setCellValue(weather[count]);  
	        row.createCell(2).setCellValue(temperature[count]); 
	        row.createCell(3).setCellValue(windDir[count]); 
	        row.createCell(4).setCellValue(windStrength[count]);
	        row.createCell(5).setCellValue(position.getText());;
	    }  
		try {  
	        
	        FileOutputStream out = new FileOutputStream(".//WheatherSpider.xls");  
	        wb.write(out);  
	        out.close();  
	       
	    } catch (FileNotFoundException e1) {  
	        System.out.println( "导出失败!");  
	        e1.printStackTrace();  
	    } catch (IOException e2) {  
	        System.out.println( "导出失败!");  
	        e2.printStackTrace();  
	}
	}


	void sendMessage()
	{
		String message = "";
		for(int i = 0; i < 4; i++)
		{
			message+= autoPositionButton.getText().toString();
			message+= " ";
			message+= time[i];
			message+= " ";
			message+= weather[i];
			message+= " ";
			message+= temperature[i];
			message+= " ";
			message+= windDir[i];
			message+= " ";
			message+= windStrength[i];
			message+= "\n";
		}
		org.apache.commons.httpclient.HttpClient client = new HttpClient();  
        PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/");  
        post.addRequestHeader("Content-Type",  
                "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码  
        NameValuePair[] data = { new NameValuePair("Uid", "fengye44"), // 注册的用户名  
                new NameValuePair("Key", "a561e30522cecbdac242"), // 注册成功后,登录网站使用的密钥  
                new NameValuePair("smsMob", position.getText().toString()), // 手机号码  
               new NameValuePair("smsText", message) };//设置短信内容        
        post.setRequestBody(data);  
  
        try {
			client.executeMethod(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        Header[] headers = post.getResponseHeaders();  
        int statusCode = post.getStatusCode();  
        System.out.println("statusCode:" + statusCode);  
        for (Header h : headers)
        {  
        System.out.println(h.toString());
        }  
        String result = null;
		try {
			result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println(result);  
    	post.releaseConnection();  
	}
	


}