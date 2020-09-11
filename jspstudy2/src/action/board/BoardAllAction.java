package action.board;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oreilly.servlet.MultipartRequest;

import action.ActionForward;
import model.Board;
import model.BoardDao;
import model.MybatisConnection;

/*
	1. �Ķ���� ���� model.Board ��ü ����.
		useBean ���Ұ�: request ������ �Ķ���Ϳ� ��Ŭ������ ������Ƽ ��
						request ��ü�� ����� �� ����
	2. �Խù� ��ȣ num ���� ��ϵ� num�� �ִ밪�� ��ȸ. �ִ���+1 ��ϵ� �Խù��� ��ȣ.
		db���� maxnum�� ���ؼ� +1 ������ num �����ϱ�
	3. board ������ db�� ����ϱ�.
		��ϼ���: �޼��� ���. list.jsp ������ �̵�
		��Ͻ���: �޼��� ���. writeForm.jsp ������ �̵�
 */
public class BoardAllAction {
	BoardDao dao = new BoardDao();
	public ActionForward hello(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("hello", "Hello World");
		return new ActionForward();
	}

	public ActionForward write(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msg = "�Խù� ��� ����";
		String url = "writeForm.do";
		String path = request.getServletContext().getRealPath("/") + "model1/board/file/";
		try {
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			MultipartRequest multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "euc-kr");
			Board board = new Board();
			board.setName(multi.getParameter("name"));
			board.setPass(multi.getParameter("pass"));
			board.setSubject(multi.getParameter("subject"));
			board.setContent(multi.getParameter("content"));
			board.setFile1(multi.getFilesystemName("file1"));
			BoardDao dao = new BoardDao();
			int num = dao.maxnum(); // board table���� num �÷��� �ִ밪 ����

			board.setNum(++num);
			board.setGrp(num);
			if (dao.insert(board)) {
				msg = "�Խù� ��� ����";
				url = "list.do";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

	/*
	 * 1. ���������� 10���� �Խù��� ���. pageNum �Ķ���Ͱ��� ���� => ���� ��� 1�� ����. 2. �ֱ� ��ϵ� �Խù��� ���� ����
	 * ��ġ. 3. db���� �ش� �������� ��µ� ������ ��ȸ�Ͽ� list ��ü�� ���� list ��ü�� request�� ��ü�� �Ӽ����� ����Ͽ�
	 * list.jsp�� ������ �̵�
	 */
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		} catch (NumberFormatException e) {		}
		String column = request.getParameter("column");
		String find = request.getParameter("find");
		if(column == null || column.trim().equals("")) {//column���� ����
			column = null;
			find = null;
		}
		if(find == null || find.trim().equals("")) {//find���� ����
			column = null;
			find = null;
		}
		int limit = 10;// ���������� ����� �Խù� �Ǽ�
		BoardDao dao = new BoardDao(); 
		// ��ϵ� ��ü �Խù��� �Ǽ� �Ǵ�, �˻��� �Խù��� �Ǽ�
		int boardcount = dao.boardCount(column,find);
		// list: ȭ�鿡 ����� �Խñ� ���� ���
		List<Board> list = dao.list(pageNum, limit,column,find);
		int maxpage = (int) ((double) boardcount / limit + 0.95);
		int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1;// ������������ȣ
		int endpage = startpage + 9;// ���������� ��ȣ

		if (endpage > maxpage)
			endpage = maxpage;
		int boardnum = boardcount - (pageNum - 1) * limit;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sf.format(new Date());

		request.setAttribute("boardcount", boardcount);
		request.setAttribute("list", list);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("boardnum", boardnum);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("today", today);
		return new ActionForward();
	}

	public ActionForward info(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 1. num �Ķ���� ���� 2. num���� �Խù��� db���� ��ȸ. 3. num���� �Խù��� ��ȸ�� ������Ű��. 4. ��ȸ�� �Խù���
		 * ȭ�鿡 ���
		 */
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDao dao = new BoardDao();
		Board b = dao.selectOne(num); // �ش� �Խù� ��ȸ
		if (request.getRequestURI().contains("board/info.do")) {
			dao.readcntAdd(num); // �ش�Խù� ��ȸ �Ǽ� 1����
		}
		request.setAttribute("b", b);
		return new ActionForward();
	}

	public ActionForward reply(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		Board b = new Board();
		b.setName(request.getParameter("name"));
		b.setPass(request.getParameter("pass"));
		b.setSubject(request.getParameter("subject"));
		b.setContent(request.getParameter("content"));
		b.setNum(Integer.parseInt(request.getParameter("num")));
		b.setGrp(Integer.parseInt(request.getParameter("grp")));
		b.setGrplevel(Integer.parseInt(request.getParameter("grplevel")));
		b.setGrpstep(Integer.parseInt(request.getParameter("grpstep")));
		String msg = "�亯��Ͻ� �����߻�";
		String url = "replyForm.do?num=" + b.getNum();

		BoardDao dao = new BoardDao();
//		���� ��ϵ� ����� ���� �ٷ� �Ʒ��� ��ȸ�ǵ��� db ����
		dao.grpStepAdd(b.getGrp(), b.getGrpstep());
		int grplevel = b.getGrplevel();
		int grpstep = b.getGrpstep();
		int num = dao.maxnum();// num�� �ִ밪 ����
		b.setNum(++num);
		b.setGrplevel(grplevel + 1);
		b.setGrpstep(grpstep + 1);
		if (dao.insert(b)) {
			msg = "�亯��� �Ϸ�";
			url = "list.do";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}
	
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Board board = new Board();
		String path = request.getServletContext().getRealPath("/") + "model1/board/file/";
		MultipartRequest multi = new MultipartRequest(request,path,10*1024*1024,"euc-kr");
		board.setNum(Integer.parseInt(multi.getParameter("num")));
		board.setName(multi.getParameter("name"));
		board.setPass(multi.getParameter("pass"));
		board.setSubject(multi.getParameter("subject"));
		board.setContent(multi.getParameter("content"));
		board.setFile1(multi.getFilesystemName("file1"));
		if(board.getFile1()==null || board.getFile1().equals("")){
			board.setFile1(multi.getParameter("file2"));
		}
		//2. ��й�ȣ ����
		BoardDao dao = new BoardDao();
		//dbBoard: ������ ���� ����. ��й�ȣ ������ ���
		Board dbBoard = dao.selectOne(board.getNum());
		String msg = "��й�ȣ�� Ʋ�Ƚ��ϴ�.";
		String url = "updateForm.do?num=" + board.getNum();
		if(board.getPass().equals(dbBoard.getPass())){
			if(dao.update(board)){ 
				msg =  "�Խù� ���� �Ϸ�";
				url = "info.do?num=" + board.getNum();
			}else{
				msg = "�Խù� ��������";
			}
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");		
	}
	
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		String pass = request.getParameter("pass");

		BoardDao dao = new BoardDao();
		Board b = dao.selectOne(num);
		String msg = "��й�ȣ�� Ʋ�Ƚ��ϴ�.";
		String url = "deleteForm.do?num=" + num;
		if(b==null){
			msg = "���� �Խñ��Դϴ�.";
			url = "list.do";
		}else{ 
			if(pass.equals(b.getPass())){
				if(dao.delete(b)){
					msg =  "�Խù� ���� �Ϸ�";
					url = "list.do";
				}else{ 
					msg = "�Խù� ���� ����";
					url = "info.do?num=" + num;
				}
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}
	public ActionForward imgupload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getServletContext().getRealPath("/")
				+ "model2/board/imgfile/";
		File f = new File(path);
		if(!f.exists()) f.mkdir();
		MultipartRequest multi = new MultipartRequest(request, path, 10*1024*1024, "euc-kr");
		String fileName = multi.getFilesystemName("upload");
		request.setAttribute("fileName", fileName);
		request.setAttribute("CKEditorFuncNum", request.getParameter("CKEditorFuncNum"));
		return new ActionForward(false, "ckeditor.jsp");
	}

	//ä��ȭ��
	public ActionForward chatform(HttpServletRequest request, HttpServletResponse response){
		if(logincheck(request)) {
			return new ActionForward();
		}else {
			return new ActionForward(false,"../alert.jsp");
		}				
	}

	private boolean logincheck(HttpServletRequest request) {
		String login = (String)request.getSession().getAttribute("login");
		if(login == null) {
			request.setAttribute("msg", "�α��� �� �ŷ��ϼ���");
			request.setAttribute("url", "../member/loginForm.me");
			return false;
		}
		return true;
	}
	
	public ActionForward graph(HttpServletRequest request, HttpServletResponse response){
		//[{name:'ȫ�浿', cnt:9},{name:'111', cnt:3}] 
		List<Map<String,Integer>> list = dao.boardgraph();
		StringBuilder json = new StringBuilder("[");
		int i = 0;
		for(Map<String,Integer> m : list) {
			for(Map.Entry<String, Integer> me : m.entrySet()) {
				if(me.getKey().equals("name"))
					json.append("{\"name\":\"" + me.getValue() + "\",");
				if(me.getKey().equals("cnt"))
					json.append("\"cnt\":" + me.getValue() + "}");
			}
			i++;
			if(i<list.size()) json.append(",");
		}
		json.append("]");
		request.setAttribute("json", json.toString().trim());
		
		return new ActionForward();		
	}
	public ActionForward graph2(HttpServletRequest request, HttpServletResponse response){
		//[{name:'ȫ�浿', cnt:9},{name:'111', cnt:3}] 
		List<Map<String,Integer>> list = dao.boardgraph2();
		
		request.setAttribute("list", list);		
		return new ActionForward();		
	}
	
	public ActionForward graph22(HttpServletRequest request, HttpServletResponse response){
		//[{name:'ȫ�浿', cnt:9},{name:'111', cnt:3}] 
		List<Map<String,Integer>> list = dao.boardgraph2();
		StringBuilder json = new StringBuilder("[");
		int i = 0;
		for(Map<String,Integer> m : list) {
			for(Map.Entry<String, Integer> me : m.entrySet()) {
				if(me.getKey().equals("date"))
					json.append("{\"date\":\"" + me.getValue() + "\",");
				if(me.getKey().equals("cnt"))
					json.append("\"cnt\":" + me.getValue() + "}");
			}
			i++;
			if(i<list.size()) json.append(",");
		}
		json.append("]");
		request.setAttribute("json", json.toString().trim());
		
		return new ActionForward(false, "graph.jsp");		
	}
	
	//jsoup�� �̿��Ͽ� ũ�Ѹ��ϱ�.
	public ActionForward exchange(HttpServletRequest request, HttpServletResponse response){
		String url = "https://www.koreaexim.go.kr/site/program/financial/exchange?menuid=001001004002001";
		String line = "";
		Document doc = null;
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		try{
			doc = Jsoup.connect(url).get();
			Elements e1 = doc.select(".tc");	//�����ڵ�, ȯ���� �±׼���
			Elements e2 = doc.select(".tl2.bdl");	//�����̸�		
			for(int i=0;i<e1.size();i++){
				if(e1.get(i).html().equals("USD")) {
					list.add(e1.get(i).html());	//USD���� ����
					for(int j=1;j<=6;j++) {
						list.add(e1.get(i+j).html());
					}
					break;
				}
			}
			for(Element ele: e2) {
				if(ele.html().contains("�̱�")) {
					list2.add(ele.html());
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		request.setAttribute("list", list);	//��ȭ�ڵ�, ȯ������
		request.setAttribute("list2", list2);	//������
		request.setAttribute("today", new Date());
		return new ActionForward();		
	}
	
	
	public ActionForward exchange2(HttpServletRequest request, HttpServletResponse response){
		Map<String,List<String>> map = new HashMap<>();
		try {
			String kebhana = Jsoup.connect
				("http://fx.kebhana.com/FER1101M.web").get().text();
			String strJson = kebhana.substring(kebhana.indexOf("{"));
			JSONParser jsonParser = new JSONParser();
			JSONObject json = (JSONObject)jsonParser.parse(strJson.trim());	//JSON��ü����
			JSONArray array = (JSONArray)json.get("����Ʈ");
			
			for(int i=0;i<array.size();i++){
				JSONObject obj = (JSONObject)array.get(i);
				if(obj.get("��ȭ��").toString().contains("�̱�")||
					obj.get("��ȭ��").toString().contains("�Ϻ�")||
					obj.get("��ȭ��").toString().contains("����")||
					obj.get("��ȭ��").toString().contains("�߱�")){
					String str = obj.get("��ȭ��").toString();
					String[] sarr = str.split(" ");
					String key = sarr[0];
					String code = sarr[1];
					List<String> list = new ArrayList<String>();
					list.add(code);
					list.add(obj.get("�Ÿű�����").toString());
					list.add(obj.get("�����ĽǶ�").toString());
					list.add(obj.get("������Ƕ�").toString());
					map.put(key, list);
				}
			}
			request.setAttribute("date", json.get("��¥").toString());
			request.setAttribute("map", map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ActionForward();	
	}
}
