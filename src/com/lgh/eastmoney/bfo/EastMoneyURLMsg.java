package com.lgh.eastmoney.bfo;
/**
 * 股票涨跌的URL信息
 * @author liuguohu
 *
 */
public class EastMoneyURLMsg{
		private String host;
		private String listener;
		private String[] parameters;
		private String type;
		public EastMoneyURLMsg(String type,String host,String listener,String parameters){
			this.type = type;
			this.host = host;
			this.listener = listener;
			if(parameters!=null)
			 this.parameters = parameters.split("&");
		}
		
		
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("host:"+host+"  listener:"+listener+"  parameters:"+getParameters());
			return sb.toString();
		}
		public String getType(){
			return type;
		}
		public String getURL(){
			if(host!=null&&listener!=null){
				return this.host+this.listener+this.getParameters();
			}
			return null;
		}
		public String getHost() {
			return host;
		}
		public String getListener() {
			return listener;
		}
		public String getParameters() {
			StringBuffer sb = new StringBuffer();
			if(parameters!=null){
				for(int i=0;i<parameters.length;i++){
					sb.append(parameters[i]);
					if(i!=parameters.length-1){
						sb.append("&");
					}
				}
			}
			return sb.toString();
		}
		public void setParameter(String key,String value){
			if(parameters!=null){
				for(int i = 0;i<parameters.length;i++){
					String[] maps= parameters[i].split("=");
					if(maps!=null&&maps.length==2&&maps[0].trim().equals(key.trim())){
						parameters[i] = key+"="+value;
					}
				}
			}
		}
	}