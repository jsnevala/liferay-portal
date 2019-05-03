// This file was automatically generated from form.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.field = function(opt_data, opt_ignored) {
  return '' + ((opt_data.field != null) ? soy.$$filterNoAutoescape(opt_data.field) : '');
};
if (goog.DEBUG) {
  ddm.field.soyTemplateName = 'ddm.field';
}


ddm.fields = function(opt_data, opt_ignored) {
  var output = '';
  var fieldList10 = opt_data.fields;
  var fieldListLen10 = fieldList10.length;
  for (var fieldIndex10 = 0; fieldIndex10 < fieldListLen10; fieldIndex10++) {
    var fieldData10 = fieldList10[fieldIndex10];
    output += ddm.field(soy.$$augmentMap(opt_data, {field: fieldData10}));
  }
  return output;
};
if (goog.DEBUG) {
  ddm.fields.soyTemplateName = 'ddm.fields';
}


ddm.form_renderer_js = function(opt_data, opt_ignored) {
  return '<script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {if (Liferay.DDMFieldTypesReady) {Liferay.component( \'' + soy.$$escapeJsString(opt_data.containerId) + 'DDMForm\', new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeJsString(opt_data.containerId) + '\', dataProviderURL: \'' + soy.$$filterNoAutoescape(opt_data.dataProviderURL) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', evaluation: ' + soy.$$filterNoAutoescape(opt_data.evaluation) + ', evaluatorURL: \'' + soy.$$filterNoAutoescape(opt_data.evaluatorURL) + '\',' + ((opt_data.layout) ? 'layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ',' : '') + 'portletNamespace: \'' + soy.$$escapeJsString(opt_data.portletNamespace) + '\', readOnly: ' + soy.$$escapeJsValue(opt_data.readOnly) + ', readOnlyFields : ' + soy.$$filterNoAutoescape(opt_data.readOnlyFields) + ', showRequiredFieldsWarning: ' + soy.$$escapeJsValue(opt_data.showRequiredFieldsWarning) + ', showSubmitButton: ' + soy.$$escapeJsValue(opt_data.showSubmitButton) + ', submitLabel: \'' + soy.$$escapeJsString(opt_data.submitLabel) + '\', templateNamespace: \'' + soy.$$escapeJsString(opt_data.templateNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render() );}else {Liferay.onceAfter( \'DDMFieldTypesReady\', function ()  {Liferay.component( \'' + soy.$$escapeJsString(opt_data.containerId) + 'DDMForm\', new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeJsString(opt_data.containerId) + '\', dataProviderURL: \'' + soy.$$filterNoAutoescape(opt_data.dataProviderURL) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', evaluation: ' + soy.$$filterNoAutoescape(opt_data.evaluation) + ', evaluatorURL: \'' + soy.$$filterNoAutoescape(opt_data.evaluatorURL) + '\',' + ((opt_data.layout) ? 'layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ',' : '') + 'portletNamespace: \'' + soy.$$escapeJsString(opt_data.portletNamespace) + '\', readOnly: ' + soy.$$escapeJsValue(opt_data.readOnly) + ', readOnlyFields : ' + soy.$$filterNoAutoescape(opt_data.readOnlyFields) + ', showRequiredFieldsWarning: ' + soy.$$escapeJsValue(opt_data.showRequiredFieldsWarning) + ', showSubmitButton: ' + soy.$$escapeJsValue(opt_data.showSubmitButton) + ', submitLabel: \'' + soy.$$escapeJsString(opt_data.submitLabel) + '\', templateNamespace: \'' + soy.$$escapeJsString(opt_data.templateNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render() );});}var destroyFormHandle = function(event) {var form = Liferay.component(\'' + soy.$$escapeJsString(opt_data.containerId) + 'DDMForm\'); var portlet = event.portlet; if (portlet && portlet.contains(form.get(\'container\'))) {form.destroy(); Liferay.detach(\'destroyPortlet\', destroyFormHandle);}};});<\/script>';
};
if (goog.DEBUG) {
  ddm.form_renderer_js.soyTemplateName = 'ddm.form_renderer_js';
}


ddm.form_rows = function(opt_data, opt_ignored) {
  var output = '';
  var rowList103 = opt_data.rows;
  var rowListLen103 = rowList103.length;
  for (var rowIndex103 = 0; rowIndex103 < rowListLen103; rowIndex103++) {
    var rowData103 = rowList103[rowIndex103];
    output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData103.columns})) + '</div>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.form_rows.soyTemplateName = 'ddm.form_rows';
}


ddm.form_row_column = function(opt_data, opt_ignored) {
  return '<div class="col-md-' + soy.$$escapeHtmlAttribute(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};
if (goog.DEBUG) {
  ddm.form_row_column.soyTemplateName = 'ddm.form_row_column';
}


ddm.form_row_columns = function(opt_data, opt_ignored) {
  var output = '';
  var columnList115 = opt_data.columns;
  var columnListLen115 = columnList115.length;
  for (var columnIndex115 = 0; columnIndex115 < columnListLen115; columnIndex115++) {
    var columnData115 = columnList115[columnIndex115];
    output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData115}));
  }
  return output;
};
if (goog.DEBUG) {
  ddm.form_row_columns.soyTemplateName = 'ddm.form_row_columns';
}


ddm.required_warning_message = function(opt_data, opt_ignored) {
  return '' + ((opt_data.showRequiredFieldsWarning) ? soy.$$filterNoAutoescape(opt_data.requiredFieldsWarningMessageHTML) : '');
};
if (goog.DEBUG) {
  ddm.required_warning_message.soyTemplateName = 'ddm.required_warning_message';
}


ddm.paginated_form = function(opt_data, opt_ignored) {
  var output = '<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtmlAttribute(opt_data.containerId) + '"><div class="lfr-ddm-form-content">';
  if (opt_data.pages.length > 1) {
    output += '<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
    var pageList139 = opt_data.pages;
    var pageListLen139 = pageList139.length;
    for (var pageIndex139 = 0; pageIndex139 < pageListLen139; pageIndex139++) {
      var pageData139 = pageList139[pageIndex139];
      output += '<li ' + ((pageIndex139 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData139.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex139 + 1) + '</div></li>';
    }
    output += '</ul></div>';
  }
  output += '<div class="lfr-ddm-form-pages" autoescape="deprecated-contextual">';
  var pageList164 = opt_data.pages;
  var pageListLen164 = pageList164.length;
  for (var pageIndex164 = 0; pageIndex164 < pageListLen164; pageIndex164++) {
    var pageData164 = pageList164[pageIndex164];
    output += '<div class="' + ((pageIndex164 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData164.title) ? '<h3 class="lfr-ddm-form-page-title">' + soy.$$escapeHtml(pageData164.title) + '</h3>' : '') + ((pageData164.description) ? '<h4 class="lfr-ddm-form-page-description">' + soy.$$escapeHtml(pageData164.description) + '</h4>' : '') + ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData164.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData164.rows})) + '</div>';
  }
  output += '</div></div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-lg btn-primary hide lfr-ddm-form-pagination-prev" type="button"><i class="icon-angle-left"></i> ' + soy.$$escapeHtml(opt_data.strings.previous) + '</button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">' + soy.$$escapeHtml(opt_data.strings.next) + ' <i class="icon-angle-right"></i></button>' + ((opt_data.showSubmitButton) ? '<button class="btn btn-lg btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" disabled type="submit">' + soy.$$escapeHtml(opt_data.submitLabel) + '</button>' : '') + '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.paginated_form.soyTemplateName = 'ddm.paginated_form';
}


ddm.simple_form = function(opt_data, opt_ignored) {
  var output = '<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtmlAttribute(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
  var pageList194 = opt_data.pages;
  var pageListLen194 = pageList194.length;
  for (var pageIndex194 = 0; pageIndex194 < pageListLen194; pageIndex194++) {
    var pageData194 = pageList194[pageIndex194];
    output += ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData194.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData194.rows}));
  }
  output += '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.simple_form.soyTemplateName = 'ddm.simple_form';
}


ddm.tabbed_form = function(opt_data, opt_ignored) {
  var output = '<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtmlAttribute(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs nav-tabs-default">';
  var pageList204 = opt_data.pages;
  var pageListLen204 = pageList204.length;
  for (var pageIndex204 = 0; pageIndex204 < pageListLen204; pageIndex204++) {
    var pageData204 = pageList204[pageIndex204];
    output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData204.title) + '</a></li>';
  }
  output += '</ul><div class="tab-content">';
  var pageList218 = opt_data.pages;
  var pageListLen218 = pageList218.length;
  for (var pageIndex218 = 0; pageIndex218 < pageListLen218; pageIndex218++) {
    var pageData218 = pageList218[pageIndex218];
    output += ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData218.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + '<div class="tab-pane ' + ((pageIndex218 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData218.rows})) + '</div>';
  }
  output += '</div></div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.tabbed_form.soyTemplateName = 'ddm.tabbed_form';
}


ddm.settings_form = function(opt_data, opt_ignored) {
  var output = '<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtmlAttribute(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
  var pageList236 = opt_data.pages;
  var pageListLen236 = pageList236.length;
  for (var pageIndex236 = 0; pageIndex236 < pageListLen236; pageIndex236++) {
    var pageData236 = pageList236[pageIndex236];
    output += '<div class="lfr-ddm-form-page' + ((pageIndex236 == 0) ? ' active basic' : '') + ((pageIndex236 == pageListLen236 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData236.rows})) + '</div>';
  }
  output += '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.settings_form.soyTemplateName = 'ddm.settings_form';
}
