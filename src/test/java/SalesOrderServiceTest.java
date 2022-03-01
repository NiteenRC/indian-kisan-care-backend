import com.nc.med.mapper.CustomerBalance;
import com.nc.med.mapper.OrderStatus;
import com.nc.med.model.*;
import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.DailySummaryRepository;
import com.nc.med.repo.SalesOrderRepo;
import com.nc.med.service.SalesOrderService;
import com.nc.med.service.SalesOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SalesOrderService.class)
public class SalesOrderServiceTest {
    @Mock
    private SalesOrderRepo salesOrderRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private DailySummaryRepository dailySummaryRepository;

    @InjectMocks
    private SalesOrderServiceImpl salesOrderService;

    public List<SalesOrder> getSalesOrder() {
        List<SalesOrder> salesOrderList = new ArrayList<>();
        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = null;
        try {
            billDate = dmyFormat.parse(StringFormatDate);
        } catch (ParseException ignored) {
        }

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setSalesOrderID(1L);
        salesOrder.setTotalPrice(1000);
        salesOrder.setAmountPaid(900);
        salesOrder.setTotalQty(10);
        salesOrder.setStatus(OrderStatus.PARTIAL);
        salesOrder.setCurrentBalance(50);
        salesOrder.setBillDate(billDate);
        Customer customer = Customer.builder().customerName("AAA").phoneNumber("98323453245").build();
        salesOrder.setCustomer(customer);

        Category category = Category.builder().categoryName("Fertilizer").build();
        Product product = Product.builder().createdDate(new Date()).price(100).productDesc("").productName("").qty(0).category(category).build();

        SalesOrderDetail salesOrderDetail = new SalesOrderDetail();
        salesOrderDetail.setSalesOrderDetailID(3L);
        salesOrderDetail.setProduct(product);
        salesOrderDetail.setQtyOrdered(10);
        salesOrder.setSalesOrderDetail(Collections.singletonList(salesOrderDetail));
        salesOrderList.add(salesOrder);
        // Mockito.when(salesOrderRepo.findAll(Sort.by("billDate").descending())).thenReturn(SalesOrderList);
        return salesOrderList;
    }

    @Test
    public void findAllTest() {
        Mockito.when(salesOrderRepo.findAll(Sort.by("billDate").descending())).thenReturn(getSalesOrder());
        Assertions.assertEquals(getSalesOrder(), salesOrderService.findAllOrders());
    }

    @Test
    public void findCustomerBalanceByCustomerTest() {
        Customer customer = customerRepo.findById(1L).orElse(null);
        Customer customer2 = Customer.builder().customerName("AA").phoneNumber("12342").build();
        Optional<Customer> optional = Optional.of(customer2);
        Mockito.when(customerRepo.findById(1L)).thenReturn(optional);
        Mockito.when(salesOrderRepo.findAmountBalanceByCustomer(customer)).thenReturn(getSalesOrder());

        CustomerBalance CustomerBalance = salesOrderService.findCustomerBalanceByCustomer(1L);
        // Assertions.assertEquals(50, CustomerBalance.getBalance());
    }

    @Test
    public void saveOrderTest() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        Customer customer = order.getCustomer();
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 40, order.getCustomer());
        DailySummary dailySummary2 = new DailySummary(order.getBillDate(), 10, 20, 30, 40, order.getCustomer());
        List<DailySummary> dailySummaries = Arrays.asList(dailySummary1, dailySummary2);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
    }

    @Test
    public void saveOrderNoCustomerTest() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setCurrentDue(100L);
        Customer customer = Customer.builder().customerName("").phoneNumber("").build();
        order.setCustomer(customer);
        List<DailySummary> dailySummaries = Collections.emptyList();

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
    }

    @Test
    public void saveOrderNoCustomer1Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setCurrentDue(100L);
        Customer customer = Customer.builder().customerName("").phoneNumber("").build();
        order.setCustomer(customer);
        Customer customer1 = Customer.builder().customerName("AAA").phoneNumber("98323453245").build();
        customer.setId(2L);
        Mockito.when(customerRepo.findAll()).thenReturn(Collections.singletonList(customer1));
        List<DailySummary> dailySummaries = Collections.emptyList();

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
    }

    @Test
    public void saveOrderCase1Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(800);
        order.setAmountPaid(200);
        order.setCurrentDue(0);
        Customer customer = order.getCustomer();
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 0, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);

        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(600);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(0);
    }

    @Test
    public void saveOrderCase2Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(800);
        order.setAmountPaid(500);
        order.setCurrentDue(200L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 0, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(500);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(200);
    }

    @Test
    public void saveOrderCase3Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(800);
        order.setAmountPaid(100);
        order.setCurrentDue(200L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 0, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(900);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(100);
    }

    @Test
    public void saveOrderCase4Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(800);
        order.setAmountPaid(1000);
        order.setCurrentDue(200L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 0, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(0);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(200);
    }

    @Test
    public void saveOrderCase41Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(500);
        order.setAmountPaid(300);
        order.setCurrentDue(0);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 200, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(200);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(200);
    }

    @Test
    public void saveOrderCase42Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(0);
        order.setAmountPaid(100);
        order.setCurrentDue(200);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 200, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(100);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(300);
    }

    @Test
    public void saveOrderCase5Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(800);
        order.setAmountPaid(300);
        order.setCurrentDue(300L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 0, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(800);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(300);
    }

    @Test
    public void saveOrderCase51Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(0);
        order.setAmountPaid(200);
        order.setCurrentDue(800L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 300, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(600);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(500);
    }

    @Test
    public void saveOrderCase6Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(800);
        order.setAmountPaid(300);
        order.setCurrentDue(300L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 0, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(800);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(300);
    }

    @Test
    public void saveOrderCase61Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(50);
        order.setAmountPaid(300);
        order.setCurrentDue(800L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 300, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(550);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(600);
    }

    @Test
    public void saveOrderCase71Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(200);
        order.setAmountPaid(300);
        order.setCurrentDue(800L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 300, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(700);
        assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(600);
    }

    @Test
    public void saveOrderCase72Test() throws ParseException {
        SalesOrder order = getSalesOrder().get(0);
        order.setTotalPrice(100);
        order.setAmountPaid(50);
        order.setCurrentDue(700L);
        Customer customer = order.getCustomer();
        customer.setId(2L);
        Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
        String StringFormatDate = dmyFormat.format(myDate);
        Date billDate = dmyFormat.parse(StringFormatDate);

        DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 600, order.getCustomer());
        List<DailySummary> dailySummaries = Collections.singletonList(dailySummary1);

        Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
                .thenReturn(dailySummaries);
        Mockito.when(salesOrderRepo.save(order)).thenReturn(order);
        SalesOrder salesOrder = salesOrderService.saveOrder(order);
        assertThat(salesOrder).isNotEqualTo(null);
        assertThat(dailySummaries.get(0).getDueAmount()).isEqualTo(750);
        //assertThat(dailySummaries.get(0).getDueCollection()).isEqualTo(600);
    }
}
