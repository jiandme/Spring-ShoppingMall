package kr.co.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.vo.MemberVO;

public class CartInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
    	System.out.println("CartInterceptor preHandle ¿€µø");
        
        HttpSession session = request.getSession();
        
        MemberVO memberVO = (MemberVO)session.getAttribute("member");
		
		if(memberVO == null) {
			response.sendRedirect("/main");
			return false;
		} else {
			return true;
		}
    }
    
}
