package com.nc.med.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nc.med.mapper.BarChartModel;
import com.nc.med.mapper.CustomerBalance;
import com.nc.med.mapper.CustomerBalanceSheet;
import com.nc.med.mapper.SalesOrderSearch;
import com.nc.med.mapper.StockBook;
import com.nc.med.mapper.StockBookData;
import com.nc.med.mapper.StockBookModel;
import com.nc.med.mapper.StockData;
import com.nc.med.model.Customer;
import com.nc.med.model.Product;
import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;
import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.SalesOrderDetailRepo;
import com.nc.med.repo.SalesOrderRepo;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {
    public static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
    private final SalesOrderRepo salesOrderRepo;
    private final CustomerRepo customerRepo;
    private final ProductService productService;
    private final SalesOrderDetailRepo orderDetailRepo;

    public SalesOrderServiceImpl(SalesOrderRepo salesOrderRepo, CustomerRepo customerRepo, ProductService productService, SalesOrderDetailRepo orderDetailRepo) {
        this.salesOrderRepo = salesOrderRepo;
        this.customerRepo = customerRepo;
        this.productService = productService;
        this.orderDetailRepo = orderDetailRepo;
    }

    @Override
    public SalesOrder saveOrder(SalesOrder order) {
        int totalQty = order.getSalesOrderDetail().stream().mapToInt(SalesOrderDetail::getQtyOrdered).sum();
        order.setTotalQty(totalQty);

        Customer customer = order.getCustomer();

        if (Objects.equals(customer.getCustomerName(), "")) {
            List<Customer> customers = customerRepo.findAll();
            Long maxId = 1L;
            if (!customers.isEmpty()) {
                maxId = Objects.requireNonNull(customerRepo.findAll().stream().max(Comparator.comparing(Customer::getId)).orElse(null)).getId();
            }
            order.setCustomer(customerRepo.save(new Customer("UNKNOWN" + maxId, order.getCustomer().getPhoneNumber())));

        } else {
            String customerName = customer.getCustomerName().toUpperCase();
            Customer customerObj = customerRepo.findByCustomerName(customerName);

            if (customerObj == null) {
                customerObj = customerRepo.save(new Customer(customerName, order.getCustomer().getPhoneNumber()));
            }
            customerObj.setPhoneNumber(order.getCustomer().getPhoneNumber());
            customerRepo.save(customerObj);
            order.setCustomer(customerObj);
        }
        
        double due = order.getPreviousBalance() - order.getCurrentBalance() - order.getAmountPaid();
        if(due <= 0) {
        	due = due+order.getTotalPrice();
        	order.setPreviousBalance(due);
        	order.setCurrentBalance(due);
        } else {
        	due = due+order.getTotalPrice();
        	order.setPreviousBalance(due);
        	order.setCurrentBalance(order.getTotalPrice());
        }
        // purchaseOrderRepo.updateAddress(supplier, sum);
        //order.setCurrentBalance(order.getPreviousBalance()-order.getCurrentBalance());
        return salesOrderRepo.save(order);
    }

    @Override
    public SalesOrder findByOrderID(Long orderID) {
        return salesOrderRepo.findById(orderID).orElse(null);
    }

    @Override
    public void deleteOrder(SalesOrder order) {
        for (SalesOrderDetail orderDetails : order.getSalesOrderDetail()) {
            Product product = orderDetails.getProduct();
            product.setQty(product.getQty() + orderDetails.getQtyOrdered());
            product.setProfit(product.getProfit() - orderDetails.getProfit());
            productService.saveProduct(product);
            orderDetailRepo.delete(orderDetails);
        }
        salesOrderRepo.delete(order);
    }

    @Override
    public List<SalesOrder> findAllOrders() {
        return salesOrderRepo.findAll(Sort.by("billDate").descending());
    }

    @Override
    public CustomerBalance findCustomerBalanceByCustomer(Long customerID) {
        Customer customer = customerRepo.findById(customerID).orElse(null);
        double balance = salesOrderRepo.findDueAmount(customer);
        return new CustomerBalance(customer, balance);
    }

    @Override
    public double findAllCustomersBalance() {
        return salesOrderRepo.findAll().stream().mapToDouble(SalesOrder::getCurrentBalance).sum();
    }

    @Override
    public List<CustomerBalanceSheet> findCurrentBalanceByCustomers() {
        return salesOrderRepo.findAll().stream()
                .collect(Collectors.groupingBy(SalesOrder::getCustomer)).entrySet().stream().map(x -> {
                    List<SalesOrder> salesOrders = x.getValue();
                    double totalPrice = salesOrders.stream().mapToDouble(SalesOrder::getTotalPrice).sum();
                    double amountPaid = salesOrders.stream().mapToDouble(SalesOrder::getAmountPaid).sum();
                    //double dueAmount = salesOrders.stream().mapToDouble(SalesOrder::getCurrentBalance).sum();
                    double dueAmount = salesOrderRepo.findDueAmount(x.getKey());
                    
                    int size = salesOrders.size();
                    SalesOrder order = salesOrders.get(size - 1);
                    Date billDate = order.getBillDate();
                    Date dueDate = order.getDueDate();
                    if (dueAmount > 0) {
                        return new CustomerBalanceSheet(x.getKey(), totalPrice, amountPaid, dueAmount, billDate, dueDate);
                    } else return null;
                }).filter(Objects::nonNull).sorted().collect(Collectors.toList());
    }

    @Override
	public List<BarChartModel> findBarChartModels() {
		List<Object[]> salesOrderObj = salesOrderRepo.fetchDailyTransaction();
		List<Object[]> salesOrderObjDueCollection = salesOrderRepo.fetchDueDailyTransaction();
		List<Object[]> salesOrderObjDueGivenCollection = salesOrderRepo.fetchDueGivenDailyTransaction();
		List<BarChartModel> barChartModelList = new ArrayList<>();
		List<BarChartModel> barChartDueCollectionModelList = new ArrayList<>();
		List<BarChartModel> barChartDueGivenCollectionModelList = new ArrayList<>();
		List<BarChartModel> finalBarChartModelList = new ArrayList<>();

		for (Object[] objects : salesOrderObj) {
			int dueAmount = (int) (Double.parseDouble(objects[1].toString()) - Double.parseDouble(objects[2].toString()));
			if (dueAmount < 0) {
				dueAmount = 0;
			}
			barChartModelList.add(new BarChartModel(objects[0].toString(), Double.valueOf(objects[1].toString()),
					dueAmount, Double.parseDouble(objects[3].toString())));
		}

		for (Object[] objects : salesOrderObjDueCollection) {
			barChartDueCollectionModelList
					.add(new BarChartModel(objects[0].toString(), Double.valueOf(objects[1].toString())));
		}

		for (Object[] objects : salesOrderObjDueGivenCollection) {
			barChartDueGivenCollectionModelList
					.add(new BarChartModel(objects[0].toString(), (int)Double.parseDouble(objects[1].toString())));
		}

		for (BarChartModel barChartModel : barChartModelList) {
			for (BarChartModel barChartModel1 : barChartDueCollectionModelList) {
				if (barChartModel.getCreatedDate().equals(barChartModel1.getCreatedDate())) {
					barChartModel.setDueCollection(Math.abs(barChartModel1.getDueCollection()));
					break;
				}
			}

			for (BarChartModel barChartModel1 : barChartDueGivenCollectionModelList) {
				if (barChartModel.getCreatedDate().equals(barChartModel1.getCreatedDate())) {
					barChartModel.setDueAmount(Math.abs(barChartModel1.getDueAmount()));
					break;
				}
			}
			finalBarChartModelList.add(barChartModel);
		}
		LOGGER.info("barChartModelList {} ", finalBarChartModelList);
		return finalBarChartModelList;
	}

    @Override
    public List<SalesOrder> findAllByCustomer(Long customerID) {
        LOGGER.info("filter by customer id: {}", customerID);
        return salesOrderRepo.findAllByCustomer(customerRepo.findById(customerID).orElse(null));
    }

    @Override
    public List<SalesOrder> salesOrderDetailSearch(SalesOrderSearch salesOrderSearch) {
        LOGGER.info("salesOrderSearch data to filter: {}", salesOrderSearch);
        return salesOrderRepo.findByCustomerIdAndCreatedDateBetweenAndStatus(
                (long) salesOrderSearch.getCustomerId(), salesOrderSearch.getStartDate(),
                salesOrderSearch.getEndDate(), salesOrderSearch.getStatus());
    }

    @Override
    public StockBookData findStockDataByDateAndProduct(String productName, LocalDate startDate, LocalDate endDate) {
        List<StockData> stockData = new ArrayList<>();
        int stock = 0;

        for (Product product : productService.findAllProduct()) {
            List<Object[]> salesStockObj = salesOrderRepo.fetchSalesOrderStock(product.getId());
            List<Object[]> purchaseStockObj = salesOrderRepo.fetchPurchaseOrderStock(product.getId());
            Set<String> dates = new TreeSet<>();
            Map<String, StockBookModel> salesStockMap = new TreeMap<>();
            Map<String, StockBookModel> purchaseStockMap = new TreeMap<>();

            int i = 0, j = 0;
            while (i < purchaseStockObj.size() || j < salesStockObj.size()) {
                mapObjectToData(purchaseStockObj, dates, purchaseStockMap, i, false);
                mapObjectToData(salesStockObj, dates, salesStockMap, j, true);
                i++;
                j++;
            }

            for (String date : dates) {
                if (purchaseStockMap.get(date) != null) {
                    stock += purchaseStockMap.get(date).getSoldStock();
                }

                if (salesStockMap.get(date) != null) {
                    int openingStock = stock;
                    stock -= salesStockMap.get(date).getSoldStock();
                    int qtySold = openingStock - stock;
                    StockBook stockBook = new StockBook(salesStockMap.get(date).getProductName(), openingStock, qtySold, stock, salesStockMap.get(date).getPrice(), salesStockMap.get(date).getProfit());
                    if (stock < 0) {
                        stockBook = new StockBook(salesStockMap.get(date).getProductName(), 0, 0, 0, salesStockMap.get(date).getPrice(), salesStockMap.get(date).getProfit());
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                    LocalDate localDate = LocalDate.parse(date, formatter);
                    stockData.add(new StockData(localDate, stockBook));
                }
            }
            stock = 0;
        }
        return getStockBookData(productName, startDate, endDate, stockData);
    }

    @Override
    public List<SalesOrderDetail> deleteOrderDetails(SalesOrderDetail orderDetails) {
        Product product = orderDetails.getProduct();
        product.setQty(product.getQty() + orderDetails.getQtyOrdered());
        product.setProfit(product.getProfit() - orderDetails.getProfit());
        productService.saveProduct(product);
        orderDetailRepo.delete(orderDetails);

        List<SalesOrderDetail> salesOrderDetailList = orderDetailRepo.findBySalesOrder(orderDetails.getSalesOrder());
        if (salesOrderDetailList.isEmpty()) {
            salesOrderRepo.delete(orderDetails.getSalesOrder());
            return Collections.emptyList();
        } else {
            SalesOrder salesOrder = salesOrderRepo.findById(orderDetails.getSalesOrder().getSalesOrderID()).orElse(null);
            assert salesOrder != null;
            int totalQty = salesOrder.getTotalQty() - orderDetails.getQtyOrdered();
            double orderedTotalPrice = orderDetails.getSalesPrice() * orderDetails.getQtyOrdered();
            double salesPrice = salesOrder.getTotalPrice();
            double totalAmount = salesOrder.getAmountPaid() - orderedTotalPrice;
            double profitPerProduct = (orderDetails.getSalesPrice() - orderDetails.getPurchasePrice()) * orderDetails.getQtyOrdered();
            double totalProfit = salesOrder.getTotalProfit() - profitPerProduct;

            salesOrder.setTotalProfit(totalProfit);
            salesOrder.setTotalQty(totalQty);
            salesOrder.setTotalPrice(salesPrice - orderedTotalPrice);
            salesOrder.setAmountPaid(totalAmount);
            salesOrder.setSalesOrderDetail(salesOrderDetailList);
            return salesOrderRepo.save(salesOrder).getSalesOrderDetail();
        }
    }

    private StockBookData getStockBookData(String productName, LocalDate startDate, LocalDate endDate, List<StockData> stockData) {
        Stream<StockData> streamStockData = stockData.stream();
        if (startDate != null && endDate != null) {
            Stream<StockData> stream = stockData.stream()
                    .filter(x -> (x.getDate().isAfter(startDate) && x.getDate().isBefore(endDate.plusDays(1))));
            if (!productName.equalsIgnoreCase("null")) {
                streamStockData = stream.filter(product -> product.getStockBook().getProductName().equals(productName));
            } else {
                streamStockData = stream.filter(x -> (x.getDate().isAfter(startDate) && x.getDate().isBefore(endDate.plusDays(1))));
            }
        } else if (!productName.equalsIgnoreCase("null")) {
            streamStockData = streamStockData
                    .filter(product -> product.getStockBook().getProductName().equals(productName));
        }
        List<StockData> stockDa = streamStockData.sorted().collect(Collectors.toList());
        int totalSoldStock = stockDa.stream().mapToInt(x -> x.getStockBook().getSoldStock()).sum();
        double totalPrice = stockDa.stream().mapToDouble(x -> x.getStockBook().getOpeningBalance() * x.getStockBook().getSoldStock()).sum();
        double totalProfit = stockDa.stream().mapToDouble(x -> x.getStockBook().getClosingBalance()).sum();
        return new StockBookData(stockDa, totalSoldStock, totalPrice, totalProfit);
    }

    private void mapObjectToData(List<Object[]> purchaseStockObj, Set<String> dates, Map<String, StockBookModel> purchaseStockMap, int i, boolean sales) {
        if (sales) {
            try {
                dates.add(purchaseStockObj.get(i)[0].toString());
                purchaseStockMap.put(purchaseStockObj.get(i)[0].toString(),
                        new StockBookModel(purchaseStockObj.get(i)[0].toString(),
                                purchaseStockObj.get(i)[1].toString(),
                                Integer.parseInt(purchaseStockObj.get(i)[2].toString()),
                                Double.parseDouble(purchaseStockObj.get(i)[3].toString()),
                                Double.parseDouble(purchaseStockObj.get(i)[4].toString())));
            } catch (IndexOutOfBoundsException e) {
            }
        } else {
            try {
                dates.add(purchaseStockObj.get(i)[0].toString());
                purchaseStockMap.put(purchaseStockObj.get(i)[0].toString(),
                        new StockBookModel(purchaseStockObj.get(i)[0].toString(),
                                purchaseStockObj.get(i)[1].toString(),
                                Integer.parseInt(purchaseStockObj.get(i)[2].toString())));
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }
}
