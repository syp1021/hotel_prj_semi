package kr.co.sist.admin.reservation;


/**
 * üũ�� ���ڷ� ������ ��ȸ�� ����� VO
 * @author user
 */
public class ReserveDateVO {
	
	private String year, month, day;

	public ReserveDateVO() {
	}

	public ReserveDateVO(String year, String month, String day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

}//class
