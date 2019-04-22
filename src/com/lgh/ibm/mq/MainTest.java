//package com.lgh.ibm.mq;
//
//public class MainTest {
//	@Test
//	public void sendMsg() {
//		List<Push_App> list = appDao.findTenDatas();
//		System.out.println(list.size());
//		for (Push_App app : list) {
// 
//			System.out.println(app.getSerial() + ":" + app.getMsg());
// 
//		}
//		
//		try {
//			SendDataToMQ.openMQ("AppMsg", "SendMsg");
//			SendDataToMQ.sendMsgs(list);
//			//SendDataToMQ.sendMsg(list.get(0));
//			SendDataToMQ.closeMQ();
//			
//			
//		} catch (MQException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//		
//		
//	}
// 
//	@Test
//	public void receiveMsg(){
//		try {
//			
//			ReceiveDataFromMQ.openMQ("AppMsg", "SendMsg");
//			List<Object> list = ReceiveDataFromMQ.receiveAllMsgs();
//			//List<Object> list = ReceiveDataFromMQ.receivePartMsgs(10);
//			for(Object data : list){
//				
//				if(data instanceof Push_App){
//					Push_App app = (Push_App) data;
//					System.out.println("序列号:"+app.getSerial()+" 发送的消息是:" + app.getMsg());
//					
//				}
//				
//			}
//			ReceiveDataFromMQ.closeMQ();
//			
//			
//			
//		} catch (MQException e) {
//			e.printStackTrace();
//		} catch (StreamCorruptedException e) {
//			e.printStackTrace();
//		} catch (OptionalDataException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//}
