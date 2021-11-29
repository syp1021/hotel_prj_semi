package kr.co.sist.user.card;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;


public class ModifyCard {

	/**
	 * ������� id�� ī���ȣ ������Ʈ
	 * @param cVO
	 * @return
	 * @throws DataAccessException
	 */
	public int updateCard ( CardVO cVO ) throws DataAccessException{
		int cnt = 0;
		
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
				
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
				
		// 3. ���� ����
		String updateCard = "update card_info set card_no = ? where id = ?";
		cnt = jt.update(updateCard, cVO.getCard_no(), cVO.getId() );
		return cnt;
	}//updateCard
	
}//class
