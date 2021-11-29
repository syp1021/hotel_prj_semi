package kr.co.sist.user.login;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;

public class UpdateMemDAO {
	/**
	 * ��ȭ��ȣ,�̸���,�̸� �����ϴ� ��.
	 * @param mVO
	 * @return
	 * @throws 
	 */
	public int updateMem(MemberChgVO mVO)throws DataAccessException{
		//1.Spring Container ���
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		//2. JdbcTemplate ���
		JdbcTemplate jt=gjt.getJdbcTemplate();
		//3.������ ����.
		String updateMem="update member set kname=?,tel=?,email=? where id=?";
		int cnt= jt.update(updateMem,mVO.getKname(), mVO.getTel(), mVO.getEmail(), mVO.getId() );
		//4. Spring Container �ݱ�.
		gjt.closeAc();
		
		return cnt;
		}
}
