package com.lgcns.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = -5610428201110928860L;
	
	private static String RESPONSE_RESULT_OK = "{\"Result\":\"OK\"}";
	private static String RESPONSE_RESULT_QUEUE_EXIST = "{\"Result\":\"Queue Exist\"}";
	private static String RESPONSE_RESULT_QUEUE_FULL = "{\"Result\":\"Queue Full\"}";
	private static String RESPONSE_RESULT_NO_MESSAGE = "{\"Result\":\"No Message\"}";
//	private static String RESPONSE_RESULT_NO_SERVIC = "{\"Result\":\"Service Unavailable\"}";
	private static String RESPONSE_MESSAGE_FORMAT = "{\"Result\":\"Ok\",\"MessageID\":\"%s\",\"Message\":\"%s\"}";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().substring(1);
		String[] cmd = url.split("/");

		resp.setStatus(200);

		System.out.println(cmd[0]);

		// RECEIVE, DLQ
		switch(cmd[0]) {
		case "RECEIVE":
			Message msg = processReceive(cmd);
			if (msg == null) {
				resp.getWriter().write(RESPONSE_RESULT_NO_MESSAGE);
			} else {
				resp.getWriter().write(String.format(RESPONSE_MESSAGE_FORMAT, msg.messageId, msg.message));
			}
			break;

//		case "DLQ":
//			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().substring(1);
		String[] cmd = url.split("/");
		String bodyJson = getJsonFromReqeustBody(req);
			
		resp.setStatus(200);
		
		System.out.println(cmd[0]);
		
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		map = (Map<String,Object>) gson.fromJson(bodyJson, map.getClass());
		
		// CREATE, SEND, ACK, FAIL
		switch(cmd[0]) {
		case "CREATE":
			if (!processCreate(cmd, map)) {
				resp.getWriter().write(RESPONSE_RESULT_QUEUE_EXIST);
			} else {
				resp.getWriter().write(RESPONSE_RESULT_OK);
			}
			break;
		case "SEND":
			if (!processSend(cmd, map)) {
				resp.getWriter().write(RESPONSE_RESULT_QUEUE_FULL);
			} else {
				resp.getWriter().write(RESPONSE_RESULT_OK);
			}
			break;
		case "ACK":
			processAck(cmd, map);
			resp.getWriter().write(RESPONSE_RESULT_OK);
			break;
		case "FAIL":
			processFail(cmd, map);
			resp.getWriter().write(RESPONSE_RESULT_OK);
			break;
		}
	}

	private boolean processCreate(String[] cmd, Map<String,Object> map) {
		if (RunManager.queues.containsKey(cmd[1])) {
			System.out.println("Queue Exist");
			return false;
		}
		
		int size = ((Double)map.get("QueueSize")).intValue();
		SizedQueue q = new SizedQueue(size);
		RunManager.queues.put(cmd[1], q);
		return true;
	}

	private boolean processSend(String[] cmd, Map<String,Object> map) {
		SizedQueue q = RunManager.queues.get(cmd[1]);
		if (q.isFull()) {
			System.out.println("Queue Full");
			return false;
		}
		
		q.push(new Message(UUID.randomUUID().toString(), map.get("Message").toString()));
		
		return true;
	}

	private Message processReceive(String[] cmd) {
		Message msg = RunManager.queues.get(cmd[1]).poll();
		System.out.println("Receive: " + ((msg != null) ? msg.toString() : "null"));
		return msg;
	}

	private void processAck(String[] cmd, Map<String,Object> map) {
		SizedQueue q = RunManager.queues.get(cmd[1]);
		q.remove(cmd[2]);
		System.out.println("Remove: " + cmd[2]);
	}

	private void processFail(String[] cmd, Map<String,Object> map) {
		SizedQueue q = RunManager.queues.get(cmd[1]);
		q.remove(cmd[2]);
		System.out.println("Recover: " + cmd[2]);
	}
	
	private static String getJsonFromReqeustBody(HttpServletRequest request) throws IOException {
		 
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
 
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
 
        body = stringBuilder.toString();
        return body;
    }
}


class SizedQueue {
	private List<Message> list = new ArrayList<>();
	private ConcurrentList<Message> queue = new ConcurrentList<Message>(list);
	private int lengthLimits = 0;
	
	
	public SizedQueue(int length) {
		lengthLimits = length;
	}

	public synchronized void push(Message msg) {
		queue.add(msg);
	}

	public synchronized Message poll() {
		Message msg = null;
		Iterator<Message> ir = queue.iterator();
		
		while (ir.hasNext()) {
			msg = ir.next(); 
			
			if (msg.status == MessageStatus.READY) {
				msg.status = MessageStatus.RECEIVE_PENDING;
				return msg;
			}
		}
		
		return msg;
	}
	
	public synchronized void remove(String uuid) {
		Message msg = null;
		Iterator<Message> ir = queue.iterator();
		
		while (ir.hasNext()) {
			msg = ir.next(); 
			
			if (msg.messageId.equals(uuid)) {
				ir.remove();
				break;
			}
		}
	}
	
	public synchronized void recover(String uuid) {
		Message msg = null;
		Iterator<Message> ir = queue.iterator();
		
		while (ir.hasNext()) {
			msg = ir.next(); 
			
			if (msg.messageId.equals(uuid)) {
				msg.status = MessageStatus.READY;
				msg.FailCount++;
				break;
			}
		}
	}
	
	public boolean isFull() {
		return (lengthLimits <= queue.size()) ? true : false;
	}
}

class Message implements Serializable {
	private static final long serialVersionUID = -200346811228212898L;
	
	public String messageId;
	public String message;
	public MessageStatus status = MessageStatus.READY;
	public int FailCount = 0;
	
	
	public Message(String messageId, String message) {
		this.messageId = messageId;
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("[MSG] Message ID: %s, Message: %s", messageId, message);
	}
}

enum MessageStatus {
	READY,
	RECEIVE_PENDING,
	RECEIVED,
	WAIT
}
