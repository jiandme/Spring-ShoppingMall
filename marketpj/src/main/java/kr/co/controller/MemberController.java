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

	//이메일 객체
    @Autowired
    private JavaMailSender mailSender;
    
    //암호화 객체
    @Autowired
    private BCryptPasswordEncoder pwEncoder;
	
	//회원가입 페이지
	@GetMapping("/join")
	public void joinGet() {

		logger.info("회원가입 페이지 진입");

	}
	
	//회원가입
	@PostMapping("/join")
	public String joinPost(MemberVO memberVO) {
		logger.info("회원가입 진입");
		
		//입력받은 비밀번호를 암호화시킨뒤 다시 VO에 넣어주고 Service 계층으로 넘겨줌
		String encodePw = pwEncoder.encode(memberVO.getMemberPw());
		memberVO.setMemberPw(encodePw);
		
		memberService.memberJoin(memberVO);
		logger.info("회원가입 성공");
		return "redirect:/main";
	}
	
	//로그인 페이지
	@GetMapping("/login")
	public void loginGet() {

		logger.info("로그인 페이지 진입");

	}
	
	//로그인
	@PostMapping("/login.do")
	public String loginPost(MemberVO memberVO, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		logger.info("로그인 POST");
		
		HttpSession session = request.getSession();
		MemberVO login = memberService.memberLogin(memberVO);
		boolean pwdMatch = pwEncoder.matches(memberVO.getMemberPw(), login.getMemberPw());
		
		if(login !=null && pwdMatch == true) {    //아이디,비밀번호 일치
			login.setMemberPw(""); //인코딩된 비밀번호 정보 삭제
			session.setAttribute("member", login); 
			return "redirect:/main";

		}
		rttr.addFlashAttribute("result",0); //불일치
		return "redirect:/member/login";
	}
	
	//로그아웃
	@GetMapping("/logout.do")
	public String logoutGet(HttpServletRequest request) throws Exception {
		logger.info("로그아웃 동기");
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/main";
		
	}
	
	//비동기 로그아웃
	@PostMapping("logout")
	@ResponseBody
	public void logoutPost(HttpServletRequest request) throws Exception {
		logger.info("로그아웃 비동기");
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	
	// 아이디 중복 검사
	@PostMapping("/memberIdChk")
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception {

		logger.info("memberIdChk() 진입");
		
		int result = memberService.idCheck(memberId);
		
		logger.info("결과값 = " + result);
		
		if(result != 0) {
			
			return "fail";	// 중복 아이디가 존재
			
		} else {
			
			return "success";	// 중복 아이디 x
			
		}	

	} // memberIdChkPOST() 종료
	
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheckGet(String email) throws Exception{
		
		/* 뷰(View)로부터 넘어온 데이터 확인 */
        logger.info("이메일 데이터 전송 확인");
        

        /* 인증번호(난수) 생성 */
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        
        /* 이메일 보내기 */
        String setFrom = "ehfvndcjstk@naver.com";
        String toMail = email;
        String title = "회원가입 인증 이메일 입니다.";
        String content = 
                "홈페이지를 방문해주셔서 감사합니다." +
                "<br><br>" + 
                "인증 번호는 " + checkNum + "입니다." + 
                "<br>" + 
                "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        
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
		
		//ajax로 반환할떄의 데이터 타입은 String만 가능하므로 형변환
		String num = Integer.toString(checkNum);
		return num;
 
	}
	
	
}
