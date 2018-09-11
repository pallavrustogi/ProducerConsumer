package kitchen.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import kitchen.model.OrderDto;
import com.fasterxml.jackson.core.type.TypeReference;
import kitchen.service.KitchenScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by prustogi on 8/23/18.
 */
@Component
public class OrderRepository {
	private List<OrderDto> orders;
	@Autowired
	KitchenScheduler scheduler;

	@Value("${input.file.name:input.json}")
	String inputFile;

	@PostConstruct
	public void init() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<OrderDto>> orderList = new TypeReference<List<OrderDto>>() {
		};
		InputStream is = getClass().getClassLoader().getResourceAsStream(inputFile);
		try {
			orders = mapper.readValue(is, orderList);
			scheduler.EnableScheduler();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<OrderDto> getCurrentOrders() {
		return orders;
	}

}
