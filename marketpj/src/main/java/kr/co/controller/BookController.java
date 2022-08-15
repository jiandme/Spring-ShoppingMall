package kr.co.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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

import kr.co.service.AttachService;
import kr.co.service.BookService;
import kr.co.service.ReplyService;
import kr.co.vo.AttachImageVO;
import kr.co.vo.BookVO;
import kr.co.vo.Criteria;
import kr.co.vo.PageDTO;
import kr.co.vo.ReplyDTO;

@Controller
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private AttachService attachService;
	@Autowired
	private BookService bookService;
	@Autowired
	private ReplyService replyService;
	
	//���� ������ �̵�
	@GetMapping("/main")
	public void mainPageGET(Model model) {

		logger.info("���� ������ ����");
		model.addAttribute("cate1", bookService.getCateCode1());
		model.addAttribute("cate2", bookService.getCateCode2());
		

	}
	
	/* ��ǰ �˻� */
	@GetMapping("/search")
	public String searchGoodsGET(Criteria cri, Model model) {
		
		List<BookVO> list = bookService.goodsGetList(cri);
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listcheck", "empty");
			return "search";
		}
		model.addAttribute("pageMaker", new PageDTO(cri, bookService.goodsGetTotal(cri)));
		
		String[] typeArr = cri.getType().split("");
		
		for(String s : typeArr) {
			if(s.equals("T") || s.equals("A")) {
				model.addAttribute("filter_info", bookService.getCateInfo(cri));		
			}
		}

		return "search";
		
	}
	
	
	/* ��ǰ �� */
	@GetMapping("/goodsDetail/{bookId}")
	public String goodsDetailGET(@PathVariable("bookId")int bookId, Model model) {
		
		logger.info("goodsDetailGET()..........");
		
		model.addAttribute("goodsInfo", bookService.getGoodsInfo(bookId));
		
		return "/goodsDetail";
	}
	
	
	
	
	//�̹��� �����ֱ�
	@GetMapping("/display")
	public ResponseEntity<byte[]> getImage(String fileName){
		File file = new File("c:\\upload\\" + fileName);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* �̹��� ���� ��ȯ */
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachImageVO>> getAttachList(int bookId){
		
		logger.info("getAttachList.........." + bookId);
		
		return new ResponseEntity<List<AttachImageVO>>(attachService.getAttachList(bookId), HttpStatus.OK);
		
	}
	
	/* ���� ���� */
	@GetMapping("/replyEnroll/{memberId}")
	public String replyEnrollWindowGET(@PathVariable("memberId")String memberId, int bookId, Model model) {
		BookVO bookVO = bookService.getGoodsInfo(bookId);
		model.addAttribute("bookInfo", bookVO);
		model.addAttribute("memberId", memberId);
		
		return "/replyEnroll";
	}
	
	/* ���� ���� �˾�â */
	@GetMapping("/replyUpdate")
	public String replyUpdateWindowGET(ReplyDTO dto, Model model) {
		BookVO book = bookService.getGoodsInfo(dto.getBookId());
		model.addAttribute("bookInfo", book);
		model.addAttribute("replyInfo", replyService.getUpdateReply(dto.getReplyId()));
		model.addAttribute("memberId", dto.getMemberId());
		
		return "/replyUpdate";
	}
}
