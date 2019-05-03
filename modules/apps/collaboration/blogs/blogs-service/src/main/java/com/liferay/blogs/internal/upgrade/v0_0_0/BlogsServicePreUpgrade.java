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

package com.liferay.blogs.internal.upgrade.v0_0_0;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(immediate = true)
public class BlogsServicePreUpgrade {

	@Activate
	protected void activate() throws PortalException {
		Release release = _releaseLocalService.fetchRelease(
			"com.liferay.blogs.service");

		if ((release != null) &&
			StringUtil.equals(release.getSchemaVersion(), "1.0.0")) {

			_releaseLocalService.deleteRelease(release.getReleaseId());
		}
	}

	@Reference(target = ModuleServiceLifecycle.DATABASE_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private ReleaseLocalService _releaseLocalService;

}