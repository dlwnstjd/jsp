package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import model.ChatDao;

@ServerEndpoint("/chatting")	//�������� ����Ŭ���� ����
public class ChatController {
	private ChatDao dao = new ChatDao();
	private static Set<Session> clients	=
			Collections.synchronizedSet(new HashSet<Session>());
	@OnMessage	//client���� �޼��� ����
	public void onMessage(String message, Session session) throws IOException{
		//session: �޼��� ������ session ��ü
		//message: Ŭ���̾�Ʈ�� ������ �޼���, loginid: �Է¤�
		synchronized (clients) {	//������ ����ȭ
			dao.insert(message);
			//broadcast: ���Ŭ���̾�Ʈ�� �޼��� ����
			for(Session client : clients) {	
				if(!client.equals(session)) {
					//Ŭ���̾�Ʈ�� �޼��� ����
					client.getBasicRemote().sendText(message);
				}
			}			
		}
	}
	@OnOpen	//cilent�� ���� �Ǿ�����
	public void onOpen(Session session) {
		clients.add(session);
	}
	@OnClose
	public void onClose(Session session) {
		clients.remove(session);
	}
}
