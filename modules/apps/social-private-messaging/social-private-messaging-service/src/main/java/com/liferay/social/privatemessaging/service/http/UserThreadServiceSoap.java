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

package com.liferay.social.privatemessaging.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.social.privatemessaging.service.UserThreadServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>UserThreadServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.social.privatemessaging.model.UserThreadSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.social.privatemessaging.model.UserThread</code>, that is translated to a
 * <code>com.liferay.social.privatemessaging.model.UserThreadSoap</code>. Methods that SOAP
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
 * @see UserThreadServiceHttp
 * @generated
 */
@ProviderType
public class UserThreadServiceSoap {

	public static com.liferay.message.boards.kernel.model.MBMessage
			getLastThreadMessage(long mbThreadId)
		throws RemoteException {

		try {
			com.liferay.message.boards.kernel.model.MBMessage returnValue =
				UserThreadServiceUtil.getLastThreadMessage(mbThreadId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.message.boards.kernel.model.MBMessageSoap[]
			getThreadMessages(
				long mbThreadId, int start, int end, boolean ascending)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.kernel.model.MBMessage>
				returnValue = UserThreadServiceUtil.getThreadMessages(
					mbThreadId, start, end, ascending);

			return com.liferay.message.boards.kernel.model.MBMessageSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getThreadMessagesCount(long mbThreadId)
		throws RemoteException {

		try {
			int returnValue = UserThreadServiceUtil.getThreadMessagesCount(
				mbThreadId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.social.privatemessaging.model.UserThreadSoap[]
			getUserUserThreads(boolean deleted)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.privatemessaging.model.UserThread>
				returnValue = UserThreadServiceUtil.getUserUserThreads(deleted);

			return com.liferay.social.privatemessaging.model.UserThreadSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		UserThreadServiceSoap.class);

}