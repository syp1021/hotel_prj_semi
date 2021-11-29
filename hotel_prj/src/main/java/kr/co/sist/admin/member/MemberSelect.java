package kr.co.sist.admin.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;

public class MemberSelect {

	/**
	 * ȸ�� ��ȸ ( ���̵�,�̸�,�������,��ȣ,�̸���,�����̸�)
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<MemberVO> selectMember() throws SQLException {
		List<MemberVO> list = null;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();

		JdbcTemplate jt = gjt.getJdbcTemplate();
 
		StringBuilder selectMember = new StringBuilder();
		selectMember.append("select id,kname,birth_year,tel,email,ename_fst,ename_lst")
							.append(" from member ")
							.append(" where m_status= 'Y'") ;
		list=jt.query(selectMember.toString(), new SelectMember());
		gjt.closeAc();
		return list;
	}// MemberSelect

	public class SelectMember implements RowMapper<MemberVO> {
		public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberVO mv = new MemberVO();
			mv.setId(rs.getString("id"));
			mv.setKname(rs.getString("kname"));
			mv.setBirth_year(rs.getString("birth_year"));
			mv.setTel(rs.getString("tel"));
			mv.setEmail(rs.getString("email"));
			mv.setEname_fst(rs.getString("ename_fst"));
			mv.setEname_lst(rs.getString("ename_lst"));
			return mv;
		}
	}// selectMember
	
	
	/**
	 * �̸��� ��ȸ�Ͽ� Ư��ȸ�� ��ȸ
	 * 
	 * @param kname
	 * @return
	 * @throws EmptyResultDataAccessException
	 * @throws IncorrectResultSizeDataAccessException
	 * @throws BadSqlGrammarException
	 */
	public MemberVO selectSpecificMember(String id) throws SQLException {// mapRow method throws
		MemberVO mVO = null;
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		// 2 JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		// 3. ���� ����
		String select = "select id,kname,birth_year,tel,email,ename_fst,ename_lst from member where id=?";
		// interface�� anonymous inner class�� �����Ͽ� �׾ȿ��� ��ȸ����� VO�� �Ҵ�.
		mVO = jt.queryForObject(select, new Object[] { id }, new RowMapper<MemberVO>() {
			public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberVO mVO = new MemberVO();
				// ResultSet�� ����Ͽ� ��ȸ����� VO�� ����
				mVO.setId(rs.getString("id"));
				mVO.setKname(rs.getString("kname"));
				mVO.setBirth_year(rs.getString("birth_year"));
				mVO.setTel(rs.getString("tel"));
				mVO.setEmail(rs.getString("email"));
				mVO.setEname_fst(rs.getString("ename_fst"));
				mVO.setEname_lst(rs.getString("ename_lst"));
				// ��ȸ����� ������ dv��ȯ
				return mVO;
			}
		});
		// 4,. Spring Container �ݱ�
		gjt.closeAc();
		return mVO;
	}
	/**
	 * �̸��� ��ȸ�Ͽ� Ż��ȸ�� ��ȸ
	 * 
	 * @param kname
	 * @return
	 * @throws EmptyResultDataAccessException
	 * @throws IncorrectResultSizeDataAccessException
	 * @throws BadSqlGrammarException
	 */
	public MemberVO selectSpecificMemberDelete(String id) throws SQLException {
		MemberVO mVO = null;
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		// 2 JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		// 3. ���� ����
		String select = "select id,kname,out_date from member where m_status='N' and id=?";
		// interface�� anonymous inner class�� �����Ͽ� �׾ȿ��� ��ȸ����� VO�� �Ҵ�.
		mVO = jt.queryForObject(select, new Object[] { id }, new RowMapper<MemberVO>() {
			public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberVO mVO = new MemberVO();
				// ResultSet�� ����Ͽ� ��ȸ����� VO�� ����
				mVO.setId(rs.getString("id"));
				mVO.setKname(rs.getString("kname"));
				mVO.setOut_date(rs.getDate("out_date"));
				// ��ȸ����� ������ dv��ȯ
				return mVO;
			}
		});
		// 4,. Spring Container �ݱ�
		gjt.closeAc();
		return mVO;
	}

	/**
	 * ������ ���̵� ��й�ȣ
	 * 
	 * @param mg_id
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public String loginChk(String mg_id, String pass) throws SQLException {
		String result = "";

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();

		JdbcTemplate jt = gjt.getJdbcTemplate();

		StringBuilder selectLogin = new StringBuilder();
		selectLogin.append("select mg_id").append(" from manager").append(" where mg_id=? and pass=?");
		result = jt.queryForObject(selectLogin.toString(), new Object[] { mg_id, pass }, String.class);

		gjt.closeAc();
		
		return result;
	}// loginChk

}// class