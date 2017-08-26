package com.lgh.jms;

import java.util.Hashtable;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.lgh.jms.UserInfo;
import com.lgh.util.logging.LogUtil;

public class QueueReceive implements MessageListener {
	// Defines the JNDI context factory.
	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";

	// Defines the JNDI provider url.
	public final static String PROVIDER_URL = "t3://localhost:7001";

	// Defines the JMS connection factory for the queue.
	public final static String JMS_FACTORY = "jms/QueueConnectionFactory";

	// Defines the queue 用的是对应 QUEUE的JNDI名子
	public final static String QUEUE = "jms/fileQueue";

	private QueueConnectionFactory qconFactory;

	private QueueConnection qcon;

	private QueueSession qsession;

	private QueueReceiver qreceiver;

	private Queue queue;

	private boolean quit = false;

	/**
	 * Message listener interface.
	 * 
	 * @param msg
	 *            message
	 */
	public void onMessage(Message msg) {
		try {
			String msgText = "";
			double d = 0;

			if (msg instanceof TextMessage) {
				msgText = ((TextMessage) msg).getText();
			} else if (msg instanceof StreamMessage) {
				msgText = ((StreamMessage) msg).readString();
				d = ((StreamMessage) msg).readDouble();
			} else if (msg instanceof BytesMessage) {
				byte[] block = new byte[1024];
				((BytesMessage) msg).readBytes(block);
				msgText = String.valueOf(block);
			} else if (msg instanceof MapMessage) {
				msgText = ((MapMessage) msg).getString("name");
			} else if (msg instanceof ObjectMessage) {
				UserInfo ui = (UserInfo) ((ObjectMessage) msg).getObject();
				msgText = ui.getName();
				d = ui.getAge();
			}

			LogUtil.info("Message Received: " + msgText + "\t" + d);

			if (msgText.equalsIgnoreCase("quit")) {
				synchronized (this) {
					quit = true;
					this.notifyAll(); // Notify main thread to quit
				}
			}
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}

	/**
	 * Creates all the necessary objects for receiving messages from a JMS
	 * queue.
	 * 
	 * @param ctx
	 *            JNDI initial context
	 * @param queueName
	 *            name of queue
	 * @exception NamingException
	 *                if operation cannot be performed
	 * @exception JMSException
	 *                if JMS fails to initialize due to internal error
	 */
	public void init(Context ctx, String queueName) throws NamingException,
			JMSException {
		qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
		qcon = qconFactory.createQueueConnection();
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) ctx.lookup(queueName);
		qreceiver = qsession.createReceiver(queue);
		qreceiver.setMessageListener(this);
		qcon.start();
	}

	/**
	 * Closes JMS objects.
	 * 
	 * @exception JMSException
	 *                if JMS fails to close objects due to internal error
	 */
	public void close() throws JMSException {
		qreceiver.close();
		qsession.close();
		qcon.close();
	}

	/**
	 * main() method.
	 * 
	 * @param args
	 *            WebLogic Server URL
	 * @exception Exception
	 *                if execution fails
	 */

	public static void main(String[] args) throws Exception {

		InitialContext ic = getInitialContext();
		QueueReceive qr = new QueueReceive();
		qr.init(ic, QUEUE);

		System.out
				.println("JMS Ready To Receive Messages (To quit, send a \"quit\" message).");

		// Wait until a "quit" message has been received.
		synchronized (qr) {
			while (!qr.quit) {
				try {
					qr.wait();
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		qr.close();
	}

	private static InitialContext getInitialContext() throws NamingException {
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, PROVIDER_URL);
		return new InitialContext(env);
	}

	public String getEncoding() throws Exception {
		return "Hello World!";
	}
}
