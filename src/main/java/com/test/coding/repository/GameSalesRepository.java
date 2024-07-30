package com.test.coding.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.coding.model.GameSales;

@Repository
public interface GameSalesRepository extends JpaRepository<GameSales, Long> {

	List<GameSales> findBySalePriceGreaterThan(float salePrice);

	List<GameSales> findBySalePriceLessThan(float salePrice);

	List<GameSales> findByDateOfSaleBetween(Date startDate, Date endDate);
}
