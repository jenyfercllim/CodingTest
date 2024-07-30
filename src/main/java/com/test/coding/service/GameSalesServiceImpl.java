package com.test.coding.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.coding.model.GameSales;
import com.test.coding.repository.GameSalesRepository;

@Service
public class GameSalesServiceImpl implements GameSalesService {

	@Autowired
	private GameSalesRepository gameSalesRepository;

	@Override
	public List<GameSales> retrieveSalePriceGreaterThan(float salePrice) {
		return gameSalesRepository.findBySalePriceGreaterThan(salePrice);
	}

	@Override
	public List<GameSales> retrieveSalePriceLessThan(float salePrice) {
		return gameSalesRepository.findBySalePriceLessThan(salePrice);
	}

	@Override
	public List<GameSales> retrieveateOfSaleBetween(Date startDate, Date endDate) {
		return gameSalesRepository.findByDateOfSaleBetween(startDate, endDate);
	}

	@Override
	public List<GameSales> retrieveAllGameSales() {
		return gameSalesRepository.findAll();
	}

	@Override
	public void saveGameSales(GameSales gameSales) {
		gameSalesRepository.save(gameSales);
	}

	@Override
	public Page<GameSales> retrieveAllGameSalesWithPagination(Pageable pageable) {
		return gameSalesRepository.findAllWithPagination(pageable);
	}
}
