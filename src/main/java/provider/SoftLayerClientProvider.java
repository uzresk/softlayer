package provider;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.softlayer.api.ApiClient;
import com.softlayer.api.RestApiClient;

public class SoftLayerClientProvider {

	private static final String BUNDLE_NAME = "softlayer";

	public static ApiClient createApiClient() {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
		String userName = bundle.getString("softlayer.username");
		String apiKey = bundle.getString("softlayer.apikey");
		return createApiClient(userName, apiKey);
	}

	public static ApiClient createApiClient(String userName, String apiKey) {

		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(apiKey)) {
			throw new IllegalArgumentException("userName or apiKey is null.");
		}

		// proxy settings
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
		String proxyHost = bundle.getString("proxyHost");
		String proxyPort = bundle.getString("proxyPort");

		if (StringUtils.isNotEmpty(proxyHost)
				&& StringUtils.isNotEmpty(proxyPort)) {
			System.setProperty("proxySet", "true");
			System.setProperty("proxyHost", proxyHost);
			System.setProperty("proxyPort", proxyPort);
		}

		// logging
		boolean logEnabled = "0".equals(bundle.getString("log.enable")) ? false
				: true;

		if (logEnabled) {
			return new RestApiClient().withCredentials(userName, apiKey)
					.withLoggingEnabled();
		} else {
			return new RestApiClient().withCredentials(userName, apiKey);
		}
	}
}
