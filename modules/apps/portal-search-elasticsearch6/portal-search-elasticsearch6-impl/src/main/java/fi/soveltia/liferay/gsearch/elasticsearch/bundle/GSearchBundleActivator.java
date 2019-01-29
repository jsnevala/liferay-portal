
package fi.soveltia.liferay.gsearch.elasticsearch.bundle;

import com.liferay.portal.bundle.blacklist.BundleBlacklistManager;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

/**
 * Custom adapter bundle activator. This tries to stop the standard adapter if
 * it was started up already.
 *
 * @author Petteri Karttunen
 */
public class GSearchBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext)
		throws Exception {

		// Disable

		disableDefaultAdapter(bundleContext);

		// Add to blacklist

		// This seems to be broken in FP6
		
		// addDefaultAdapterToBlackList(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext)
		throws Exception {

		// Enable default adapter on bundle stop.

		/*
		ServiceReference<BundleBlacklistManager> serviceReference =
			bundleContext.getServiceReference(BundleBlacklistManager.class);

		try {

			BundleBlacklistManager bundleBlacklistManager =
				bundleContext.getService(serviceReference);

			bundleBlacklistManager.removeFromBlacklistAndInstall(
				DEFAULT_ADAPTER_SYMBOLIC_NAME);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
		*/
	}

	/**
	 * Add default adapter to the blacklist.
	 * 
	 * @param bundleContext
	 * @throws IOException
	 */
	private void addDefaultAdapterToBlackList(BundleContext bundleContext)
		throws IOException {
		
		ServiceReference<BundleBlacklistManager> serviceReference =
			bundleContext.getServiceReference(BundleBlacklistManager.class);

		try {

			BundleBlacklistManager bundleBlacklistManager =
				bundleContext.getService(serviceReference);

			bundleBlacklistManager.addToBlacklistAndUninstall(
				DEFAULT_ADAPTER_API_SYMBOLIC_NAME, DEFAULT_ADAPTER_IMPL_SYMBOLIC_NAME);

		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	/**
	 * Disable default adapter.
	 * 
	 * @param bundleContext
	 */
	private void disableDefaultAdapter(BundleContext bundleContext)
		throws BundleException {

		for (Bundle bundle : bundleContext.getBundles()) {

			String symbolicName = bundle.getSymbolicName();

			if (symbolicName.equals(DEFAULT_ADAPTER_API_SYMBOLIC_NAME) ||
				symbolicName.equals(DEFAULT_ADAPTER_IMPL_SYMBOLIC_NAME)) {

				if (_log.isInfoEnabled()) {
					_log.info("Stopping the default adapter" + bundle);
				}
				bundle.stop();
				bundle.uninstall();
			}
			break;
		}
	}

	private static final String DEFAULT_ADAPTER_API_SYMBOLIC_NAME =
		"com.liferay.portal.search.elasticsearch6.api";

	private static final String DEFAULT_ADAPTER_IMPL_SYMBOLIC_NAME =
		"com.liferay.portal.search.elasticsearch6.impl";

	private static final Log _log =
		LogFactoryUtil.getLog(GSearchBundleActivator.class);

}