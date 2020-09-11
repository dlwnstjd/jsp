package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
import model.Member;
import model.MemberDao;

/*
 1. �Ķ���� �� ����
 2. db�� ������ �о, ���̵�� ��й�ȣ�� ��
 3. ���̵� ���� ���
 	 ���̵� �����ϴ�. �޼��� ��� �� loginForm.jsp ������ �̵�
 	 ��й�ȣ Ʋ�� ���
 	 ��й�ȣ�� Ʋ���ϴ�. �޼��� ��� �� loginForm.jsp ������ �̵�
 4. ���̵�, ��й�ȣ ��ġ.
 	-session ��ü�� �α��� ���� ����
 	-'�̸�'���� �α��� �߽��ϴ�. �޼��� ��� ��, main.jsp��  ������ �̵�
 */ 
public class LoginAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String msg = "���̵� Ȯ���ϼ���";
		String url = "loginForm.me";
		//1. �Ķ���� ����
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		//2. db ���� �б�.
		Member mem = new MemberDao().selectOne(id);
		//3. ���̵�, ��й�ȣ ����
		if(mem != null){
			if(pass.equals(mem.getPass())){
				request.getSession().setAttribute("login", id);
				msg = mem.getName() + "���� �α��� �߽��ϴ�.";
				url = "main.me";
			}else{
				msg = "��й�ȣ�� Ʋ���ϴ�.";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return new ActionForward(false, "../alert.jsp");
	}

}
