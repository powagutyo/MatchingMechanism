package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import object.InitData;
import object.InitData.MATCHINGMODE;
import object.Obj;
import object.Problem;

public class FileManager {
	/**
	 * Excelのファイル読み込みを行うクラス
	 * 
	 * @param pro
	 * @throws IOException
	 */
	public static void excel_FileWrite(Problem pro) {
		File file = new File(pro.getData().M_OUTPUTFILENAME);
		XSSFWorkbook workbook = null;
		try {

			FileInputStream fis = new FileInputStream(file);
			try {
				workbook = new XSSFWorkbook(fis);

			} catch (Exception e) {
				workbook = new XSSFWorkbook();
			}

			FileOutputStream fos = new FileOutputStream(file);
			XSSFSheet sheet;

			try {
				sheet = workbook.createSheet(pro.getData().M_SHEETNAME);
			} catch (Exception e) {
				sheet = workbook.getSheet(pro.getData().M_SHEETNAME);
			}

			XSSFRow xr = sheet.createRow(0);
			XSSFCell xc = null;

			writeInitData(pro, sheet, xr, xc);

			// ここら辺が初期設定書き込み部分
			xr = sheet.createRow(2);
			xc = xr.createCell(0);
			xc.setCellValue("個体表");

			xc = xr.createCell(1);
			xc.setCellValue("空きシート");

			xc = xr.createCell(2);
			xc.setCellValue("妥当な不満を持つ生徒");

			for (int i = 0; i < pro.getData().M_HOPESCHOOLNUMBER; i++) {
				xc = xr.createCell(i + 3);
				xc.setCellValue("第" + i + "希望");
			}

			xr = sheet.createRow((int) pro.getData().M_SEEDNUM + 2);
			xc = xr.createCell(0);
			xc.setCellValue(pro.getData().M_SEEDNUM);
			xc = xr.createCell(2);
			xc.setCellValue(pro.getNotSatisfyStudent());
			xc = xr.createCell(1);
			xc.setCellValue(pro.getRequestFreeSeat());

			for (int i = 0; i < pro.getData().M_HOPESCHOOLNUMBER; i++) {
				xc = xr.createCell(i + 3);
				xc.setCellValue(pro.getResultAssained()[i]);
			}
			workbook.write(fos);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			if (workbook != null)
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
		}
	}

	/**
	 * 学生ｎ関する情報を書き込むメソッド
	 * @param pro
	 * @throws IOException
	 */
	public static void excel_FileWrite_2(InitData data, double freeSeat,
			double notSatisyStudent, double[] result, long time, double n_ProvisonalDA) {
		File file = new File(data.M_OUTPUTFILENAME);
		XSSFWorkbook workbook = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			try {
				workbook = new XSSFWorkbook(fis);
			} catch (Exception e) {
				workbook = new XSSFWorkbook();
			}
			FileOutputStream fos = new FileOutputStream(file);
			XSSFSheet sheet;
			try {
				sheet = workbook.createSheet(data.M_SHEETNAME);
			} catch (Exception e) {
				sheet = workbook.getSheet(data.M_SHEETNAME);
			}

			XSSFRow xr = sheet.createRow(0);
			XSSFCell xc = null;
			/***
			 * 0,1行目
			 */
			xc = xr.createCell(0);
			xc.setCellValue("Student");
			xc = xr.createCell(1);
			xc.setCellValue("School");

			xr = sheet.createRow(1);
			xc = xr.createCell(0);
			xc.setCellValue(data.M_STUDENT);
			xc = xr.createCell(1);
			xc.setCellValue(data.M_SCHOOL);
			/*************/
			/*****2行めの初期変数表示部分********/
			xr = sheet.createRow(2);
			xc = xr.createCell(0);
			xc.setCellValue("要素的下限制約");

			xc = xr.createCell(1);
			xc.setCellValue("空きシート");

			xc = xr.createCell(2);
			xc.setCellValue("妥当な不満を持つ生徒");
			
			xc = xr.createCell(3);
			xc.setCellValue("経過時間");
			xc = xr.createCell(4);
			xc.setCellValue("仮DAの回数");
			
			/****************/
			for (int i = 0; i < data.M_HOPESTUDENTNUMBER; i++) {
				xc = xr.createCell(i + 5);
				xc.setCellValue("第" + (i+1) + "希望");
			}
			int pos = data.M_PARTLOWERLIMIT - 64 + 3;
			xr = sheet.createRow(pos);
			xc = xr.createCell(0);
			xc.setCellValue(data.M_PARTLOWERLIMIT);
			xc = xr.createCell(1);
			xc.setCellValue(freeSeat);
			xc = xr.createCell(2);
			xc.setCellValue(notSatisyStudent);
			xc = xr.createCell(3);
			xc.setCellValue(time);
			xc = xr.createCell(4);
			xc.setCellValue(n_ProvisonalDA);
			
			
			for (int i = 0; i < data.M_HOPESTUDENTNUMBER; i++) {
				xc = xr.createCell(i + 5);
				xc.setCellValue(result[i]);
			}
			workbook.write(fos);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			if (workbook != null)
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
		}
	}
	/**
	 * 
	 * 学校の選考順を書き込むメソッド
	 * @param pro
	 * @throws IOException
	 */
	public static void excel_FileWrite_3(InitData data, double[] result) {
		File file = new File(data.M_OUTPUTFILENAME);
		XSSFWorkbook workbook = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			try {
				workbook = new XSSFWorkbook(fis);
			} catch (Exception e) {
				workbook = new XSSFWorkbook();
			}
			FileOutputStream fos = new FileOutputStream(file);
			XSSFSheet sheet;
			try {
				sheet = workbook.createSheet(data.M_SHEETNAME_SCHOOL);
			} catch (Exception e) {
				sheet = workbook.getSheet(data.M_SHEETNAME_SCHOOL);
			}

			XSSFRow xr = sheet.createRow(0);
			XSSFCell xc = null;
			/***
			 * 0,1行目
			 */
			xc = xr.createCell(0);
			xc.setCellValue("Student");
			xc = xr.createCell(1);
			xc.setCellValue("School");

			xr = sheet.createRow(1);
			xc = xr.createCell(0);
			xc.setCellValue(data.M_STUDENT);
			xc = xr.createCell(1);
			xc.setCellValue(data.M_SCHOOL);
			/*************/
			/*****2行めの初期変数表示部分********/
			xr = sheet.createRow(2);
			xc = xr.createCell(0);
			xc.setCellValue("要素的下限制約");
			
			/****************/
			for (int i = 0; i < data.M_HOPESCHOOLNUMBER; i++) {
				xc = xr.createCell(i + 1);
				xc.setCellValue("第" + (i+1) + "希望");
			}
			int pos = data.M_PARTLOWERLIMIT - 64 + 3;
			xr = sheet.createRow(pos);
			xc = xr.createCell(0);
			xc.setCellValue(data.M_PARTLOWERLIMIT);
			
			for (int i = 0; i < data.M_HOPESCHOOLNUMBER; i++) {
				xc = xr.createCell(i + 1);
				xc.setCellValue(result[i]);
			}
			workbook.write(fos);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			if (workbook != null)
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
		}
	}

	private static void writeInitData(Problem pro, XSSFSheet sheet, XSSFRow xr,
			XSSFCell xc) {
		xc = xr.createCell(0);
		xc.setCellValue("Student");
		xc = xr.createCell(1);
		xc.setCellValue("School");

		xr = sheet.createRow(1);
		xc = xr.createCell(0);
		xc.setCellValue(pro.getData().M_STUDENT);
		xc = xr.createCell(1);
		xc.setCellValue(pro.getData().M_SCHOOL);
	}

	/**
	 * 生徒の希望読み込み
	 *
	 * @throws IOException
	 */
	public static void readStudentKindSeat(String fileName, Obj[] obj,
			Obj[] obj2) throws IOException {
		File file = new File(fileName);
		BufferedReader bf = null;
		String mes = "";
		String[] stringsplits = null;
		int counter = 0;
		try {
			bf = new BufferedReader(new FileReader(file));
			while (true) {
				mes = bf.readLine();
				if (mes == null)
					break;
				stringsplits = mes.split(",");
				int size = stringsplits.length;
				for (int i = 0; i < size; i++) {
					obj[counter].changeKindSeat(i,
							obj2[Integer.parseInt(stringsplits[i])]);
				}
				counter++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (bf != null) {
				bf.close();
			}
		}
	}

}
