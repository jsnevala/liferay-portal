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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ShoppingCoupon. This utility wraps
 * <code>com.liferay.shopping.service.impl.ShoppingCouponLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponLocalService
 * @generated
 */
@ProviderType
public class ShoppingCouponLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.shopping.service.impl.ShoppingCouponLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.shopping.model.ShoppingCoupon addCoupon(
			long userId, String code, boolean autoCode, String name,
			String description, int startDateMonth, int startDateDay,
			int startDateYear, int startDateHour, int startDateMinute,
			int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
			int endDateMinute, boolean neverExpire, boolean active,
			String limitCategories, String limitSkus, double minOrder,
			double discount, String discountType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCoupon(
			userId, code, autoCode, name, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverExpire, active, limitCategories, limitSkus, minOrder, discount,
			discountType, serviceContext);
	}

	/**
	 * Adds the shopping coupon to the database. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingCoupon the shopping coupon
	 * @return the shopping coupon that was added
	 */
	public static com.liferay.shopping.model.ShoppingCoupon addShoppingCoupon(
		com.liferay.shopping.model.ShoppingCoupon shoppingCoupon) {

		return getService().addShoppingCoupon(shoppingCoupon);
	}

	/**
	 * Creates a new shopping coupon with the primary key. Does not add the shopping coupon to the database.
	 *
	 * @param couponId the primary key for the new shopping coupon
	 * @return the new shopping coupon
	 */
	public static com.liferay.shopping.model.ShoppingCoupon
		createShoppingCoupon(long couponId) {

		return getService().createShoppingCoupon(couponId);
	}

	public static void deleteCoupon(long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCoupon(couponId);
	}

	public static void deleteCoupon(
		com.liferay.shopping.model.ShoppingCoupon coupon) {

		getService().deleteCoupon(coupon);
	}

	public static void deleteCoupons(long groupId) {
		getService().deleteCoupons(groupId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the shopping coupon with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param couponId the primary key of the shopping coupon
	 * @return the shopping coupon that was removed
	 * @throws PortalException if a shopping coupon with the primary key could not be found
	 */
	public static com.liferay.shopping.model.ShoppingCoupon
			deleteShoppingCoupon(long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteShoppingCoupon(couponId);
	}

	/**
	 * Deletes the shopping coupon from the database. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingCoupon the shopping coupon
	 * @return the shopping coupon that was removed
	 */
	public static com.liferay.shopping.model.ShoppingCoupon
		deleteShoppingCoupon(
			com.liferay.shopping.model.ShoppingCoupon shoppingCoupon) {

		return getService().deleteShoppingCoupon(shoppingCoupon);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.shopping.model.impl.ShoppingCouponModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.shopping.model.impl.ShoppingCouponModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.shopping.model.ShoppingCoupon fetchShoppingCoupon(
		long couponId) {

		return getService().fetchShoppingCoupon(couponId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.shopping.model.ShoppingCoupon getCoupon(
			long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCoupon(couponId);
	}

	public static com.liferay.shopping.model.ShoppingCoupon getCoupon(
			String code)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCoupon(code);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the shopping coupon with the primary key.
	 *
	 * @param couponId the primary key of the shopping coupon
	 * @return the shopping coupon
	 * @throws PortalException if a shopping coupon with the primary key could not be found
	 */
	public static com.liferay.shopping.model.ShoppingCoupon getShoppingCoupon(
			long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getShoppingCoupon(couponId);
	}

	/**
	 * Returns a range of all the shopping coupons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.shopping.model.impl.ShoppingCouponModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping coupons
	 * @param end the upper bound of the range of shopping coupons (not inclusive)
	 * @return the range of shopping coupons
	 */
	public static java.util.List<com.liferay.shopping.model.ShoppingCoupon>
		getShoppingCoupons(int start, int end) {

		return getService().getShoppingCoupons(start, end);
	}

	/**
	 * Returns the number of shopping coupons.
	 *
	 * @return the number of shopping coupons
	 */
	public static int getShoppingCouponsCount() {
		return getService().getShoppingCouponsCount();
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingCoupon>
		search(
			long groupId, long companyId, String code, boolean active,
			String discountType, boolean andOperator, int start, int end) {

		return getService().search(
			groupId, companyId, code, active, discountType, andOperator, start,
			end);
	}

	public static int searchCount(
		long groupId, long companyId, String code, boolean active,
		String discountType, boolean andOperator) {

		return getService().searchCount(
			groupId, companyId, code, active, discountType, andOperator);
	}

	public static com.liferay.shopping.model.ShoppingCoupon updateCoupon(
			long userId, long couponId, String name, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			boolean neverExpire, boolean active, String limitCategories,
			String limitSkus, double minOrder, double discount,
			String discountType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCoupon(
			userId, couponId, name, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, endDateHour, endDateMinute, neverExpire,
			active, limitCategories, limitSkus, minOrder, discount,
			discountType, serviceContext);
	}

	/**
	 * Updates the shopping coupon in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param shoppingCoupon the shopping coupon
	 * @return the shopping coupon that was updated
	 */
	public static com.liferay.shopping.model.ShoppingCoupon
		updateShoppingCoupon(
			com.liferay.shopping.model.ShoppingCoupon shoppingCoupon) {

		return getService().updateShoppingCoupon(shoppingCoupon);
	}

	public static ShoppingCouponLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ShoppingCouponLocalService, ShoppingCouponLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ShoppingCouponLocalService.class);

		ServiceTracker<ShoppingCouponLocalService, ShoppingCouponLocalService>
			serviceTracker =
				new ServiceTracker
					<ShoppingCouponLocalService, ShoppingCouponLocalService>(
						bundle.getBundleContext(),
						ShoppingCouponLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}