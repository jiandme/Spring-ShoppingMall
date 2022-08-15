package kr.co.controller;

import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.MemberService;
import kr.co.service.OrderService;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderPageDTO;

@Controller
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/order/{memberId}")
	public String orderPgaeGET(@PathVariable("memberId") String memberId, OrderPageDTO opd, Model model) {
		
		logger.info("林巩其捞瘤 立加");
		model.addAttribute("orderList", orderService.getGoodsInfo(opd.getOrders()));
		model.addAttribute("memberInfo", memberService.getMemberInfo(memberId));
		return "/order";
		
	}
	
	@PostMapping("/order")
	public String orderPagePost(OrderDTO orderDTO, HttpServletRequest request) {
		
		orderService.order(orderDTO);	
		
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId(orderDTO.getMemberId());
		HttpSession session = request.getSession();
		
		try {
			MemberVO memberLogin = memberService.memberLogin(memberVO);
			memberLogin.setMemberPw("");
			session.setAttribute("member", memberLogin);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/main";
	}
	
	
}
