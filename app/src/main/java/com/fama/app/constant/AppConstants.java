package com.fama.app.constant;

import java.io.File;

import android.os.Environment;

@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface AppConstants {

	int SEARCH_TIME = 1500;
	String ADD_MONEY = "ADD";
	String DEDUCT_MONEY = "DEDUCT";

	enum UserStatusType {AVAILABLE,BUSY,DO_NOT_DISTURB,OFF_TO_WORK,APPEAR_AWAY,REST_STATUS}

	String ONE = "SmFp";

	String PATROLLER_LIST_SEARCH = "Patroller name...";
	String USER_LIST_SEARCH = "User name...";
	String FRT_LIST_SEARCH = "FRT name...";
	String SPAN_LIST_SEARCH = "Span name/id...";

	String DETERMINE_VALUE = "mkXnEciL0F2HaCuF/gCRPKkLgl33zWswNOiYMiacAKM=";
	String DETERMINE_KEY = "determine";


	String USERAVAIL="Available";
	String USERBUSY="Busy";
	String USER_DND="Do Not Disturb";
	String USER_OFF_TO_WORK="Off to work";
	String USER_AWAY="Appear Away";
	String USER_REST="Rest status";
	String USER_STATUS="userStatus";
	String APPNAME = "JioFiberPlus";

	final int CATEGORY_PAT_ALL=1;
	final int CATEGORY_PAT_ACTIVE=2;
	final int CATEGORY_FRT=1;
	final int CATEGORY_TRT_=2;
	final int CATEGORY_ONM_ENG_=3;
	final int CATEGORY_ONM_LEAD=4;

	final int CATEGORY_ALL=1;
	final int CATEGORY_INTRACITY=2;
	final int CATEGORY_INTERCITY=3;


	final String CATEGORY_FRT_STR="FRT Head";
	final String CATEGORY_TRT_STR_="TRT Head";
	final String CATEGORY_ONM_ENG_STR_="ONM Engineer";
	final String CATEGORY_ONM_LEAD_STR="ONM Lead";

	final String CATEGORY_ONM_ENG_STR_1="O&M Engineer";
	final String CATEGORY_ONM_LEAD_STR1="O&M Lead";

	int PORT = 443;
	int HTTP_PORT = 80;

	 String REQUIRED = "Field is required";

	boolean LOG_ENABLE = false;
	static final int NUM_PAGES = 0;
	int HOME_POSITION = 14;
	int DASHBOARD_POSITION = 15;
	int USERPROFILE_POSITION = 17;
	int NAV_OPENFAULT = 0;
	int NAV_RCA = 1;
	int NAV_REPORT = 9;
	int NAV_LOGOUT = 12;
	int NAV_UM = 8;
	int HELP = 10;

	int ALERT_POSITION = 16;
	int PENDING_SELECTION_POSITION = 0;
	int CLOSURE_SELECTION_POSITION = 1;
	int ALERTS_SELECTION_POSITION = 2;
	int RESOURCES_SELECTION_POSITION = 3;
	int EWALL_SELECTION_POSITION = 4;
	int PENDING_IMAGES_LENGTH = 5;
	int INVENTORY_IMAGES_LENGTH = 3;
	int EBOARD_IMAGES_LENGTH = 4;
	int INVENTORY_SELECTION_POSITION =2;
	int CLOSURE_IMAGES_LENGTH = 5;
	int RESOURCE_IMAGES_LENGTH = 5;
	int ALERTS_IMAGES_LENGTH = 4;
	int EWALL_IMAGES_LENGTH = 3;

	int EBOARD_CASE = 3;

	int STATUS_AVAILABLE = 1;
	int STATUS_INMEETING = 2;
	int STATUS_OFFLINE = 3;
	int STATUS_FILELD_VISIT = 4;

	String EMAIL_ID = "Sanjay.Nand.Gupta@ril.com;jiofiberplus@gmail.com";
	String MOBILE_NO = "07718823771";
	String LANDLINE_NO = "02244788868";

	String USER = "user";
	String LOGIN_STATE= "login_state";
	String PLEASE_SELECT= "Please select";

	 String FIRST_TIME_DATA = "first_time_data";

	String USER_PERMISSION = "user_permissions";
	String ACTIVITY_LOGIN = "login";
	String HIDE_STRING = "MD5";
	String REQUEST_VAR = "Siteforge123";
	String REQUEST_FILE_FORMAT = "BKS";
	 double lat = 22.7253, lon = 75.8655;

	String DATA = "data";
	String FILTER_KEY_PRIORITY = "filter_key_priority";
	String KEY_TASK_ID = "task_id";
	String KEY_EDIT_TASK_ID = "edit_task_id";

	String SUCCESS = "";
	int STATUS_CODE = 404;

	String SUCCESS_MSG = "Success";
	String ERROR_MSG = "Failed";

	String PRIORIT_ALL="all";
	String PRIORIT_CRITICAL="critical";
	String PRIORIT_HIGH="high";
	String PRIORIT_MEDIUM="medium";
	String PRIORIT_LOW="low";

	String FOLDER_ASSIGNED_TO_ME="Assigned to me";
	String FOLDER_ASSIGNED_BY_ME="Assigned by me";
	String FOLDER_MY_TASK="My Task";
	String FOLDER_ALL_TASK="All Task";
	String FOLDER_COMPLETED="Work completed";
	String FOLDER_REVIEW="Review & closure";
	String FOLDER_OVERDUE="Overdue";
	String FOLDER_CLOSED="Closed tasks";

	String TASK_STATUS_COMPLETED="completed";
	String TASK_STATUS_OPEN="Open";
	String TASK_STATUS_CLOSED="Closed";
	String TASK_STATUS_REOPEN="Reopen";

	String HEADING_TODAY="Today";
	String HEADING_WEEK="Week";
	String HEADING_ALL="All";
	String HEADING_OTHER="Other";

	String TODAY="today";
	String TOMORROW="tomorrow";
	String THIS_WEAK="this_weak";
	String OTHER="other";
	String OVER_DUE="over_due";

	String JSON_DATA = "json_data";

	int IMAGE_WIDTH = 600;
	int IMAGE_HEIGHT = 600;
	int LARGE_IMAGE_HEIGHT = 1200;
	int SMALL_IMAGE_HEIGHT = 50;
	int IMAGE_THUMB_WIDTH = 100;
	int IMAGE_THUMB_HEIGHT = 100;
	double max_image_size = 10485760;
	String REMOVE_DATA = "Are you sure you want to remove?";

	//Accept Reasons
	String ACCEPT_ALERT_PATROLLER="ALERT PATROLLER";//Valued
	String ACCEPT_ASSIGED_PATROLLER="ASSIGN PATROLLER";//Important
	String ACCEPT_ASSIGED_ENGG="ASSIGN ENGINEER";//Important

	//Reject Reasons
	String REJECT_ACTION_TAKEN ="ACTION TAKEN";//Submitted
	String REJECT_FAKE_SPAM ="FAKE or SPAM";//Submitted
	String REJECT_IRRELEVANT ="IRRELEVANT";//Submitted


	String THREE = "R2FuZXNo";
	String JIOFIBER_ATTACHMENT_DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getPath() + "/.JioFiber/Images/Feedback";
	String JIO_FIBER_UPDATED_APK_DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getPath() + "/.JioFiber/apk";
	String APKNAME = "JioFiber+.apk";

	String USER_PROFILE="User Profile";
	String HELP_SECTION="Help";




	String FAULTSTITLE="Pending Fiber Faults";
	String POSTREPAIRTITLE="Pending Post-Repair";
	String RCA_TITLE="Pending RCA Cases";
	String IMPROVEMENT_TITLE="Pending Improvement";
	String HELPDESK_TITLE="Issue Dashboard";//"Pending HelpDesk Cases";

	//Closure

	String CLOSUREFAULTSTITLE="Closed Fiber Faults";
	String CLOSUREPOSTREPAIRTITLE="Closed Post-Repair";
	String CLOSURE_RCA_TITLE="Closed RCA Cases";
	String CLOSURE_IMPROVEMENT_TITLE="Closed Improvement";
	String CLOSURE_HELPDESK_TITLE="Closed HelpDesk Cases";

	//Alerts
	String TODO="To-Do Tasks Summary";
	String TASK_DETAIL="Task Details";
	String TASKLIST="Tasks List";
	String CREATE_NEW_TASK="Create New Task";

	//HelpDesk
	String ADD_ISSUE="Log Issues";
	String ISSUE_LIST="Issues List";
	String HELPDESK_DETAIL="Issue Detail";
	String ISSUE_STATUS_RESOLVED="Resolved";
	String ISSUE_STATUS_OPEN="Open";
	String ISSUE_STATUS_CLOSED="Closed";
	String ISSUE_STATUS_REOPEN="Reopen";
	String KEY_EDIT_ISSUE_ID="key_edit_issue_id";
	String KEY_SET_DUEDATE_ISSUE_ID="key_set_duedate_issue_id";



    String JIOMITRA_DASHBOARD="Feedbacks Dashboard";
	String JIOMITRA="Feedbacks Summary";
	String JIOMITRA_FEEDBACK_LIST="Span Feedbacks List";
	String JIOMITRA_FEEDBACK_DETAIL="Feedback Details";
	String HELP_DESK="Help Desk";
	String PATROLLER_ALLOC="Patroller Allocation List";
	String PATROLLER_ASSIGNEMENT="Patroller Assignment";

	//Resources
	String MANPOWER="Manpower";
	String TOOLS="Tools";
	String MATERIALS="Materials";
	String PATROLLER="Patroller";
	String PATROLLER_LIST="Patroller List";
	String PATROLLER_DASHBOARD="Patroller Dashboard";
	String ADD_PATROLLER="Patroller Onboarding";
	String PATROLLER_DETAILS="Patroller's Details";
	String PATROLLER_PERFORM="Patroller's Performance";

	String FRT="FRT List";
	String FRTMAP="FRT Location @ Map";


	String TT_LIST="Trouble Ticket";

	//eWall
	String OVERALL="Overall Posts";
	String INDIVIDUAL="Individual Posts";
	String COMMON="Common Posts";
	String MYPOST="My Posts";
	String NEWPOSTS="New Post";
	String POSTDETAILCOMMENT="Posts Details";
	String POST_TYPE_DIRECTIVES="Directives";
	String POST_TYPE_MEMOS="Memos";
	String POST_TYPE_GUIDELINS="Guidelines & SOPs";
	String POST_TYPE_MISCELLANEOUS="Miscellaneous";


	String FORWARD_EDIT="Forward/ Edit Posts";
	String SELECT_VALID_TIME = "Pleas select valid time";
	//gcm constants

	String APIKEY = "AIzaSyAjjIwlJhWaRmQwC27PBEQ_XzHf9J7qw7c";
	String SENDERID = "171763630853";
	String PLATFORM = "JIOFIBERPLUS";


	//Changes by AbhishekS
	public int COMMENT_TAB_SELECTED = 0;
	public int SUBTASK_TAB_SELECTED = 1;
	public int ATTACHMENTS_TAB_SELECTED = 2;
	public String KEY_DUE_DATE_TITLE= "DUE_DATE_TITLE";

	public int FORWARD_POST = 1;
	public int EDIT_POST = 2;
	public int DELETE_POST = 3;
	public String KEY_ACTION_ON_POST = "ACTION_ON_POST";

	public String STORAGE_PATH = Environment.getExternalStorageDirectory().getPath();
	public String STORAGE_PATH_FOR_JIO_FIBRE_PLUS = STORAGE_PATH + File.separator + ".JioFiber";
	public String STORAGE_PATH_FOR_EWALL = STORAGE_PATH_FOR_JIO_FIBRE_PLUS + File.separator + "EWall";
	public String STORAGE_PATH_FOR_TODO = STORAGE_PATH_FOR_JIO_FIBRE_PLUS + File.separator + "ToDo";
	public String STORAGE_PATH_FOR_EWALL_ATTACHMENTS = STORAGE_PATH_FOR_EWALL  + File.separator + "Attachments";
	public String STORAGE_PATH_FOR_TODO_ATTACHMENTS = STORAGE_PATH_FOR_TODO  + File.separator + "Attachments";
	public String STORAGE_PATH_FOR_PATROLLER = STORAGE_PATH_FOR_JIO_FIBRE_PLUS  + File.separator + "Patrollers";
	public String STORAGE_PATH_FOR_PROFILE = STORAGE_PATH_FOR_JIO_FIBRE_PLUS  + File.separator + "Profile";
	public String STORAGE_PATH_FOR_USER_PROFILE = STORAGE_PATH_FOR_JIO_FIBRE_PLUS  + File.separator + "USerProfile";

	public int MAXIMUM_EWALL_ATTACHMENTS_ALLOWED = 10;
	public String PATROLLER_KEY = "patroller";
	public String PATROLLER_UPDATE_ACTION = "PATROLLER_UPDATE_ACTION";
	public String SPAN_KEY = "assigned_span";
	///


	//Patroller Action Taken
	String VALIDATED = "Validate";//
	String REJECT = "Reject";
	String CLOSE = "Closed";
	String REOPEN = "Reopen";//
	String ACTION_ACK = "Acknowledge";//
	String ACTION_SUPERVISION = "Supervision";//
	String ACTION_REQ_FRT = "Request for FRT";//
	String ACTION_REQ_HELP="Request for other help";//
	String TOTAL_COUNT = "total_count";//
	String FILTER_KEY = "filter_key";//
	String TOTAL_PTLR = "total_ptlr";
	String ACTIVE = "active";
	String INACTIVE = "inactive";
	String OFFLINE = "offline";
	String AWAY = "away";
	String ASSIGNED_SPAN = "assigned_span";
	String UNASSIGNED_SPAN = "unassigned_span";

	//Action Display String for Patroller and MPJC;
		 String PATROLLER_ACTION_UPDATE = "Update";
		 String PATROLLER_ACTION_SUPERVISION = "Supervising";
		 String PATROLLER_ACTION_FRT = "FRT Requested";
		 String PATROLLER_ACTION_HELP = "Help Requested";
		 String PATROLLER_ACTION_REJECT = "Rejected";
         String PATROLLER_ACTION_CLOSED= "Closed";
		 String MPJC_ACTION_UPDATE = "Update";
		 String MPJC_ACTION_REJECT = "Rejected";
         String MPJC_ACTION_REOPENED= "Reopened";
         String MPJC_ACTION_CLOSED= "Closed";

    //JioMitraAlertsDashboard Constants
    public enum TimeFilter{
        LAST24HRS("24HOUR"),
        LAST7DAYS("7DAYS"),
        LAST30DAYS("30DAYS"),
        LASTQUARTER("QUARTER");

        private final String value;

        TimeFilter(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }



    public enum FeedbackType{
        TOTALFEEDBACK("TOTAL"),
        MITRAFEEDBACK("MITRA"),
        PATROLLERFEEDBACK("PATROLLER");

        private final String value;

        FeedbackType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum FeedbackCategory{
        DIGBYJCB("Roadside digging by JCB"),
        DIGBYLABOUR("Roadside digging by labours"),
        FIBERSAGGING("Fiber Cable sagging/ on ground"),
        FIREROAD("Fire on roadside"),
        FIBERMANHOLE("Open or broken fiber manhole"),
        TABDIGBYJCB("\tRoadside digging by JCB"),
        TABDIGBYLABOUR("\tRoadside digging by labours"),
        TABFIBERSAGGING("\tFiber Cable sagging/ on ground"),
        TABFIREROAD("\tFire on roadside"),
        TABFIBERMANHOLE("\tOpen or broken fiber manhole"),
        PENDING("PENDING"),
        REJECTED("REJECTED"),
        CLOSED("CLOSE"),
        PATACK("ACKNOWLEDGE");

        private final String value;

        FeedbackCategory(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    String BUNDLE_GEO_FILTER_REGION_KEY = "GEO_FILTER_REGION";
    String BUNDLE_GEO_FILTER_R4G_KEY = "GEO_FILTER_R4G";
    String BUNDLE_GEO_FILTER_MP_KEY = "GEO_FILTER_MP";
    String BUNDLE_GEO_FILTER_JC_KEY = "GEO_FILTER_JC";
    String BUNDLE_TIME_FILTER_KEY = "TIME_FILTER_SELECTED";
    String BUNDLE_FEEDBACK_TYPE_KEY = "FEEDBACK_TYPE_SELECTED";
    String BUNDLE_FEEDBACK_ALERT_TYPE_KEY = "FEEDBACK_ALERT_TYPE_SELECTED";
    String BUNDLE_FEEDBACK_STATUS_KEY = "FEEDBACK_STATUS_SELECTED";
    String BUNDLE_FEEDBACK_SPAN_COUNT_KEY = "FEEDBACK_SPAN_COUNT";
    String BUNDLE_FEEDBACK_ISFILTERED_KEY = "FEEDBACK_ISFILTERED";


	//Action Patroller ;
	String PTR_COMMENT_CATEGORY_SPAN = "SPAN_CHAT";
	String PTR_COMMENT_CATEGORYE_COMMON = "COMMON_CHAT";
	String PTR_COMMENT_SENDER_JFP = "JFP_USER";
	String PTR_COMMENT_SENDER_PTR = "PATROLLER";

	//Map key

		int ALL_PATROLLER_ROUTE =1;//
		int CURRENT_PATROLLER_ROUTE =2;//
		int PATROLLER_HISTORRY_ROUTE=3;//
		int ALL_FRT_ROUTE =4;//
		int CURRENT_FRT_ROUTE =  5;//
		int FRT_HISTORRY_ROUTE = 6;//

	/* HelpDesk Constant*/

	String HELP_DESK_FOLDER_ALL_ISSUE="All issue";
	String HELP_DESK_FOLDER_RAISED_TO_ME="Raised to me";
	String HELP_DESK_FOLDER_RAISED_BY_ME="Raised by me";
	String HELP_DESK_FOLDER_RESOLVED="Issue resolved";
	String HELP_DESK_FOLDER_REVIEW="Acknowledged";
	String HELP_DESK_FOLDER_OVERDUE="Overdue";
	String HELP_DESK_FOLDER_CLOSED="Closed issue";


	int SEARCH_LIST=0;
	int FILTER_LIST=1;
	int DEFAULT=3;

	int PENDING=1;
	int ACTIVATED=2;

	String PENDING_STR="PENDING";
	String APPROVED_STR="APPROVED";
	String ACTIVATED_STR="ACTIVATED";
	String REJECTED_STR="REJECTED";
	String BLOCKED_STR="BLOCKED";


	String NHQ_APPROVER_STR="'NHQ Executive','NOC Executive','RHQ Executive'";//'NHQ Approver',
	String SHQ_APPROVER_STR="'SHQ Executive','MP Executive','JC Executive'";//'SHQ Approver',
	String ADMIN_STR="'ADMIN'";


}
