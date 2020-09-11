package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import model.mapper.BoardMapper;

public class BoardDao {
	private Class<BoardMapper> cls = BoardMapper.class;
	private Map<String,Object> map = new HashMap<>();
	
	public boolean delete(Board b){
		SqlSession session = MybatisConnection.getConnection();
		try {
			int cnt = session.getMapper(cls).delete(b);
			if(cnt>0) return true;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}		
		return false;
	}
	
	public boolean update(Board b){
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).update(b);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}		
		return false;
	}
	
	//게시물의 최대 번호 리턴
	public int maxnum()	{
		SqlSession session = MybatisConnection.getConnection();
		try {
			return session.getMapper(cls).maxnum();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}		
		return 0;
	}
	
	public boolean insert(Board board) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).insert(board);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}		
		return false;		
	}
	
	public int boardCount(String column, String find) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			if(column != null) {
				String[] cols = column.split(",");
				map.put("col1", cols[0]);
				if(cols.length > 1) {
					map.put("col2", cols[1]);
				}
				if(cols.length > 2) {
					map.put("col3", cols[2]);
				}
			}
			map.put("find", find);
			System.out.println(map);
			return session.getMapper(cls).boardCount(map);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}		
		return 0;		
	}
	
	public List<Board> list(int pageNum, int limit, String column, String find) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("start", (pageNum-1)*limit);
			map.put("limit", limit);
			//subject,name
			if(column != null) {
				String[] cols = column.split(",");
				map.put("col1", cols[0]);//subject
				if(cols.length > 1) {
					map.put("col2", cols[1]);//name
				}
				if(cols.length > 2) {
					map.put("col3", cols[2]);
				}
			}
			map.put("find", find);
			return session.getMapper(cls).select(map);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
		return null;		 	
	}
	
	public Board selectOne(int num) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			map.clear();
			map.put("num", num);
			return session.getMapper(cls).select(map).get(0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}		
		return null;	
	}
	
	public void readcntAdd(int num) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).readcntAdd(num);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
	}
	
	public void grpStepAdd(int grp, int grpstep) {
		SqlSession session = MybatisConnection.getConnection();
		try {
			session.getMapper(cls).grpStepAdd(grp,grpstep);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}			
	}

	public List<Map<String, Integer>> boardgraph() {
		SqlSession session = MybatisConnection.getConnection();
		List<Map<String,Integer>> list = null;
		try {
			list = session.getMapper(cls).graph();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
		return list;
	}
	public List<Map<String, Integer>> boardgraph2() {
		SqlSession session = MybatisConnection.getConnection();
		List<Map<String,Integer>> list = null;
		try {
			list = session.getMapper(cls).graph2();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			MybatisConnection.close(session);
		}
		return list;
	}
}
