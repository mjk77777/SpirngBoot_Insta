package com.cos.photogramstart.util;

public class Script {
	
	public static String back(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");   //alert 창 띄우고
		sb.append("history.back();");   // 뒤로 가는
		sb.append("</script>");
		return sb.toString();   //script 코드 리턴하는 
	}

}
