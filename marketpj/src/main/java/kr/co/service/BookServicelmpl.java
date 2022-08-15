package kr.co.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.dao.AttachDAO;
import kr.co.dao.AuthorDAO;
import kr.co.dao.BookDAO;
import kr.co.dao.MemberDAO;
import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateFilterDTO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;

@Service
public class BookServicelmpl implements BookService {
	
	@Autowired
	private BookDAO bookDAO;
	@Autowired
	private AttachDAO attachDAO;
	
	@Override
	public List<BookVO> goodsGetList(Criteria cri) {
		List<BookVO> list = bookDAO.goodsGetList(cri);
		//이미지 정보 삽입
		list.forEach(book -> {
			
			int bookId = book.getBookId();
			
			List<AttachImageVO> imageList = attachDAO.getAttachList(bookId);
			
			book.setImageList(imageList);
			
		});
		return list;
	}
	
	@Override
	public int goodsGetTotal(Criteria cri) {
		return bookDAO.goodsGetTotal(cri);
	}
	
	@Override
	public List<CateVO> getCateCode1() {
		return bookDAO.getCateCode1();
	}
	
	@Override
	public List<CateVO> getCateCode2() {
		return bookDAO.getCateCode2();
	}
	
	/*
	 * @Override public String[] getCateList(Criteria cri) { return
	 * bookDAO.getCateList(cri); }
	 */
	
	@Override
	public List<CateFilterDTO> getCateInfo(Criteria cri) {
		return bookDAO.getCateInfo(cri);
	}
	
	@Override
	public BookVO getGoodsInfo(int bookId) {
		
		BookVO goodsInfo = bookDAO.getGoodsInfo(bookId);
		goodsInfo.setImageList(attachDAO.getAttachList(bookId));
		return goodsInfo;
	}
	
	
		
}
