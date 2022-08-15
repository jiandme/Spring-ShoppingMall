package kr.co.dao;

import kr.co.vo.MemberVO;

public interface MemberDAO {
	
	//ȸ������
	public void memberJoin(MemberVO memberVO);	
	//���̵� �ߺ�üũ
	public int idCheck(String memberId) throws Exception;
	//�α���
	public MemberVO memberLogin(MemberVO memberVO) throws Exception;
	/* �ֹ��� �ּ� ���� */
	public MemberVO getMemberInfo(String memberId);
}
