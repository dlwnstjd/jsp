package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
/*
 1. id �Ķ���� �����ϱ�.
 2. login ���� �����ϱ�
 	�α׾ƿ� ������ ��� �α����ϼ���. �޼��� ��� �� loginForm.jsp �������̵�
 3. ���� ȭ�� ����ϱ�
 */
public class DeleteFormAction extends UserLoginAction{

	@Override
	protected ActionForward doExecute(HttpServletRequest request, HttpServletResponse response) {
		
		return new ActionForward();
	}

}
