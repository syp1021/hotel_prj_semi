package kr.co.sist.admin.member;

import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;

public class MemberUpdate {

	
	
	/**
	 * Ư��ȸ�� ���� method / update�� status N�� ����
	 * 
	 * @param kname ȸ�� �̸�
	 * @return ���� ���� �� 1��ȯ
	 */
	public int memberFlag(String id) {
		int cnt = 0;

		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		// 3. ���� ����
		String deleteRes = "update member set 	m_status='N', out_date=sysdate	where id=?";
		cnt = jt.update(deleteRes, id);
		// 4. Spring Container �ݱ�
		gjt.closeAc();
		return cnt;
	}// delete
}// class
