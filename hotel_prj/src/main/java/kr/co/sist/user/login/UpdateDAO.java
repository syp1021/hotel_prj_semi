package kr.co.sist.user.login;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.login.MemberChgPassVO;

public class UpdateDAO {

	
	
	
	/**
	 * ��й�ȣ
	 * @param mVO
	 * @return
	 * @throws 
	 */
	public int updatePass(MemberChgPassVO mVO)throws DataAccessException{
		//1.Spring Container ���
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		//2. JdbcTemplate ���
		JdbcTemplate jt=gjt.getJdbcTemplate();
		//3.������ ����.   
		String updatePass="update member set pass=? where id=? and pass=?";
		int cnt=jt.update(updatePass,mVO.getChange_pass(), mVO.getId(), mVO.getPass() );
		//4. Spring Container �ݱ�.
		gjt.closeAc();
		
		return cnt;
	}
	
	/**
	 * @param kname
	 * @return
	 */
	public int delmember(memberVO mVO)throws SQLException {
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		// 3. ���� ����
		String deleteRes = "update member set m_status='N', out_date = sysdate where id=? ";
		int cnt = jt.update(deleteRes, mVO.getId());
		// 4. Spring Container �ݱ�
		gjt.closeAc();

		return cnt;
	}// delete
	
}
