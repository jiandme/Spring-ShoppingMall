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

	// ������ ������ �̵�
	@GetMapping("/main")
	public void adminPageGet() {

		logger.info("������ ������ ����");

	}

	/* ��ǰ ��� ������ ���� */
	@GetMapping("/goodsEnroll")
	public void goodsEnrollGet(Model model) throws Exception {
		logger.info("��ǰ ��� ������ ����");

		// ī�װ� ����Ʈ�� json �����ͷ� ��ȯ
		ObjectMapper objm = new ObjectMapper();
		List list = adminService.cateList();
		// ����Ʈ�� ��� ��ü�� ��Ʈ�� Ÿ������ ��ȯ
		String cateList = objm.writeValueAsString(list);
		model.addAttribute("cateList", cateList);

	}

	/* ��ǰ ��� */
	@PostMapping("/goodsEnroll")
	public String goodsEnrollPost(BookVO bookVO, RedirectAttributes rttr) throws Exception {

		logger.info("��ǰ��Ͽ�û");

		adminService.bookEnroll(bookVO);

		rttr.addFlashAttribute("enroll_result", bookVO.getBookName());

		return "redirect:/admin/goodsManage";
	}

	/* �̹��� ���� ���ε� */
	@PostMapping(value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPost(MultipartFile[] uploadFile) {

		logger.info("���� ���ε� AJAX");
		
		/* �̹��� ���� üũ */
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

		// ���ε� �ϴ� ���� �޾ƿ���
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-", File.separator);
		File uploadPath = new File(uploadFolder, datePath);
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		/* �̹��� ���� ��� ��ü */
		List<AttachImageVO> list = new ArrayList();
		
		
		// �Ѿ���� ���� ����
		for (MultipartFile multipartFile : uploadFile) {
			
			AttachImageVO attachImageVO = new AttachImageVO();

			/* ���� �̸� */
			String uploadFileName = multipartFile.getOriginalFilename();
			attachImageVO.setFileName(uploadFileName);
			attachImageVO.setUploadPath(datePath);

			/* uuid ���� ���� �̸� */
			String uuid = UUID.randomUUID().toString();
			attachImageVO.setUuid(uuid);

			uploadFileName = uuid + "_" + uploadFileName;

			/* ���� ��ġ, ���� �̸��� ��ģ File ��ü */
			File saveFile = new File(uploadPath, uploadFileName);

			/* ���� ���� */
			try {
				// ���� ���� ����
				multipartFile.transferTo(saveFile);

				/*
				 * //����Ͽ� �̹��� ������ ���� File thumbnailFile = new File(uploadPath, "s_" +
				 * uploadFileName); BufferedImage bo_image = ImageIO.read(saveFile);
				 * 
				 * ���� double ratio = 3; ���� ���� int width = (int) (bo_image.getWidth() / ratio);
				 * int height = (int) (bo_image.getHeight() / ratio);
				 * 
				 * 
				 * BufferedImage bt_image = new BufferedImage(width, height,
				 * BufferedImage.TYPE_3BYTE_BGR); Graphics2D graphic =
				 * bt_image.createGraphics(); graphic.drawImage(bo_image, 0, 0,width,height,
				 * null); ImageIO.write(bt_image, "jpg", thumbnailFile);
				 */

				/* ��� 2 */
				File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);

				BufferedImage bo_image = ImageIO.read(saveFile);

				// ����
				double ratio = 3;
				// ���� ����
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
	
	
	/* �̹��� ���� ���� */
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName){
		
		logger.info("�̹������� ���� �����̸� : " + fileName);
		File file = null;
		
		try {
			
			/* ����� ���� ���� */
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			
			file.delete();
			
			/* ���� ���� ���� */
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

	/* ��ǰ ���� ������ ���� */
	@GetMapping("/goodsManage")
	public void goodsManageGet(Criteria cri, Model model) throws Exception {
		logger.info("��ǰ ���� ������ ����");

		List list = adminService.goodsGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // ��ǰ ���� ���
		} else {
			model.addAttribute("listCheck", "empty"); // ��ǰ �������� ���� ���
		}

		// ������ �̵� �������̽� ������
		int total = adminService.goodsGetTotal(cri);

		PageDTO pageMaker = new PageDTO(cri, total);

		model.addAttribute("pageMaker", pageMaker);

	}

	/* ��ǰ �� ������, ���������� ���� */
	@GetMapping({ "/goodsDetail", "/goodsModify" })
	public void goodsDetailGet(Model model, Criteria cri, int bookId) throws Exception {
		logger.info("��ǰ ��,���� ������ ����");

		ObjectMapper mapper = new ObjectMapper();

		/* ī�װ� ����Ʈ ������ */
		model.addAttribute("cateList", mapper.writeValueAsString(adminService.cateList()));

		model.addAttribute("cri", cri);

		model.addAttribute("goodsInfo", adminService.goodsGetDetail(bookId));

	}

	/* ��ǰ ���� ���� */
	@PostMapping("/goodsModify")
	public String goodsModifyPOST(BookVO bookVO, RedirectAttributes rttr) {

		logger.info("��ǰ ���� ������ ����");

		int result = adminService.goodsModify(bookVO);

		rttr.addFlashAttribute("modify_result", result);

		return "redirect:/admin/goodsManage";

	}

	/* ��ǰ ���� ���� */
	@PostMapping("/goodsDelete")
	public String goodsDeletePost(int bookId, RedirectAttributes rttr) {

		logger.info("goodsDeletePOST..........");
		
		List<AttachImageVO> fileList = attachService.getAttachList(bookId);
		
		if(fileList != null) {
			
			List<Path> pathList = new ArrayList();
			
			fileList.forEach(attachImageVO ->{
				
				// ���� �̹���
				Path path = Paths.get("C:\\upload", attachImageVO.getUploadPath(), attachImageVO.getUuid() + "_" + attachImageVO.getFileName());
				pathList.add(path);
				
				// ������ �̹���
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

	/* �۰� ��� ������ ���� */
	@GetMapping("/authorEnroll")
	public void authorEnrollGet() throws Exception {
		logger.info("�۰� ��� ������ ����");
	}

	/* �۰� ��� */
	@PostMapping("/authorEnroll.do")
	public String authEnrollPost(AuthorVO authorVO, RedirectAttributes rttr) throws Exception {
		logger.info("�۰� ��� ����");
		authorService.authorEnroll(authorVO);
		rttr.addFlashAttribute("enroll_result", authorVO.getAuthorName());
		return "redirect:/admin/authorManage";
	}

	/* �۰� ���� ������ ���� */
	@GetMapping("/authorManage")
	public void authorManageGet(Criteria cri, Model model) throws Exception {
		logger.info("�۰� ���� ������ ����");
		// �۰� ��� ��� ������
		// model.addAttribute("list", authorService.authorGetList(cri));
		List list = authorService.authorGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // �۰� ���� ���
		} else {
			model.addAttribute("listCheck", "empty"); // �۰� �������� ���� ���
		}

		// ������ �̵� �������̽� ������
		int total = authorService.authorGetTotal(cri);

		PageDTO pageMaker = new PageDTO(cri, total);

		model.addAttribute("pageMaker", pageMaker);

	}

	/* �۰� �� ������ ���� */
	@GetMapping({ "/authorDetail", "/authorModify" })
	public void authorDetailGet(int authorId, Model model, Criteria cri) {

		logger.info("�۰� �� ������ ����");

		// �۰� ���� ������ ���� ( �˻�, ����¡ )
		model.addAttribute("cri", cri);
		// �۰� ����
		model.addAttribute("authorInfo", authorService.authorGetDetail(authorId));

	}

	/* �۰� ���� ���� */
	@PostMapping("/authorModify")
	public String authorModifyPost(AuthorVO author, RedirectAttributes rttr) throws Exception {

		logger.info("authorModifyPOST......." + author);

		int result = authorService.authorModify(author);

		rttr.addFlashAttribute("modify_result", result);

		return "redirect:/admin/authorManage";

	}

	/* �۰� ���� ���� */
	@PostMapping("authorDelete")
	public String authorDeletePost(RedirectAttributes rttr, int authorId) {

		logger.info("�۰� ���� ����");

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

	/* �۰� �˻� �˾�â */
	@GetMapping("/authorPop")
	public void authorPopGet(Criteria cri, Model model) throws Exception {

		logger.info("�۰� �˻� �˾�");

		cri.setAmount(5);

		List list = authorService.authorGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // �۰� ���� ���
		} else {
			model.addAttribute("listCheck", "empty"); // �۰� �������� ���� ���
		}

		// ������ �̵� �������̽� ������
		int total = authorService.authorGetTotal(cri);

		PageDTO pageMaker = new PageDTO(cri, total);

		model.addAttribute("pageMaker", pageMaker);

	}
	
	/* �ֹ� ��Ȳ ������ */
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
	
	/* �ֹ����� */
	@PostMapping("/orderCancle")
	public String orderCanclePOST(OrderCancelDTO orderCancelDTO, HttpServletRequest request) {
		
		orderService.orderCancle(orderCancelDTO);		
		
		return "redirect:/admin/orderList?keyword=" + orderCancelDTO.getKeyword() + "&amount=" + orderCancelDTO.getAmount() + "&pageNum=" + orderCancelDTO.getPageNum();
	}

}
