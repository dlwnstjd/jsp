package model;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.ChatMapper;

public class ChatDao { 
	private Class<ChatMapper> cls = ChatMapper.class;
	private Map<String,Object> map = new HashMap<>();
	public void insert(String message) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			String[] msg = message.split(":");
			map.put("id", msg[0].trim());
			map.put("content", msg[1].trim());
			System.out.println(message);
			session.getMapper(cls).insert(map);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}		
	}
}
