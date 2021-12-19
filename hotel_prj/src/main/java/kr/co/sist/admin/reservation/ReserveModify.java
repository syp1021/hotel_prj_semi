package kr.co.sist.admin.reservation;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;

/**
 * Ư�� ������ ����/������ �۾��� �ϴ� Ŭ����
 * 
 * @author user
 */
public class ReserveModify {

	/**
	 * Ư������� ���� method / update�� status N�� ����
	 * 
	 * @param resNum ���õ� �����ȣ
	 * @return ���� ���� �� 1��ȯ
	 */
	public int deleteRes(String resNum) throws DataAccessException {
		int cnt = 0;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		String deleteRes = "update reservation 	set res_status='N'	where res_no=?";
		cnt = jt.update(deleteRes, resNum);

		gjt.closeAc();

		return cnt;
	}// deleteRes

	/**
	 * Ư�� ����� ���� ��û��� update�ϴ� method
	 * @param ruVO
	 * @return
	 * @throws DataAccessException
	 */
	public int updateRes(ReserveUpdateVO ruVO) throws DataAccessException {
		int cnt = 0;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		StringBuilder sb = new StringBuilder();
		sb.append("update reservation		")
		.append("	set 	chkin_date = to_char(to_date(?,'yyyy.mm.dd'),'yyyy.mm.dd'),")
		.append("		chkout_date = to_char(to_date(?,'yyyy.mm.dd'),'yyyy.mm.dd'),")
		.append("		adult = ?, child = ?,")
		.append("		room_no = (select room_no from room where r_name=?),")
		.append("       add_req = ?	 , res_date=sysdate	").append("where  res_no=?	");

		cnt = jt.update(sb.toString(), ruVO.getChkInDate(), ruVO.getChkOutDate(), ruVO.getAdult(),
				ruVO.getChild(), ruVO.getrName(), ruVO.getAddReq(), ruVO.getResNo());

		gjt.closeAc();

		return cnt;
	}// updateRes

}// class
