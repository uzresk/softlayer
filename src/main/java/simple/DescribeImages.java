package simple;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import provider.SoftLayerClientProvider;

import com.softlayer.api.ApiClient;
import com.softlayer.api.service.Account;
import com.softlayer.api.service.virtual.guest.block.device.template.Group;

public class DescribeImages {

	public static void main(String[] args) {

		ApiClient client = SoftLayerClientProvider.createApiClient();
		Account.Service accountService = Account.service(client);

		for (Group group : accountService.getBlockDeviceTemplateGroups()) {

			System.out.println(ToStringBuilder.reflectionToString(group,
					ToStringStyle.MULTI_LINE_STYLE));
			System.out.println(group.getName());
		}
	}
}
