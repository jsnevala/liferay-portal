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

package com.liferay.dynamic.data.mapping.form.evaluator.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class DDMFormEvaluatorHelper {

	public DDMFormEvaluatorHelper(
		DDMForm ddmForm, DDMFormValues ddmFormValues,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		Locale locale) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;

		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);

		if (ddmFormValues == null) {
			ddmFormValues = createEmptyDDMFormValues(ddmForm);
		}

		_rootDDMFormFieldValues = ddmFormValues.getDDMFormFieldValues();

		_locale = locale;
	}

	public DDMFormEvaluationResult evaluate() throws PortalException {
		DDMFormEvaluationResult ddmFormEvaluationResult =
			new DDMFormEvaluationResult();

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			evaluateDDMFormFieldValues(
				_rootDDMFormFieldValues, new HashSet<DDMFormFieldValue>());

		ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
			ddmFormFieldEvaluationResults);

		return ddmFormEvaluationResult;
	}

	protected DDMFormValues createEmptyDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(ddmFormField.getName());

			Value value = new UnlocalizedValue(StringPool.BLANK);

			if (ddmFormField.isLocalizable()) {
				value = new LocalizedValue(_locale);

				value.addString(_locale, StringPool.BLANK);
			}

			ddmFormFieldValue.setValue(value);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected boolean evaluateBooleanExpression(
			String expressionString,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		if (Validator.isNull(expressionString)) {
			return true;
		}

		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(expressionString);

		try {
			setDDMExpressionVariables(
				ddmExpression, _rootDDMFormFieldValues,
				ancestorDDMFormFieldValues);

			return ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid expression or expression that is making " +
						"reference to a field no longer available: " +
							expressionString);
			}
		}

		return true;
	}

	protected DDMFormFieldEvaluationResult evaluateDDMFormFieldValue(
			DDMFormFieldValue ddmFormFieldValue,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		ancestorDDMFormFieldValues.add(ddmFormFieldValue);

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			evaluateDDMFormFieldValue(
				ddmFormFieldValue, ancestorDDMFormFieldValues, ddmFormField);

		ancestorDDMFormFieldValues.remove(ddmFormFieldValue);

		return ddmFormFieldEvaluationResult;
	}

	protected DDMFormFieldEvaluationResult evaluateDDMFormFieldValue(
			DDMFormFieldValue ddmFormFieldValue,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues,
			DDMFormField ddmFormField)
		throws PortalException {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId());

		boolean visible = evaluateBooleanExpression(
			ddmFormField.getVisibilityExpression(), ancestorDDMFormFieldValues);

		ddmFormFieldEvaluationResult.setVisible(visible);

		if (visible && ddmFormField.isRequired() &&
			isDDMFormFieldValueEmpty(ddmFormFieldValue)) {

			ddmFormFieldEvaluationResult.setErrorMessage(
				LanguageUtil.get(_locale, "this-field-is-required"));

			ddmFormFieldEvaluationResult.setValid(false);
		}
		else if (!isDDMFormFieldValueEmpty(ddmFormFieldValue)) {
			DDMFormFieldValidation ddmFormFieldValidation =
				ddmFormField.getDDMFormFieldValidation();

			String validationExpression = getValidationExpression(
				ddmFormFieldValidation);

			try {
				boolean valid = evaluateBooleanExpression(
					validationExpression, ancestorDDMFormFieldValues);

				ddmFormFieldEvaluationResult.setValid(valid);

				if (!valid) {
					String errorMessage =
						ddmFormFieldValidation.getErrorMessage();

					if (Validator.isNull(errorMessage)) {
						errorMessage = LanguageUtil.get(
							getResourceBundle(_locale),
							"this-field-is-invalid");
					}

					ddmFormFieldEvaluationResult.setErrorMessage(errorMessage);
				}
			}
			catch (NumberFormatException nfe) {
				String errorMessage = ddmFormFieldValidation.getErrorMessage();

				if (!errorMessage.equals("")) {
					ddmFormFieldEvaluationResult.setErrorMessage(errorMessage);
				}
				else {
					ResourceBundle resourceBundle =
						ResourceBundleUtil.getBundle(
							"content.Language", _locale, getClass());

					ddmFormFieldEvaluationResult.setErrorMessage(
						LanguageUtil.get(
							resourceBundle,
							"the-text-is-not-a-number-or-exceeds-the-maximum-" +
								"value"));
				}

				ddmFormFieldEvaluationResult.setValid(false);
			}
		}

		List<DDMFormFieldEvaluationResult> nestedDDMFormFieldEvaluationResults =
			evaluateDDMFormFieldValues(
				ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ancestorDDMFormFieldValues);

		ddmFormFieldEvaluationResult.setNestedDDMFormFieldEvaluationResults(
			nestedDDMFormFieldEvaluationResults);

		return ddmFormFieldEvaluationResult;
	}

	protected List<DDMFormFieldEvaluationResult> evaluateDDMFormFieldValues(
			List<DDMFormFieldValue> ddmFormFieldValues,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				evaluateDDMFormFieldValue(
					ddmFormFieldValue, ancestorDDMFormFieldValues);

			ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);
		}

		return ddmFormFieldEvaluationResults;
	}

	protected DDMFormFieldValueAccessor<?> getDDMFormFieldValueAccessor(
		String type) {

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(type);

		if (ddmFormFieldValueAccessor != null) {
			return ddmFormFieldValueAccessor;
		}

		return _defaultDDMFormFieldValueAccessor;
	}

	protected double getDoubleValue(String variableValue) {
		if (Validator.isNull(variableValue)) {
			return 0.0;
		}

		try {
			if (variableValue.matches(_FLOAT_REGEXP)) {
				return Double.parseDouble(variableValue);
			}

			throw new NumberFormatException();
		}
		catch (NumberFormatException nfe) {
			throw nfe;
		}
	}

	protected String getJSONArrayValueString(String valueString) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(valueString);

			return jsonArray.getString(0);
		}
		catch (JSONException jsone) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return valueString;
		}
	}

	protected long getLongValue(String variableValue) {
		if (Validator.isNull(variableValue)) {
			return 0L;
		}

		return GetterUtil.getLongStrict(variableValue);
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());
	}

	protected String getValidationExpression(
		DDMFormFieldValidation ddmFormFieldValidation) {

		if (ddmFormFieldValidation == null) {
			return null;
		}

		return ddmFormFieldValidation.getExpression();
	}

	protected String getValueString(
		DDMFormFieldValue ddmFormFieldValue, String type) {

		if (ddmFormFieldValue == null) {
			return null;
		}

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(type);

		Object value = ddmFormFieldValueAccessor.getValue(
			ddmFormFieldValue, _locale);

		if (value == null) {
			return null;
		}

		String valueString = value.toString();

		if (value instanceof JSONArray) {
			return getJSONArrayValueString(valueString);
		}

		return valueString;
	}

	protected String getVariableType(String dataType, String valueString) {
		if (dataType.equals("integer") && !Validator.isNumber(valueString)) {
			return "double";
		}

		return dataType;
	}

	protected boolean isDDMFormFieldValueEmpty(
		DDMFormFieldValue ddmFormFieldValue) {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(ddmFormField.getType());

		return ddmFormFieldValueAccessor.isEmpty(ddmFormFieldValue, _locale);
	}

	protected void setDDMExpressionFactory(
		DDMExpressionFactory ddmExpressionFactory) {

		_ddmExpressionFactory = ddmExpressionFactory;
	}

	protected void setDDMExpressionVariables(
			DDMExpression<Boolean> ddmExpression,
			List<DDMFormFieldValue> ddmFormFieldValues,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			String name = ddmFormFieldValue.getName();

			DDMFormField ddmFormField = _ddmFormFieldsMap.get(name);

			if (ddmFormField.isRepeatable() &&
				!ancestorDDMFormFieldValues.contains(ddmFormFieldValue)) {

				continue;
			}

			if (ddmFormField.isTransient()) {
				continue;
			}

			String valueString = getValueString(
				ddmFormFieldValue, ddmFormField.getType());

			if ((valueString != null) && ddmExpression.hasVariable(name)) {
				setExpressionVariableValue(
					ddmExpression, name,
					getVariableType(ddmFormField.getDataType(), valueString),
					valueString);
			}

			setDDMExpressionVariables(
				ddmExpression, ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ancestorDDMFormFieldValues);
		}
	}

	protected void setExpressionVariableValue(
			DDMExpression<Boolean> ddmExpression, String variableName,
			String variableType, String variableValue)
		throws PortalException {

		if (variableType.equals("boolean")) {
			ddmExpression.setBooleanVariableValue(
				variableName, GetterUtil.getBoolean(variableValue));
		}
		else if (variableType.equals("double")) {
			ddmExpression.setDoubleVariableValue(
				variableName, getDoubleValue(variableValue));
		}
		else if (variableType.equals("integer")) {
			ddmExpression.setLongVariableValue(
				variableName, getLongValue(variableValue));
		}
		else if (variableType.equals("string")) {
			ddmExpression.setStringVariableValue(variableName, variableValue);
		}
	}

	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private static final String _FLOAT_REGEXP = "^([+-]?\\d*\\.?\\d*)$";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormEvaluatorHelper.class);

	private DDMExpressionFactory _ddmExpressionFactory;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormFieldValueAccessor<String>
		_defaultDDMFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();
	private JSONFactory _jsonFactory;
	private final Locale _locale;
	private final List<DDMFormFieldValue> _rootDDMFormFieldValues;

}