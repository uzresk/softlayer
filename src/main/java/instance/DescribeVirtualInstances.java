package instance;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;

public class DescribeVirtualInstances {

	public static void main(String[] args) {

		if (args.length == 1) {
			Long id = new Long(args[0]);
			describeInstance(id);

		} else {
			describeInstance();
		}
	}

	private static void describeInstance() {

		ApiClient client = SoftLayerClientProvider.createApiClient();

		Account.Service accountService = Account.service(client);

		accountService
				.getVirtualGuests()
				.stream()
				.map(s -> ToStringBuilder.reflectionToString(s,
						ToStringStyle.MULTI_LINE_STYLE))
				.forEach(System.out::println);
	}

	private static void describeInstance(Long id) {
		ApiClient client = SoftLayerClientProvider.createApiClient();

		Account.Service accountService = Account.service(client);

		System.out.println("[ID][Domain][Status]");

		accountService
				.getVirtualGuests()
				.stream()
				.filter(s -> id.equals(s.getId()))
				.map(s -> "[" + s.getId() + "]["
						+ s.getFullyQualifiedDomainName() + "]["
						+ s.asService(client).getPowerState().getName() + "]")
				.forEach(System.out::println);
	}
}
