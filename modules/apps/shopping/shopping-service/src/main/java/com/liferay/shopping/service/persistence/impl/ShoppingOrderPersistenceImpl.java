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

package com.liferay.shopping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.shopping.exception.NoSuchOrderException;
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.shopping.model.impl.ShoppingOrderModelImpl;
import com.liferay.shopping.service.persistence.ShoppingOrderPersistence;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the shopping order service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ShoppingOrderPersistenceImpl
	extends BasePersistenceImpl<ShoppingOrder>
	implements ShoppingOrderPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ShoppingOrderUtil</code> to access the shopping order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ShoppingOrderImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the shopping orders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching shopping orders
	 */
	@Override
	public List<ShoppingOrder> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of matching shopping orders
	 */
	@Override
	public List<ShoppingOrder> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping orders
	 */
	@Override
	public List<ShoppingOrder> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping orders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping orders
	 */
	@Override
	public List<ShoppingOrder> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId;
			finderArgs = new Object[] {groupId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<ShoppingOrder> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingOrder>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingOrder shoppingOrder : list) {
					if ((groupId != shoppingOrder.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<ShoppingOrder>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingOrder>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping order
	 * @throws NoSuchOrderException if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder findByGroupId_First(
			long groupId, OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = fetchByGroupId_First(
			groupId, orderByComparator);

		if (shoppingOrder != null) {
			return shoppingOrder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchOrderException(msg.toString());
	}

	/**
	 * Returns the first shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder fetchByGroupId_First(
		long groupId, OrderByComparator<ShoppingOrder> orderByComparator) {

		List<ShoppingOrder> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping order
	 * @throws NoSuchOrderException if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder findByGroupId_Last(
			long groupId, OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (shoppingOrder != null) {
			return shoppingOrder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchOrderException(msg.toString());
	}

	/**
	 * Returns the last shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder fetchByGroupId_Last(
		long groupId, OrderByComparator<ShoppingOrder> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ShoppingOrder> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param orderId the primary key of the current shopping order
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping order
	 * @throws NoSuchOrderException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder[] findByGroupId_PrevAndNext(
			long orderId, long groupId,
			OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, shoppingOrder, groupId, orderByComparator, true);

			array[1] = shoppingOrder;

			array[2] = getByGroupId_PrevAndNext(
				session, shoppingOrder, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingOrder getByGroupId_PrevAndNext(
		Session session, ShoppingOrder shoppingOrder, long groupId,
		OrderByComparator<ShoppingOrder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						shoppingOrder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ShoppingOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping orders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching shopping orders that the user has permission to view
	 */
	@Override
	public List<ShoppingOrder> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of matching shopping orders that the user has permission to view
	 */
	@Override
	public List<ShoppingOrder> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping orders that the user has permission to view
	 */
	@Override
	public List<ShoppingOrder> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ShoppingOrder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingOrderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ShoppingOrderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<ShoppingOrder>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63;.
	 *
	 * @param orderId the primary key of the current shopping order
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping order
	 * @throws NoSuchOrderException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder[] filterFindByGroupId_PrevAndNext(
			long orderId, long groupId,
			OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				orderId, groupId, orderByComparator);
		}

		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, shoppingOrder, groupId, orderByComparator, true);

			array[1] = shoppingOrder;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, shoppingOrder, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingOrder filterGetByGroupId_PrevAndNext(
		Session session, ShoppingOrder shoppingOrder, long groupId,
		OrderByComparator<ShoppingOrder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ShoppingOrder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingOrderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ShoppingOrderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						shoppingOrder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ShoppingOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping orders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ShoppingOrder shoppingOrder :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(shoppingOrder);
		}
	}

	/**
	 * Returns the number of shopping orders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching shopping orders
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping orders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching shopping orders that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SHOPPINGORDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ShoppingOrder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"shoppingOrder.groupId = ?";

	private FinderPath _finderPathFetchByNumber;
	private FinderPath _finderPathCountByNumber;

	/**
	 * Returns the shopping order where number = &#63; or throws a <code>NoSuchOrderException</code> if it could not be found.
	 *
	 * @param number the number
	 * @return the matching shopping order
	 * @throws NoSuchOrderException if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder findByNumber(String number)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = fetchByNumber(number);

		if (shoppingOrder == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("number=");
			msg.append(number);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOrderException(msg.toString());
		}

		return shoppingOrder;
	}

	/**
	 * Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param number the number
	 * @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder fetchByNumber(String number) {
		return fetchByNumber(number, true);
	}

	/**
	 * Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param number the number
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder fetchByNumber(
		String number, boolean retrieveFromCache) {

		number = Objects.toString(number, "");

		Object[] finderArgs = new Object[] {number};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByNumber, finderArgs, this);
		}

		if (result instanceof ShoppingOrder) {
			ShoppingOrder shoppingOrder = (ShoppingOrder)result;

			if (!Objects.equals(number, shoppingOrder.getNumber())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			boolean bindNumber = false;

			if (number.isEmpty()) {
				query.append(_FINDER_COLUMN_NUMBER_NUMBER_3);
			}
			else {
				bindNumber = true;

				query.append(_FINDER_COLUMN_NUMBER_NUMBER_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindNumber) {
					qPos.add(number);
				}

				List<ShoppingOrder> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByNumber, finderArgs, list);
				}
				else {
					ShoppingOrder shoppingOrder = list.get(0);

					result = shoppingOrder;

					cacheResult(shoppingOrder);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByNumber, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ShoppingOrder)result;
		}
	}

	/**
	 * Removes the shopping order where number = &#63; from the database.
	 *
	 * @param number the number
	 * @return the shopping order that was removed
	 */
	@Override
	public ShoppingOrder removeByNumber(String number)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = findByNumber(number);

		return remove(shoppingOrder);
	}

	/**
	 * Returns the number of shopping orders where number = &#63;.
	 *
	 * @param number the number
	 * @return the number of matching shopping orders
	 */
	@Override
	public int countByNumber(String number) {
		number = Objects.toString(number, "");

		FinderPath finderPath = _finderPathCountByNumber;

		Object[] finderArgs = new Object[] {number};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

			boolean bindNumber = false;

			if (number.isEmpty()) {
				query.append(_FINDER_COLUMN_NUMBER_NUMBER_3);
			}
			else {
				bindNumber = true;

				query.append(_FINDER_COLUMN_NUMBER_NUMBER_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindNumber) {
					qPos.add(number);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_NUMBER_NUMBER_2 =
		"shoppingOrder.number = ?";

	private static final String _FINDER_COLUMN_NUMBER_NUMBER_3 =
		"(shoppingOrder.number IS NULL OR shoppingOrder.number = '')";

	private FinderPath _finderPathFetchByPPTxnId;
	private FinderPath _finderPathCountByPPTxnId;

	/**
	 * Returns the shopping order where ppTxnId = &#63; or throws a <code>NoSuchOrderException</code> if it could not be found.
	 *
	 * @param ppTxnId the pp txn ID
	 * @return the matching shopping order
	 * @throws NoSuchOrderException if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder findByPPTxnId(String ppTxnId)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = fetchByPPTxnId(ppTxnId);

		if (shoppingOrder == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ppTxnId=");
			msg.append(ppTxnId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOrderException(msg.toString());
		}

		return shoppingOrder;
	}

	/**
	 * Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ppTxnId the pp txn ID
	 * @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder fetchByPPTxnId(String ppTxnId) {
		return fetchByPPTxnId(ppTxnId, true);
	}

	/**
	 * Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ppTxnId the pp txn ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder fetchByPPTxnId(
		String ppTxnId, boolean retrieveFromCache) {

		ppTxnId = Objects.toString(ppTxnId, "");

		Object[] finderArgs = new Object[] {ppTxnId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByPPTxnId, finderArgs, this);
		}

		if (result instanceof ShoppingOrder) {
			ShoppingOrder shoppingOrder = (ShoppingOrder)result;

			if (!Objects.equals(ppTxnId, shoppingOrder.getPpTxnId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			boolean bindPpTxnId = false;

			if (ppTxnId.isEmpty()) {
				query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_3);
			}
			else {
				bindPpTxnId = true;

				query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindPpTxnId) {
					qPos.add(ppTxnId);
				}

				List<ShoppingOrder> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByPPTxnId, finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"ShoppingOrderPersistenceImpl.fetchByPPTxnId(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ShoppingOrder shoppingOrder = list.get(0);

					result = shoppingOrder;

					cacheResult(shoppingOrder);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByPPTxnId, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ShoppingOrder)result;
		}
	}

	/**
	 * Removes the shopping order where ppTxnId = &#63; from the database.
	 *
	 * @param ppTxnId the pp txn ID
	 * @return the shopping order that was removed
	 */
	@Override
	public ShoppingOrder removeByPPTxnId(String ppTxnId)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = findByPPTxnId(ppTxnId);

		return remove(shoppingOrder);
	}

	/**
	 * Returns the number of shopping orders where ppTxnId = &#63;.
	 *
	 * @param ppTxnId the pp txn ID
	 * @return the number of matching shopping orders
	 */
	@Override
	public int countByPPTxnId(String ppTxnId) {
		ppTxnId = Objects.toString(ppTxnId, "");

		FinderPath finderPath = _finderPathCountByPPTxnId;

		Object[] finderArgs = new Object[] {ppTxnId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

			boolean bindPpTxnId = false;

			if (ppTxnId.isEmpty()) {
				query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_3);
			}
			else {
				bindPpTxnId = true;

				query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindPpTxnId) {
					qPos.add(ppTxnId);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PPTXNID_PPTXNID_2 =
		"shoppingOrder.ppTxnId = ?";

	private static final String _FINDER_COLUMN_PPTXNID_PPTXNID_3 =
		"(shoppingOrder.ppTxnId IS NULL OR shoppingOrder.ppTxnId = '')";

	private FinderPath _finderPathWithPaginationFindByG_U_PPPS;
	private FinderPath _finderPathWithoutPaginationFindByG_U_PPPS;
	private FinderPath _finderPathCountByG_U_PPPS;

	/**
	 * Returns all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @return the matching shopping orders
	 */
	@Override
	public List<ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus) {

		return findByG_U_PPPS(
			groupId, userId, ppPaymentStatus, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of matching shopping orders
	 */
	@Override
	public List<ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus, int start, int end) {

		return findByG_U_PPPS(
			groupId, userId, ppPaymentStatus, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping orders
	 */
	@Override
	public List<ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {

		return findByG_U_PPPS(
			groupId, userId, ppPaymentStatus, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping orders
	 */
	@Override
	public List<ShoppingOrder> findByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache) {

		ppPaymentStatus = Objects.toString(ppPaymentStatus, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_U_PPPS;
			finderArgs = new Object[] {groupId, userId, ppPaymentStatus};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_U_PPPS;
			finderArgs = new Object[] {
				groupId, userId, ppPaymentStatus, start, end, orderByComparator
			};
		}

		List<ShoppingOrder> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingOrder>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingOrder shoppingOrder : list) {
					if ((groupId != shoppingOrder.getGroupId()) ||
						(userId != shoppingOrder.getUserId()) ||
						!ppPaymentStatus.equals(
							shoppingOrder.getPpPaymentStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

			boolean bindPpPaymentStatus = false;

			if (ppPaymentStatus.isEmpty()) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
			}
			else {
				bindPpPaymentStatus = true;

				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindPpPaymentStatus) {
					qPos.add(ppPaymentStatus);
				}

				if (!pagination) {
					list = (List<ShoppingOrder>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingOrder>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping order
	 * @throws NoSuchOrderException if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder findByG_U_PPPS_First(
			long groupId, long userId, String ppPaymentStatus,
			OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = fetchByG_U_PPPS_First(
			groupId, userId, ppPaymentStatus, orderByComparator);

		if (shoppingOrder != null) {
			return shoppingOrder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", ppPaymentStatus=");
		msg.append(ppPaymentStatus);

		msg.append("}");

		throw new NoSuchOrderException(msg.toString());
	}

	/**
	 * Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder fetchByG_U_PPPS_First(
		long groupId, long userId, String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator) {

		List<ShoppingOrder> list = findByG_U_PPPS(
			groupId, userId, ppPaymentStatus, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping order
	 * @throws NoSuchOrderException if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder findByG_U_PPPS_Last(
			long groupId, long userId, String ppPaymentStatus,
			OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = fetchByG_U_PPPS_Last(
			groupId, userId, ppPaymentStatus, orderByComparator);

		if (shoppingOrder != null) {
			return shoppingOrder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", ppPaymentStatus=");
		msg.append(ppPaymentStatus);

		msg.append("}");

		throw new NoSuchOrderException(msg.toString());
	}

	/**
	 * Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 */
	@Override
	public ShoppingOrder fetchByG_U_PPPS_Last(
		long groupId, long userId, String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator) {

		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);

		if (count == 0) {
			return null;
		}

		List<ShoppingOrder> list = findByG_U_PPPS(
			groupId, userId, ppPaymentStatus, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param orderId the primary key of the current shopping order
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping order
	 * @throws NoSuchOrderException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder[] findByG_U_PPPS_PrevAndNext(
			long orderId, long groupId, long userId, String ppPaymentStatus,
			OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException {

		ppPaymentStatus = Objects.toString(ppPaymentStatus, "");

		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = getByG_U_PPPS_PrevAndNext(
				session, shoppingOrder, groupId, userId, ppPaymentStatus,
				orderByComparator, true);

			array[1] = shoppingOrder;

			array[2] = getByG_U_PPPS_PrevAndNext(
				session, shoppingOrder, groupId, userId, ppPaymentStatus,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingOrder getByG_U_PPPS_PrevAndNext(
		Session session, ShoppingOrder shoppingOrder, long groupId, long userId,
		String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

		query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

		boolean bindPpPaymentStatus = false;

		if (ppPaymentStatus.isEmpty()) {
			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
		}
		else {
			bindPpPaymentStatus = true;

			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindPpPaymentStatus) {
			qPos.add(ppPaymentStatus);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						shoppingOrder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ShoppingOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @return the matching shopping orders that the user has permission to view
	 */
	@Override
	public List<ShoppingOrder> filterFindByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus) {

		return filterFindByG_U_PPPS(
			groupId, userId, ppPaymentStatus, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of matching shopping orders that the user has permission to view
	 */
	@Override
	public List<ShoppingOrder> filterFindByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus, int start, int end) {

		return filterFindByG_U_PPPS(
			groupId, userId, ppPaymentStatus, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders that the user has permissions to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping orders that the user has permission to view
	 */
	@Override
	public List<ShoppingOrder> filterFindByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus, int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PPPS(
				groupId, userId, ppPaymentStatus, start, end,
				orderByComparator);
		}

		ppPaymentStatus = Objects.toString(ppPaymentStatus, "");

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

		boolean bindPpPaymentStatus = false;

		if (ppPaymentStatus.isEmpty()) {
			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
		}
		else {
			bindPpPaymentStatus = true;

			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ShoppingOrder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingOrderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ShoppingOrderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindPpPaymentStatus) {
				qPos.add(ppPaymentStatus);
			}

			return (List<ShoppingOrder>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param orderId the primary key of the current shopping order
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping order
	 * @throws NoSuchOrderException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder[] filterFindByG_U_PPPS_PrevAndNext(
			long orderId, long groupId, long userId, String ppPaymentStatus,
			OrderByComparator<ShoppingOrder> orderByComparator)
		throws NoSuchOrderException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PPPS_PrevAndNext(
				orderId, groupId, userId, ppPaymentStatus, orderByComparator);
		}

		ppPaymentStatus = Objects.toString(ppPaymentStatus, "");

		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = filterGetByG_U_PPPS_PrevAndNext(
				session, shoppingOrder, groupId, userId, ppPaymentStatus,
				orderByComparator, true);

			array[1] = shoppingOrder;

			array[2] = filterGetByG_U_PPPS_PrevAndNext(
				session, shoppingOrder, groupId, userId, ppPaymentStatus,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingOrder filterGetByG_U_PPPS_PrevAndNext(
		Session session, ShoppingOrder shoppingOrder, long groupId, long userId,
		String ppPaymentStatus,
		OrderByComparator<ShoppingOrder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

		boolean bindPpPaymentStatus = false;

		if (ppPaymentStatus.isEmpty()) {
			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
		}
		else {
			bindPpPaymentStatus = true;

			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ShoppingOrder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingOrderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ShoppingOrderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindPpPaymentStatus) {
			qPos.add(ppPaymentStatus);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						shoppingOrder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ShoppingOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 */
	@Override
	public void removeByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus) {

		for (ShoppingOrder shoppingOrder :
				findByG_U_PPPS(
					groupId, userId, ppPaymentStatus, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(shoppingOrder);
		}
	}

	/**
	 * Returns the number of shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @return the number of matching shopping orders
	 */
	@Override
	public int countByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus) {

		ppPaymentStatus = Objects.toString(ppPaymentStatus, "");

		FinderPath finderPath = _finderPathCountByG_U_PPPS;

		Object[] finderArgs = new Object[] {groupId, userId, ppPaymentStatus};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

			boolean bindPpPaymentStatus = false;

			if (ppPaymentStatus.isEmpty()) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
			}
			else {
				bindPpPaymentStatus = true;

				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindPpPaymentStatus) {
					qPos.add(ppPaymentStatus);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @return the number of matching shopping orders that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_PPPS(
		long groupId, long userId, String ppPaymentStatus) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_PPPS(groupId, userId, ppPaymentStatus);
		}

		ppPaymentStatus = Objects.toString(ppPaymentStatus, "");

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_SHOPPINGORDER_WHERE);

		query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

		boolean bindPpPaymentStatus = false;

		if (ppPaymentStatus.isEmpty()) {
			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
		}
		else {
			bindPpPaymentStatus = true;

			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ShoppingOrder.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindPpPaymentStatus) {
				qPos.add(ppPaymentStatus);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_PPPS_GROUPID_2 =
		"shoppingOrder.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_PPPS_USERID_2 =
		"shoppingOrder.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2 =
		"shoppingOrder.ppPaymentStatus = ?";

	private static final String _FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3 =
		"(shoppingOrder.ppPaymentStatus IS NULL OR shoppingOrder.ppPaymentStatus = '')";

	public ShoppingOrderPersistenceImpl() {
		setModelClass(ShoppingOrder.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("number", "number_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the shopping order in the entity cache if it is enabled.
	 *
	 * @param shoppingOrder the shopping order
	 */
	@Override
	public void cacheResult(ShoppingOrder shoppingOrder) {
		entityCache.putResult(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey(),
			shoppingOrder);

		finderCache.putResult(
			_finderPathFetchByNumber, new Object[] {shoppingOrder.getNumber()},
			shoppingOrder);

		finderCache.putResult(
			_finderPathFetchByPPTxnId,
			new Object[] {shoppingOrder.getPpTxnId()}, shoppingOrder);

		shoppingOrder.resetOriginalValues();
	}

	/**
	 * Caches the shopping orders in the entity cache if it is enabled.
	 *
	 * @param shoppingOrders the shopping orders
	 */
	@Override
	public void cacheResult(List<ShoppingOrder> shoppingOrders) {
		for (ShoppingOrder shoppingOrder : shoppingOrders) {
			if (entityCache.getResult(
					ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey()) ==
						null) {

				cacheResult(shoppingOrder);
			}
			else {
				shoppingOrder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shopping orders.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ShoppingOrderImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping order.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingOrder shoppingOrder) {
		entityCache.removeResult(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ShoppingOrderModelImpl)shoppingOrder, true);
	}

	@Override
	public void clearCache(List<ShoppingOrder> shoppingOrders) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingOrder shoppingOrder : shoppingOrders) {
			entityCache.removeResult(
				ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey());

			clearUniqueFindersCache(
				(ShoppingOrderModelImpl)shoppingOrder, true);
		}
	}

	protected void cacheUniqueFindersCache(
		ShoppingOrderModelImpl shoppingOrderModelImpl) {

		Object[] args = new Object[] {shoppingOrderModelImpl.getNumber()};

		finderCache.putResult(
			_finderPathCountByNumber, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByNumber, args, shoppingOrderModelImpl, false);

		args = new Object[] {shoppingOrderModelImpl.getPpTxnId()};

		finderCache.putResult(
			_finderPathCountByPPTxnId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByPPTxnId, args, shoppingOrderModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ShoppingOrderModelImpl shoppingOrderModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {shoppingOrderModelImpl.getNumber()};

			finderCache.removeResult(_finderPathCountByNumber, args);
			finderCache.removeResult(_finderPathFetchByNumber, args);
		}

		if ((shoppingOrderModelImpl.getColumnBitmask() &
			 _finderPathFetchByNumber.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				shoppingOrderModelImpl.getOriginalNumber()
			};

			finderCache.removeResult(_finderPathCountByNumber, args);
			finderCache.removeResult(_finderPathFetchByNumber, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {shoppingOrderModelImpl.getPpTxnId()};

			finderCache.removeResult(_finderPathCountByPPTxnId, args);
			finderCache.removeResult(_finderPathFetchByPPTxnId, args);
		}

		if ((shoppingOrderModelImpl.getColumnBitmask() &
			 _finderPathFetchByPPTxnId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				shoppingOrderModelImpl.getOriginalPpTxnId()
			};

			finderCache.removeResult(_finderPathCountByPPTxnId, args);
			finderCache.removeResult(_finderPathFetchByPPTxnId, args);
		}
	}

	/**
	 * Creates a new shopping order with the primary key. Does not add the shopping order to the database.
	 *
	 * @param orderId the primary key for the new shopping order
	 * @return the new shopping order
	 */
	@Override
	public ShoppingOrder create(long orderId) {
		ShoppingOrder shoppingOrder = new ShoppingOrderImpl();

		shoppingOrder.setNew(true);
		shoppingOrder.setPrimaryKey(orderId);

		shoppingOrder.setCompanyId(companyProvider.getCompanyId());

		return shoppingOrder;
	}

	/**
	 * Removes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param orderId the primary key of the shopping order
	 * @return the shopping order that was removed
	 * @throws NoSuchOrderException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder remove(long orderId) throws NoSuchOrderException {
		return remove((Serializable)orderId);
	}

	/**
	 * Removes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping order
	 * @return the shopping order that was removed
	 * @throws NoSuchOrderException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder remove(Serializable primaryKey)
		throws NoSuchOrderException {

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder shoppingOrder = (ShoppingOrder)session.get(
				ShoppingOrderImpl.class, primaryKey);

			if (shoppingOrder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(shoppingOrder);
		}
		catch (NoSuchOrderException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ShoppingOrder removeImpl(ShoppingOrder shoppingOrder) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(shoppingOrder)) {
				shoppingOrder = (ShoppingOrder)session.get(
					ShoppingOrderImpl.class, shoppingOrder.getPrimaryKeyObj());
			}

			if (shoppingOrder != null) {
				session.delete(shoppingOrder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shoppingOrder != null) {
			clearCache(shoppingOrder);
		}

		return shoppingOrder;
	}

	@Override
	public ShoppingOrder updateImpl(ShoppingOrder shoppingOrder) {
		boolean isNew = shoppingOrder.isNew();

		if (!(shoppingOrder instanceof ShoppingOrderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(shoppingOrder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					shoppingOrder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in shoppingOrder proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ShoppingOrder implementation " +
					shoppingOrder.getClass());
		}

		ShoppingOrderModelImpl shoppingOrderModelImpl =
			(ShoppingOrderModelImpl)shoppingOrder;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (shoppingOrder.getCreateDate() == null)) {
			if (serviceContext == null) {
				shoppingOrder.setCreateDate(now);
			}
			else {
				shoppingOrder.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!shoppingOrderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				shoppingOrder.setModifiedDate(now);
			}
			else {
				shoppingOrder.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (shoppingOrder.isNew()) {
				session.save(shoppingOrder);

				shoppingOrder.setNew(false);
			}
			else {
				shoppingOrder = (ShoppingOrder)session.merge(shoppingOrder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ShoppingOrderModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {shoppingOrderModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				shoppingOrderModelImpl.getGroupId(),
				shoppingOrderModelImpl.getUserId(),
				shoppingOrderModelImpl.getPpPaymentStatus()
			};

			finderCache.removeResult(_finderPathCountByG_U_PPPS, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_U_PPPS, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((shoppingOrderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					shoppingOrderModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {shoppingOrderModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((shoppingOrderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_U_PPPS.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					shoppingOrderModelImpl.getOriginalGroupId(),
					shoppingOrderModelImpl.getOriginalUserId(),
					shoppingOrderModelImpl.getOriginalPpPaymentStatus()
				};

				finderCache.removeResult(_finderPathCountByG_U_PPPS, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_U_PPPS, args);

				args = new Object[] {
					shoppingOrderModelImpl.getGroupId(),
					shoppingOrderModelImpl.getUserId(),
					shoppingOrderModelImpl.getPpPaymentStatus()
				};

				finderCache.removeResult(_finderPathCountByG_U_PPPS, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_U_PPPS, args);
			}
		}

		entityCache.putResult(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey(),
			shoppingOrder, false);

		clearUniqueFindersCache(shoppingOrderModelImpl, false);
		cacheUniqueFindersCache(shoppingOrderModelImpl);

		shoppingOrder.resetOriginalValues();

		return shoppingOrder;
	}

	/**
	 * Returns the shopping order with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping order
	 * @return the shopping order
	 * @throws NoSuchOrderException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrderException {

		ShoppingOrder shoppingOrder = fetchByPrimaryKey(primaryKey);

		if (shoppingOrder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrderException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return shoppingOrder;
	}

	/**
	 * Returns the shopping order with the primary key or throws a <code>NoSuchOrderException</code> if it could not be found.
	 *
	 * @param orderId the primary key of the shopping order
	 * @return the shopping order
	 * @throws NoSuchOrderException if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder findByPrimaryKey(long orderId)
		throws NoSuchOrderException {

		return findByPrimaryKey((Serializable)orderId);
	}

	/**
	 * Returns the shopping order with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping order
	 * @return the shopping order, or <code>null</code> if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ShoppingOrder shoppingOrder = (ShoppingOrder)serializable;

		if (shoppingOrder == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingOrder = (ShoppingOrder)session.get(
					ShoppingOrderImpl.class, primaryKey);

				if (shoppingOrder != null) {
					cacheResult(shoppingOrder);
				}
				else {
					entityCache.putResult(
						ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingOrderImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(
					ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingOrderImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return shoppingOrder;
	}

	/**
	 * Returns the shopping order with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param orderId the primary key of the shopping order
	 * @return the shopping order, or <code>null</code> if a shopping order with the primary key could not be found
	 */
	@Override
	public ShoppingOrder fetchByPrimaryKey(long orderId) {
		return fetchByPrimaryKey((Serializable)orderId);
	}

	@Override
	public Map<Serializable, ShoppingOrder> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ShoppingOrder> map =
			new HashMap<Serializable, ShoppingOrder>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ShoppingOrder shoppingOrder = fetchByPrimaryKey(primaryKey);

			if (shoppingOrder != null) {
				map.put(primaryKey, shoppingOrder);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingOrderImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ShoppingOrder)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		query.append(_SQL_SELECT_SHOPPINGORDER_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ShoppingOrder shoppingOrder : (List<ShoppingOrder>)q.list()) {
				map.put(shoppingOrder.getPrimaryKeyObj(), shoppingOrder);

				cacheResult(shoppingOrder);

				uncachedPrimaryKeys.remove(shoppingOrder.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingOrderImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the shopping orders.
	 *
	 * @return the shopping orders
	 */
	@Override
	public List<ShoppingOrder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of shopping orders
	 */
	@Override
	public List<ShoppingOrder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping orders
	 */
	@Override
	public List<ShoppingOrder> findAll(
		int start, int end,
		OrderByComparator<ShoppingOrder> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ShoppingOrderModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of shopping orders
	 */
	@Override
	public List<ShoppingOrder> findAll(
		int start, int end, OrderByComparator<ShoppingOrder> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<ShoppingOrder> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingOrder>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SHOPPINGORDER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGORDER;

				if (pagination) {
					sql = sql.concat(ShoppingOrderModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ShoppingOrder>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingOrder>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the shopping orders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ShoppingOrder shoppingOrder : findAll()) {
			remove(shoppingOrder);
		}
	}

	/**
	 * Returns the number of shopping orders.
	 *
	 * @return the number of shopping orders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGORDER);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ShoppingOrderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the shopping order persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			ShoppingOrderModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingOrderModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathFetchByNumber = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByNumber",
			new String[] {String.class.getName()},
			ShoppingOrderModelImpl.NUMBER_COLUMN_BITMASK);

		_finderPathCountByNumber = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNumber",
			new String[] {String.class.getName()});

		_finderPathFetchByPPTxnId = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByPPTxnId",
			new String[] {String.class.getName()},
			ShoppingOrderModelImpl.PPTXNID_COLUMN_BITMASK);

		_finderPathCountByPPTxnId = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPPTxnId",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByG_U_PPPS = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_U_PPPS = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			ShoppingOrderModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingOrderModelImpl.USERID_COLUMN_BITMASK |
			ShoppingOrderModelImpl.PPPAYMENTSTATUS_COLUMN_BITMASK |
			ShoppingOrderModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByG_U_PPPS = new FinderPath(
			ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	}

	public void destroy() {
		entityCache.removeCache(ShoppingOrderImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SHOPPINGORDER =
		"SELECT shoppingOrder FROM ShoppingOrder shoppingOrder";

	private static final String _SQL_SELECT_SHOPPINGORDER_WHERE_PKS_IN =
		"SELECT shoppingOrder FROM ShoppingOrder shoppingOrder WHERE orderId IN (";

	private static final String _SQL_SELECT_SHOPPINGORDER_WHERE =
		"SELECT shoppingOrder FROM ShoppingOrder shoppingOrder WHERE ";

	private static final String _SQL_COUNT_SHOPPINGORDER =
		"SELECT COUNT(shoppingOrder) FROM ShoppingOrder shoppingOrder";

	private static final String _SQL_COUNT_SHOPPINGORDER_WHERE =
		"SELECT COUNT(shoppingOrder) FROM ShoppingOrder shoppingOrder WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"shoppingOrder.orderId";

	private static final String _FILTER_SQL_SELECT_SHOPPINGORDER_WHERE =
		"SELECT DISTINCT {shoppingOrder.*} FROM ShoppingOrder shoppingOrder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {ShoppingOrder.*} FROM (SELECT DISTINCT shoppingOrder.orderId FROM ShoppingOrder shoppingOrder WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN ShoppingOrder ON TEMP_TABLE.orderId = ShoppingOrder.orderId";

	private static final String _FILTER_SQL_COUNT_SHOPPINGORDER_WHERE =
		"SELECT COUNT(DISTINCT shoppingOrder.orderId) AS COUNT_VALUE FROM ShoppingOrder shoppingOrder WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "shoppingOrder";

	private static final String _FILTER_ENTITY_TABLE = "ShoppingOrder";

	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingOrder.";

	private static final String _ORDER_BY_ENTITY_TABLE = "ShoppingOrder.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ShoppingOrder exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ShoppingOrder exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ShoppingOrderPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"number"});

}