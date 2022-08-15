package kr.co.dao;

import java.util.List;

import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CateFilterDTO;
import kr.co.vo.CateVO;
import kr.co.vo.Criteria;

public interface BookDAO {


	/* 상품 검색 */
	public List<BookVO> goodsGetList(Criteria cri);
	
	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri);
	
	/* 국내 카테고리 리스트 */
	public List<CateVO> getCateCode1();
	
	/* 외국 카테고리 리스트 */
	public List<CateVO> getCateCode2();
	
	/* 검색 대상 카테고리 리스트 */
	/* public String[] getCateList(Criteria cri); */
	
	/* 카테고리 정보(+검색대상 갯수) */
	public List<CateFilterDTO> getCateInfo(Criteria cri);
	
	/* 상품 정보 */
	public BookVO getGoodsInfo(int bookId);
	
}
