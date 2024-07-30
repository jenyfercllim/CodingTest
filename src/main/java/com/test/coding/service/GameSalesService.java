package com.test.coding.service;

import java.util.Date;
import java.util.List;

import com.test.coding.model.GameSales;

public interface GameSalesService {

	List<GameSales> retrieveSalePriceGreaterThan(float salePrice);

	List<GameSales> retrieveSalePriceLessThan(float salePrice);

	List<GameSales> retrieveateOfSaleBetween(Date startDate, Date endDate);

	List<GameSales> retrieveAllGameSales();

	void saveGameSales(GameSales gameSales);
}
