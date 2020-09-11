package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;

public abstract class UserLoginAction implements Action{
	protected String login;
	protected String id;
	
	//boolean으로 리턴타입 변경
	//메서드로 호출 static?
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		login = (String)request.getSession().getAttribute("login");
		id = request.getParameter("id");
		if(login == null) {
			request.setAttribute("msg", "로그인 후 거래하세요");
			request.setAttribute("url", "loginForm.me");
			return new ActionForward(false,"../alert.jsp");
		}
		if(id != null && !id.equals(login) && !login.equals("admin")) {
			request.setAttribute("msg", "본인 거래만 가능합니다.");
			request.setAttribute("url", "main.me");
			return new ActionForward(false, "../alert.jsp");
		}
		if(id==null||id.trim().equals("")) id= login;
		return doExecute(request,response);
	}

	protected abstract ActionForward doExecute
	(HttpServletRequest request, HttpServletResponse response);

}
