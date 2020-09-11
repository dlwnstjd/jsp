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
	1. 파라미터 값을 model.Board 객체 저장.
		useBean 사용불가: request 정보의 파라미터와 빈클래스의 프로퍼티 비교
						request 객체를 사용할 수 없음
	2. 게시물 번호 num 현재 등록된 num의 최대값을 조회. 최대한+1 등록된 게시물의 번호.
		db에서 maxnum을 구해서 +1 값으로 num 설정하기
	3. board 내용을 db에 등록하기.
		등록성공: 메세지 출력. list.jsp 페이지 이동
		등록실패: 메세지 출력. writeForm.jsp 페이지 이동
 */
public class BoardAllAction {
	BoardDao dao = new BoardDao();
	public ActionForward hello(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("hello", "Hello World");
		return new ActionForward();
	}

	public ActionForward write(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msg = "게시물 등록 실패";
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
			int num = dao.maxnum(); // board table에서 num 컬럼의 최대값 리턴

			board.setNum(++num);
			board.setGrp(num);
			if (dao.insert(board)) {
				msg = "게시물 등록 성공";
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
	 * 1. 한페이지당 10건의 게시물을 출력. pageNum 파라미터값을 저장 => 없는 경우 1로 설정. 2. 최근 등록된 게시물이 가장 위에
	 * 배치. 3. db에서 해당 페이지에 출력될 내용을 조회하여 list 객체로 저장 list 객체를 request의 객체의 속성으로 등록하여
	 * list.jsp로 페이지 이동
	 */
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		} catch (NumberFormatException e) {		}
		String column = request.getParameter("column");
		String find = request.getParameter("find");
		if(column == null || column.trim().equals("")) {//column값이 없음
			column = null;
			find = null;
		}
		if(find == null || find.trim().equals("")) {//find값이 없음
			column = null;
			find = null;
		}
		int limit = 10;// 한페이지에 출력할 게시물 건수
		BoardDao dao = new BoardDao(); 
		// 등록된 전체 게시물의 건수 또는, 검색된 게시물의 건수
		int boardcount = dao.boardCount(column,find);
		// list: 화면에 출력할 게시글 내용 목록
		List<Board> list = dao.list(pageNum, limit,column,find);
		int maxpage = (int) ((double) boardcount / limit + 0.95);
		int startpage = ((int) (pageNum / 10.0 + 0.9) - 1) * 10 + 1;// 시작페이지번호
		int endpage = startpage + 9;// 종료페이지 번호

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
		 * 1. num 파라미터 저장 2. num값의 게시물을 db에서 조회. 3. num값의 게시물의 조회수 증가시키기. 4. 조회된 게시물을
		 * 화면에 출력
		 */
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDao dao = new BoardDao();
		Board b = dao.selectOne(num); // 해당 게시물 조회
		if (request.getRequestURI().contains("board/info.do")) {
			dao.readcntAdd(num); // 해당게시물 조회 건수 1증가
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
		String msg = "답변등록시 오류발생";
		String url = "replyForm.do?num=" + b.getNum();

		BoardDao dao = new BoardDao();
//		현재 등록된 답글은 원글 바로 아래에 조회되도록 db 수정
		dao.grpStepAdd(b.getGrp(), b.getGrpstep());
		int grplevel = b.getGrplevel();
		int grpstep = b.getGrpstep();
		int num = dao.maxnum();// num의 최대값 저장
		b.setNum(++num);
		b.setGrplevel(grplevel + 1);
		b.setGrpstep(grpstep + 1);
		if (dao.insert(b)) {
			msg = "답변등록 완료";
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
		//2. 비밀번호 검증
		BoardDao dao = new BoardDao();
		//dbBoard: 수정전 정보 저장. 비밀번호 검증시 사용
		Board dbBoard = dao.selectOne(board.getNum());
		String msg = "비밀번호가 틀렸습니다.";
		String url = "updateForm.do?num=" + board.getNum();
		if(board.getPass().equals(dbBoard.getPass())){
			if(dao.update(board)){ 
				msg =  "게시물 수정 완료";
				url = "info.do?num=" + board.getNum();
			}else{
				msg = "게시물 수정실패";
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
		String msg = "비밀번호가 틀렸습니다.";
		String url = "deleteForm.do?num=" + num;
		if(b==null){
			msg = "없는 게시글입니다.";
			url = "list.do";
		}else{ 
			if(pass.equals(b.getPass())){
				if(dao.delete(b)){
					msg =  "게시물 수정 완료";
					url = "list.do";
				}else{ 
					msg = "게시물 수정 실패";
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

	//채팅화면
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
			request.setAttribute("msg", "로그인 후 거래하세요");
			request.setAttribute("url", "../member/loginForm.me");
			return false;
		}
		return true;
	}
	
	public ActionForward graph(HttpServletRequest request, HttpServletResponse response){
		//[{name:'홍길동', cnt:9},{name:'111', cnt:3}] 
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
		//[{name:'홍길동', cnt:9},{name:'111', cnt:3}] 
		List<Map<String,Integer>> list = dao.boardgraph2();
		
		request.setAttribute("list", list);		
		return new ActionForward();		
	}
	
	public ActionForward graph22(HttpServletRequest request, HttpServletResponse response){
		//[{name:'홍길동', cnt:9},{name:'111', cnt:3}] 
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
	
	//jsoup을 이용하여 크롤링하기.
	public ActionForward exchange(HttpServletRequest request, HttpServletResponse response){
		String url = "https://www.koreaexim.go.kr/site/program/financial/exchange?menuid=001001004002001";
		String line = "";
		Document doc = null;
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		try{
			doc = Jsoup.connect(url).get();
			Elements e1 = doc.select(".tc");	//국가코드, 환율값 태그선택
			Elements e2 = doc.select(".tl2.bdl");	//국가이름		
			for(int i=0;i<e1.size();i++){
				if(e1.get(i).html().equals("USD")) {
					list.add(e1.get(i).html());	//USD정보 저장
					for(int j=1;j<=6;j++) {
						list.add(e1.get(i+j).html());
					}
					break;
				}
			}
			for(Element ele: e2) {
				if(ele.html().contains("미국")) {
					list2.add(ele.html());
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		request.setAttribute("list", list);	//통화코드, 환율정보
		request.setAttribute("list2", list2);	//국가명
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
			JSONObject json = (JSONObject)jsonParser.parse(strJson.trim());	//JSON객체변경
			JSONArray array = (JSONArray)json.get("리스트");
			
			for(int i=0;i<array.size();i++){
				JSONObject obj = (JSONObject)array.get(i);
				if(obj.get("통화명").toString().contains("미국")||
					obj.get("통화명").toString().contains("일본")||
					obj.get("통화명").toString().contains("유로")||
					obj.get("통화명").toString().contains("중국")){
					String str = obj.get("통화명").toString();
					String[] sarr = str.split(" ");
					String key = sarr[0];
					String code = sarr[1];
					List<String> list = new ArrayList<String>();
					list.add(code);
					list.add(obj.get("매매기준율").toString());
					list.add(obj.get("현찰파실때").toString());
					list.add(obj.get("현찰사실때").toString());
					map.put(key, list);
				}
			}
			request.setAttribute("date", json.get("날짜").toString());
			request.setAttribute("map", map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ActionForward();	
	}
}
