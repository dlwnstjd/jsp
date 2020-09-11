package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
import model.Member;
import model.MemberDao;
/*
 1. �α׾ƿ������� ���. �α��� �ϼ���. �޼��� ���.
 	�ٸ� ������� ��й�ȣ ���� �Ұ�.
 	opener �������� loginForm.jsp ������ �̵�.
 2. pass, chgpass �Ķ���� �� ����
 3. pass ��й�ȣ�� db�� ����� ��й�ȣ�� Ʋ����
 	��й�ȣ ���� �޼��� ���. passwordForm.jsp ������ �̵�
 4. pass ��й�ȣ�� db�� ����� ��й�ȣ�� ������
 	��й�ȣ ����
 	opener �������� updateForm.jsp ������ �̵�.
 	���� ������ �ݱ�.
 */
public class PassAction extends UserLoginAction{
	@Override
	protected ActionForward doExecute(HttpServletRequest request, HttpServletResponse response) {
		boolean opener = false;
		boolean closer = false;
		String msg = null;
		String url = null;
		if(login == null){
			opener = true;
			closer = true;
			msg = "�α��� �ϼ���.";
			url = "loginForm.jsp";
		}else{	//�α��λ���
			String pass = request.getParameter("pass");	//������ ��й�ȣ
			String chgpass = request.getParameter("chgpass");//������ ��й�ȣ
			MemberDao dao = new MemberDao();
			Member mem = dao.selectOne(login);
			//mem.getPass(): db�� ����� ��й�ȣ
			if(pass.equals(mem.getPass())){	//�Էµ� ��й�ȣ�� db�� ����� ��й�ȣ�� ����
				closer = true;
				if(dao.updatePass(login,chgpass) > 0){
					msg="��й�ȣ�� ����Ǿ����ϴ�.";
				}else {
					msg="��й�ȣ ����� ������ �߻��߽��ϴ�.";
				}
			}else{
				msg="��й�ȣ �����Դϴ�. Ȯ���ϼ���.";
				url="passwordForm.me";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		request.setAttribute("opener", opener);
		request.setAttribute("closer", closer);
		return new ActionForward(false, "../alert.jsp");
	}
}
