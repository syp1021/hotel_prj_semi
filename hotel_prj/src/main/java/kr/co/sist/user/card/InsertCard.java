package kr.co.sist.user.card;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;


public class InsertCard {

	public int insertCard (CardVO cardVO) throws DataAccessException{
		int cnt = 0;
		
		// 1. GetJdbcTemplate ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance(); // JdbcTemplate ����
				
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		System.out.println(cnt);
		System.out.println( cardVO.getCard_no());
		
		//  ������
		String insertCard = "insert into card_info (card_no, company, val_mm, val_yy, id, res_no) values( ?, ?, ?, ?, ?, ?)";
		
		// 3. ������ ���� 
		cnt = jt.update(insertCard, cardVO.getCard_no(), cardVO.getCompany(), cardVO.getVal_mm(), cardVO.getVal_yy(), cardVO.getId(), cardVO.getRes_no() );
		
		// 4. ���� ����
		gjt.closeAc();
				
		return cnt;
		
	}//insertCard
}//class
