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

package com.liferay.dynamic.data.lists.form.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.dynamic.data.lists.exportimport.staged.model.repository.DDLRecordStagedModelRepository;
import com.liferay.dynamic.data.lists.form.web.internal.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.capability.ReferencedStagedModelImporterCapability;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM,
	service = ExportImportPortletPreferencesProcessor.class
)
public class DDLFormExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return null;
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.toList(
			new Capability[] {_referencedStagedModelImporterCapability});
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.addPortletPermissions(
				DDLPermission.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to export portlet permissions", pe);
		}

		String portletId = portletDataContext.getPortletId();

		final long recordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null));

		if (recordSetId == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Record set ID is not set for preferences of portlet " +
						portletId);
			}

			return portletPreferences;
		}

		long recordSetGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", StringPool.BLANK));

		if (recordSetGroupId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No group ID found in preferences of portlet " + portletId);
			}

			return portletPreferences;
		}

		Group group = _groupLocalService.fetchGroup(recordSetGroupId);

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!group.isStagedPortlet(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Record set is not staged in the site " + group.getName());
			}

			return portletPreferences;
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		if (recordSetGroupId != previousScopeGroupId) {
			portletDataContext.setScopeGroupId(recordSetGroupId);
		}

		DDLRecordSet recordSet = _ddlRecordSetLocalService.fetchRecordSet(
			recordSetId);

		if (recordSet != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, recordSet);

			ActionableDynamicQuery recordActionableDynamicQuery =
				_ddlRecordStagedModelRepository.getExportActionableDynamicQuery(
					portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);

			final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
				recordActionableDynamicQuery.getAddCriteriaMethod();

			recordActionableDynamicQuery.setAddCriteriaMethod(
				new ActionableDynamicQuery.AddCriteriaMethod() {

					@Override
					public void addCriteria(DynamicQuery dynamicQuery) {
						addCriteriaMethod.addCriteria(dynamicQuery);

						Property property = PropertyFactoryUtil.forName(
							"recordSetId");

						dynamicQuery.add(property.eq(recordSetId));
					}

				});

			try {
				recordActionableDynamicQuery.performActions();
			}
			catch (PortalException pe) {
				throw new PortletDataException(
					"Unable to export referenced records", pe);
			}
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.importPortletPermissions(
				DDLPermission.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to export portlet permissions", pe);
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long importGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", null));

		long groupId = MapUtil.getLong(groupIds, importGroupId, importGroupId);

		long importedRecordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null));

		Map<String, Long> recordSetGroupIds =
			(Map<String, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class + ".groupId");

		if (recordSetGroupIds.containsKey(importedRecordSetId)) {
			groupId = recordSetGroupIds.get(importedRecordSetId);
		}

		portletDataContext.setScopeGroupId(groupId);

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		long recordSetId = MapUtil.getLong(
			recordSetIds, importedRecordSetId, importedRecordSetId);

		try {
			portletPreferences.setValue("groupId", String.valueOf(groupId));

			portletPreferences.setValue(
				"recordSetId", String.valueOf(recordSetId));
		}
		catch (ReadOnlyException roe) {
			throw new PortletDataException(
				"Unable to update portlet preferences during import", roe);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord)",
		unbind = "-"
	)
	protected void setDDLRecordStagedModelRepository(
		DDLRecordStagedModelRepository ddlRecordStagedModelRepository) {

		_ddlRecordStagedModelRepository = ddlRecordStagedModelRepository;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormExportImportPortletPreferencesProcessor.class);

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	private DDLRecordStagedModelRepository _ddlRecordStagedModelRepository;

	@Reference(unbind = "-")
	private GroupLocalService _groupLocalService;

	@Reference
	private ReferencedStagedModelImporterCapability
		_referencedStagedModelImporterCapability;

}