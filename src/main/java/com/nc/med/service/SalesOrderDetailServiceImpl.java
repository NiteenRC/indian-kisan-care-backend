package com.nc.med.service;

import com.nc.med.mapper.ProductSaleSummary;
import com.nc.med.model.Product;
import com.nc.med.model.SalesOrderDetail;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.SalesOrderDetailRepo;
import com.nc.med.util.ValidationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesOrderDetailServiceImpl implements SalesOrderDetailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderDetailServiceImpl.class);
    double totalProfit = 0;
    @Autowired
    private SalesOrderDetailRepo orderDetailRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ValidationProperties validationProperties;

    @Override
    public SalesOrderDetail saveSalesOrderDetail(SalesOrderDetail salesOrderDetail) throws Exception {
    	Product product = productRepo.getOne(salesOrderDetail.getProduct().getId());
        int productQty = product.getQty();
        int qtyOrdered = salesOrderDetail.getQtyOrdered();

        if (productQty < qtyOrdered) {
            throw new Exception(validationProperties.getStock());
        }

        double salesPrice = salesOrderDetail.getSalesPrice();
        double purchasePrice = product.getPrice();
        double profit = product.getProfit();
        profit += Math.round((salesPrice - purchasePrice) * qtyOrdered);

        product.setQty(productQty - qtyOrdered);
        //product.setCurrentPrice(salesPrice);
        product.setProfit(profit);
        
        salesOrderDetail.setPurchasePrice(purchasePrice);
        salesOrderDetail.setProfit(Math.round((salesPrice - purchasePrice) * qtyOrdered));

        LOGGER.info("Profit for this {} is {} ", product.getProductName(), profit);
        productRepo.save(product);
        return orderDetailRepo.save(salesOrderDetail);
    }

    @Override
    public List<ProductSaleSummary> salesOrderDetailProductWise() {
        List<ProductSaleSummary> productSaleSummaries = orderDetailRepo.findAll().stream()
                .collect(Collectors.groupingBy(SalesOrderDetail::getProduct)).entrySet().stream().map(x -> {
                    int sumOfQtyOrdered = x.getValue().stream().mapToInt(SalesOrderDetail::getQtyOrdered).sum();
                    double sumOfProfit = x.getValue().stream().mapToDouble(SalesOrderDetail::getProfit).sum();
                    return new ProductSaleSummary(x.getKey().getProductName(), sumOfQtyOrdered, sumOfProfit);
                }).collect(Collectors.toList());
        Collections.sort(productSaleSummaries);
        LOGGER.info("productSaleSummaries {} ", productSaleSummaries);
        return productSaleSummaries;
    }

    @Override
    public SalesOrderDetail findSalesOrderDetailByProductName(String productName) {
        return null;// orderDetailRepo.findByProductName(productName);
    }

    @Override
    public SalesOrderDetail findBySalesOrderDetailID(Integer salesOrderDetailID) {
        return orderDetailRepo.findById(salesOrderDetailID).get();
    }

    @Override
    public void deleteSalesOrderDetail(SalesOrderDetail salesOrderDetailID) {
        orderDetailRepo.delete(salesOrderDetailID);
    }

    @Override
    public List<SalesOrderDetail> findAllSalesOrderDetails() {
        return orderDetailRepo.findAll();
    }

    @Override
    public List<SalesOrderDetail> saveSalesOrderDetail(List<SalesOrderDetail> salesOrderDetails) {
        return orderDetailRepo.saveAll(salesOrderDetails);
    }
}
