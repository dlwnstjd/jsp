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

@ServerEndpoint("/chatting")	//웹소켓의 서버클래스 지정
public class ChatController {
	private ChatDao dao = new ChatDao();
	private static Set<Session> clients	=
			Collections.synchronizedSet(new HashSet<Session>());
	@OnMessage	//client에서 메세지 수신
	public void onMessage(String message, Session session) throws IOException{
		//session: 메세지 전송한 session 객체
		//message: 클라이언트가 전송한 메세지, loginid: 입력ㅁ
		synchronized (clients) {	//스레드 동기화
			dao.insert(message);
			//broadcast: 모든클라이언트에 메세지 전송
			for(Session client : clients) {	
				if(!client.equals(session)) {
					//클라이언트로 메세지 전송
					client.getBasicRemote().sendText(message);
				}
			}			
		}
	}
	@OnOpen	//cilent가 연결 되었을때
	public void onOpen(Session session) {
		clients.add(session);
	}
	@OnClose
	public void onClose(Session session) {
		clients.remove(session);
	}
}
