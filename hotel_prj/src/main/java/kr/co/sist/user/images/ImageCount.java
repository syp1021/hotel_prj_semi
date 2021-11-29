package kr.co.sist.user.images;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;

public class ImageCount {

	/**
	 * ��� ��ϵ� ���� ���� 
	 * ĳ���� �ε������� ���� �� ���
	 * @param room_no
	 * @return
	 * @throws EmptyResultDataAccessException
	 * @throws IncorrectResultSizeDataAccessException
	 * @throws BadSqlGrammarException
	 */
	public int selectCountImg( int room_no ) throws EmptyResultDataAccessException, IncorrectResultSizeDataAccessException, BadSqlGrammarException{
		int cnt = 0;
		
		// 1. Spring Container�� ����� �� �ִ� ��ü ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		
		// 2. �������� ������ �� �ִ� ��ü ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		// 3. ������ ���� �� ��� ���
		String selectCountImg = "select count(img_src) from images where room_no = ? ";
		
		cnt = jt.queryForObject(selectCountImg, new Object[] { room_no }, int.class);
				
		// 4. Spring Container �ݱ�
		gjt.closeAc();
		
		return cnt;
	}//selectDname
	
	
	/**
	 * ��ѹ��� �����̹����� ��������
	 * @param room_no
	 * @return
	 * @throws SQLException
	 */
	public List<ImagesVO> selectImages( int room_no ) throws SQLException {
		List<ImagesVO> list = null;
		
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		// 3. ��������
		StringBuilder selectImg = new StringBuilder();
		selectImg.append
		("	select img_src").append("	from images	");
		
		// Dynamic query
		if( room_no != 0) {
			selectImg.append("	where room_no = ?	");
		}//end if
			
		if( room_no == 0) {
			// ��� ��������� ��ȸ
			list = jt.query(selectImg.toString(), new SelectImg() );
		}else {
			// �μ��� ��������� ��ȸ
			list = jt.query(selectImg.toString(), new Object[] { Long.valueOf(room_no) }, new SelectImg() );
		}//end else
		
		
		// 4. Spring Container �ݱ�
		gjt.closeAc();
		
		return list;
	}//selectDeptEmp
	
	/////////////////////////////////////////////////
	public class SelectImg implements RowMapper<ImagesVO>{
		public ImagesVO mapRow(ResultSet rs, int rowNum) throws SQLException{
			ImagesVO iv = new ImagesVO();
			iv.setImg_src(rs.getString("img_src"));
			return iv;
		}//mapRow
	}
	
}//class
