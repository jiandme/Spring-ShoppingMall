package kr.co.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

@Repository
public class ReplyDAOlmpl implements ReplyDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int enrollReply(ReplyDTO replyDTO) {
		return sqlSession.insert("replyMapper.enrollReply", replyDTO);
	}
	
	@Override
	public Integer checkReply(ReplyDTO replyDTO) {
		return sqlSession.selectOne("replyMapper.checkReply", replyDTO);
	}
	
	@Override
	public List<ReplyDTO> getReplyList(Criteria cri) {
		return sqlSession.selectList("replyMapper.getReplyList", cri);
	}
	
	@Override
	public int getReplyTotal(int bookId) {
		return sqlSession.selectOne("replyMapper.getReplyTotal", bookId);
	}
	
	@Override
	public int updateReply(ReplyDTO replyDTO) {
		return sqlSession.update("replyMapper.updateReply", replyDTO);
	}
	
	@Override
	public ReplyDTO getUpdateReply(int replyId) {
		return sqlSession.selectOne("replyMapper.getUpdateReply", replyId);
	}
	
	@Override
	public int deleteReply(int replyId) {
		return sqlSession.delete("replyMapper.deleteReply", replyId);
	}
}
