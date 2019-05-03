AUI.add(
	'liferay-ddm-form-field-date',
	function(A) {
		var isArray = Array.isArray;
		var Lang = A.Lang;

		var datePicker = new A.DatePicker(
			{
				popover: {
					zIndex: Liferay.zIndex.TOOLTIP
				},
				trigger: '.liferay-ddm-form-field-date .trigger'
			}
		);

		var DateField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'string'
					},

					mask: {
						valueFn: '_maskValueFn'
					},

					type: {
						value: 'date'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-date',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							datePicker.after('selectionChange', A.bind('_afterSelectionChange', instance)),
							datePicker.on('activeInputChange', A.bind('_onActiveInputChange', instance))
						);

						if (!instance.get('readOnly')) {
							instance.bindContainerEvent('blur', instance._onBlurInput, '.form-control');
							instance.bindContainerEvent('click', instance._onClickCalendar, '.input-group-addon');
							instance.bindContainerEvent('focus', instance._fireFocusEvent, '.form-control');
							instance.bindContainerEvent('keypress', instance._onKeyPressDateForm, '#inputDateForm');
						}
					},

					formatDate: function(isoDate) {
						var instance = this;

						var formattedDate;

						if (isoDate) {
							formattedDate = A.Date.format(
								A.Date.parse('%Y-%m-%d', isoDate),
								{
									format: instance.get('mask')
								}
							);
						}

						return formattedDate || '';
					},

					getISODate: function(date) {
						var instance = this;

						return A.Date.format(date);
					},

					getTemplateContext: function() {
						var instance = this;

						var value = instance.getContextValue();

						return A.merge(
							DateField.superclass.getTemplateContext.apply(instance, arguments),
							{
								displayValue: instance.formatDate(value),
								value: value
							}
						);
					},

					getTriggerNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.one('.trigger');
					},

					hasFocus: function(node) {
						var instance = this;

						var hasFocus = false;

						if (node) {
							var calendar = datePicker.getCalendar();

							var popover = datePicker.getPopover();

							if (calendar.get('boundingBox').contains(node) || popover.get('visible')) {
								hasFocus = true;
							}
						}

						return hasFocus;
					},

					setValue: function(isoDate) {
						var instance = this;

						DateField.superclass.setValue.apply(instance, arguments);

						var formattedDate = instance.formatDate(isoDate);

						instance.getTriggerNode().val(formattedDate);
					},

					_afterSelectionChange: function(event) {
						var instance = this;

						var triggerNode = instance.getTriggerNode();

						if (datePicker.get('activeInput') === triggerNode) {
							var date = event.newSelection;

							if (isArray(date) && date.length) {
								date = date[0];
							}

							if (Lang.isDate(date)) {
								instance.setValue(instance.getISODate(date));
							}

							instance.validate();
						}

						instance._fireStartedFillingEvent();
					},

					_maskValueFn: function() {
						var dateFormat = Liferay.AUI.getDateFormat();
						var languageId = Liferay.ThemeDisplay.getLanguageId().replace('_', '-');

						var customDateFormat = A.Intl.get('datatype-date-format', 'x', languageId);

						if (customDateFormat) {
							dateFormat = customDateFormat;
						}

						return dateFormat;
					},

					_onActiveInputChange: function(event) {
						var instance = this;

						var triggerNode = instance.getTriggerNode();

						if (event.newVal === triggerNode) {
							datePicker.set('mask', instance.get('mask'));
						}
					},

					_onBlurInput: function() {
						var instance = this;

						var inputDate = document.getElementById('inputDateForm');

						if (inputDate.value.length == 0) {
							var nullDate = instance.getISODate(null);

							instance.setValue(nullDate);
						}

						if (!instance.hasFocus(document.activeElement)) {
							instance._fireBlurEvent();
						}
					},

					_onClickCalendar: function() {
						var instance = this;

						instance.getTriggerNode().focus();

						datePicker.show();
					},

					_onKeyPressDateForm: function(event) {
						var backspaceKeyCodes = [8, 46];

						var keyCode = event.keyCode;

						var expression = String.fromCharCode(keyCode);

						var inputDate = document.getElementById('inputDateForm');

						var regex = /[\d|//|\b]/;

						if (!regex.test(expression) || (inputDate.value.length > 9 && backspaceKeyCodes.indexOf(keyCode) == -1)) {
							event.preventDefault();
						}
					},

					_renderErrorMessage: function() {
						var instance = this;

						DateField.superclass._renderErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-group-container');

						inputGroup.insert(container.one('.help-block'), 'after');
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Date = DateField;
	},
	'',
	{
		requires: ['aui-datepicker', 'liferay-ddm-form-renderer-field']
	}
);