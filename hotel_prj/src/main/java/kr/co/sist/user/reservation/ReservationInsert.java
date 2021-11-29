package kr.co.sist.user.reservation;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;



public class ReservationInsert {

	public int insertRes ( ReservationVO rsVO ) throws DataAccessException{
		int cnt = 0;
		
		// 1. GetJdbcTemplate ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance(); // JdbcTemplate ����
				
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		// ����
		String insertRes = "insert into reservation (res_no, id, room_no, adult, child, "
				+ "chkin_date, chkout_date, add_req, cc_agree, pi_agree, res_date, res_status, card_no, company)"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, 'Y', ?, ? )";
		System.out.println(rsVO);
		// ������ ����
		cnt = jt.update(insertRes, rsVO.getRes_no(), rsVO.getId(), rsVO.getRoom_no(), rsVO.getAdult(),
				rsVO.getChild(), rsVO.getChkin_date(), rsVO.getChkout_date(), rsVO.getAdd_req(), rsVO.getCc_agree(),
				rsVO.getPi_agree(), rsVO.getCard_no(), rsVO.getCompany());
		
		// 4. ���� ����
		gjt.closeAc();
				
		return cnt;
	}//insertRes
}//class
