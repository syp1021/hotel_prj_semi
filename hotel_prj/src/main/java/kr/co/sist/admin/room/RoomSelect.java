package kr.co.sist.admin.room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.dao.DataAccessException;
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
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> selectAllRName() throws DataAccessException {
		List<String> list = null;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		String select = "select r_name from room where r_status='Y'";
		list = jt.query(select, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String name = rs.getString("r_name");
				return name;
			}// mapRow
		});

		gjt.closeAc();

		return list;
	}// selectAllRName

	/**
	 * ��ϵ� ��� room ������ ��ȸ
	 * @param rName
	 * @param rStatus
	 * @return
	 * @throws DataAccessException
	 */
	public List<RoomVO> selectRoomInfo(String rName, String rStatus) throws DataAccessException {
		List<RoomVO> roomList = null;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

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
	 * @param rName
	 * @return
	 * @throws DataAccessException
	 */
	public List<OtherImgVO> selectOtherImg(String rName) throws DataAccessException {
		List<OtherImgVO> imgList = null;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

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

		gjt.closeAc();
		
		return imgList;
	}// selectOtherImg
	
	
	/**
	 * ���� �߰� �� ���� ����ȣ�� RoomNo�� ��ȸ�Ͽ� ���
	 * @return
	 * @throws DataAccessException
	 */
	public int selectMaxRoomNo() throws DataAccessException{
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
	 * @param rName
	 * @param roomNum
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> selectRoomName(String rName, String roomNum) throws DataAccessException {
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