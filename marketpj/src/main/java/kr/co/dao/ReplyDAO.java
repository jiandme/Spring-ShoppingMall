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
	
	/* ¥Ò±€ µÓ∑œ */
	public int enrollReply(ReplyDTO replyDTO);
	
	/* ¥Ò±€ ¡∏¿Á √º≈© */
	public Integer checkReply(ReplyDTO replyDTO);
	
	/* ¥Ò±€ ∆‰¿Ã¬° */
	public List<ReplyDTO> getReplyList(Criteria cri);
	
	/* ¥Ò±€ √— ∞πºˆ(∆‰¿Ã¬°) */
	public int getReplyTotal(int bookId);
	
	/* ¥Ò±€ ºˆ¡§ */
	public int updateReply(ReplyDTO replyDTO);
	
	/* ¥Ò±€ «—∞≥ ¡§∫∏(ºˆ¡§∆‰¿Ã¡ˆ) */
	public ReplyDTO getUpdateReply(int replyId);
	
	/* ¥Ò±€ ªË¡¶ */
	public int deleteReply(int replyId);
}
