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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADEntity;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.util.SafeDisplayValueUtil;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/view_uad_entities"
	},
	service = MVCRenderCommand.class
)
public class ViewUADEntitiesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _selectedUserHelper.getSelectedUser(
				renderRequest);

			String applicationKey = ParamUtil.getString(
				renderRequest, "applicationKey");
			String uadRegistryKey = ParamUtil.getString(
				renderRequest, "uadRegistryKey");

			ViewUADEntitiesDisplay viewUADEntitiesDisplay =
				new ViewUADEntitiesDisplay();

			viewUADEntitiesDisplay.setApplicationKey(applicationKey);
			viewUADEntitiesDisplay.setApplicationUADDisplays(
				_uadRegistry.getApplicationUADDisplays(applicationKey));

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(renderResponse);

			PortletURL currentURL = PortletURLUtil.getCurrent(
				renderRequest, renderResponse);

			UADDisplay uadDisplay = _uadRegistry.getUADDisplay(uadRegistryKey);

			viewUADEntitiesDisplay.setSearchContainer(
				_getSearchContainer(
					renderRequest, currentURL, uadDisplay,
					selectedUser.getUserId(), liferayPortletResponse));
			viewUADEntitiesDisplay.setTypeName(
				uadDisplay.getTypeName(
					LocaleThreadLocal.getThemeDisplayLocale()));

			viewUADEntitiesDisplay.setUADRegistryKey(uadRegistryKey);

			renderRequest.setAttribute(
				UADWebKeys.INFO_PANEL_UAD_DISPLAY, uadDisplay);
			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY, viewUADEntitiesDisplay);
		}
		catch (Exception pe) {
			throw new PortletException(pe);
		}

		return "/view_uad_entities.jsp";
	}

	private <T> UADEntity<T> _constructUADEntity(
			T entity, UADDisplay<T> uadDisplay,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		UADEntity<T> uadEntity = new UADEntity(
			entity, uadDisplay.getPrimaryKey(entity),
			uadDisplay.getEditURL(
				entity, liferayPortletRequest, liferayPortletResponse));

		Map<String, Object> columnFieldValues = uadDisplay.getFieldValues(
			entity, uadDisplay.getColumnFieldNames());

		for (String columnFieldName : uadDisplay.getColumnFieldNames()) {
			uadEntity.addColumnEntry(
				columnFieldName,
				SafeDisplayValueUtil.get(
					columnFieldValues.get(columnFieldName)));
		}

		return uadEntity;
	}

	private SearchContainer<UADEntity> _getSearchContainer(
			RenderRequest renderRequest, PortletURL currentURL,
			UADDisplay uadDisplay, long selectedUserId,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			renderRequest, currentURL, null, null);

		searchContainer.setEmptyResultsMessage(
			"no-entities-remain-of-this-type");
		searchContainer.setId("UADEntities");

		List<Object> entities = uadDisplay.getRange(
			selectedUserId, searchContainer.getStart(),
			searchContainer.getEnd());

		List<UADEntity> uadEntities = new ArrayList<>();

		for (Object entity : entities) {
			uadEntities.add(
				_constructUADEntity(
					entity, uadDisplay, liferayPortletRequest,
					liferayPortletResponse));
		}

		searchContainer.setResults(uadEntities);

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			liferayPortletResponse);

		Class<?> uadClass = uadDisplay.getTypeClass();

		rowChecker.setRememberCheckBoxStateURLRegex(
			"uadRegistryKey=" + uadClass.getName());

		searchContainer.setRowChecker(rowChecker);

		searchContainer.setTotal((int)uadDisplay.count(selectedUserId));

		return searchContainer;
	}

	@Reference
	private Portal _portal;

	@Reference
	private SelectedUserHelper _selectedUserHelper;

	@Reference
	private UADRegistry _uadRegistry;

}