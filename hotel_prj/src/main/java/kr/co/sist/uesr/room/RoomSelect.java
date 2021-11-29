package kr.co.sist.uesr.room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;




public class RoomSelect {
	
	
	/**
	 * Ȱ��ȭ�� �� ��ȸ
	 * @return
	 * @throws SQLException
	 */
	public List<Integer> selectAllRoomNo() throws SQLException{
		List<Integer> list = null;
		
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		// 3. ��������
		String selectRoomNo = "select room_no from room where r_status='Y'";
		list = jt.query(selectRoomNo, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer i = Integer.valueOf(rs.getInt("room_no"));
				return i;
			}//mapRow
		});
		
		// 4. Spring Container �ݱ�
		gjt.closeAc();
		
		return list;
	}//selectAllDeptno
	
	
	
	/**
	 * ���ο� ������ ������ �������� ���� ���� / ��� ������ �����̹���, ����Ӹ� ������
	 * @return
	 * @throws SQLException
	 */
	public List<RoomVO> selectMainRoom() throws SQLException{
		List<RoomVO> rList = null;
		
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		// 3. ��������
		String selectMainRoom = "select room_no, r_name, main_img from room where r_status = 'Y'  order by room_no asc";
		rList = jt.query(selectMainRoom, new RowMapper<RoomVO>() {

			@Override
			public RoomVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RoomVO rVO = new RoomVO();
				rVO.setRoom_no(rs.getInt("room_no"));
				rVO.setR_name(rs.getString("r_name"));
				rVO.setMain_img(rs.getString("main_img"));
				return rVO;
			}//mapRow
			
		});
		
		// 4. Spring Container �ݱ�
		gjt.closeAc();
		
		return rList;
	}//selectAllDeptno
	
	

	/**
	 * �ϳ��� �������� ������
	 * @param room_no
	 * @return
	 * @throws SQLException
	 */
	public RoomVO selectRoomInfo( int room_no ) throws SQLException{
		RoomVO rv = null;
		
		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		// 3. ���� ����
		String select = "select r_name, description, bed_type, max_guest, r_size, chkin_time, chkout_time, r_view, "
				+ "amnt_gen, amnt_bath, amnt_other, main_img, price from room where room_no = ?";
		
		// interface�� anonymous inner class�� �����Ͽ� �� �ȿ��� ��ȸ����� VO�� �Ҵ�
		rv = jt.queryForObject(select, new Object[] { room_no }, 
				new RowMapper<RoomVO>() {
			public RoomVO mapRow(ResultSet rs, int rowNum) throws SQLException{
						RoomVO rv = new RoomVO();
						// ResultSet�� ����Ͽ� ��ȸ����� VO�� ����
						rv.setR_name(rs.getString("r_name"));
						rv.setDescription(rs.getString("description"));
						rv.setBed_type(rs.getString("bed_type"));
						rv.setMax_guest(rs.getInt("max_guest"));
						rv.setR_view(rs.getString("r_view"));
						rv.setR_size(rs.getString("r_size"));
						rv.setChkin_time(rs.getString("chkin_time"));
						rv.setChkout_time(rs.getString("chkout_time"));
						rv.setAmnt_gen(rs.getString("amnt_gen"));
						rv.setAmnt_bath(rs.getString("amnt_bath"));
						rv.setAmnt_other(rs.getString("amnt_other"));
						rv.setMain_img(rs.getString("main_img"));
						rv.setPrice(rs.getInt("price"));
						
						
						// ��ȸ����� ������ dv ��ȯ
						return rv;
					}
				});

		// 4. Spring Container �ݱ�
		gjt.closeAc();
		
		return rv;
	}//selectRoomInfo
	
	
	
	/**
	 * ����ڰ� ������ �ο����� ��¥�� �޾� ���డ���� ���� ��ȸ�ϱ�
	 * @param start_date
	 * @param end_date
	 * @param adult
	 * @param child
	 * @return
	 * @throws SQLException
	 */
	public List<RoomVO> selectAvaileReserve (String start_date, String end_date, String adult, String child)
		throws SQLException{
		
		List<RoomVO> list = null;
		
		//Spring Container���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
				
		//JdbcTemplate ���	
		JdbcTemplate jt = gjt.getJdbcTemplate();
				
		//��������
		StringBuilder selectAvailReserve = new StringBuilder();
		selectAvailReserve
		.append("	select 	room_no, room.r_status, room.r_name, room.max_guest, room.description, room.price, room.main_img	")
		.append("	from room	")
		.append("	where room_no not in ")
		.append("	(select room.room_no	")
		.append("	from  room room, reservation res	")
		.append("	where (res.room_no=room.room_no)	")
		.append("	and (  (to_date(chkin_date,'yyyy.mm.dd')<=?	")
		.append("	and to_date(chkout_date,'yyyy.mm.dd') >= ?))	")
		.append(" 	)	")
		.append("	and ((to_number(?) + nvl(to_number(?),0) ) <= room.max_guest)	")
		.append("   and(room.r_status = 'Y')"	);
		// ��¥�� �ش��ϴ� ���� ����,
		// �ִ� �ο������� �۰ų� ���� ��
		// adult, child �Ķ���͸� String������ �޾ƿ��� ������, ���갡���� number�� ����ȯ�Ѵ�.
		// ��� 0 ���϶��� ����Ͽ� nvl ���
		
		
		list = jt.query(selectAvailReserve.toString(), new Object[] { start_date, end_date, adult, child },
				new RowMapper<RoomVO>() {

					@Override
					public RoomVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
						RoomVO rVO = new RoomVO();
						rVO.setR_name(rs.getString("r_name"));
						rVO.setRoom_no(rs.getInt("room_no"));
						rVO.setR_status(rs.getString("r_status"));
						rVO.setMax_guest(rs.getInt("max_guest"));
						rVO.setDescription(rs.getString("description"));
						rVO.setPrice(rs.getInt("price"));
						rVO.setMain_img(rs.getString("main_img"));
						return rVO;
					}
	
		});
				
		//Spring Container �������
		gjt.closeAc();
		
		return list;
		
	}//selectAvailReserve
	
}//class
