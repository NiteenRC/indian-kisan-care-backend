package com.nc.med.service;

import com.nc.med.mapper.*;
import com.nc.med.model.Customer;
import com.nc.med.model.Product;
import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;
import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.SalesOrderDetailRepo;
import com.nc.med.repo.SalesOrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        double sum = 0.0d;
        int totalQty = order.getSalesOrderDetail().stream().mapToInt(SalesOrderDetail::getQtyOrdered).sum();
        order.setTotalQty(totalQty);

        Customer customer = order.getCustomer();
        Customer customerObj = null;

        if (Objects.equals(customer.getCustomerName(), "")) {
            List<Customer> customers = customerRepo.findAll();
            Long maxId = 1L;
            if (!customers.isEmpty()) {
                maxId = customerRepo.findAll().stream().max(Comparator.comparing(Customer::getId)).get().getId();
            }
            order.setCustomer(customerRepo.save(new Customer("UNKNOWN" + maxId)));

        } else {
            String customerName = customer.getCustomerName().toUpperCase();
            customerObj = customerRepo.findByCustomerName(customerName);

            if (customerObj == null) {
                customerObj = customerRepo.save(new Customer(customerName));
            }
            order.setCustomer(customerObj);

            try {
                sum = order.getCurrentBalance();
                sum += salesOrderRepo.findCurrentSum(customerObj);
            } catch (Exception e) {
                LOGGER.error(String.valueOf(e));
            }
        }
        // purchaseOrderRepo.updateAddress(supplier, sum);
        order.setPreviousBalance(sum);
        return salesOrderRepo.save(order);
    }

    @Override
    public SalesOrder findByOrderID(Long orderID) {
        return salesOrderRepo.findById(orderID).get();
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

        double prev = order.getPreviousBalance();
        double current = order.getCurrentBalance();
        Long cusomerId = order.getCustomer().getId();

        salesOrderRepo.delete(order);

        SalesOrder salesOrder = salesOrderRepo.findFirstByCustomerIdOrderBySalesOrderIDDesc(cusomerId);

        if (salesOrder != null) {
            // salesOrder.setPreviousBalance( prev + salesOrder.getPreviousBalance());
            // salesOrder.setCurrentBalance( current + salesOrder.getCurrentBalance());
            // salesOrderRepo.saveAndFlush(salesOrder);
        }
    }

    @Override
    public List<SalesOrder> findAllOrders() {
        return salesOrderRepo.findAll(Sort.by("billDate").descending());
    }

    @Override
    public double findCustomerBalanceByCustomer(Long customerID) {
        List<SalesOrder> salesOrders = salesOrderRepo
                .findAmountBalanceByCustomer(customerRepo.findById(customerID).get());
        return salesOrders.stream().mapToDouble(SalesOrder::getCurrentBalance).sum();
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
                    double dueAmount = salesOrders.stream().mapToDouble(SalesOrder::getCurrentBalance).sum();

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
        List<BarChartModel> barChartModelList = new ArrayList<>();

        for (int j = 0; j < salesOrderObj.size(); j++) {
            barChartModelList.add(new BarChartModel(salesOrderObj.get(j)[0].toString(),
                    Double.valueOf(salesOrderObj.get(j)[1].toString()),
                    (Double.parseDouble(salesOrderObj.get(j)[1].toString()) - Double.parseDouble(salesOrderObj.get(j)[2].toString())),
                    Double.parseDouble(salesOrderObj.get(j)[3].toString())));
        }
        LOGGER.info("barChartModelList {} ", barChartModelList);

        return barChartModelList;
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
                StockBook stockBook = null;
                if (purchaseStockMap.get(date) != null) {
                    stock += purchaseStockMap.get(date).getSoldStock();
                }

                if (salesStockMap.get(date) != null) {
                    int openingStock = stock;
                    stock -= salesStockMap.get(date).getSoldStock();
                    int qtySold = openingStock - stock;
                    stockBook = new StockBook(salesStockMap.get(date).getProductName(), openingStock, qtySold, stock, salesStockMap.get(date).getPrice(), salesStockMap.get(date).getProfit());
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
