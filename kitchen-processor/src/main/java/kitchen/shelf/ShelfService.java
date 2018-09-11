package kitchen.shelf;

import kitchen.model.OrderDto;
import kitchen.model.ShelfEnum;
import kitchen.model.ShelfOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by prustogi on 8/23/18.
 */
@Component
public class ShelfService {
	private static final Logger log = LoggerFactory.getLogger(ShelfService.class);

	private List<ShelfOrder> hotShelf = new ArrayList<>();
	private List<ShelfOrder> coldShelf = new ArrayList<>();
	private List<ShelfOrder> frozenShelf = new ArrayList<>();
	private List<ShelfOrder> overflowShelf = new ArrayList<>();

	@Value("${Shelf.hot.size:15}")
	private int hotShelfSize;

	@Value("${Shelf.cold.size:15}")
	private int coldShelfSize;

	@Value("${Shelf.frozen.size:15}")
	private int frozenShelfSize;

	@Value("${Shelf.overflow.size:20}")
	private int overflowShelfSize;

	public void placeOnShelf(OrderDto order){
		ShelfOrder shelfOrder = new ShelfOrder(order);
		shelfOrder.setCreatedAt(Calendar.getInstance().getTime());
		switch (shelfOrder.getTemp().getId()) {
			case 1:
				if(hotShelf.size()<hotShelfSize) {
					shelfOrder.setShelfAt(ShelfEnum.hot);
					addAndNormalize(hotShelf, shelfOrder);
					log.info("Order Added to Hot Shelf. Name: {}", shelfOrder.getName());
				} else if (overflowShelf.size()<overflowShelfSize){
					shelfOrder.setShelfAt(ShelfEnum.overflow);
					addAndNormalize(overflowShelf, shelfOrder);
					log.info("Order Added to Overflow Shelf. Name: {}", shelfOrder.getName());
				} else {
					log.info("Order Discarded. No Empty Shelf. Name: {}", shelfOrder.getName());
				}
				break;
			case 2:
				if(coldShelf.size()<coldShelfSize) {
					shelfOrder.setShelfAt(ShelfEnum.cold);
					addAndNormalize(coldShelf, shelfOrder);
					log.info("Order Added to Cold Shelf. Name: {}", shelfOrder.getName());
				} else if (overflowShelf.size()<overflowShelfSize){
					shelfOrder.setShelfAt(ShelfEnum.overflow);
					addAndNormalize(overflowShelf, shelfOrder);
					log.info("Order Added to Overflow Shelf. Name: {}", shelfOrder.getName());
				} else {
					log.info("Order Discarded. No Empty Shelf. Name: {}", shelfOrder.getName());
				}
				break;
			case 3:
				if(frozenShelf.size()<frozenShelfSize) {
					shelfOrder.setShelfAt(ShelfEnum.frozen);
					addAndNormalize(frozenShelf, shelfOrder);
					log.info("Order Added to Frozen Shelf. Name: {}", shelfOrder.getName());
				} else if (overflowShelf.size()<overflowShelfSize){
					shelfOrder.setShelfAt(ShelfEnum.overflow);
					addAndNormalize(overflowShelf, shelfOrder);
					log.info("Order Added to Overflow Shelf. Name: {}", shelfOrder.getName());
				} else {
					log.info("Order Discarded. No Empty Shelf. Name: {}", shelfOrder.getName());
				}
				break;
			default:
				log.info("Order Discarded. UnknownType. Name: {}", shelfOrder.getName());

		}

	}

	private void addAndNormalize(List<ShelfOrder> shelf, ShelfOrder shelfOrder) {
		shelf.add(shelfOrder);
		refreshShelf();
	}

	private void refreshShelf() {
		for(Iterator<ShelfOrder> it = hotShelf.iterator(); it.hasNext();) {
			ShelfOrder shelfOrder = it.next();
			long orderAge = Math.abs(Calendar.getInstance().getTimeInMillis()-shelfOrder.getCreatedAt().getTime())/1000;
			int value = (int)((shelfOrder.getShelfLife() - orderAge) - (shelfOrder.getDecayRate() * orderAge));
			if(value==0) {
				it.remove();
				log.info("Order Decayed on hot Shelf. Name: {}", shelfOrder.getName());
			}
		}

		for(Iterator<ShelfOrder> it = coldShelf.iterator(); it.hasNext();) {
			ShelfOrder shelfOrder = it.next();
			long orderAge = Math.abs(Calendar.getInstance().getTimeInMillis()-shelfOrder.getCreatedAt().getTime())/1000;
			int value = (int)((shelfOrder.getShelfLife() - orderAge) - (shelfOrder.getDecayRate() * orderAge));
			if(value==0) {
				it.remove();
				log.info("Order Decayed on cold Shelf. Name: {}", shelfOrder.getName());
			}
		}

		for(Iterator<ShelfOrder> it = frozenShelf.iterator(); it.hasNext();) {
			ShelfOrder shelfOrder = it.next();
			long orderAge = Math.abs(Calendar.getInstance().getTimeInMillis()-shelfOrder.getCreatedAt().getTime())/1000;
			int value = (int)((shelfOrder.getShelfLife() - orderAge) - (shelfOrder.getDecayRate() * orderAge));
			if(value==0) {
				it.remove();
				log.info("Order Decayed on frozen Shelf. Name: {}", shelfOrder.getName());
			}
		}

		for(Iterator<ShelfOrder> it = coldShelf.iterator(); it.hasNext();) {
			ShelfOrder shelfOrder = it.next();
			long orderAge = Math.abs(Calendar.getInstance().getTimeInMillis()-shelfOrder.getCreatedAt().getTime())/1000;
			int value = (int)((shelfOrder.getShelfLife() - orderAge) - (shelfOrder.getDecayRate() * orderAge));
			if(value==0) {
				it.remove();
				log.info("Order Decayed on overflow Shelf. Name: {}", shelfOrder.getName());
			}
		}
	}

	public boolean areShelvesEmpty(){
		if(CollectionUtils.isEmpty(hotShelf) && CollectionUtils.isEmpty(coldShelf)
				&& CollectionUtils.isEmpty(frozenShelf) && CollectionUtils.isEmpty(overflowShelf)) {
			log.info("No Orders Left on Shelves");
			return true;
		}
		return false;
	}

	public ShelfOrder getNextOrder() {
		if(!CollectionUtils.isEmpty(overflowShelf)) {
			ShelfOrder shelfOrder = overflowShelf.get(0);
			overflowShelf.remove(shelfOrder);
			log.info("Out for Delivery. Name: {}", shelfOrder.getName());
			refreshShelf();
			return shelfOrder;
		} else if(!CollectionUtils.isEmpty(hotShelf)) {
			ShelfOrder shelfOrder = hotShelf.get(0);
			hotShelf.remove(shelfOrder);
			log.info("Out for Delivery. Name: {}", shelfOrder.getName());
			refreshShelf();
			return shelfOrder;
		} else if(!CollectionUtils.isEmpty(coldShelf)) {
			ShelfOrder shelfOrder = coldShelf.get(0);
			log.info("Out for Delivery. Name: {}", shelfOrder.getName());
			coldShelf.remove(shelfOrder);
			refreshShelf();
			return shelfOrder;
		} else if(!CollectionUtils.isEmpty(frozenShelf)) {
			ShelfOrder shelfOrder = frozenShelf.get(0);
			frozenShelf.remove(shelfOrder);
			log.info("Out for Delivery. Name: {}", shelfOrder.getName());
			refreshShelf();
			return shelfOrder;
		}
		log.info("No Orders Left for Delivery");
		return null;
	}
}
