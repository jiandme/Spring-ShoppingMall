package kr.co.service;

import kr.co.vo.MemberVO;

public interface MemberService {
	
	//ȸ������
	public void memberJoin(MemberVO memberVO);
	//���̵� �ߺ�üũ
	public int idCheck(String memberId) throws Exception;
	//�α���
	public MemberVO memberLogin(MemberVO memberVO) throws Exception;
	/* �ֹ��� �ּ� ���� */
	public MemberVO getMemberInfo(String memberId);
		
}
