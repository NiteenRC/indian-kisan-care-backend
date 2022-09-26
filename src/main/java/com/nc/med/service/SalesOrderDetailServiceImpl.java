package com.nc.med.service;

import com.nc.med.mapper.ProductDetail;
import com.nc.med.mapper.ProductSaleSummary;
import com.nc.med.model.Product;
import com.nc.med.model.SalesOrderDetail;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.SalesOrderDetailRepo;
import com.nc.med.util.ValidationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SalesOrderDetailServiceImpl implements SalesOrderDetailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderDetailServiceImpl.class);
    private final SalesOrderDetailRepo orderDetailRepo;
    private final ProductRepo productRepo;
    private final ValidationProperties validationProperties;

    public SalesOrderDetailServiceImpl(SalesOrderDetailRepo orderDetailRepo, ProductRepo productRepo, ValidationProperties validationProperties) {
        this.orderDetailRepo = orderDetailRepo;
        this.productRepo = productRepo;
        this.validationProperties = validationProperties;
    }

    @Override
    public SalesOrderDetail saveSalesOrderDetail(SalesOrderDetail salesOrderDetail) throws Exception {
        Product product = productRepo.findById(salesOrderDetail.getProduct().getId()).orElse(null);
        assert product != null;
        int productQty = product.getQty();
        int qtyOrdered = salesOrderDetail.getQtyOrdered();

        if (productQty < qtyOrdered) {
            throw new Exception(validationProperties.getStock());
        }

        double salesPrice = salesOrderDetail.getSalesPrice();
        double purchasePrice = product.getPrice();
        double profit = product.getProfit();
        long round = Math.round((salesPrice - purchasePrice) * qtyOrdered);
        profit += round;

        product.setQty(productQty - qtyOrdered);
        //product.setCurrentPrice(salesPrice);
        product.setProfit(profit);

        salesOrderDetail.setPurchasePrice(purchasePrice);
        salesOrderDetail.setProfit(round);
        salesOrderDetail.setProductName(product.getProductName());

        LOGGER.info("Profit for this {} is {} ", product.getProductName(), profit);
        productRepo.save(product);
        return orderDetailRepo.save(salesOrderDetail);
    }

    @Override
    public ProductSaleSummary salesOrderDetailProductWise(String productName, LocalDate start, LocalDate end) {
        List<ProductDetail> productDetails = new ArrayList<>();
        List<Object[]> productDetailsObj;

        if (!Objects.equals(productName, "null") && start != null & end != null) {
            productDetailsObj = orderDetailRepo.productSalesSummaryWithAllFields(productName, start, end);
        } else if (start != null && end != null) {
            productDetailsObj = orderDetailRepo.productSalesSummaryWithoutProductName(start, end);
        } else if (!Objects.equals(productName, "null")) {
            productDetailsObj = orderDetailRepo.productSalesSummaryWithoutDates(productName);
        } else {
            productDetailsObj = orderDetailRepo.productSalesSummaryAllData();
        }

        int totalQty = 0;
        double totalProfit = 0;

        for (Object[] objects : productDetailsObj) {
            totalQty += ((BigDecimal) objects[2]).intValue();
            totalProfit += (Double) objects[3];
            productDetails.add(new ProductDetail((Date) objects[0], (String) objects[1],
                    ((BigDecimal) objects[2]).intValue(), (Double) objects[3]));
        }
        return new ProductSaleSummary(productDetails, totalQty, totalProfit);
    }

    @Override
    public SalesOrderDetail findById(Long orderID) {
        return orderDetailRepo.findById(orderID).orElse(null);
    }

}
