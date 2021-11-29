package kr.co.sist.user.card;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;

public class SelectCard {
	
	
	/**
	 * ��ȸ������ ������ �˻��� �ȵǹǷ�, �׶� �� �÷���
	 * @param card_no
	 * @return
	 * @throws SQLException
	 */
	public CardVO checkSavedCard (String id) throws SQLException{
		CardVO cdVO = null;
		
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
						
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		// 3. ����
		String checkFlag = "select nvl(max(card_no), '0') card_no from  card_info where id = ?";
		
		// interface�� anonymous inner class�� �����Ͽ� �� �ȿ��� ��ȸ����� VO�� �Ҵ�
		cdVO = jt.queryForObject(checkFlag, new Object[] {id},
				new RowMapper<CardVO>() {
				@Override
					public CardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					CardVO cdVO = new CardVO();
					cdVO.setCard_no(rs.getString("card_no"));
					return cdVO;
				}
			});
	
	// 4. Spring Container �ݱ�
	gjt.closeAc();
	
		return cdVO;
	}//checkSavedCard
	
	
	/**
	 * ����� id�� ������ ����� ī�� ���� ��������
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public CardVO selectCardInfo ( String id) throws SQLException{
		
		CardVO cVO = null;
		
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
				
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		// 3. ����
		String selectCard = "select card_no, company, val_mm, val_yy from card_info where id = ?";
		
		// interface�� anonymous inner class�� �����Ͽ� �� �ȿ��� ��ȸ����� VO�� �Ҵ�
		cVO = jt.queryForObject(selectCard, new Object[] {id},
				new RowMapper<CardVO>() {
					@Override
					public CardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						CardVO cVO = new CardVO();
						cVO.setCard_no(rs.getString("card_no"));
						cVO.setCompany(rs.getString("company"));
						cVO.setVal_mm(rs.getString("val_mm"));
						cVO.setVal_yy(rs.getString("val_yy"));
						
						
						return cVO;
					}
				});
		
		// 4. Spring Container �ݱ�
		gjt.closeAc();
		
		return cVO;
	}//selectCardInfo
}//class
