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

		// インスタンスの�?報を指定して表示します�??
		Stream<Guest> stream = accountService.getVirtualGuests().stream()
				.filter(s -> id.equals(s.getId()));
		Guest guest = stream.findFirst().get();

		String powerStatus = guest.asService(client).getPowerState().getName();

		System.out.println("id[" + id + "] Status[" + powerStatus + "]");

		// 起動中なら停止、停止中なら起動する�??
		if ("Running".equals(powerStatus)) {
			guest.asService(client).powerOffSoft();

		} else if ("Halted".equals(powerStatus)) {
			guest.asService(client).powerOn();
		}

		// ス�?ータスを�?�表示
		accountService
				.getVirtualGuests()
				.stream()
				.filter(s -> id.equals(s.getId()))
				.map(s -> "id[" + id + "] Status["
						+ s.asService(client).getPowerState().getName() + "]")
				.forEach(System.out::println);

		// 起�?
		// accountService.getVirtualGuests().stream()
		// .filter(s -> s.getId() == 8501951L)
		// .forEach(s -> s.asService(client).powerOn());

		// accountService.withMask().hardware().id();
		// accountService.withMask().hardware().hostname();
		// accountService.withMask().hardware().fullyQualifiedDomainName();

		// BareMetal
		// for (Hardware hardware : accountService.getObject().getHardware()) {
		// // ID
		// System.out.println(hardware.getId());
		// // ホスト名
		// System.out.println(hardware.getHostname());
		// // ホスト名
		// System.out.println(hardware.getFullyQualifiedDomainName());
		// Hardware.Service service = hardware.asService(client);
		// // 停止
		// // service.powerOff();
		// // 起�?
		// // service.powerOn();
		// // ス�?ータス(on/off)
		// System.out.println(service.getServerPowerState());
		// }

	}
}
