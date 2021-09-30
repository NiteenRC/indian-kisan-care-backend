package com.nc.med.service;

import com.nc.med.mapper.BalancePayment;
import com.nc.med.mapper.SupplierBalanceSheet;
import com.nc.med.model.Product;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.PurchaseOrderDetail;
import com.nc.med.model.Supplier;
import com.nc.med.repo.PurchaseOrderDetailRepo;
import com.nc.med.repo.PurchaseOrderRepo;
import com.nc.med.repo.SupplierRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);
    private final PurchaseOrderRepo purchaseOrderRepo;
    private final SupplierRepo supplierRepo;
    private final ProductService productService;
    private final PurchaseOrderDetailRepo orderDetailRepo;

    public PurchaseOrderServiceImpl(PurchaseOrderRepo purchaseOrderRepo, SupplierRepo supplierRepo, ProductService productService, PurchaseOrderDetailRepo orderDetailRepo) {
        this.purchaseOrderRepo = purchaseOrderRepo;
        this.supplierRepo = supplierRepo;
        this.productService = productService;
        this.orderDetailRepo = orderDetailRepo;
    }

    @Override
    public PurchaseOrder saveOrder(PurchaseOrder order) {
        double sum = 0.0d;
        int totalQty = order.getPurchaseOrderDetail().stream().mapToInt(PurchaseOrderDetail::getQtyOrdered).sum();
        order.setTotalQty(totalQty);

        Supplier supplier = order.getSupplier();

        if (Objects.equals(supplier.getSupplierName(), "")) {
            List<Supplier> suppliers = supplierRepo.findAll();
            Long maxId = 1L;
            if (!suppliers.isEmpty()) {
                maxId += suppliers.stream().max(Comparator.comparing(Supplier::getId)).orElse(null).getId();
            }
            order.setSupplier(supplierRepo.save(new Supplier("UNKNOWN" + maxId)));
        } else {
            String supplierName = supplier.getSupplierName().toUpperCase();
            Supplier supplierObj = supplierRepo.findBySupplierName(supplierName);

            if (supplierObj == null) {
                supplierObj = supplierRepo.save(new Supplier(supplierName));
            }
            order.setSupplier(supplierObj);

            try {
                sum = order.getCurrentBalance();
                sum += purchaseOrderRepo.findCurrentSum(supplierObj);
            } catch (Exception e) {
                LOGGER.error(String.valueOf(e));
            }
        }
        // purchaseOrderRepo.updateAddress(supplier, sum);
        order.setPreviousBalance(sum);
        return purchaseOrderRepo.save(order);
    }

    @Override
    public PurchaseOrder findByOrderID(Long orderID) {
        return purchaseOrderRepo.findById(orderID).orElse(null);
    }

    @Override
    public void deleteOrder(PurchaseOrder order) throws Exception {
        List<Product> products = new ArrayList<>();
        List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();

        /*
        Delete all purchase order belongs to supplier
        double dueAmount = findSupplierBalanceBySupplier(order.getSupplier().getId());

        if (order.getTotalPrice() == 0) {
            if (dueAmount == 0) {
                purchaseOrderRepo.softDeleteBySupplier(order.getSupplier());
            } else {
                throw new Exception("Please pay all due amount before deleting supplier order");
            }
        }*/

        for (PurchaseOrderDetail orderDetails : order.getPurchaseOrderDetail()) {
            Product product = orderDetails.getProduct();
            double pp = product.getPrice();
            double pq = product.getQty();
            double op = orderDetails.getPrice();
            double oq = orderDetails.getQtyOrdered();

            if (pq >= oq) {
                double avgPrice = (pp * pq - op * oq) / (pq - oq);
                double averagePurchasePrice = Math.round(avgPrice);
                product.setQty((int) (pq - oq));
                product.setPrice(averagePurchasePrice);
                products.add(product);
                purchaseOrderDetails.add(orderDetails);
            } else {
                throw new Exception("Could not able to delete this transactions");
            }
        }
        if (productService.saveAllProduct(products).isEmpty()) {
            throw new Exception("Could not updated product price and qty");
        }
        orderDetailRepo.deleteAll(purchaseOrderDetails);
        purchaseOrderRepo.delete(order);
    }

    @Override
    public List<PurchaseOrderDetail> deleteOrderDetails(PurchaseOrderDetail orderDetails) throws Exception {
        Product product = orderDetails.getProduct();
        double pp = product.getPrice();
        double pq = product.getQty();
        double op = orderDetails.getPrice();
        double oq = orderDetails.getQtyOrdered();

        if (pq >= oq) {
            double avgPrice = (pp * pq - op * oq) / (pq - oq);
            double averagePurchasePrice = Math.round(avgPrice);
            product.setQty((int) (pq - oq));
            product.setPrice(averagePurchasePrice);
            productService.saveProduct(product);
            orderDetailRepo.delete(orderDetails);
            List<PurchaseOrderDetail> purchaseOrderDetailList = orderDetailRepo.findByPurchaseOrder(orderDetails.getPurchaseOrder());
            if (purchaseOrderDetailList.isEmpty()) {
                purchaseOrderRepo.delete(orderDetails.getPurchaseOrder());
                return Collections.emptyList();
            } else {
                PurchaseOrder purchaseOrder = purchaseOrderRepo.findById(orderDetails.getPurchaseOrder().getPurchaseOrderID()).orElse(null);
                assert purchaseOrder != null;
                int totalQty = purchaseOrder.getTotalQty() - orderDetails.getQtyOrdered();
                double orderedTotalPrice = orderDetails.getPrice() * orderDetails.getQtyOrdered();
                double purchasePrice = purchaseOrder.getTotalPrice();
                double totalAmount = purchaseOrder.getAmountPaid() - orderedTotalPrice;
                purchaseOrder.setTotalQty(totalQty);
                purchaseOrder.setTotalPrice(purchasePrice - orderedTotalPrice);
                purchaseOrder.setAmountPaid(totalAmount);
                purchaseOrder.setPurchaseOrderDetail(purchaseOrderDetailList);
                return purchaseOrderRepo.save(purchaseOrder).getPurchaseOrderDetail();
            }
        } else {
            throw new Exception("Could not able to delete this transactions");
        }
    }

    @Override
    public List<PurchaseOrder> findAllOrders() {
        return purchaseOrderRepo.findAll(Sort.by("billDate").descending());
    }

    @Override
    public double findSupplierBalanceBySupplier(Long supplierID) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepo
                .findAmountBalanceBySupplier(supplierRepo.findById(supplierID).orElse(null));
        return purchaseOrders.stream().mapToDouble(PurchaseOrder::getCurrentBalance).sum();
    }

    @Override
    public double findAllSuppliersBalance() {
        return purchaseOrderRepo.findAll().stream().mapToDouble(PurchaseOrder::getCurrentBalance).sum();
    }

    @Override
    public List<SupplierBalanceSheet> findCurrentBalanceBySuppliers() {
        return purchaseOrderRepo.findAll().stream()
                .collect(Collectors.groupingBy(PurchaseOrder::getSupplier)).entrySet().stream().map(x -> {
                    List<PurchaseOrder> purchaseOrders = x.getValue();
                    double totalPrice = purchaseOrders.stream().mapToDouble(PurchaseOrder::getTotalPrice).sum();
                    double amountPaid = purchaseOrders.stream().mapToDouble(PurchaseOrder::getAmountPaid).sum();
                    double dueAmount = purchaseOrders.stream().mapToDouble(PurchaseOrder::getCurrentBalance).sum();

                    int size = purchaseOrders.size();
                    PurchaseOrder order = purchaseOrders.get(size - 1);
                    Date billDate = order.getBillDate();
                    Date dueDate = order.getDueDate();
                    if (dueAmount > 0) {
                        return new SupplierBalanceSheet(x.getKey(), totalPrice, amountPaid, dueAmount, billDate, dueDate);
                    } else return null;
                }).filter(Objects::nonNull).sorted().collect(Collectors.toList());
    }

    @Override
    public PurchaseOrder getPurchaseOrder(BalancePayment balancePayment) {
        PurchaseOrder order = new PurchaseOrder();
        order.setSupplier(supplierRepo.findById(balancePayment.getId()).orElse(null));
        order.setAmountPaid(balancePayment.getPayAmount());
        order.setCurrentBalance(-balancePayment.getPayAmount());
        order.setPurchaseOrderDetail(Collections.emptyList());
        order.setBillDate(new Date());
        return order;
    }
}
