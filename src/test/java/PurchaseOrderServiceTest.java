import com.nc.med.mapper.OrderStatus;
import com.nc.med.model.PurchaseOrder;
import com.nc.med.model.PurchaseOrderDetail;
import com.nc.med.repo.PurchaseOrderRepo;
import com.nc.med.service.PurchaseOrderService;
import com.nc.med.service.PurchaseOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = PurchaseOrderService.class)
public class PurchaseOrderServiceTest {
    @Mock
    private PurchaseOrderRepo purchaseOrderRepo;

    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderService;

    private final List<PurchaseOrder> purchaseOrderList = new ArrayList<>();

    @BeforeEach
    public void init() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseOrderID(1L);
        purchaseOrder.setTotalPrice(1000);
        purchaseOrder.setAmountPaid(900);
        purchaseOrder.setTotalQty(10);
        purchaseOrder.setStatus(OrderStatus.PARTIAL);

        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
        purchaseOrderDetail.setPurchaseOrderDetailID(3L);
        //purchaseOrderDetail.setProduct();
        //purchaseOrder.setPurchaseOrderDetail(purchaseOrderDetail);
        purchaseOrderList.add(purchaseOrder);
        Mockito.when(purchaseOrderRepo.findAll(Sort.by("billDate").descending())).thenReturn(purchaseOrderList);
    }

    @Test
    public void findAllTest() {
        Assertions.assertEquals(purchaseOrderList, purchaseOrderService.findAllOrders());
    }
}
