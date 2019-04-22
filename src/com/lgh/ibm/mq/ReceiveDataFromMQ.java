//package com.lgh.ibm.mq;
//
//import java.io.IOException;
//import java.io.InvalidClassException;
//import java.io.OptionalDataException;
//import java.io.StreamCorruptedException;
//import java.util.ArrayList;
//import java.util.List;
// 
//import com.ibm.mq.MQC;
//import com.ibm.mq.MQEnvironment;
//import com.ibm.mq.MQException;
//import com.ibm.mq.MQMessage;
//import com.ibm.mq.MQQueue;
//import com.ibm.mq.MQQueueManager;
// 
//public class ReceiveDataFromMQ {
// 
//	static {
//		MQEnvironment.hostname = "localhost";
//		MQEnvironment.port = 1417;
//		MQEnvironment.CCSID = 1381;
//		MQEnvironment.channel = "Server";//服务器通道
// 
//	}
//	private static MQQueueManager manager = null;
//	private static MQQueue queue = null;
// 
//	public static void openMQ(String qMgr, String q) throws MQException {
//		manager = new MQQueueManager(qMgr);
//		//必须要写MQC.MQOO_INQUIRE，否者无法查询队列深度（即消息条数）
//		queue = manager.accessQueue(q, MQC.MQOO_INPUT_AS_Q_DEF|MQC.MQOO_INQUIRE);
//	}
// 
//	public static void closeMQ() throws MQException {
// 
//		
//		queue.close();
//		manager.disconnect();
// 
//	}
// 
//	public static Object receivedMsg() throws MQException,
//			StreamCorruptedException, OptionalDataException,
//			ClassNotFoundException, IOException {
// 
//		MQMessage msg = new MQMessage();
//		queue.get(msg);
// 
//		Object obj = msg.readObject();
// 
//		manager.commit();
//		return obj;
// 
//	}
// 
//	public static List<Object> receiveAllMsgs() throws MQException,
//			StreamCorruptedException, OptionalDataException,
//			ClassNotFoundException, IOException {
// 
//		List<Object> datas = new ArrayList<Object>();
//		
//		while ( queue.getCurrentDepth() > 0) {
// 
//			MQMessage msg = new MQMessage();
//			queue.get(msg);
//			datas.add(msg.readObject());
//			manager.commit();
//			
//		}
//		
//		return datas;
//	}
// 
//	public static List<Object> receivePartMsgs(int sum) throws MQException,
//			InvalidClassException, StreamCorruptedException,
//			OptionalDataException, ClassNotFoundException, IOException {
// 
//		List<Object> datas = new ArrayList<Object>();
// 
//		while (sum > 0) {
// 
//			MQMessage msg = new MQMessage();
//			queue.get(msg);
//			datas.add(msg.readObject());
//			manager.commit();
//			sum--;
// 
//		}
// 
//		return datas;
// 
//	}
// 
//}