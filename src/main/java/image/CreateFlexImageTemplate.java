package image;

import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;
import com.softlayer.api.service.container.disk.image.capture.Template;
import com.softlayer.api.service.virtual.guest.block.device.template.Group;

public class CreateFlexImageTemplate {

	public static void main(String[] args) {

		if (StringUtils.isEmpty(args[0])) {
			throw new RuntimeException("args is null or blank.");
		}

		ApiClient client = SoftLayerClientProvider.createApiClient();

		Account.Service accountService = Account.service(client);

		final String deviceName = args[0];

		// template description
		Template paramTemplate = new Template();
		paramTemplate.setName("template from " + deviceName);
		paramTemplate.setDescription("description example");
		paramTemplate.setSummary("summary example");

		// create flex image
		Stream<Group> stream = accountService
				.getVirtualGuests()
				.stream()
				.filter(s -> deviceName.equals(s.getFullyQualifiedDomainName()))
				.map(s -> s.asService(client).captureImage(paramTemplate));

		// describe template
		System.out.println(ToStringBuilder.reflectionToString(stream
				.findFirst().get(), ToStringStyle.MULTI_LINE_STYLE));

	}
}
