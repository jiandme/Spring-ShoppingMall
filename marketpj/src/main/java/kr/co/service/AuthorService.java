package kr.co.service;

import java.util.List;

import kr.co.vo.AuthorVO;
import kr.co.vo.Criteria;

public interface AuthorService {
	
	/* 작가 등록 */
    public void authorEnroll(AuthorVO author) throws Exception;
    
	/* 작가 목록 */
    public List<AuthorVO> authorGetList(Criteria cri);
    
	/* 전체 작가 숫자 */
    public int authorGetTotal(Criteria cri);
    
	/* 작가 상세 정보 */
    public AuthorVO authorGetDetail(int authorId);
    
	/* 작가 정보 수정 */
    public int authorModify(AuthorVO authorVO);
    
	/* 작가 정보 삭제 */
    public int authorDelete(int authorId);
}
