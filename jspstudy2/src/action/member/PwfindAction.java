package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
import model.MemberDao;

public class PwfindAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		
		String pw = new MemberDao().pwfind(id, email, tel);
		pw = "**" + pw.substring(2,pw.length());
		request.setAttribute("pw", pw);
		return new ActionForward();
	}

}
