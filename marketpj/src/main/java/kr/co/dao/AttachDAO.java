package kr.co.dao;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.Criteria;

public interface AttachDAO {
	
	/* 이미지 데이터 반환 */
    public List<AttachImageVO> getAttachList(int bookId);

}
