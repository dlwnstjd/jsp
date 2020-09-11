package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
import model.MemberDao;

public class IdfindAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		
		String id = new MemberDao().idfind(email, tel);
		
		id = id.substring(0,id.length()-2) + "**";
		request.setAttribute("id", id);
		return new ActionForward();
	}
}
