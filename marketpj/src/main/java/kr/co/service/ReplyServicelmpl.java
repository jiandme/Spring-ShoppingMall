package kr.co.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dao.AttachDAO;
import kr.co.dao.AuthorDAO;
import kr.co.dao.BookDAO;
import kr.co.dao.CartDAO;
import kr.co.dao.MemberDAO;
import kr.co.dao.OrderDAO;
import kr.co.dao.ReplyDAO;
import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderCancelDTO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderItemDTO;
import kr.co.vo.OrderPageItemDTO;
import kr.co.vo.PageDTO;
import kr.co.vo.ReplyDTO;
import kr.co.vo.ReplyPageDTO;

@Service
public class ReplyServicelmpl implements ReplyService {
	
	@Autowired
	private ReplyDAO replyDAO;

	
	@Override
	public int enrollReply(ReplyDTO replyDTO) {
		int result = replyDAO.enrollReply(replyDTO);
		return result;
	}
	
	@Override
	public String checkReply(ReplyDTO replyDTO) {
		Integer result = replyDAO.checkReply(replyDTO);
		
		if(result == null) {
			return "0";
		} else {
			return "1";
		}
	}
	
	@Override
	public ReplyPageDTO replyList(Criteria cri) {
		
		ReplyPageDTO replyPageDTO = new ReplyPageDTO();
		
		replyPageDTO.setList(replyDAO.getReplyList(cri));
		replyPageDTO.setPageInfo(new PageDTO(cri, replyDAO.getReplyTotal(cri.getBookId())));
		
		return replyPageDTO;
	}
	
	
	@Override
	public int updateReply(ReplyDTO replyDTO) { 
		int result = replyDAO.updateReply(replyDTO);
		return result;
	}
	
	@Override
	public ReplyDTO getUpdateReply(int replyId) {
		return replyDAO.getUpdateReply(replyId);
	}
	
	@Override
	public int deleteReply(ReplyDTO replyDTO) {
		int result = replyDAO.deleteReply(replyDTO.getReplyId()); 
		return result;
	}
	
	
	
		
}
