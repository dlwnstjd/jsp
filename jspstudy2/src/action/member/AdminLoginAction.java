package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;

/*
 1. �α׾ƿ� ����: �α����� �ʿ��մϴ�. �޼��� ���. loginForm.me ������ �̵�
 2. �α��� ���� 
 	   �Ϲݻ����: �����ڸ� ������ �ŷ��Դϴ�. �޼��� ���. main.me ������ �̵�
 3. ������ ���� => ������ �����
 	doExecute �޼��� ȣ�� 
 */
public abstract class AdminLoginAction implements Action{
	protected String login;
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String login = (String)request.getSession().getAttribute("login");
		if(login == null){
			request.setAttribute("msg", "�α����� �ʿ��մϴ�");
			request.setAttribute("url", "loginForm.me");
			return new ActionForward(false, "../alert.jsp");
		}
		if(!login.equals("admin")){
			request.setAttribute("msg", "�����ڸ� ������ �ŷ��Դϴ�");
			request.setAttribute("url", "main.me");
			return new ActionForward(false, "../alert.jsp");
		}
		return doExecute(request,response);
	}
	protected abstract ActionForward doExecute
		(HttpServletRequest request, HttpServletResponse response);

}
