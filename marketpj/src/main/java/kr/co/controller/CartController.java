package kr.co.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.service.AttachService;
import kr.co.service.BookService;
import kr.co.service.CartService;
import kr.co.vo.AttachImageVO;
import kr.co.vo.BookVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.PageDTO;

@Controller
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartService cartService;
	
	//��ٱ��� �߰�
	@PostMapping("/cart/add")
	@ResponseBody
	public String addCartPOST(CartDTO cartDTO, HttpServletRequest request) {
		
		logger.info("addCartPost ����");
		// �α��� üũ
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("member");
		if(memberVO == null) {
			return "5";
		}
		
		// īƮ ���
		
		int result = cartService.addCart(cartDTO);
		
		return result + "";
	}
	
	//��ٱ��� ȭ��
	@GetMapping("/cart/{memberId}")
	public String cartPageGET(@PathVariable("memberId") String memberId, Model model) {
		
		model.addAttribute("cartInfo", cartService.getCartList(memberId));
		
		return "/cart";
	}
	
	/* ��ٱ��� ���� ���� */
	@PostMapping("/cart/update")
	public String updateCartPOST(CartDTO cartDTO) {
		
		cartService.modifyCount(cartDTO);
		
		return "redirect:/cart/" + cartDTO.getMemberId();
		
	}
	
	/* ��ٱ��� ���� */
	@PostMapping("/cart/delete")
	public String deleteCartPOST(CartDTO cartDTO) {
		
		logger.info("��ٱ��� ���� POST");
		
		cartService.deleteCart(cartDTO.getCartId());
		
		return "redirect:/cart/" + cartDTO.getMemberId();
		
	}
	
	
}
