package kr.co.sist.admin.room;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * �����߰�/���濡�� - �̹��� �߰����� ����� Ŭ����
 * 
 * @author user
 */
/**
 * @author user
 *
 */
public class UploadImgList {

	/**
	 * temp ���Ͽ� �����ϴ� �̹��� ��� ��ȸ
	 * @return List<UploadImgVO>
	 */
	public List<UploadImgVO> searchImgList() {
		List<UploadImgVO> list = new ArrayList<UploadImgVO>();

		// 1. ���� ����Ʈ�� ������ ���� ����
		File temp = new File("/usr/local/www/hotel_prj/temp");

		// 2. �ش� ������ ��� ����, ���丮�� ����
		File[] listFiles = null;
		
		if(temp.listFiles() != null) { // ���� �ƴҶ� !
			listFiles = temp.listFiles();
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss");

			UploadImgVO uliVO = null;
			for (File file : listFiles) {
				// 3. ���Ͽ� ���ؼ��� ������ ����
				if (file.isFile()) {
					// 4. list�� ���ϸ� �Ҵ�
					uliVO = new UploadImgVO();
					uliVO.setImgName(file.getName());
					uliVO.setImgLeng((int) ((file.length()) / 1024));
					uliVO.setLastModified(sdf.format(new Date(file.lastModified())));
					list.add(uliVO);
				} // end if
			} // end for

			// ��ϼ������ ����
			Collections.sort(list, new CompareDateAsc());
		}//end if
		
		return list;
	}// searchImgList

	/**
	 * List<UploadImgVO>���� ��ϼ������ �����ϴ� inner Class
	 * 
	 * @author user
	 */
	public class CompareDateAsc implements Comparator<UploadImgVO> {
		@Override
		public int compare(UploadImgVO o1, UploadImgVO o2) {
			return o1.getLastModified().compareTo(o2.getLastModified());
		}
	}// class

	/**
	 * �����̹��� ��Ͻ� ���ϸ��� mainImg�� �����Ͽ� �����ϴ� method
	 * @param fileName
	 */
	public void markMainImg(String fileName) {

		File selectedImgPath = new File("/usr/local/www/hotel_prj/temp/" + fileName);
		File mainImgPath = new File("/usr/local/www/hotel_prj/temp/"
				+ fileName.substring(0, fileName.indexOf(".")) + "_main" + fileName.substring(fileName.indexOf(".")));

		File temp = new File("/usr/local/www/hotel_prj/temp");

		// ��ü ���� ����Ʈ ��ȸ �� main ���ԵǾ��ְų� ���� �̸����� ����Ǿ��ִ� ������ �ִٸ� ���� �� rename ó��
		File[] listFiles = temp.listFiles();
		
		for (File file : listFiles) {
			if (file.isFile() && file.getName().contains("_main")) {
				File delFile = new File(temp + "/" + file.getName());
				delFile.delete();
				break; 
			} // end if
		} // end for 

		selectedImgPath.renameTo(mainImgPath);
	}// markMainImg

	/**
	 * temp ������ ��� �̹��� ����
	 */
	public void removeAllImg() {
		File temp = new File("/usr/local/www/hotel_prj/temp");

		if (temp.exists()) {
			File[] listFiles = temp.listFiles();
			if (listFiles.length != 0) {
				for (int i = 0; i < listFiles.length; i++) {
					System.gc();
					listFiles[i].delete();
				} // end for
			} // end if
		} // end if
	}// removeAllImg

	/**
	 * temp ������ Ư�� �̹����� ����
	 * @param fileName
	 */
	public void removeSelectedImg(String fileName) {

		File temp = new File("/usr/local/www/hotel_prj/temp");

		if (temp.exists()) {
			File[] listFiles = temp.listFiles();
			if (listFiles.length != 0) {
				for (int i = 0; i < listFiles.length; i++) {
					// �����Ϸ��� ���ϰ� ������ �̸��� �ִٸ� ����
					if (listFiles[i].getName().equals(fileName)) {
						File delImg = new File(temp + "/" + fileName);
						delImg.delete();
						break;
					} // end if
				} // end for
			} // end if
		} // end if
	}// removeSelectedImg

	
	/**
	 * ���� �߰�/���� �� ��Ÿ �̹����� ������ ���ϸ��� �迭�� �����ϴ� method
	 * @return
	 */
	public String[] searchOtherImg() {
		List<UploadImgVO> list = searchImgList();
		// ��ȸ�� list size ��ŭ �迭 ����
		String[] otherImgList = new String[list.size() - 1];

		int i = 0;
		for (UploadImgVO VO : list) {
			if (VO.getImgName().contains("main")) {// main img �����ϰ� String[]�� ���
				continue;
			} // end if
			otherImgList[i] = VO.getImgName();
			i++;
		} // end for

		return otherImgList;
	}// searchOtherImg

	/**
	 * �����߰� ���� �� temp�� �ִ� �������� roomImages������ �̵�
	 * @throws IOException
	 */
	public void moveRoomImg() throws IOException {
		// �� ����
		File tempFolder = new File("/usr/local/www/hotel_prj/temp");
		// ������ ����
		File imgFolder = new File("/usr/local/www/hotel_prj/roomImages");
		
		if(!tempFolder.exists()) {
			tempFolder.mkdirs();
		}
		if(!imgFolder.exists()) {
			imgFolder.mkdirs();
		}
		
		// �� ������ ���ϼ���ŭ ���� �迭 ����
		File[] fileList = tempFolder.listFiles();

		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			for (File file : fileList) {
				File temp = new File(imgFolder.getAbsolutePath() + "/" + file.getName());

				fis = new FileInputStream(file);
				fos = new FileOutputStream(temp);

				byte[] data = new byte[512];
				int byteCnt = 0;
				while ((byteCnt = fis.read(data)) != -1) { // End Of File üũ
					fos.write(data, 0, byteCnt);
				} // end while
			} // end for
			fos.flush();
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		} // finally
	}// moveRoomImg

	
	/**
	 * ���� ���� ���� ��,
	 * param�� ��ġ�ϴ� �̹������� roomImages���� ã�Ƽ� temp�� ����
	 * @throws IOException
	 */
	public void moveImgtoTemp(List<String> list) throws IOException {
		// �� ����
		File imgFolder = new File("/usr/local/www/hotel_prj/roomImages");
		// ������ ����
		File tempFolder = new File("/usr/local/www/hotel_prj/temp");
		
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			for (int i = 0; i < list.size() ; i++) { // param���� ���� �̹����� ����ŭ �ݺ�
				File dbImg = new File(imgFolder.getAbsolutePath() + "/" + list.get(i));
				File temp = new File(tempFolder.getAbsolutePath() + "/" + list.get(i));

				fis = new FileInputStream(dbImg);
				fos = new FileOutputStream(temp);

				byte[] data = new byte[512];
				int byteCnt = 0;
				while ((byteCnt = fis.read(data)) != -1) { // End Of File üũ
					fos.write(data, 0, byteCnt);
				} // end while
			} // end for
			fos.flush();
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		} // finally
	}// moveImgtoTemp

	
	/**
	 * ���� ���� �Ϸ� �� ������ roomImages�� ���� ���� 
	 * @param imgList ���� �̹��� List (main, other)
	 * @param ������Ʈ�� new mainImg
	 * @param rName ���� ��� RoomName
	 * @throws DataAccessException 
	 */
	public void removeOriginalImg(String[] imgList, String newMainImg, String rName) throws DataAccessException {
		//roomImages���� ���� �̹��� ����Ʈ
		List<String> delImgList = new ArrayList<String>();
		File original = new File("/usr/local/www/hotel_prj/roomImages");
		
		RoomSelect rs = new RoomSelect();
		//���� �Ϸ� �� ��Ÿ �̹���
		List<OtherImgVO> dbList = rs.selectOtherImg(rName);
		
		//���� �Ϸ� �� ���ο� �̹��� ����Ʈ ����
		List<String> newImgList = new ArrayList<String>();
		newImgList.add(newMainImg);
		if(dbList.size()!=0) {
			for(OtherImgVO VO : dbList) {
				newImgList.add(VO.getImgSrc());
			}//end for
		}// end if
		
		for(String oldImg : imgList) {
			for(String newImg : newImgList) {
				if (newImg.equals(oldImg)) {
					break;
				}//end if
				delImgList.add(oldImg);
			}//end for
		}//end for
		
		//���� ����Ʈ�� ������ return
		if(delImgList.size()==0) {
			return;
		}//end if
		
		for(String imgSrc : delImgList) {
				File delImg = new File(original + "/" + imgSrc);
				delImg.delete();
			}//end for
		
	}// removeOriginalImg
	
}// class