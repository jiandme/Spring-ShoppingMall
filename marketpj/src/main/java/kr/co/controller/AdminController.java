package kr.co.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.service.AttachService;
import kr.co.service.AuthorService;
import kr.co.service.MemberService;
import kr.co.service.OrderService;
import kr.co.service.AdminService;
import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderCancelDTO;
import kr.co.vo.OrderDTO;
import kr.co.vo.PageDTO;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AuthorService authorService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AttachService attachService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;

	// 관리자 페이지 이동
	@GetMapping("/main")
	public void adminPageGet() {

		logger.info("관리자 페이지 진입");

	}

	/* 상품 등록 페이지 접속 */
	@GetMapping("/goodsEnroll")
	public void goodsEnrollGet(Model model) throws Exception {
		logger.info("상품 등록 페이지 접속");

		// 카테고리 리스트를 json 데이터로 변환
		ObjectMapper objm = new ObjectMapper();
		List list = adminService.cateList();
		// 리스트에 담긴 객체를 스트링 타입으로 변환
		String cateList = objm.writeValueAsString(list);
		model.addAttribute("cateList", cateList);

	}

	/* 상품 등록 */
	@PostMapping("/goodsEnroll")
	public String goodsEnrollPost(BookVO bookVO, RedirectAttributes rttr) throws Exception {

		logger.info("상품등록요청");

		adminService.bookEnroll(bookVO);

		rttr.addFlashAttribute("enroll_result", bookVO.getBookName());

		return "redirect:/admin/goodsManage";
	}

	/* 이미지 파일 업로드 */
	@PostMapping(value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPost(MultipartFile[] uploadFile) {

		logger.info("파일 업로드 AJAX");
		
		/* 이미지 파일 체크 */
		for(MultipartFile multipartFile: uploadFile) {
			
			File checkfile = new File(multipartFile.getOriginalFilename());
			String type = null;
			
			try {
				type = Files.probeContentType(checkfile.toPath());
				logger.info("MIME TYPE : " + type);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(!type.startsWith("image")) {
				
				List<AttachImageVO> list = null;
				return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
				
			}
			
		}// for
		
		String uploadFolder = "C:\\upload";

		// 업로드 하는 일자 받아오기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-", File.separator);
		File uploadPath = new File(uploadFolder, datePath);
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		/* 이미저 정보 담는 객체 */
		List<AttachImageVO> list = new ArrayList();
		
		
		// 넘어오는 파일 저장
		for (MultipartFile multipartFile : uploadFile) {
			
			AttachImageVO attachImageVO = new AttachImageVO();

			/* 파일 이름 */
			String uploadFileName = multipartFile.getOriginalFilename();
			attachImageVO.setFileName(uploadFileName);
			attachImageVO.setUploadPath(datePath);

			/* uuid 적용 파일 이름 */
			String uuid = UUID.randomUUID().toString();
			attachImageVO.setUuid(uuid);

			uploadFileName = uuid + "_" + uploadFileName;

			/* 파일 위치, 파일 이름을 합친 File 객체 */
			File saveFile = new File(uploadPath, uploadFileName);

			/* 파일 저장 */
			try {
				// 원본 파일 저장
				multipartFile.transferTo(saveFile);

				/*
				 * //썸네일용 이미지 생성후 저장 File thumbnailFile = new File(uploadPath, "s_" +
				 * uploadFileName); BufferedImage bo_image = ImageIO.read(saveFile);
				 * 
				 * 비율 double ratio = 3; 넓이 높이 int width = (int) (bo_image.getWidth() / ratio);
				 * int height = (int) (bo_image.getHeight() / ratio);
				 * 
				 * 
				 * BufferedImage bt_image = new BufferedImage(width, height,
				 * BufferedImage.TYPE_3BYTE_BGR); Graphics2D graphic =
				 * bt_image.createGraphics(); graphic.drawImage(bo_image, 0, 0,width,height,
				 * null); ImageIO.write(bt_image, "jpg", thumbnailFile);
				 */

				/* 방법 2 */
				File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);

				BufferedImage bo_image = ImageIO.read(saveFile);

				// 비율
				double ratio = 3;
				// 넓이 높이
				int width = (int) (bo_image.getWidth() / ratio);
				int height = (int) (bo_image.getHeight() / ratio);

				Thumbnails.of(saveFile).size(width, height).toFile(thumbnailFile);

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			list.add(attachImageVO);

		} // for
		
		ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list, HttpStatus.OK);
		
		return result;
	}
	
	
	/* 이미지 파일 삭제 */
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName){
		
		logger.info("이미지파일 삭제 파일이름 : " + fileName);
		File file = null;
		
		try {
			
			/* 썸네일 파일 삭제 */
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			
			file.delete();
			
			/* 원본 파일 삭제 */
			String originFileName = file.getAbsolutePath().replace("s_", "");
			
			logger.info("originFileName : " + originFileName);
			
			file = new File(originFileName);
			
			file.delete();
			
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
			
		}
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
		
		
	}

	/* 상품 관리 페이지 접속 */
	@GetMapping("/goodsManage")
	public void goodsManageGet(Criteria cri, Model model) throws Exception {
		logger.info("상품 관리 페이지 접속");

		List list = adminService.goodsGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // 상품 존재 경우
		} else {
			model.addAttribute("listCheck", "empty"); // 상품 존재하지 않을 경우
		}

		// 페이지 이동 인터페이스 데이터
		int total = adminService.goodsGetTotal(cri);

		PageDTO pageMaker = new PageDTO(cri, total);

		model.addAttribute("pageMaker", pageMaker);

	}

	/* 상품 상세 페이지, 수정페이지 접속 */
	@GetMapping({ "/goodsDetail", "/goodsModify" })
	public void goodsDetailGet(Model model, Criteria cri, int bookId) throws Exception {
		logger.info("상품 상세,수정 페이지 접속");

		ObjectMapper mapper = new ObjectMapper();

		/* 카테고리 리스트 데이터 */
		model.addAttribute("cateList", mapper.writeValueAsString(adminService.cateList()));

		model.addAttribute("cri", cri);

		model.addAttribute("goodsInfo", adminService.goodsGetDetail(bookId));

	}

	/* 상품 정보 수정 */
	@PostMapping("/goodsModify")
	public String goodsModifyPOST(BookVO bookVO, RedirectAttributes rttr) {

		logger.info("상품 수정 데이터 전송");

		int result = adminService.goodsModify(bookVO);

		rttr.addFlashAttribute("modify_result", result);

		return "redirect:/admin/goodsManage";

	}

	/* 상품 정보 삭제 */
	@PostMapping("/goodsDelete")
	public String goodsDeletePost(int bookId, RedirectAttributes rttr) {

		logger.info("goodsDeletePOST..........");
		
		List<AttachImageVO> fileList = attachService.getAttachList(bookId);
		
		if(fileList != null) {
			
			List<Path> pathList = new ArrayList();
			
			fileList.forEach(attachImageVO ->{
				
				// 원본 이미지
				Path path = Paths.get("C:\\upload", attachImageVO.getUploadPath(), attachImageVO.getUuid() + "_" + attachImageVO.getFileName());
				pathList.add(path);
				
				// 섬네일 이미지
				path = Paths.get("C:\\upload", attachImageVO.getUploadPath(), "s_" + attachImageVO.getUuid()+"_" + attachImageVO.getFileName());
				pathList.add(path);
				
			});
			
			pathList.forEach(path ->{
				path.toFile().delete();
			});
			
		}

		int result = adminService.goodsDelete(bookId);

		rttr.addFlashAttribute("delete_result", result);

		return "redirect:/admin/goodsManage";

	}

	/* 작가 등록 페이지 접속 */
	@GetMapping("/authorEnroll")
	public void authorEnrollGet() throws Exception {
		logger.info("작가 등록 페이지 접속");
	}

	/* 작가 등록 */
	@PostMapping("/authorEnroll.do")
	public String authEnrollPost(AuthorVO authorVO, RedirectAttributes rttr) throws Exception {
		logger.info("작가 등록 서버");
		authorService.authorEnroll(authorVO);
		rttr.addFlashAttribute("enroll_result", authorVO.getAuthorName());
		return "redirect:/admin/authorManage";
	}

	/* 작가 관리 페이지 접속 */
	@GetMapping("/authorManage")
	public void authorManageGet(Criteria cri, Model model) throws Exception {
		logger.info("작가 관리 페이지 접속");
		// 작가 목록 출력 데이터
		// model.addAttribute("list", authorService.authorGetList(cri));
		List list = authorService.authorGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty"); // 작가 존재하지 않을 경우
		}

		// 페이지 이동 인터페이스 데이터
		int total = authorService.authorGetTotal(cri);

		PageDTO pageMaker = new PageDTO(cri, total);

		model.addAttribute("pageMaker", pageMaker);

	}

	/* 작가 상세 페이지 접속 */
	@GetMapping({ "/authorDetail", "/authorModify" })
	public void authorDetailGet(int authorId, Model model, Criteria cri) {

		logger.info("작가 상세 페이지 접속");

		// 작가 관리 페이지 정보 ( 검색, 페이징 )
		model.addAttribute("cri", cri);
		// 작가 정보
		model.addAttribute("authorInfo", authorService.authorGetDetail(authorId));

	}

	/* 작가 정보 수정 */
	@PostMapping("/authorModify")
	public String authorModifyPost(AuthorVO author, RedirectAttributes rttr) throws Exception {

		logger.info("authorModifyPOST......." + author);

		int result = authorService.authorModify(author);

		rttr.addFlashAttribute("modify_result", result);

		return "redirect:/admin/authorManage";

	}

	/* 작가 정보 삭제 */
	@PostMapping("authorDelete")
	public String authorDeletePost(RedirectAttributes rttr, int authorId) {

		logger.info("작가 정보 삭제");

		int result = 0;

		try {
			result = authorService.authorDelete(authorId);
		} catch (Exception e) {
			e.printStackTrace();
			result = 2;
			rttr.addFlashAttribute("delete_result", result);
			return "redirect:/admin/authorManage";
		}

		rttr.addFlashAttribute("delete_result", result);

		return "redirect:/admin/authorManage";
	}

	/* 작가 검색 팝업창 */
	@GetMapping("/authorPop")
	public void authorPopGet(Criteria cri, Model model) throws Exception {

		logger.info("작가 검색 팝업");

		cri.setAmount(5);

		List list = authorService.authorGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty"); // 작가 존재하지 않을 경우
		}

		// 페이지 이동 인터페이스 데이터
		int total = authorService.authorGetTotal(cri);

		PageDTO pageMaker = new PageDTO(cri, total);

		model.addAttribute("pageMaker", pageMaker);

	}
	
	/* 주문 현황 페이지 */
	@GetMapping("/orderList")
	public String orderListGET(Criteria cri, Model model) {
		
		List<OrderDTO> list = adminService.getOrderList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
			model.addAttribute("pageMaker", new PageDTO(cri, adminService.getOrderTotal(cri)));
		} else {
			model.addAttribute("listCheck", "empty");
		}
		
		return "/admin/orderList";
	}
	
	/* 주문삭제 */
	@PostMapping("/orderCancle")
	public String orderCanclePOST(OrderCancelDTO orderCancelDTO, HttpServletRequest request) {
		
		orderService.orderCancle(orderCancelDTO);		
		
		return "redirect:/admin/orderList?keyword=" + orderCancelDTO.getKeyword() + "&amount=" + orderCancelDTO.getAmount() + "&pageNum=" + orderCancelDTO.getPageNum();
	}

}
