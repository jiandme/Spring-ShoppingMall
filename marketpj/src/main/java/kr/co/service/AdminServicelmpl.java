package kr.co.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dao.AuthorDAO;
import kr.co.dao.AdminDAO;
import kr.co.dao.MemberDAO;
import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderDTO;

@Service
public class AdminServicelmpl implements AdminService {
	
	@Autowired
	private AdminDAO dao;
	
	@Transactional
	@Override
	public void bookEnroll(BookVO bookVO) throws Exception {
		dao.bookEnroll(bookVO);
		
		//�̹��� ÷��
		
		if(bookVO.getImageList() == null || bookVO.getImageList().size() <= 0) {
			return;
		}
		
		for(AttachImageVO attachImageVO : bookVO.getImageList()) {
			attachImageVO.setBookId(bookVO.getBookId());
			dao.imageEnroll(attachImageVO);
		}
		
	}
	
	@Override
	public List<CateVO> cateList() {
		return dao.cateList();
	}
	
	@Override
	public List<BookVO> goodsGetList(Criteria cri) {
		return dao.goodsGetList(cri);
	}
	
	@Override
	public int goodsGetTotal(Criteria cri) {
		return dao.goodsGetTotal(cri);
	}
	
	@Override
	public BookVO goodsGetDetail(int bookId) {
		return dao.goodsGetDetail(bookId);
	}
	
	
	
	@Transactional
	@Override
	public int goodsModify(BookVO bookVO) {
		int result =  dao.goodsModify(bookVO);
		if(result == 1 && bookVO.getImageList() != null && bookVO.getImageList().size() > 0) {
			//���������ÿ� �ش� bookId�� ����� �̹��� ��� ����
			dao.deleteImageAll(bookVO.getBookId());
			//bookVO�ȿ� ������ �ִ� AttachVO�� for���� ���� bookId�� �־����� db�� ����
			for(AttachImageVO attachImageVO : bookVO.getImageList()) {
				attachImageVO.setBookId(bookVO.getBookId());
				dao.imageEnroll(attachImageVO);
			
			}
		}
		return result;
	}
	
	
	
	@Transactional
	@Override
	public int goodsDelete(int bookId) {
		dao.deleteImageAll(bookId);
		return dao.goodsDelete(bookId);
	}
	
	@Override
	public void imageEnroll(AttachImageVO attachImageVO) {
		dao.imageEnroll(attachImageVO);
	}
	
	@Override
	public List<AttachImageVO> checkFileList() {
		return dao.checkFileList();
	}
	
	@Override
	public List<OrderDTO> getOrderList(Criteria cri) {
		return dao.getOrderList(cri);
	}
	
	@Override
	public int getOrderTotal(Criteria cri) {
		return dao.getOrderTotal(cri);
	}
		
}
