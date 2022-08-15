package kr.co.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.vo.MemberVO;


public class AdminInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
    	System.out.println("AdminInterceptor preHandle �۵�");
        
    	HttpSession session = request.getSession();
        
        MemberVO login = (MemberVO)session.getAttribute("member");
    	
        if(login == null || login.getAdminCk() == 0) {    // ������ ���� �ƴ� ���
            
            response.sendRedirect("/main");    // ������������ �����̷�Ʈ
            
            return false;
            
        }
        
        return true;    // ������ ���� �α��� ���(lvo != null && lvo.getAdminCk() == 1)
    }
    
}

