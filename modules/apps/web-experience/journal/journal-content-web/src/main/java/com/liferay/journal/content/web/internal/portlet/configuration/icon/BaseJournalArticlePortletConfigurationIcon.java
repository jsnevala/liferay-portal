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

package com.liferay.journal.content.web.internal.portlet.configuration.icon;

import com.liferay.journal.content.web.configuration.JournalContentConfigurationUtil;
import com.liferay.journal.content.web.internal.display.context.JournalContentDisplayContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author     Daniel Couso
 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
 */
@Deprecated
public abstract class BaseJournalArticlePortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	public abstract double getSeparateMenusWeight();

	public abstract double getSingleMenuApplicationWeight();

	public abstract double getSingleMenuContentWeight();

	@Override
	public double getWeight() {
		if (journalContentConfigurationUtil.isSingleMenuApplication()) {
			return getSingleMenuApplicationWeight();
		}
		else if (journalContentConfigurationUtil.isSingleMenuContent()) {
			return getSingleMenuContentWeight();
		}
		else {
			return getSeparateMenusWeight();
		}
	}

	public boolean isShow(PortletRequest portletRequest) {
		if (journalContentConfigurationUtil.isSeparateMenus()) {
			return false;
		}

		JournalContentDisplayContext journalContentDisplayContext =
			getJournalContentDisplayContext(
				portletRequest, null, ddmStructureClassNameId);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		JournalArticle article = journalContentDisplayContext.getArticle();

		if (article == null) {
			return false;
		}

		try {
			if (journalContentDisplayContext.isShowEditArticleIcon() ||
				journalContentDisplayContext.isShowEditTemplateIcon() ||
				JournalArticlePermission.contains(
					permissionChecker, article, ActionKeys.PERMISSIONS)) {

				return true;
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}

		return false;
	}

	protected JournalContentDisplayContext getJournalContentDisplayContext(
		PortletRequest portletRequest, PortletResponse portletResponse,
		long ddmStructureClassNameId) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			return JournalContentDisplayContext.create(
				portletRequest, portletResponse, portletDisplay,
				ddmStructureClassNameId);
		}
		catch (PortalException pe) {
			_log.error("Unable to create display context", pe);
		}

		return null;
	}

	protected long ddmStructureClassNameId;
	protected JournalContentConfigurationUtil journalContentConfigurationUtil;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJournalArticlePortletConfigurationIcon.class);

}