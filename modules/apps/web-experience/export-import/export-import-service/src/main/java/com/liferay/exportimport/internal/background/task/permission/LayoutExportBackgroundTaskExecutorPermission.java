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

package com.liferay.exportimport.internal.background.task.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"background.task.executor.class.name=com.liferay.exportimport.background.task.LayoutExportBackgroundTaskExecutor",
		"model.class.name=com.liferay.portal.kernel.backgroundtask.BackgroundTask"
	},
	service = BaseModelPermissionChecker.class
)
public class LayoutExportBackgroundTaskExecutorPermission
	implements BaseModelPermissionChecker {

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		if (!_groupPermission.contains(
				permissionChecker, groupId,
				ActionKeys.EXPORT_IMPORT_PORTLET_INFO)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), ActionKeys.VIEW);
		}
	}

	@Reference
	private GroupPermission _groupPermission;

}