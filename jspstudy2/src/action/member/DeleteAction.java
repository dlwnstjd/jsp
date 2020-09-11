package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
import model.Member;
import model.MemberDao;
/*
//UserLoginAction���� ó��
 1. �α׾ƿ� ����: "�α����ϼ���" ���. loginForm.me ������ �̵�
 2. �Ϲݻ������ ���
 	- id�Ķ���� ������, login ������ �ٸ����
 		"���θ� Ż�� �����մϴ�." main.me �������� �̵�
--------------------------------------------------------
//DeleteAction���� ó��
 3. �Ϲݻ����
 	- id, pass ������ �̿��Ͽ� ��й�ȣ ����
 	- ��й�ȣ ����ġ: "��й�ȣ�� Ʋ��" ���. deleteForm.me ������ �̵�
 	������
 	- id�� �������� ��� Ż�� �Ұ�. list.me ������ �̵�
 4. Ż�� ����: member db���� delete ó�� �Ϸ�
 	�Ϲ� �����: ���� ���� �޽��� ���. �α׾ƿ� ����. loginForm.me ������ �̵�
 	������: ���� ���� �޽��� ���. list.me ������ �̵�.
 5. Ż�� ����: member db���� delete ó���� �����߻�
 	�Ϲݻ����: Ż�� ���� �޼��� ���. info.me ������ �̵�
 	������: ���� ����. list.me ������ �̵�
 */
public class DeleteAction extends UserLoginAction{

	@Override
	protected ActionForward doExecute(HttpServletRequest request, HttpServletResponse response) {
		String pass = request.getParameter("pass");
		String msg = null;
		String url = null;
		
		if(id.equals("admin")){
			msg = "�����ڴ� Ż���� �� �����ϴ�.";
			url = "list.me";
		}else {
			MemberDao dao = new MemberDao();
			Member dbmem = dao.selectOne(id);
			
			if(login.equals("admin") || pass.equals(dbmem.getPass())){
				if(dao.delete(id) > 0){	//��������
					if(login.equals("admin")){	//�������ΰ��
						msg = id + "����ڸ� ���� Ż�� ����";
						url = "list.me"; 
					} else{	//�Ϲݻ������ ���
						msg = id + "���� ȸ�� Ż�� �Ϸ� �Ǿ����ϴ�.";
						url = "loginForm.me";
						request.getSession().invalidate();	//�α׾ƿ�
					}
				}else { //���� ����
					msg = id + "���� Ż��� ���� �߻�";
					if(login.equals("admin")){	//�������� ���
						url = "list.me";
					}else{	//�Ϲ� ������ΰ��
						url = "info.me?id=" + id;
					}
				}
			}else {	//�Ϲݻ���ڰ� ��й�ȣ�� Ʋ�����
				msg = id + "���� ��й�ȣ�� Ʋ���ϴ�.";
				url = "deleteForm.me?id=" + id;
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);	
		return new ActionForward(false, "../alert.jsp");
	}

}
