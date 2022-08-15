package kr.co.service;

import java.util.List;

import kr.co.vo.AuthorVO;
import kr.co.vo.Criteria;

public interface AuthorService {
	
	/* �۰� ��� */
    public void authorEnroll(AuthorVO author) throws Exception;
    
	/* �۰� ��� */
    public List<AuthorVO> authorGetList(Criteria cri);
    
	/* ��ü �۰� ���� */
    public int authorGetTotal(Criteria cri);
    
	/* �۰� �� ���� */
    public AuthorVO authorGetDetail(int authorId);
    
	/* �۰� ���� ���� */
    public int authorModify(AuthorVO authorVO);
    
	/* �۰� ���� ���� */
    public int authorDelete(int authorId);
}
