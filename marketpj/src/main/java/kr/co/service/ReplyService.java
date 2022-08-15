package kr.co.service;

import java.util.List;

import kr.co.vo.AttachImageVO;
import kr.co.vo.AuthorVO;
import kr.co.vo.CartDTO;
import kr.co.vo.Criteria;
import kr.co.vo.OrderCancelDTO;
import kr.co.vo.OrderDTO;
import kr.co.vo.OrderPageItemDTO;
import kr.co.vo.ReplyDTO;
import kr.co.vo.ReplyPageDTO;

public interface ReplyService {
	
	/* 댓글 등록 */
	public int enrollReply(ReplyDTO replyDTO);
	
	/* 댓글 존재 체크 */
	public String checkReply(ReplyDTO replyDTO);
	
	/* 댓글 페이징 */
	public ReplyPageDTO replyList(Criteria cri);
	
	/* 댓글 수정 */
	public int updateReply(ReplyDTO replyDTO);
	
	/* 댓글 한개 정보(수정페이지) */
	public ReplyDTO getUpdateReply(int replyId);
	
	/* 댓글 삭제 */
	public int deleteReply(ReplyDTO replyDTO);
}
