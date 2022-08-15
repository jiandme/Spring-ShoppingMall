package kr.co.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.dao.AuthorDAO;
import kr.co.dao.MemberDAO;
import kr.co.vo.AuthorVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;

@Service
public class AuthorServicelmpl implements AuthorService {
	
	@Autowired
	private AuthorDAO dao;
	
	@Override
	public void authorEnroll(AuthorVO author) throws Exception {
		dao.authorEnroll(author);
		

	}
	
	@Override
	public List<AuthorVO> authorGetList(Criteria cri) {
		return dao.authorGetList(cri);
	}
	
	@Override
	public int authorGetTotal(Criteria cri) {
		return dao.authorGetTotal(cri);
	}
	
	@Override
	public AuthorVO authorGetDetail(int authorId) {
		return dao.authorGetDetail(authorId);
	}
	
	@Override
	public int authorModify(AuthorVO authorVO) {
		return dao.authorModify(authorVO);
	}
	
	@Override
	public int authorDelete(int authorId) {
		return dao.authorDelete(authorId);
	}
	
		
}
