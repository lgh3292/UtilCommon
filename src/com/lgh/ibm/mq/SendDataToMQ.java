//package com.lgh.ibm.mq;
//
//
//import java.io.IOException;
//import java.util.List;
// 
//import org.apache.log4j.Logger;
// 
//import com.ibm.mq.MQC;
//import com.ibm.mq.MQEnvironment;
//import com.ibm.mq.MQException;
//import com.ibm.mq.MQMessage;
//import com.ibm.mq.MQQueue;
//import com.ibm.mq.MQQueueManager;
//import com.tienon.message.entity.Push_App;
// 
//public class SendDataToMQ {
//	private static Logger log = Logger.getLogger(SendDataToMQ.class);
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
//		queue = manager.accessQueue(q, MQC.MQOO_OUTPUT);
//	}
// 
//	@SuppressWarnings("deprecation")
//	public static <T> void sendMsg(T data) throws MQException, IOException {
// 
//		MQMessage msg = new MQMessage();
// 
//		msg.writeObject(data);
// 
//		queue.put(msg);
//		manager.commit();
//		queue.close();
//		manager.disconnect();
// 
//		if (data instanceof Push_App) {
//			Push_App app = (Push_App) data;
//			log.info("批次号:" + app.getBatId() + "流水号:" + app.getSerial() + "内容:"
//					+ app.getMsg() + " 成功插入数据到MQ");
//		}
// 
//	}
// 
//	public static <T> void sendMsgs(List<T> datas) throws MQException,
//			IOException {
// 
//		for (T data : datas) {
// 
//			MQMessage msg = new MQMessage();
//			msg.writeObject(data);
//			queue.put(msg);
//			manager.commit();
//			if (data instanceof Push_App) {
//				Push_App app = (Push_App) data;
//				log.info("批次号:" + app.getBatId() + "流水号:" + app.getSerial()
//						+ "内容:" + app.getMsg() + " 成功插入数据到MQ");
//			}
//		}
// 
//		queue.close();
//		manager.disconnect();
// 
//	}
// 
//	public static void closeMQ() throws MQException {
// 
//		//manager.commit();//不要重复commit，否者会报错的
//		queue.close();
//		manager.disconnect();
// 
//	}
//}