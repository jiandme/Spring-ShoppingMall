package kr.co.controller;

import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.MemberService;
import kr.co.service.OrderService;
import kr.co.service.ReplyService;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderPageDTO;
import kr.co.vo.ReplyDTO;
import kr.co.vo.ReplyPageDTO;

@RestController
@RequestMapping("/reply")
public class ReplyController {

	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	private ReplyService replyService;
	
	/* ¥Ò±€ µÓ∑œ */
	@PostMapping("/enroll")
	public void enrollReplyPOST(ReplyDTO replyDTO) {
		logger.info("¥Ò±€µÓ∑œ");
		replyService.enrollReply(replyDTO);
	}
	
	/* ¥Ò±€ √º≈© */
	/* memberId, bookId ∆ƒ∂ÛπÃ≈Õ */
	/* ¡∏¿Á : 1 / ¡∏¿Áx : 0 */
	@PostMapping("/check")
	public String replyCheckPOST(ReplyDTO replyDTO) {
		return replyService.checkReply(replyDTO);
	}
	
	/* ¥Ò±€ ∆‰¿Ã¬° */
	@GetMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ReplyPageDTO replyListPOST(Criteria cri) {
		return replyService.replyList(cri);
	}
	
	/* ¥Ò±€ ºˆ¡§ */
	@PostMapping("/update")
	public void replyModifyPOST(ReplyDTO dto) {
		replyService.updateReply(dto);
	}
	
	/* ¥Ò±€ ªË¡¶ */
	@PostMapping("/delete")
	public void replyDeletePOST(ReplyDTO dto) {
		replyService.deleteReply(dto);
	}
}
