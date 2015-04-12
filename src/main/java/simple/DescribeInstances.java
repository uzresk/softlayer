package simple;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;

public class DescribeInstances {

	public static void main(String[] args) {

		ApiClient client = SoftLayerClientProvider.createApiClient();
		Account.Service accountService = Account.service(client);

		System.out.println("[ID][Domain][Status]");

		// インスタンスの情報をまとめて表示します。
		accountService
				.getVirtualGuests()
				.stream()
				.map(s -> "[" + s.getId() + "][" + s.getDomain() + "]["
						+ s.asService(client).getPowerState().getName() + "]")
				.forEach(System.out::println);

		// インスタンスの情報を指定して表示します。
		accountService
				.getVirtualGuests()
				.stream()
				.filter(s -> s.getId() == 8501951L)
				.map(s -> "[" + s.getId() + "][" + s.getDomain() + "]["
						+ s.asService(client).getPowerState().getName() + "]")
				.forEach(System.out::println);

		// 起動
		accountService.getVirtualGuests().stream()
				.filter(s -> s.getId() == 8501951L)
				.forEach(s -> s.asService(client).powerOn());

		// 停止
		accountService.getVirtualGuests().stream()
				.filter(s -> s.getId() == 8501951L)
				.forEach(s -> s.asService(client).powerOffSoft());

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
		// // 起動
		// // service.powerOn();
		// // ステータス(on/off)
		// System.out.println(service.getServerPowerState());
		// }

	}
}
