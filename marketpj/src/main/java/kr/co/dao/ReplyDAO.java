package kr.co.dao;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.BookVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.MemberVO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderItemDTO;
import kr.co.vo.OrderPageItemDTO;
import kr.co.vo.ReplyDTO;

public interface ReplyDAO {
	
	/* ��� ��� */
	public int enrollReply(ReplyDTO replyDTO);
	
	/* ��� ���� üũ */
	public Integer checkReply(ReplyDTO replyDTO);
	
	/* ��� ����¡ */
	public List<ReplyDTO> getReplyList(Criteria cri);
	
	/* ��� �� ����(����¡) */
	public int getReplyTotal(int bookId);
	
	/* ��� ���� */
	public int updateReply(ReplyDTO replyDTO);
	
	/* ��� �Ѱ� ����(����������) */
	public ReplyDTO getUpdateReply(int replyId);
	
	/* ��� ���� */
	public int deleteReply(int replyId);
}
