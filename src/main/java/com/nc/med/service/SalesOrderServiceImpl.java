package com.nc.med.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nc.med.mapper.BarChartModel;
import com.nc.med.mapper.CustomerBalanceSheet;
import com.nc.med.mapper.ReportBar;
import com.nc.med.mapper.SalesOrderSearch;
import com.nc.med.model.Customer;
import com.nc.med.model.SalesOrder;
import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.SalesOrderRepo;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {
	public static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderServiceImpl.class);

	@Autowired
	private SalesOrderRepo salesOrderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public SalesOrder saveOrder(SalesOrder order) {
		double sum = 0.0d;
		int totalQty = order.getSalesOrderDetail().stream().mapToInt(x -> x.getQtyOrdered()).sum();
		order.setTotalQty(totalQty);

		Customer customer = order.getCustomer();
		try {
			sum = ((double) salesOrderRepo.findCurrentSum(customer)) + order.getCurrentBalance();
		} catch (Exception e) {
		}
		// purchaseOrderRepo.updateAddress(supplier, sum);
		order.setPreviousBalance(sum);
		return salesOrderRepo.save(order);
	}

	@Override
	public SalesOrder findOrderByProductName(String productName) {
		return null;// salesOrderRepo.findByProductName(productName);
	}

	@Override
	public SalesOrder findByOrderID(Long orderID) {
		return salesOrderRepo.findById(orderID).get();
	}

	@Override
	public void deleteOrder(SalesOrder orderID) {
		salesOrderRepo.delete(orderID);
	}

	@Override
	public List<SalesOrder> findAllOrders() {
		return salesOrderRepo.findAll(Sort.by("createdDate").descending());
	}

	@Override
	public double findCustomerBalanceByCustomer(Long customerID) {
		List<SalesOrder> salesOrders = salesOrderRepo
				.findAmountBalanceByCustomer(customerRepo.findById(customerID).get());

		return salesOrders.stream().mapToDouble(salesOrder -> salesOrder.getCurrentBalance()).sum();
	}

	@Override
	public double findAllCustomersBalance() {
		return salesOrderRepo.findAll().stream().mapToDouble(salesOrder -> salesOrder.getCurrentBalance()).sum();
	}

	@Override
	public List<CustomerBalanceSheet> findCurrentBalanceByCustomers() {
		return salesOrderRepo.findAll().stream().collect(
				Collectors.groupingBy(SalesOrder::getCustomer, Collectors.summingDouble(SalesOrder::getCurrentBalance)))
				.entrySet().stream().map(x -> {
					return new CustomerBalanceSheet(x.getKey(), x.getValue());
				}).collect(Collectors.toList());
	}

	@Override
	public ReportBar findBarChartModels() throws ParseException {
		Set<BarChartModel> weeklyBarCharts = new TreeSet<>();
		Set<BarChartModel> monthlyBarCharts = new TreeSet<>();
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime lastSevenDayDate = LocalDateTime.now().minusDays(7);
		LOGGER.info("lastSevenDayDate: {} and currentDate: {}", lastSevenDayDate, currentDate);

		List<String> lastWeekDates = IntStream.rangeClosed(0, 6)
				.mapToObj(x -> LocalDate.now().minus(x, ChronoUnit.DAYS).toString()).collect(Collectors.toList());
		List<String> lastMonthDates = IntStream.rangeClosed(0, 30)
				.mapToObj(x -> LocalDate.now().minus(x, ChronoUnit.DAYS).toString()).collect(Collectors.toList());

		Object[][] salesOrderObj = salesOrderRepo.getByCreatedDateBetweenDates(lastSevenDayDate, currentDate);
		List<BarChartModel> barChartModelList = new ArrayList<>();
		for (int i = 0; i < salesOrderObj.length; i++) {
			for (int j = 0; j < salesOrderObj[i].length - 1;) {
				barChartModelList.add(new BarChartModel(salesOrderObj[i][j].toString(),
						Double.valueOf(salesOrderObj[i][++j].toString())));
			}
		}
		LOGGER.info("barChartModelList {} ", barChartModelList);

		lastWeekDates.forEach(day -> {
			barChartModelList.forEach(barChartModel -> {
				if (lastWeekDates.contains(barChartModel.getCreatedDate())) {
					weeklyBarCharts.add(barChartModel);
				}
			});
			weeklyBarCharts.add(new BarChartModel(day, 0d));
		});
		LOGGER.info("weekly report: {}", weeklyBarCharts);

		lastMonthDates.forEach(day -> {
			barChartModelList.forEach(barChartModel -> {
				if (lastMonthDates.contains(barChartModel.getCreatedDate())) {
					monthlyBarCharts.add(barChartModel);
				}
			});
			monthlyBarCharts.add(new BarChartModel(day, 0d));
		});
		LOGGER.info("monthly report: {}", monthlyBarCharts);

		// currentWeek
		LOGGER.info("barChartModelMonthData : {}", barChartModelList);

		ReportBar reportBar = new ReportBar(weeklyBarCharts, monthlyBarCharts);

		return reportBar;
	}

	public ReportBar findBarChartModelsWeekly() throws ParseException {
		Set<BarChartModel> barChartMonthData1 = new TreeSet<>();
		Calendar calendar = Calendar.getInstance();
		LocalDateTime fistDayCalendar = getStartOfDay(calendar.getTime());
		LocalDateTime currentDayCalendar = getEndOfDay(calendar.getTime());
		LOGGER.info("fistDayCalendar: {} and cusrrentDayCalendar: {}", fistDayCalendar, currentDayCalendar);

		// Java8
		List<BarChartModel> barChartModels = salesOrderRepo.findAll().stream()
				.filter(salesOrder -> (salesOrder.getCreatedDate().isAfter(fistDayCalendar)
						&& salesOrder.getCreatedDate().isBefore(currentDayCalendar)))
				.collect(Collectors.groupingBy(SalesOrder::getCreatedDate,
						Collectors.summingDouble(SalesOrder::getTotalPrice)))
				.entrySet().stream().map(x -> {
					return new BarChartModel(x.getKey().toString(), x.getValue());
				}).collect(Collectors.toList());
		LOGGER.info("barChartModels: {}", barChartModels);

		// Spring data JPA
		Object[][] salesOrderObj = salesOrderRepo.getByCreatedDateBetweenDates(fistDayCalendar, currentDayCalendar);
		List<BarChartModel> barChartModelList = new ArrayList<>();
		for (int i = 0; i < salesOrderObj.length; i++) {
			for (int j = 0; j < salesOrderObj[i].length - 1;) {
				barChartModelList.add(new BarChartModel(salesOrderObj[i][j].toString(),
						Double.valueOf(salesOrderObj[i][++j].toString())));
			}
		}
		LOGGER.info("XXX {} ", barChartModelList);

		List<String> currentWeekDays = IntStream.rangeClosed(0, 6)
				.mapToObj(x -> LocalDate.now().minus(x, ChronoUnit.DAYS).toString()).collect(Collectors.toList());

		Set<BarChartModel> weeklyBarCharts = new TreeSet<>();
		List<String> currentMonthDays = IntStream.rangeClosed(0, 30)
				.mapToObj(x -> LocalDate.now().minus(x, ChronoUnit.DAYS).toString()).collect(Collectors.toList());

		Set<BarChartModel> monthlyBarCharts = new TreeSet<>();

		currentWeekDays.forEach(day -> {
			barChartModelList.forEach(barChartModel -> {
				if (currentWeekDays.contains(barChartModel.getCreatedDate())) {
					weeklyBarCharts.add(barChartModel);
				}
			});
			weeklyBarCharts.add(new BarChartModel(day, 0d));
		});
		LOGGER.info("weekly report: {}", weeklyBarCharts);

		currentMonthDays.forEach(day -> {
			barChartModelList.forEach(barChartModel -> {
				if (currentMonthDays.contains(barChartModel.getCreatedDate())) {
					monthlyBarCharts.add(barChartModel);
				}
			});
			monthlyBarCharts.add(new BarChartModel(day, 0d));
		});
		LOGGER.info("monthly report: {}", monthlyBarCharts);

		// currentWeek
		LOGGER.info("barChartModelMonthData : {}", barChartMonthData1);

		ReportBar reportBar = new ReportBar(weeklyBarCharts, monthlyBarCharts);

		return reportBar;
	}

	private LocalDateTime getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.setTimeInMillis(0);
		calendar.set(year, month, 1, 0, 0, 0);
		return LocalDateTime.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());

	}

	private LocalDateTime getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.setTimeInMillis(0);
		calendar.set(year, month, day, 23, 59, 59);
		return LocalDateTime.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());
	}

	@Override
	public List<SalesOrder> findAllByCustomer(Long customerID) {
		LOGGER.info("filter by customer id: {}", customerID);
		return salesOrderRepo.findAllByCustomer(customerRepo.findById(customerID).get());
	}

	@Override
	public List<SalesOrder> salesOrderDetailSearch(SalesOrderSearch salesOrderSearch) {
		LOGGER.info("salesOrderSearch data to filter: {}", salesOrderSearch);
		return salesOrderRepo.findByCustomerIdAndCreatedDateBetweenAndStatus(
				Long.valueOf(salesOrderSearch.getCustomerId()), salesOrderSearch.getStartDate(),
				salesOrderSearch.getEndDate(), salesOrderSearch.getStatus());
	}
}
