package simple;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;
import com.softlayer.api.service.Hardware;
import com.softlayer.api.service.virtual.Guest;

public class DescribeInstances {

	public static void main(String[] args) {

		ApiClient client = SoftLayerClientProvider.createApiClient();
		Account.Service accountService = Account.service(client);

		System.out.println("------ VIRTUAL ------");

		// VirtualMachine
		for (Guest guest : accountService.getVirtualGuests()) {
			// ID
			System.out.println(guest.getId());
			// ホスト名
			System.out.println(guest.getHostname());
			// ホスト名
			System.out.println(guest.getFullyQualifiedDomainName());
			Guest.Service service = guest.asService(client);
			// 停止
			// service.powerOffSoft();
			// 起動
			// service.powerOn();
			// ステータス(起動中：Running/停止：Halted)
			System.out.println(service.getPowerState().getName());
		}

		System.out.println("------ BAREMETAL ------");

		accountService.withMask().hardware().id();
		accountService.withMask().hardware().hostname();
		accountService.withMask().hardware().fullyQualifiedDomainName();

		// BareMetal
		for (Hardware hardware : accountService.getObject().getHardware()) {
			// ID
			System.out.println(hardware.getId());
			// ホスト名
			System.out.println(hardware.getHostname());
			// ホスト名
			System.out.println(hardware.getFullyQualifiedDomainName());
			Hardware.Service service = hardware.asService(client);
			// 停止
			// service.powerOff();
			// 起動
			// service.powerOn();
			// ステータス(on/off)
			System.out.println(service.getServerPowerState());
		}

	}
}
