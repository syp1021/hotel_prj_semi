package kr.co.sist.admin.room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;

/**
 * ���ǿ� ���� ������ ��ȸ�ϴ� class
 * @author user
 */
public class RoomSelect {

	/**
	 * ���ຯ��� ����� 
	 * Ȱ��ȭ�� ���� ����Ʈ ��ȸ
	 * @return
	 */
	public List<String> selectAllRName() throws SQLException {
		List<String> list = null;

		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		// 3. ���� ����
		String select = "select r_name from room where r_status='Y'";
		list = jt.query(select, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String name = rs.getString("r_name");
				return name;
			}// mapRow
		});
		// 4. Spring Container�ݱ�
		gjt.closeAc();

		return list;
	}// selectAllRName

	/**
	 * ��ϵ� ��� room ������ ��ȸ
	 * @return
	 */
	public List<RoomVO> selectRoomInfo(String rName, String rStatus) throws SQLException {
		List<RoomVO> roomList = null;

		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		// 3. ���� ����
		StringBuilder select = new StringBuilder("select * from room");
		
		//�Ķ���Ͱ� ������ �� ���ǹ� �߰� 
		if(rName!=null && rStatus !=null){
			select.append("		where	r_name='")
					.append(rName)
					.append("'	and	")
					.append("r_status='")
					.append(rStatus)
					.append("'");
		}//end if
		
		if (rName != null && rStatus == null) { 
			select.append("		where	r_name='")
			.append(rName)
			.append("'");
		} // end if
		
		if (rName == null && rStatus != null) { 
			select.append("		where	r_status='")
			.append(rStatus)
			.append("'");
		} // end if

		roomList = jt.query(select.toString(), new selectRoomInfo());

		// 4. Spring Container�ݱ�
		gjt.closeAc();

		//room_no��� ����
		if(roomList!=null) {
		Collections.sort(roomList, new CompareRNoAsc());
		}
		
		return roomList;
	}// selectRoomInfo
	
	/* selectRoomInfo���� ��ȸ�� ���� ������ ���� inner class
	 * @author user
	 */
	public class selectRoomInfo implements RowMapper<RoomVO> {
		public RoomVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoomVO rmVO = new RoomVO();
			rmVO.setrStatus(rs.getString("r_status"));
			rmVO.setRoomNum(rs.getString("room_no"));
			rmVO.setRoomName(rs.getString("r_name"));
			rmVO.setMainDesc(rs.getString("description"));
			rmVO.setType(rs.getString("bed_type"));
			rmVO.setRoomSize(rs.getString("r_size"));
			rmVO.setView(rs.getString("r_view"));
			rmVO.setChkIn(rs.getString("chkin_time"));
			rmVO.setChkOut(rs.getString("chkout_time"));
			rmVO.setSpecialServ(rs.getString("service"));
			rmVO.setGeneralAmn(rs.getString("amnt_gen"));
			rmVO.setBathAmn(rs.getString("amnt_bath"));
			rmVO.setOtherAmn(rs.getString("amnt_other"));
			rmVO.setMoreInfo(rs.getString("more_info"));
			rmVO.setImg(rs.getString("main_img"));
			rmVO.setInputDate(rs.getString("input_date"));
			String price = new DecimalFormat("#,###").format(rs.getInt("price"));
			rmVO.setPrice(price);
			rmVO.setGuestNum(rs.getInt("max_guest"));
			
			return rmVO;
		}// mapRow

	}// selectRes
	
	/**
	 * List<RoomVO>���� room no ������� �����ϴ� inner Class
	 * @author user
	 */
	public class CompareRNoAsc implements Comparator<RoomVO> {
		@Override
		public int compare(RoomVO o1, RoomVO o2) {
			return o1.getRoomNum().compareTo(o2.getRoomNum());
		}
	}// class
	
	/**
	 * images ���̺��� �뺰 �̹��� ��ȸ
	 * @return
	 */
	public List<OtherImgVO> selectOtherImg(String rName) throws SQLException {
		List<OtherImgVO> imgList = null;

		// 1. Spring Container ���
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		// 2. JdbcTemplate ���
		JdbcTemplate jt = gjt.getJdbcTemplate();
		// 3. ���� ����
		StringBuilder select = new StringBuilder();

		select.append(" 	select * 	from   images")
				.append(" 	where  room_no = (select room_no")
									.append(" 	 from room	")
									.append(" 	 where r_name=?)");	
		
			imgList = jt.query(select.toString(), new Object[] {rName}, 
					new RowMapper<OtherImgVO>() {
						public OtherImgVO mapRow(ResultSet rs, int rowNum) throws SQLException  {
							OtherImgVO imgVO = new OtherImgVO();
							imgVO.setImgNo(rs.getInt("img_no"));
							imgVO.setRoomNo(rs.getInt("room_no"));
							imgVO.setImgSrc(rs.getString("img_src"));
							return imgVO;
						}//mapRow
			});
			// 4. Spring Container�ݱ�
		gjt.closeAc();
		
		return imgList;
	}// selectRoomInfo
	
	
	/**
	 * ���� �߰� �� ���� ����ȣ�� RoomNo�� ��ȸ�Ͽ� ���
	 * @return
	 */
	public int selectMaxRoomNo() {
		int num = 0;
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		String selectMaxNo = "select max(room_no) from room";
		try {
		num = jt.queryForObject(selectMaxNo, Integer.class);
		}catch (EmptyResultDataAccessException erdae) {
			num = 0;
		}//end catch
		return num;
	}//selectMaxRoomNo
	
	/**
	 * ���� ���� ��, �ߺ� �̸��� ��ȸ�ϴ� ��
	 * @return
	 */
	public List<String> selectRoomName(String rName, String roomNum) throws SQLException {
		List<String> list = null;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		String select = "select r_name from room where r_name=? and room_no!=?";
		
		list = jt.query(select, new Object[] {rName, roomNum}, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String name = rs.getString("r_name");
				return name;
			}// mapRow
		});

		gjt.closeAc();

		return list;
	}// selectRoomName
	
}// class