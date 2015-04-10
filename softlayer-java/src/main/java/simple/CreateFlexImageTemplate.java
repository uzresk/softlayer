package simple;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;
import com.softlayer.api.service.container.disk.image.capture.Template;
import com.softlayer.api.service.virtual.Guest;
import com.softlayer.api.service.virtual.guest.block.device.template.Group;

public class CreateFlexImageTemplate {

	public static void main(String[] args) {

		ApiClient client = SoftLayerClientProvider.createApiClient();

		Account.Service accountService = Account.service(client);

		for (Guest guest : accountService.getVirtualGuests()) {

			Long id = guest.getId();

			if (8501951 == id) {

				System.out.println("craete image");

				Guest.Service service = guest.asService(client);
				Template paramTemplate = new Template();
				paramTemplate.setName("8501951 name 2");
				paramTemplate.setDescription("8501951 description");
				paramTemplate.setSummary("8501951 summary");
				Group group = service.captureImage(paramTemplate);
				System.out.println(ToStringBuilder.reflectionToString(group,
						ToStringStyle.MULTI_LINE_STYLE));
			}
		}
	}

}
