package com.test.coding.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.test.coding.api.CodingTestAPI;
import com.test.coding.api.Game;
import com.test.coding.model.GameSales;
import com.test.coding.model.TraceProgressReport;
import com.test.coding.service.GameSalesServiceImpl;
import com.test.coding.service.TraceProgressReportServiceImpl;

@RestController
@RequestMapping("/")
public class GameSalesController<E> {

	CodingTestAPI api = new CodingTestAPI();

	@Autowired
	GameSalesServiceImpl gameSalesServiceImpl;

	@Autowired
	TraceProgressReportServiceImpl traceProgressReportServiceImpl;

	@RequestMapping("/index")
	public ModelAndView initialPage(ModelAndView model) {

		model.setViewName("index");
		model.addObject("message", Game.WELCOME_MESSAGE);
		return model;
	}

	@RequestMapping("/import")
	public ModelAndView showUpload() {
		return new ModelAndView("import");
	}

	@PostMapping("/import")
	public ModelAndView uploadCSVFile(@RequestParam("file") MultipartFile multipartFile) {

		String status = Game.STATUS_NOT_STARTED;

		// Retrieve CSV file progress start time.
		Date startTime = new Date();

		try {
			// Convert file format.
			File file = api.convertFile(multipartFile);

			// Extract data from CSV file.
			List<GameSales> gameSalesList = api.extractCSVFile(file);

			// Save record into database.
			for (Iterator<E> i = (Iterator<E>) gameSalesList.iterator(); i.hasNext();) {
				GameSales gameSales = (GameSales) i.next();
				gameSalesServiceImpl.saveGameSales(gameSales);
			}

			status = Game.STATUS_COMPLETE;
		} catch (IOException ioe) {
			status = Game.STATUS_IN_PROGRESS;
		} catch (Exception e) {
			status = Game.STATUS_IN_PROGRESS;
		} finally {
			// Trace CSV file progress.
			TraceProgressReport traceProgressReport = api.traceProgress(multipartFile.getOriginalFilename(), startTime,
					status);
			traceProgressReportServiceImpl.saveTraceProgress(traceProgressReport);
		}
		return new ModelAndView("import", "message", "Uploading CSV file is " + status);
	}

	@RequestMapping("/getGameSales")
	public ModelAndView showGameSales() {
		return new ModelAndView("getGameSales");
	}

	@PostMapping("/getGameSales")
	public ModelAndView retrieveGameSalesByCategory(@RequestParam("search") String search,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("salePrice") String salePrice, @RequestParam("range") String range) {

		ModelAndView model = new ModelAndView();
		List<GameSales> gameSalesList = new ArrayList<GameSales>();

		try {
			if (search.equals("Search All")) {
				gameSalesList = gameSalesServiceImpl.retrieveAllGameSales();
			} else if (search.equals("Search")) {
				if (api.emptyCheck(salePrice) && range.equals(">")) {
					gameSalesList = gameSalesServiceImpl.retrieveSalePriceGreaterThan(api.convertFloat(salePrice));
				} else if (api.emptyCheck(salePrice) && range.equals("<")) {
					gameSalesList = gameSalesServiceImpl.retrieveSalePriceLessThan(api.convertFloat(salePrice));
				} else if (api.emptyCheck(startDate) && api.emptyCheck(endDate)) {
					gameSalesList = gameSalesServiceImpl.retrieveateOfSaleBetween(api.convertDate(startDate),
							api.convertDate(endDate));
				}
			}
		} catch (ParseException pe) {
			model.addObject("message", Game.ZERO_RECORD);
		} catch (Exception e) {
			model.addObject("message", Game.ZERO_RECORD);
		}

		if (gameSalesList.contains(null) || gameSalesList.isEmpty())
			model.addObject("message", Game.ZERO_RECORD);

		model.setViewName("getGameSales");
		model.addObject("gameSalesList", gameSalesList);
		return model;
	}
}
