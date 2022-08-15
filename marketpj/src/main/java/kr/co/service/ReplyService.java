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
	
	/* ��� ��� */
	public int enrollReply(ReplyDTO replyDTO);
	
	/* ��� ���� üũ */
	public String checkReply(ReplyDTO replyDTO);
	
	/* ��� ����¡ */
	public ReplyPageDTO replyList(Criteria cri);
	
	/* ��� ���� */
	public int updateReply(ReplyDTO replyDTO);
	
	/* ��� �Ѱ� ����(����������) */
	public ReplyDTO getUpdateReply(int replyId);
	
	/* ��� ���� */
	public int deleteReply(ReplyDTO replyDTO);
}
