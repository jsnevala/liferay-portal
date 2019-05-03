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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.content.web.configuration.JournalContentConfigurationUtil;
import com.liferay.journal.content.web.internal.display.context.JournalContentDisplayContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Daniel Couso
 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
		"path=-"
	},
	service = PortletConfigurationIcon.class
)
@Deprecated
public class EditJournalArticlePortletConfigurationIcon
	extends BaseJournalArticlePortletConfigurationIcon {

	@Override
	public String getJspPath() {
		return "/configuration/icon/edit_article.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getLocale(portletRequest), getClass());

		return LanguageUtil.get(resourceBundle, "edit-web-content");
	}

	public double getSeparateMenusWeight() {
		return 0.5;
	}

	public double getSingleMenuApplicationWeight() {
		return 0.5;
	}

	public double getSingleMenuContentWeight() {
		return 20;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.hasStagingGroup() && _STAGING_LIVE_GROUP_LOCKING_ENABLED) {
			return false;
		}

		if (journalContentConfigurationUtil.isSeparateMenus()) {
			return false;
		}

		JournalContentDisplayContext journalContentDisplayContext =
			getJournalContentDisplayContext(
				portletRequest, null, ddmStructureClassNameId);

		if (journalContentDisplayContext.isShowEditArticleIcon()) {
			return true;
		}

		return false;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.content.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference(unbind = "-")
	protected void setJournalContentConfigurationUtil(
		JournalContentConfigurationUtil journalContentConfigurationUtil) {

		this.journalContentConfigurationUtil = journalContentConfigurationUtil;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		ddmStructureClassNameId = portal.getClassNameId(DDMStructure.class);
	}

	private static final boolean _STAGING_LIVE_GROUP_LOCKING_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.STAGING_LIVE_GROUP_LOCKING_ENABLED));

}