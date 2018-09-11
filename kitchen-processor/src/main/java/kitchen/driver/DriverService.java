package kitchen.driver;

import kitchen.model.Driver;
import kitchen.model.ShelfOrder;
import kitchen.shelf.ShelfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by prustogi on 8/24/18.
 */
@Component
public class DriverService {

	private static final Logger log = LoggerFactory.getLogger(DriverService.class);

	@Autowired
	private ShelfService shelfService;

	@Value("${driver.rate:3}")
	private double lambda;

	public void start() {
		while(!shelfService.areShelvesEmpty()) {
			int dispatchDrivers = getPoisson(lambda);
			log.info("{} Drivers arrived", dispatchDrivers);
			for(int i =0; i<dispatchDrivers; i++) {
				Driver driver = new Driver();

				ShelfOrder order = shelfService.getNextOrder();
				if(order == null) {
					break;
				}
				driver.setShelfOrder(order);
				log.info("Order Dispatched. Name={}", order.getName());
			}
		}

	}

	//Using example: https://stackoverflow.com/questions/1241555/algorithm-to-generate-poisson-and-binomial-random-numbers
	public static int getPoisson(double lambda) {
		double L = Math.exp(-lambda);
		double p = 1.0;
		int k = 0;

		do {
			k++;
			p *= Math.random();
		} while (p > L);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return k - 1;
	}

}
