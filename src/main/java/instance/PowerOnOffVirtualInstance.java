package instance;

import java.util.stream.Stream;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;
import com.softlayer.api.service.virtual.Guest;

public class PowerOnOffVirtualInstance {

	public static void main(String[] args) {

		if (args.length != 1) {
			throw new RuntimeException("args is null or blank.");
		}

		ApiClient client = SoftLayerClientProvider.createApiClient();
		Account.Service accountService = Account.service(client);

		Long id = new Long(args[0]);

		Stream<Guest> stream = accountService.getVirtualGuests().stream()
				.filter(s -> id.equals(s.getId()));
		Guest guest = stream.findFirst().get();

		String powerStatus = guest.asService(client).getPowerState().getName();

		System.out.println("id[" + id + "] Status[" + powerStatus + "]");

		if ("Running".equals(powerStatus)) {
			guest.asService(client).powerOffSoft();

		} else if ("Halted".equals(powerStatus)) {
			guest.asService(client).powerOn();
		}

		accountService
				.getVirtualGuests()
				.stream()
				.filter(s -> id.equals(s.getId()))
				.map(s -> "id[" + id + "] Status["
						+ s.asService(client).getPowerState().getName() + "]")
				.forEach(System.out::println);

	}
}
