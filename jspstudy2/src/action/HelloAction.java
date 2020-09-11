package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.member.Action;

public class HelloAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		request.setAttribute("hello", "Hello World");	//속성등록
		return new ActionForward(false,"hello.jsp");
	}
}
