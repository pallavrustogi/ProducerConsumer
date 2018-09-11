package kitchen.rest;

import kitchen.model.OrderDto;
import kitchen.service.KitchenScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private KitchenScheduler kitchenScheduler;

    @RequestMapping("/orderlist")
    public List<OrderDto> orderlist() {
        return kitchenScheduler.getReadyOrders();
    }


}
