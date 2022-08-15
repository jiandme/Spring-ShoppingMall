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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.MemberService;
import kr.co.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;

	//�̸��� ��ü
    @Autowired
    private JavaMailSender mailSender;
    
    //��ȣȭ ��ü
    @Autowired
    private BCryptPasswordEncoder pwEncoder;
	
	//ȸ������ ������
	@GetMapping("/join")
	public void joinGet() {

		logger.info("ȸ������ ������ ����");

	}
	
	//ȸ������
	@PostMapping("/join")
	public String joinPost(MemberVO memberVO) {
		logger.info("ȸ������ ����");
		
		//�Է¹��� ��й�ȣ�� ��ȣȭ��Ų�� �ٽ� VO�� �־��ְ� Service �������� �Ѱ���
		String encodePw = pwEncoder.encode(memberVO.getMemberPw());
		memberVO.setMemberPw(encodePw);
		
		memberService.memberJoin(memberVO);
		logger.info("ȸ������ ����");
		return "redirect:/main";
	}
	
	//�α��� ������
	@GetMapping("/login")
	public void loginGet() {

		logger.info("�α��� ������ ����");

	}
	
	//�α���
	@PostMapping("/login.do")
	public String loginPost(MemberVO memberVO, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		logger.info("�α��� POST");
		
		HttpSession session = request.getSession();
		MemberVO login = memberService.memberLogin(memberVO);
		boolean pwdMatch = pwEncoder.matches(memberVO.getMemberPw(), login.getMemberPw());
		
		if(login !=null && pwdMatch == true) {    //���̵�,��й�ȣ ��ġ
			login.setMemberPw(""); //���ڵ��� ��й�ȣ ���� ����
			session.setAttribute("member", login); 
			return "redirect:/main";

		}
		rttr.addFlashAttribute("result",0); //����ġ
		return "redirect:/member/login";
	}
	
	//�α׾ƿ�
	@GetMapping("/logout.do")
	public String logoutGet(HttpServletRequest request) throws Exception {
		logger.info("�α׾ƿ� ����");
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/main";
		
	}
	
	//�񵿱� �α׾ƿ�
	@PostMapping("logout")
	@ResponseBody
	public void logoutPost(HttpServletRequest request) throws Exception {
		logger.info("�α׾ƿ� �񵿱�");
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	
	// ���̵� �ߺ� �˻�
	@PostMapping("/memberIdChk")
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception {

		logger.info("memberIdChk() ����");
		
		int result = memberService.idCheck(memberId);
		
		logger.info("����� = " + result);
		
		if(result != 0) {
			
			return "fail";	// �ߺ� ���̵� ����
			
		} else {
			
			return "success";	// �ߺ� ���̵� x
			
		}	

	} // memberIdChkPOST() ����
	
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheckGet(String email) throws Exception{
		
		/* ��(View)�κ��� �Ѿ�� ������ Ȯ�� */
        logger.info("�̸��� ������ ���� Ȯ��");
        

        /* ������ȣ(����) ���� */
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        
        /* �̸��� ������ */
        String setFrom = "ehfvndcjstk@naver.com";
        String toMail = email;
        String title = "ȸ������ ���� �̸��� �Դϴ�.";
        String content = 
                "Ȩ�������� �湮���ּż� �����մϴ�." +
                "<br><br>" + 
                "���� ��ȣ�� " + checkNum + "�Դϴ�." + 
                "<br>" + 
                "�ش� ������ȣ�� ������ȣ Ȯ�ζ��� �����Ͽ� �ּ���.";
        
		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ajax�� ��ȯ�ҋ��� ������ Ÿ���� String�� �����ϹǷ� ����ȯ
		String num = Integer.toString(checkNum);
		return num;
 
	}
	
	
}
