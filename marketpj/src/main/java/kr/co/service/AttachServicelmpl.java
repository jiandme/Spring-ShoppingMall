package kr.co.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.dao.AttachDAO;
import kr.co.dao.AuthorDAO;
import kr.co.dao.MemberDAO;
import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;

@Service
public class AttachServicelmpl implements AttachService {
	
	@Autowired
	private AttachDAO dao;
	
	@Override
	public List<AttachImageVO> getAttachList(int bookId) {
		return dao.getAttachList(bookId);
	}
	
		
}
