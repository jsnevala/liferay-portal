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

package com.liferay.change.tracking.internal.reference.portal;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceInfoDefiner;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.model.UserGroupGroupRoleTable;
import com.liferay.portal.kernel.model.UserGroupTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRolePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class UserGroupGroupRoleTableReferenceDefinition
	implements TableReferenceDefinition<UserGroupGroupRoleTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoDefiner<UserGroupGroupRoleTable>
			tableReferenceInfoDefiner) {

		tableReferenceInfoDefiner.defineSingleColumnReference(
			UserGroupGroupRoleTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId);

		tableReferenceInfoDefiner.defineSingleColumnReference(
			UserGroupGroupRoleTable.INSTANCE.userGroupId,
			UserGroupTable.INSTANCE.userGroupId);

		tableReferenceInfoDefiner.defineSingleColumnReference(
			UserGroupGroupRoleTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId);

		tableReferenceInfoDefiner.defineSingleColumnReference(
			UserGroupGroupRoleTable.INSTANCE.roleId, RoleTable.INSTANCE.roleId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _userGroupGroupRolePersistence;
	}

	@Override
	public UserGroupGroupRoleTable getTable() {
		return UserGroupGroupRoleTable.INSTANCE;
	}

	@Reference
	private UserGroupGroupRolePersistence _userGroupGroupRolePersistence;

}