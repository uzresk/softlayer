package simple;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.ResponseHandler;
import com.softlayer.api.service.Account;
import com.softlayer.api.service.Hardware;
import com.softlayer.api.service.virtual.Guest;

public class AsyncDescribeInstances {

	public static void main(String[] args) throws Exception {

		ApiClient client = SoftLayerClientProvider.createApiClient();

		Account.Service accountService = Account.service(client);

		System.out.println("------ VIRTUAL ------");

		accountService.withMask().virtualGuests().id();
		accountService.withMask().virtualGuests().hostname();
		accountService.withMask().virtualGuests().fullyQualifiedDomainName();
		;

		accountService.asAsync().getObject(new ResponseHandler<Account>() {

			@Override
			public void onSuccess(Account account) {
				System.out.println("on success");
				// VirtualMachine
				for (Guest guest : account.getVirtualGuests()) {
					// ID
					System.out.println(guest.getId());
					// ホスト名
					System.out.println(guest.getHostname());
					// ホスト名
					System.out.println(guest.getFullyQualifiedDomainName());
					Guest.Service service = guest.asService(client);
					// service.powerOffSoft();
					// service.powerOn();
					System.out.println(service.getPowerState().getName());
				}

			}

			@Override
			public void onError(Exception ex) {
				ex.printStackTrace();

			}
		}).get();

		System.out.println("------ BAREMETAL ------");

		accountService.withMask().hardware().id();
		accountService.withMask().hardware().hostname();
		accountService.withMask().hardware().fullyQualifiedDomainName();

		accountService.asAsync().getObject(new ResponseHandler<Account>() {

			@Override
			public void onSuccess(Account value) {
				// BareMetal
				for (Hardware hardware : accountService.getObject()
						.getHardware()) {
					// ID
					System.out.println(hardware.getId());
					// ホスト名
					System.out.println(hardware.getHostname());
					// ホスト名
					System.out.println(hardware.getFullyQualifiedDomainName());
					Hardware.Service service = hardware.asService(client);
					// service.powerOff();
					// service.powerOn();
					System.out.println(service.getServerPowerState());
				}
			}

			@Override
			public void onError(Exception ex) {
				ex.printStackTrace();

			}
		}).get();
	}
}
