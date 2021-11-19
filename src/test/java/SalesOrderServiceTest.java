import com.nc.med.mapper.CustomerBalance;
import com.nc.med.mapper.OrderStatus;
import com.nc.med.mapper.CustomerBalance;
import com.nc.med.model.SalesOrder;
import com.nc.med.model.SalesOrderDetail;
import com.nc.med.model.SalesOrderDetail;
import com.nc.med.model.Category;
import com.nc.med.model.Customer;
import com.nc.med.model.DailySummary;
import com.nc.med.model.Product;
import com.nc.med.repo.SalesOrderRepo;
import com.nc.med.repo.CustomerRepo;
import com.nc.med.repo.DailySummaryRepository;
import com.nc.med.service.SalesOrderService;
import com.nc.med.service.SalesOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		System.out.println(myDate);
		SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
		String StringFormatDate = dmyFormat.format(myDate);
		Date billDate = null;
		try {
			billDate = dmyFormat.parse(StringFormatDate);
		} catch (ParseException e) {
		}

		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setSalesOrderID(1L);
		salesOrder.setTotalPrice(1000);
		salesOrder.setAmountPaid(900);
		salesOrder.setTotalQty(10);
		salesOrder.setStatus(OrderStatus.PARTIAL);
		salesOrder.setCurrentBalance(50);
		salesOrder.setBillDate(billDate);
		Customer customer = new Customer("AAA", "98323453245");
		salesOrder.setCustomer(customer);

		Category category = new Category("Fertilizer");
		Product product = new Product(new Date(), 100, "", "", 0, category);

		SalesOrderDetail salesOrderDetail = new SalesOrderDetail();
		salesOrderDetail.setSalesOrderDetailID(3L);
		salesOrderDetail.setProduct(product);
		salesOrderDetail.setQtyOrdered(10);
		salesOrder.setSalesOrderDetail(Arrays.asList(salesOrderDetail));
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
		Customer Customer = customerRepo.findById(1L).orElse(null);
		Customer customer2 = new Customer("AA", "12342");
		Optional<Customer> optional = Optional.of(customer2);
		Mockito.when(customerRepo.findById(1L)).thenReturn(optional);
		Mockito.when(salesOrderRepo.findAmountBalanceByCustomer(Customer)).thenReturn(getSalesOrder());

		CustomerBalance CustomerBalance = salesOrderService.findCustomerBalanceByCustomer(1L);
		// Assertions.assertEquals(50, CustomerBalance.getBalance());
	}

	@Test
	public void saveOrderTest() throws ParseException {
		SalesOrder order = getSalesOrder().get(0);
		Customer customer = order.getCustomer();
		Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

		Date myDate = new Date();
		System.out.println(myDate);
		SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
		String StringFormatDate = dmyFormat.format(myDate);
		Date billDate = dmyFormat.parse(StringFormatDate);

		DailySummary dailySummary1 = new DailySummary(billDate, 10, 20, 30, 40, order.getCustomer());
		DailySummary dailySummary2 = new DailySummary(order.getBillDate(), 10, 20, 30, 40, order.getCustomer());
		List<DailySummary> dailySummaries = Arrays.asList(dailySummary1, dailySummary2);

		Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
				.thenReturn(dailySummaries);
		SalesOrder salesOrder = salesOrderService.saveOrder(order);
	}

	@Test
	public void saveOrder2Test() throws ParseException {
		SalesOrder order = getSalesOrder().get(0);
		order.setCurrentDue(100L);
		Customer customer = order.getCustomer();
		customer.setId(2L);
		Mockito.when(customerRepo.findByCustomerName("AAA")).thenReturn(customer);

		final Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -2);
	    
		Date myDate = new Date();
		System.out.println(myDate);
		SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
		String StringFormatDate = dmyFormat.format(myDate);
		Date billDate = dmyFormat.parse(StringFormatDate);

		List<DailySummary> dailySummaries = Collections.emptyList();

		Mockito.when(dailySummaryRepository.findByBillDateAndCustomer(order.getBillDate(), order.getCustomer()))
				.thenReturn(dailySummaries);
		SalesOrder salesOrder = salesOrderService.saveOrder(order);
	}
}
