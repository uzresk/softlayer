package image;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;

public class DescribeImages {

	public static void main(String[] args) {

		if (args.length == 1) {
			Long id = new Long(args[0]);
			describeImage(id);

		} else {
			describeImages();
		}
	}

	private static void describeImages() {
		ApiClient client = SoftLayerClientProvider.createApiClient();
		Account.Service accountService = Account.service(client);
		accountService
				.getBlockDeviceTemplateGroups()
				.stream()
				.map(s -> "[" + s.getName() + "]["
						+ new Date(s.getCreateDate().getTimeInMillis()) + "]["
						+ s.getGlobalIdentifier() + "]")
				.forEach(System.out::println);
	}

	private static void describeImage(Long id) {
		ApiClient client = SoftLayerClientProvider.createApiClient();
		Account.Service accountService = Account.service(client);

		// describe image from imageId
		accountService
				.getBlockDeviceTemplateGroups()
				.stream()
				.filter(s -> id.equals(s.getId()))
				.map(s -> ToStringBuilder.reflectionToString(s,
						ToStringStyle.MULTI_LINE_STYLE))
				.forEach(System.out::println);

	}
}
