package com.uffizio.tools.screenbean.reports.common;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import com.google.gson.JsonObject;
import com.uffizio.microservices.PostAPIUtility;
import com.uffizio.microservices.Urls;
import com.uffizio.reportservices.constants.DateConstant;
import com.uffizio.reportservices.constants.JSONConstantKeys;import com.uffizio.reportservices.constants.ReportName;
import com.uffizio.reportservices.requestparam.RequestParameters;
import com.uffizio.reportservices.services.interfaces.BaseReportService;
import com.uffizio.reportservices.utility.common.DataUtility;
import com.uffizio.reportservices.utility.common.DateUtility;
import com.uffizio.tools.db.DBConnection;
import com.uffizio.tools.logger.ErrorLogger;
import com.uffizio.tools.projectmanager.MobileServiceBase;
import com.uffizio.tools.projectmanager.MobileServiceBase1;
import com.uffizio.tools.screenbean.MasterReportData;
import com.uffizio.tools.screenbean.ReportCalculation;
import com.uffizio.tools.utility.ReportUtility;
import com.uffizio.tools.utility.TimeZoneConversion;
import com.uffizio.tools.utility.UserRights;
public class FuelBudgetSummaryDetail implements ReportCalculation {
@Override
public ArrayList<MasterReportData> calculateRepoprtData(DBConnection dbconnection, PageContext pageContext)
throws Exception {
ArrayList<MasterReportData> almasterReportData =new ArrayList<MasterReportData>();
try {
HttpSession session = pageContext.getSession();
HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
ServletContext application = pageContext.getServletContext();
HashMap<String, String> htHcsProperties= (HashMap<String, String>) application.getAttribute("htBundle");
boolean isCallForMobile= request.getAttribute("mobile")==null ?false : (boolean) request.getAttribute("mobile");
String userCompany="";
if (htHcsProperties.containsKey("user_company")){
userCompany =""+htHcsProperties.get("user_company");
}
String screenId = "";
int userId;
int projectId;
int timeFormat;
long lFrom;
long lTo;
boolean isTimeFrame = false;
String userDateFormat = "";
String fromFilter = "";
String toFilter ="";
String vehicleGroupId = "";
String vehicleTypeId = "";
String vehicleBrandId = "";
String vehicleGroupIds = "";
String vehicleModelId = "";
String vehicleTypeIds = "";
String companyRight = "";
String locationRights="";
String vehicleRights = "";
String vehicleBrandRight = "";
String vehicleModelRight = "";
String userStartDateTime;
String userEndDateTime;
String timeFrameFromDateTime;
String timeFrameToDateTime;
Hashtable<String, String> htFilterData = null;
TimeZoneConversion timeZoneConversion;
if (isCallForMobile) {
userId=Integer.parseInt(request.getParameter("userId"));
timeZoneConversion =new TimeZoneConversion(dbconnection,userId);
projectId= Integer.parseInt(request.getParameter("projrectId")==null || request.getParameter("projrectId").equals("") ? "16" : request.getParameter("projrectId"));
UserRights userRights = MobileServiceBase1.getUserRights(request, userId,projectId, dbconnection);
vehicleRights=request.getParameter("vehicleId")==null || request.getParameter("vehicleId").equals("") ? userRights.getsVehiclerights() : request.getParameter("vehicleId") ;
companyRight = userRights.getsCompanyRigts();
vehicleBrandRight = userRights.getsVehicleBrandRights();
vehicleModelRight = userRights.getsVehicleModelRights();
locationRights = userRights.getsLocationRights();
companyRight = userRights.getsCompanyRigts();
vehicleBrandId =vehicleBrandRight;
vehicleModelId =vehicleModelRight;
timeFormat = Integer.parseInt(userRights.getsUserTimeFormate());
userDateFormat = userRights.getsUserDateFormate();
String reportStartTime = "00:00:00";
String reportEndTime = "23:59:59";
boolean bCompanyRuleTime = false;
if((!reportStartTime.equals("0")) && (!reportEndTime.equals("0")) ) {
bCompanyRuleTime = true;
}
fromFilter = request.getParameter("fromDate");
if (fromFilter == null || fromFilter.equals("") || fromFilter.equals("0") || fromFilter.equals("All")) {
fromFilter = DateUtility.getCurrentDateTime("dd-MM-yyyy");
if (bCompanyRuleTime)
fromFilter = fromFilter + " " + reportStartTime;
else
fromFilter = fromFilter + " " + " 00:00:00";
}
toFilter = request.getParameter("toDate");
if (fromFilter == null || fromFilter.equals("") || fromFilter.equals("0") || fromFilter.equals("All")) {
if (bCompanyRuleTime) {
toFilter = DateUtility.getCurrentDateTime("dd-MM-yyyy") + " " + reportEndTime;
}
else {
toFilter = DateUtility.getCurrentDateTime("dd-MM-yyyy HH:mm:ss");
}
}
fromFilter = timeZoneConversion.convertUserDateToServerDate(fromFilter, "dd-MM-yyyy HH:mm:ss");
toFilter = timeZoneConversion.convertUserDateToServerDate(toFilter, "dd-MM-yyyy HH:mm:ss");
lFrom = DateUtility.fromFormattedDate(fromFilter, "dd-MM-yyyy HH:mm:ss").getTime();
lTo = DateUtility.fromFormattedDate(toFilter, "dd-MM-yyyy HH:mm:ss").getTime();
fromFilter = timeZoneConversion.convertServerDateToUserDate(fromFilter, "dd-MM-yyyy HH:mm:ss");
toFilter = timeZoneConversion.convertServerDateToUserDate(toFilter, "dd-MM-yyyy HH:mm:ss");
isTimeFrame = StringUtils.hasText(request.getParameter("timepicker_filter")) ? Boolean.parseBoolean(request.getParameter("timepicker_filter")) : false;
userStartDateTime = DateUtility.convertDateFormat(fromFilter, "dd-MM-yyyy HH:mm:ss", DateConstant.SQL_DATE_TIME_FORMAT);
userEndDateTime = DateUtility.convertDateFormat(toFilter, "dd-MM-yyyy HH:mm:ss", DateConstant.SQL_DATE_TIME_FORMAT);
timeFrameFromDateTime = userStartDateTime;
timeFrameToDateTime = userEndDateTime;
if (isTimeFrame && StringUtils.hasText(request.getParameter("from-time-picker")) && StringUtils.hasText(request.getParameter("to-time-picker"))) {
timeFrameFromDateTime = fromFilter.split(" ")[0] + " " + request.getParameter("from-time-picker");
timeFrameToDateTime = toFilter.split(" ")[0] + " " + request.getParameter("to-time-picker");
}
}
screenId = request.getParameter("screenid");
userId = Integer.parseInt((String) session.getAttribute("userid"));
timeZoneConversion = new TimeZoneConversion(dbconnection, userId);
projectId = Integer.parseInt((String) session.getAttribute("projectid") == null ? "37" : (String) session.getAttribute("projectid"));
companyRight = (String) session.getAttribute("CompanyRights");
locationRights = (String) session.getAttribute("LocationRights");
vehicleRights = (String) session.getAttribute("VehicleRights");
vehicleModelRight = session.getAttribute("VehicleModelRights") != null ? (String) session.getAttribute("VehicleModelRights") : "0";
vehicleBrandRight = session.getAttribute("VehicleBrandRights") != null ? (String) session.getAttribute("VehicleBrandRights") : "0";
vehicleModelId = vehicleModelRight;
vehicleBrandId = vehicleBrandRight;
timeFormat = session.getAttribute("time_format") == null ? 24 : Integer.parseInt("" + session.getAttribute("time_format"));
userDateFormat = session.getAttribute("date_format") != null ? (String) session.getAttribute("date_format") : "dd-MM-yyyy";
isTimeFrame = StringUtils.hasText(request.getParameter("timepicker_filter")) ? Boolean.parseBoolean(request.getParameter("timepicker_filter")) : false;
String sFilterVehicleFlag = request.getParameter("filter_vehicle_flag") == null ? "false" : request.getParameter("filter_vehicle_flag");
String sFilterVehicle = request.getParameter("filter_vehicle") == null ? "0" : request.getParameter("filter_vehicle");
String sVehicleSearch = request.getParameter("vehicle_search") == null ? "0" : request.getParameter("vehicle_search");
if (sFilterVehicleFlag.equals("true") && !sFilterVehicle.equals("") && !sFilterVehicle.equals("0") && !sFilterVehicle.equalsIgnoreCase("All")) {
vehicleRights = sFilterVehicle;
}
if (sFilterVehicleFlag.equals("false") && !sVehicleSearch.equals("") && !sVehicleSearch.equals("0") && !sVehicleSearch.equalsIgnoreCase("All")) {
vehicleRights = sVehicleSearch;
}
htFilterData = ReportUtility.getFilterSettingsData(dbconnection, screenId, String.valueOf(userId), "0");
if (!sFilterVehicleFlag.equals("true")) {
// COMPANY FILTER
String companyFilter = request.getParameter("company");
if (companyFilter == null) companyFilter = htFilterData.get("company") == null ? null : "" + htFilterData.get("company");
if (companyFilter != null && !companyFilter.equals("0") && !companyFilter.equals("All")) {
companyRight = companyFilter;
}
// BRANCH FILTER
String locationFilter = request.getParameter("branch");
if (locationFilter == null) locationFilter = htFilterData.get("branch") == null ? null : "" + htFilterData.get("branch");
if (locationFilter != null && !locationFilter.equals("0") && !locationFilter.equals("All")) {
locationRights = locationFilter;
}
// VEHICLE TYPE FILTER
String vehucleTypeFilter = request.getParameter("vehicle_type");
if (vehucleTypeFilter == null) vehucleTypeFilter = htFilterData.get("vehicle_type") == null ? null : "" + htFilterData.get("vehicle_type");
if (vehucleTypeFilter != null && !vehucleTypeFilter.equals("0") && !vehucleTypeFilter.equals("All")) {
vehicleTypeIds = vehucleTypeFilter;
}
// VEHICLE GROUP FILTER
String vehicleGroupFilter = request.getParameter("vehicle_group");
if (vehicleGroupFilter == null) {
vehicleGroupFilter = htFilterData.get("vehicle_group") == null ? null : "" + htFilterData.get("vehicle_group");
}
if (vehicleGroupFilter != null && !vehicleGroupFilter.equals("0") && !vehicleGroupFilter.equals("All")) {
vehicleGroupIds = vehicleGroupFilter;
}
// VEHICLE BRAND FILTER
String vehicleBrandFilter = request.getParameter("vehicle_brand");
if (vehicleBrandFilter == null) {
vehicleBrandFilter = htFilterData.get("vehicle_brand") == null ? null : "" + htFilterData.get("vehicle_brand");
}
if (vehicleBrandFilter != null && !vehicleBrandFilter.equals("0") && !vehicleBrandFilter.equals("All")) {
vehicleBrandId = vehicleBrandFilter;
}
// VEHICLE MODEL FILTER
String vehicleModelFilter = request.getParameter("vehicle_model");
if (vehicleModelFilter == null) {
vehicleModelFilter = htFilterData.get("vehicle_model") == null ? null : "" + htFilterData.get("vehicle_model");
}
if (vehicleModelFilter != null && !vehicleModelFilter.equals("0") && !vehicleModelFilter.equals("All")) {
vehicleModelId = vehicleModelFilter;
}
}
// FROM FILTER //TO FILTER
String reportStartTime = (String) session.getAttribute("report_starttime") == null || ((String) session.getAttribute("report_starttime")).equals("") ? "0"
: (String) session.getAttribute("report_starttime");
String reportEndTime =
(String) session.getAttribute("report_endtime") == null || ((String) session.getAttribute("report_endtime")).equals("") ? "0" : (String) session.getAttribute("report_endtime");
boolean isCompanyRuleTime = false;
if (!reportStartTime.equals("0") && !reportEndTime.equals("0")) {
isCompanyRuleTime = true;
}
if (!sFilterVehicleFlag.equals("true")) {
fromFilter = request.getParameter("from_date_filter");
toFilter = request.getParameter("to_date_filter");
}
else {
fromFilter = request.getParameter("vfromdate");
toFilter = request.getParameter("vtodate");
}
if (timeFormat == 24) {
if (fromFilter != null && !fromFilter.equals("") && !fromFilter.equals("0") && !fromFilter.equals("All")) {
fromFilter += ":00";
fromFilter = timeZoneConversion.convertUserDateToServerDate(fromFilter, userDateFormat + " HH:mm:ss");
fromFilter = DateUtility.convertDateFormat(fromFilter, userDateFormat + " HH:mm:ss", "dd-MM-yyyy HH:mm:ss");
}
else {
fromFilter = timeZoneConversion.getUserCurrentDate("dd-MM-yyyy");
if (isCompanyRuleTime) {
fromFilter = fromFilter + " " + reportStartTime;
fromFilter = timeZoneConversion.convertUserDateToServerDate(fromFilter + ":00", "dd-MM-yyyy HH:mm:ss");
}
else {
fromFilter = fromFilter + " " + " 00:00:00";
fromFilter = timeZoneConversion.convertUserDateToServerDate(fromFilter, "dd-MM-yyyy HH:mm:ss");
}
}
if (toFilter != null && !toFilter.equals("") && !toFilter.equals("0") && !toFilter.equals("All")) {
toFilter += ":00";
toFilter = timeZoneConversion.convertUserDateToServerDate(toFilter, userDateFormat + " HH:mm:ss");
toFilter = DateUtility.convertDateFormat(toFilter, userDateFormat + " HH:mm:ss", "dd-MM-yyyy HH:mm:ss");
}
else {
if (isCompanyRuleTime) {
toFilter = timeZoneConversion.getUserCurrentDate("dd-MM-yyyy") + " " + reportEndTime;
toFilter = timeZoneConversion.convertUserDateToServerDate(toFilter + ":00", "dd-MM-yyyy HH:mm:ss");
}
else {
toFilter = timeZoneConversion.getUserCurrentDate("dd-MM-yyyy HH:mm:ss");
toFilter = timeZoneConversion.convertUserDateToServerDate(toFilter, "dd-MM-yyyy HH:mm:ss");
}
}
lFrom = DateUtility.fromFormattedDate(fromFilter, "dd-MM-yyyy HH:mm:ss").getTime();
lTo = DateUtility.fromFormattedDate(toFilter, "dd-MM-yyyy HH:mm:ss").getTime();
fromFilter = timeZoneConversion.convertServerDateToUserDate(fromFilter, "dd-MM-yyyy HH:mm:ss");
toFilter = timeZoneConversion.convertServerDateToUserDate(toFilter, "dd-MM-yyyy HH:mm:ss");
}
else {
if (fromFilter == null || fromFilter.equals("") || fromFilter.equals("0") || fromFilter.equals("All")) {
if (isCompanyRuleTime) {
fromFilter = timeZoneConversion.getUserCurrentDate("dd-MM-yyyy") + " " + reportStartTime;
fromFilter = timeZoneConversion.convertUserDateToServerDate(fromFilter, "dd-MM-yyyy HH:mm");
fromFilter = DateUtility.convertDateFormat(fromFilter, "dd-MM-yyyy HH:mm", "dd-MM-yyyy hh:mm");
}
else {
fromFilter = timeZoneConversion.getUserCurrentDate("dd-MM-yyyy") + " 00:00 AM";
fromFilter = timeZoneConversion.convertUserDateToServerDate(fromFilter, "dd-MM-yyyy HH:mm aaa");
}
}
else {
fromFilter = timeZoneConversion.convertUserDateToServerDate(fromFilter, userDateFormat + " hh:mm aaa");
fromFilter = DateUtility.convertDateFormat(fromFilter, userDateFormat + " hh:mm", "dd-MM-yyyy hh:mm");
}
if (toFilter == null || toFilter.equals("") || toFilter.equals("0") || toFilter.equals("All")) {
if (isCompanyRuleTime) {
toFilter = timeZoneConversion.getUserCurrentDate("dd-MM-yyyy") + " " + reportEndTime;
toFilter = timeZoneConversion.convertUserDateToServerDate(toFilter, "dd-MM-yyyy HH:mm");
toFilter = DateUtility.convertDateFormat(toFilter, "dd-MM-yyyy HH:mm", "dd-MM-yyyy hh:mm");
}
else {
toFilter = timeZoneConversion.getUserCurrentDate("dd-MM-yyyy hh:mm aaa");
toFilter = timeZoneConversion.convertUserDateToServerDate(toFilter, "dd-MM-yyyy hh:mm aaa");
}
}
else {
toFilter = timeZoneConversion.convertUserDateToServerDate(toFilter, userDateFormat + " hh:mm aaa");
toFilter = DateUtility.convertDateFormat(toFilter, userDateFormat + " hh:mm", "dd-MM-yyyy hh:mm");
}
lFrom = DateUtility.fromFormattedDate(fromFilter, "dd-MM-yyyy hh:mm aaa").getTime();
lTo = DateUtility.fromFormattedDate(toFilter, "dd-MM-yyyy hh:mm aaa").getTime();
fromFilter = timeZoneConversion.convertServerDateToUserDate(fromFilter, "dd-MM-yyyy hh:mm aaa");
toFilter = timeZoneConversion.convertServerDateToUserDate(toFilter, "dd-MM-yyyy hh:mm aaa");
}
userStartDateTime = DateConstant.SDF_SQL_DATE_TIME_FORMAT.format(new Date(lFrom));
userEndDateTime = DateConstant.SDF_SQL_DATE_TIME_FORMAT.format(new Date(lTo));
userStartDateTime = timeZoneConversion.convertServerDateToUserDate(userStartDateTime, DateConstant.SQL_DATE_TIME_FORMAT);
userEndDateTime = timeZoneConversion.convertServerDateToUserDate(userEndDateTime, DateConstant.SQL_DATE_TIME_FORMAT);
timeFrameFromDateTime = userStartDateTime;
timeFrameToDateTime = userEndDateTime;
if (isTimeFrame && StringUtils.hasText(request.getParameter("from-time-picker")) && StringUtils.hasText(request.getParameter("to-time-picker"))) {
timeFrameFromDateTime = timeFrameFromDateTime.split(" ")[0] + " " + request.getParameter("from-time-picker");
timeFrameToDateTime = timeFrameToDateTime.split(" ")[0] + " " + request.getParameter("to-time-picker");
if (timeFormat == 24) {
timeFrameFromDateTime = timeFrameFromDateTime + ":00";
timeFrameToDateTime = timeFrameToDateTime + ":00";
}
else {
timeFrameFromDateTime = DateUtility.convertDateFormat(timeFrameFromDateTime, DateConstant.SQL_DATE_FORMAT + " hh:mm", DateConstant.SQL_DATE_FORMAT + " HH:mm:ss");
timeFrameToDateTime = DateUtility.convertDateFormat(timeFrameToDateTime, DateConstant.SQL_DATE_FORMAT + " hh:mm", DateConstant.SQL_DATE_FORMAT + " HH:mm:ss");
}
}
String userReportStartDateTime = userStartDateTime;
String userReportEndDateTime = userEndDateTime;
RequestParameters requestParameters = new RequestParameters();
requestParameters.setUserId(userId);
requestParameters.setUserDateFormat(userDateFormat);
requestParameters.setUserTimeFormat(timeFormat);
requestParameters.setProjectId(projectId);
requestParameters.setCompanyIds(companyRight);
requestParameters.setLocationIds(locationRights);
requestParameters.setVehicleIds(vehicleRights);
requestParameters.setVehicleGroupIds(vehicleGroupIds);
requestParameters.setVehicleTypeIds(vehicleTypeIds);
requestParameters.setVehicleBrandIds(vehicleBrandId);
requestParameters.setVehicleModelIds(vehicleModelId);
requestParameters.setStartDate(timeFrameFromDateTime.split(" ")[0]);
requestParameters.setStartTime(timeFrameFromDateTime.split(" ")[1]);
requestParameters.setEndDate(timeFrameToDateTime.split(" ")[0]);
requestParameters.setEndTime(timeFrameToDateTime.split(" ")[1]);
requestParameters.setDefaultFilterFromDateTime(userReportStartDateTime);
requestParameters.setDefaultFilterToDateTime(userReportEndDateTime);
requestParameters.setShiftTiming(false);
requestParameters.setFullDayShift(false);
requestParameters.setUserCompany(userCompany);
requestParameters.setRequestFor(ReportName.FUEL_BUDGET);
requestParameters.setReportLevel(BaseReportService.SUMMARY_AND_DETAIL);
JSONObject apiResponce = null;
String apiString = Urls.REPORT;
apiResponce = PostAPIUtility.callApi(apiString, requestParameters);
int level;
String subTitle;
String title;
JsonObject jsonObject;
JSONObject vehicleDataJsonObj;
JSONArray detailJsonArray;
JSONArray summaryJsonArray;
JSONObject dataRow;
MasterReportData masterReportData;
ArrayList<JsonObject> alDetailData;
ArrayList<JsonObject> alSummaryData;
JSONObject dataJsonObj = apiResponce.getJSONObject(JSONConstantKeys.DATA);
JSONArray vehicleDataJsonArray = dataJsonObj.getJSONArray(JSONConstantKeys.DATA_ROWS);
if (apiResponce != null && apiResponce.getInt(JSONConstantKeys.RESULT) == 1) {
for (int i = 0; i < vehicleDataJsonArray.length(); i++) {
subTitle = null;
title = null;
alDetailData = new ArrayList<>();
alSummaryData = new ArrayList<>();
vehicleDataJsonObj = vehicleDataJsonArray.getJSONObject(i);
level = vehicleDataJsonObj.getInt(JSONConstantKeys.LEVEL);
if (vehicleDataJsonObj.has(JSONConstantKeys.SUB_TITLE)) {
subTitle = vehicleDataJsonObj.getString(JSONConstantKeys.SUB_TITLE);
}
if (vehicleDataJsonObj.has(JSONConstantKeys.TITLE)) {
title = vehicleDataJsonObj.getString(JSONConstantKeys.TITLE);
}
detailJsonArray = vehicleDataJsonObj.getJSONArray(JSONConstantKeys.DETAIL);
summaryJsonArray = vehicleDataJsonObj.getJSONArray(JSONConstantKeys.SUMMARY);
if (level == 1) {
for (int j = 0; j < detailJsonArray.length(); j++) {
dataRow = detailJsonArray.getJSONObject(j);
jsonObject = new JsonObject();
jsonObject.addProperty("pid", dataRow.getString("pid"));
jsonObject.addProperty("company", dataRow.getString("company"));
jsonObject.addProperty("branch", dataRow.getString("branch"));
jsonObject.addProperty("vehicle_info", dataRow.getString("vehicle_info"));
jsonObject.addProperty("vehicle_brand", dataRow.getString("vehicle_brand"));
jsonObject.addProperty("vehicle_model", dataRow.getString("vehicle_model"));
jsonObject.addProperty("filled_qty", dataRow.getDouble("filled_qty"));
jsonObject.addProperty("fuel_expense", dataRow.getDouble("fuel_expense"));
jsonObject.addProperty("defined_amount", dataRow.getString("defined_amount"));
jsonObject.addProperty("diffrence_in_cost", dataRow.getString("diffrence_in_cost"));
jsonObject.addProperty("defined_in_distance", dataRow.getString("defined_in_distance"));
jsonObject.addProperty("travelled_distance", dataRow.getString("travelled_distance"));
jsonObject.addProperty("diffrences_in_distance", dataRow.getString("diffrences_in_distance"));
jsonObject.addProperty("total_travel_duration", dataRow.getString("total_travel_duration"));
jsonObject.addProperty("total_idle_duration", dataRow.getString("total_idle_duration"));
alDetailData.add(jsonObject);
}
for (int j = 0; j < summaryJsonArray.length(); j++) {
dataRow = summaryJsonArray.getJSONObject(j);
jsonObject = new JsonObject();
jsonObject.addProperty("fuel_expense", dataRow.getDouble("fuel_expense"));
jsonObject.addProperty("filled_qty", dataRow.getDouble("filled_qty"));
jsonObject.addProperty("diffrence_in_cost", dataRow.getDouble("travelled_distance"));
jsonObject.addProperty("diffrences_in_distance", dataRow.getString("total_travel_duration"));
jsonObject.addProperty("total_travel_duration", dataRow.getString("total_travel_duration"));
jsonObject.addProperty("total_idle_duration", dataRow.getString("total_idle_duration"));
alSummaryData.add(jsonObject);
}
}
else if (level == 2) {
String date;
for (int j = 0; j < detailJsonArray.length(); j++) {
dataRow = detailJsonArray.getJSONObject(j);
date = dataRow.getString("expense_date");
date = DateUtility.convertDateFormat(date, DateConstant.SQL_DATE_TIME_FORMAT, userDateFormat);
jsonObject = new JsonObject();
jsonObject.addProperty("pid", dataRow.getInt("pid"));
jsonObject.addProperty("expense_date", date);
jsonObject.addProperty("expense_by", dataRow.getString("expense_by"));
jsonObject.addProperty("expense_amount", dataRow.getDouble("expense_amount"));
jsonObject.addProperty("reference_no", dataRow.getString("reference_no"));
jsonObject.addProperty("fuel_source", dataRow.getString("fuel_source"));
jsonObject.addProperty("description", dataRow.getString("description"));
jsonObject.addProperty("travelled_distance", dataRow.getDouble("travelled_distance"));
jsonObject.addProperty("work_hour", dataRow.getString("work_hour"));
jsonObject.addProperty("filled_quantity", dataRow.getDouble("filled_quantity"));
jsonObject.addProperty("per_unit_cost", dataRow.getDouble("per_unit_cost"));
jsonObject.addProperty("download_bill", dataRow.getString("download_bill"));
jsonObject.addProperty("location_address", dataRow.getString("location_address"));
jsonObject.addProperty("comments_data", dataRow.getString("comments_data"));
alDetailData.add(jsonObject);
}
for (int j = 0; j < summaryJsonArray.length(); j++) {
dataRow = summaryJsonArray.getJSONObject(j);
jsonObject = new JsonObject();
jsonObject.addProperty("expense_amount", dataRow.getDouble("expense_amount"));
jsonObject.addProperty("filled_quantity", dataRow.getDouble("filled_quantity"));
jsonObject.addProperty("travelled_distance", dataRow.getDouble("travelled_distance"));
jsonObject.addProperty("no_of_records", dataRow.getInt("no_of_records"));
System.err.println("456............");
System.err.println("FuelExpenseSummary............");
alSummaryData.add(jsonObject);
}
}
masterReportData = new MasterReportData();
masterReportData.setLevel((short) level);
masterReportData.setSubTitle(subTitle);
masterReportData.setTitle(title);
masterReportData.setAlSummaryData(alSummaryData);
masterReportData.setAlDetailData(alDetailData);
almasterReportData.add(masterReportData);
}
}
}
catch (Exception e) {
ErrorLogger.error(e, "");
}
return almasterReportData;
}
}