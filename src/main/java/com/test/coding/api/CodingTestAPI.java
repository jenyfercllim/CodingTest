package com.test.coding.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.test.coding.model.GameSales;
import com.test.coding.model.TraceProgressReport;

@RestController
public class CodingTestAPI<E> {

	SimpleDateFormat formatter = new SimpleDateFormat(Game.DATE_TIME_FORMAT);
	
	// Convert long format.
	public long convertLong(String num) {
		return Long.parseLong(num);
	}
	
	// Convert int format.
	public int convertInteger(String num) {
		return Integer.parseInt(num);
	}
	
	// Convert float format.
	public float convertFloat(String num) {
		return Float.parseFloat(num);
	}
	
	// Convert date format.
	public Date convertDate(String date) throws ParseException {
		return formatter.parse(date);
	}
	
	// Empty String check.
	public boolean emptyCheck(String string) throws ParseException {
		return string != null && !string.equals("");
	}

	// Convert file format.
	public File convertFile(MultipartFile file) throws IOException {

		File convertfile = new File(file.getName());
		convertfile.createNewFile();

		FileOutputStream fos = new FileOutputStream(convertfile);
		fos.write(file.getBytes());
		fos.close();

		return convertfile;
	}

	// Extract all records from CSV file.
	public List<GameSales> extractCSVFile(File csvFile) {

		int skipHeader = 0;

		List<String[]> allRecords = new ArrayList<String[]>();
		List<GameSales> gameSalesList = new ArrayList<GameSales>();

		try (CSVReader reader = new CSVReader(new FileReader(csvFile.getName()))) {

			allRecords = reader.readAll();
			for (Iterator<E> i = (Iterator<E>) allRecords.iterator(); i.hasNext();) {

				String[] record = (String[]) i.next();

				// Skip header.
				if (skipHeader == 0) {
					skipHeader++;
					continue;
				}

				// Transfer each row of record to GameSales object.
				GameSales gameSales = new GameSales();
				gameSales.setId(convertLong(record[0]));
				gameSales.setGameNo(convertInteger(record[1]));
				gameSales.setGameName(record[2]);
				gameSales.setGameCode(record[3]);
				gameSales.setType(convertInteger(record[4]));
				gameSales.setCostPrice(convertFloat(record[5]));
				gameSales.setTax(convertInteger(record[6]));
				gameSales.setSalePrice(convertFloat(record[7]));
				gameSales.setDateOfSale(convertDate(record[8]));

				// Insert each record into the list.
				gameSalesList.add(gameSales);
			}

		} catch (Exception e) {
		}

		return gameSalesList;
	}

	// Trace file uploading progress.
	public TraceProgressReport traceProgress(String fileName, Date startTime, String status) {

		// Calculate time different.
		long timeDifferent = new Date().getTime() - startTime.getTime();
		long milliseconds = timeDifferent % 1000;
		long seconds = (timeDifferent / 1000) % 60;
		long minutes = (timeDifferent / (1000 * 60)) % 60;
		long hours = (timeDifferent / (1000 * 60 * 60)) % 24;

		String period = hours + ":" + minutes + ":" + seconds + "." + milliseconds;

		// Save trace record to TraceProgressReport object. 
		TraceProgressReport traceProgressReport = new TraceProgressReport();
		traceProgressReport.setFileName(fileName);
		traceProgressReport.setPerformancePeriod(period);
		traceProgressReport.setReadStatus(status);
		traceProgressReport.setUploadDate(new Date());

		return traceProgressReport;
	}
}
