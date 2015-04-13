package instance;

import java.util.stream.Stream;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;
import com.softlayer.api.service.Hardware;

public class PowerOnOffBaremetalInstance {

	public static void main(String[] args) {

		if (args.length != 1) {
			throw new RuntimeException("args is null or blank.");
		}

		ApiClient client = SoftLayerClientProvider.createApiClient();
		Account.Service accountService = Account.service(client);
		accountService.withMask().hardware().id();
		accountService.withMask().hardware().hostname();
		accountService.withMask().hardware().fullyQualifiedDomainName();

		Long id = new Long(args[0]);

		// インスタンスの�?報を指定して表示します�??
		Stream<Hardware> stream = accountService.getObject().getHardware()
				.stream().filter(s -> id.equals(s.getId()));
		Hardware hardware = stream.findFirst().get();

		String powerStatus = hardware.asService(client).getServerPowerState();

		System.out.println("id[" + id + "] Status[" + powerStatus + "]");

		// 起動中なら停止、停止中なら起動する�??
		if ("on".equals(powerStatus)) {
			hardware.asService(client).powerOff();

		} else if ("off".equals(powerStatus)) {
			hardware.asService(client).powerOn();
		}

		// ス�?ータスを�?�表示(power on/offは非同期ではな�?ので、ス�?ータスは変わらな�?)
		accountService
				.getObject()
				.getHardware()
				.stream()
				.filter(s -> id.equals(s.getId()))
				.map(s -> "id[" + id + "] Status["
						+ s.asService(client).getServerPowerState() + "]")
				.forEach(System.out::println);
	}
}
