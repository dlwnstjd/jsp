package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
/*
 1. �α��� ���� ����	
 	�α��� ����: ���� ȭ��
 	�α׾ƿ� ����: �α����ϼ���. �޼��� ��� �� loginForm.jsp �������� �̵�
 */
public class MainAction extends UserLoginAction{
	@Override
	protected ActionForward doExecute(HttpServletRequest request, HttpServletResponse response) {
		return new ActionForward();
	}

}
