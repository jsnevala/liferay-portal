/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.shopping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.shopping.service.ShoppingOrderServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>ShoppingOrderServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.shopping.model.ShoppingOrderSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.shopping.model.ShoppingOrder</code>, that is translated to a
 * <code>com.liferay.shopping.model.ShoppingOrderSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderServiceHttp
 * @generated
 */
@ProviderType
public class ShoppingOrderServiceSoap {

	public static void completeOrder(
			long groupId, String number, String ppTxnId, String ppPaymentStatus,
			double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			ShoppingOrderServiceUtil.completeOrder(
				groupId, number, ppTxnId, ppPaymentStatus, ppPaymentGross,
				ppReceiverEmail, ppPayerEmail, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteOrder(long groupId, long orderId)
		throws RemoteException {

		try {
			ShoppingOrderServiceUtil.deleteOrder(groupId, orderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.shopping.model.ShoppingOrderSoap getOrder(
			long groupId, long orderId)
		throws RemoteException {

		try {
			com.liferay.shopping.model.ShoppingOrder returnValue =
				ShoppingOrderServiceUtil.getOrder(groupId, orderId);

			return com.liferay.shopping.model.ShoppingOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void sendEmail(
			long groupId, long orderId, String emailType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			ShoppingOrderServiceUtil.sendEmail(
				groupId, orderId, emailType, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.shopping.model.ShoppingOrderSoap updateOrder(
			long groupId, long orderId, String ppTxnId, String ppPaymentStatus,
			double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail)
		throws RemoteException {

		try {
			com.liferay.shopping.model.ShoppingOrder returnValue =
				ShoppingOrderServiceUtil.updateOrder(
					groupId, orderId, ppTxnId, ppPaymentStatus, ppPaymentGross,
					ppReceiverEmail, ppPayerEmail);

			return com.liferay.shopping.model.ShoppingOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.shopping.model.ShoppingOrderSoap updateOrder(
			long groupId, long orderId, String billingFirstName,
			String billingLastName, String billingEmailAddress,
			String billingCompany, String billingStreet, String billingCity,
			String billingState, String billingZip, String billingCountry,
			String billingPhone, boolean shipToBilling,
			String shippingFirstName, String shippingLastName,
			String shippingEmailAddress, String shippingCompany,
			String shippingStreet, String shippingCity, String shippingState,
			String shippingZip, String shippingCountry, String shippingPhone,
			String ccName, String ccType, String ccNumber, int ccExpMonth,
			int ccExpYear, String ccVerNumber, String comments)
		throws RemoteException {

		try {
			com.liferay.shopping.model.ShoppingOrder returnValue =
				ShoppingOrderServiceUtil.updateOrder(
					groupId, orderId, billingFirstName, billingLastName,
					billingEmailAddress, billingCompany, billingStreet,
					billingCity, billingState, billingZip, billingCountry,
					billingPhone, shipToBilling, shippingFirstName,
					shippingLastName, shippingEmailAddress, shippingCompany,
					shippingStreet, shippingCity, shippingState, shippingZip,
					shippingCountry, shippingPhone, ccName, ccType, ccNumber,
					ccExpMonth, ccExpYear, ccVerNumber, comments);

			return com.liferay.shopping.model.ShoppingOrderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ShoppingOrderServiceSoap.class);

}