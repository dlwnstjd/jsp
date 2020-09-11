package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionForward;
/*
 1. 로그인 여부 검증	
 	로그인 상태: 현재 화면
 	로그아웃 상태: 로그인하세요. 메세지 출력 후 loginForm.jsp 페이지로 이동
 */
public class MainAction extends UserLoginAction{
	@Override
	protected ActionForward doExecute(HttpServletRequest request, HttpServletResponse response) {
		return new ActionForward();
	}

}
