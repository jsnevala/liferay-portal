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

package com.liferay.portal.background.task.internal.service.permission;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.background.task.model.BackgroundTask",
	service = BaseModelPermissionChecker.class
)
public class BackgroundTaskPermission implements BaseModelPermissionChecker {

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(primaryKey);

		String taskExecutorClassName =
			backgroundTask.getTaskExecutorClassName();

		BaseModelPermissionChecker modelPermissionChecker =
			_serviceTrackerMap.getService(taskExecutorClassName);

		if (modelPermissionChecker == null) {
			return;
		}

		modelPermissionChecker.checkBaseModel(
			permissionChecker, groupId, primaryKey, actionId);
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.singleValueMap(
			bundleContext, BaseModelPermissionChecker.class,
			"background.task.executor.class.name");

		_serviceTrackerMap.open();
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	private ServiceTrackerMap<String, BaseModelPermissionChecker>
		_serviceTrackerMap;

}