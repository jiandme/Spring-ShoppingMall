package kr.co.dao;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.Criteria;

public interface AttachDAO {
	
	/* �̹��� ������ ��ȯ */
    public List<AttachImageVO> getAttachList(int bookId);

}
