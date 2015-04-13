package instance;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;

public class DescribeBaremetalInstances {

	public static void main(String[] args) {

		System.out.println("[ID][fullyQualifiedDomainName][Status]");

		if (args.length == 1) {
			Long id = new Long(args[0]);
			describeBaremetalInstance(id);

		} else {
			describeBaremetalInstances();
		}

		ApiClient client = SoftLayerClientProvider.createApiClient();
		Account.Service accountService = Account.service(client);

		// インスタンスの�?報をまとめて表示します�??
		accountService
				.getObject()
				.getHardware()
				.stream()
				.map(s -> "[" + s.getId() + "]["
						+ s.getFullyQualifiedDomainName() + "]["
						+ s.asService(client).getServerPowerState() + "]")
				.forEach(System.out::println);
	}

	private static void describeBaremetalInstances() {

		ApiClient client = SoftLayerClientProvider.createApiClient();

		Account.Service accountService = Account.service(client);
		accountService.withMask().hardware().id();
		accountService.withMask().hardware().hostname();
		accountService.withMask().hardware().fullyQualifiedDomainName();

		// インスタンスの�?報をまとめて表示します�??
		accountService
				.getObject()
				.getHardware()
				.stream()
				.map(s -> "[" + s.getId() + "]["
						+ s.getFullyQualifiedDomainName() + "]["
						+ s.asService(client).getServerPowerState() + "]")
				.forEach(System.out::println);
	}

	private static void describeBaremetalInstance(Long id) {

		ApiClient client = SoftLayerClientProvider.createApiClient();

		Account.Service accountService = Account.service(client);
		accountService.withMask().hardware().id();
		accountService.withMask().hardware().hostname();
		accountService.withMask().hardware().fullyQualifiedDomainName();

		// インスタンスの�?報を指定して表示します�??
		accountService
				.getObject()
				.getHardware()
				.stream()
				.filter(s -> id.equals(s.getId()))
				.map(s -> "[" + s.getId() + "]["
						+ s.getFullyQualifiedDomainName() + "]["
						+ s.asService(client).getServerPowerState() + "]")
				.forEach(System.out::println);
	}
}
