package kitchen.service;

import kitchen.model.OrderDto;
import kitchen.shelf.ShelfService;
import kitchen.driver.DriverService;
import kitchen.data.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by prustogi on 8/23/18.
 */
@Component
@EnableScheduling
public class KitchenScheduler {

	private static final Logger log = LoggerFactory.getLogger(KitchenScheduler.class);

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ShelfService shelfService;

	@Autowired
	private DriverService driverService;

	private AtomicBoolean enabled = new AtomicBoolean(true);

	private List<OrderDto> orderReadyList = new ArrayList<>();

	@Value("${kitchen.order.prepare.time:0}")
	private int prepareRate;

	@Scheduled(fixedRate=250)
	public void getNewOrder() {
		if (enabled.get()) {
			if (!CollectionUtils.isEmpty(orderRepository.getCurrentOrders())) {
				OrderDto firstOrder = orderRepository.getCurrentOrders().get(0);
				orderRepository.getCurrentOrders().remove(0);
				log.info("Received Order. Name: {}", firstOrder.getName());
				makeOrder(prepareRate, firstOrder);
			} else {
				log.info("All orders have been placed. Disabling Kitchen Scheduler");
				log.info("Starting Drivers");
				driverService.start();
				enabled.set(false);
			}
		}
	}

	public void makeOrder(int timeToMakeOrder, OrderDto order) {
		try {
			Thread.sleep(timeToMakeOrder);
			log.info("Order Ready. Name: {}", order.getName());
			orderReadyList.add(order);
			shelfService.placeOnShelf(order);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public List<OrderDto> getReadyOrders() {
		return orderReadyList;
	}

	public void EnableScheduler() {
		log.info("Enabling Kitchen Scheduler");
		enabled.set(true);
	}
}
