package com.thunder.pay.constant;

public interface MessageConstant {

    String SUCCESS = "success";



    String WRONG_LOGIN_MSG = "Please enter a valid username or password";
    String Exception = "There is some problem in fetching data";
    String UPLOAD_Exception = "There is some problem on uploading data";
    String REQUEST_TIMEOUT = "Request Time out, Please try again";

    String SERVER_DOWN = "Unknown error occured!!!Please try later";
    String NETWORK_NOT_AVAILABLE = "Network not available";
    String SESSION_EXPIRED = "Your session has been expired, Please login again!";
    String GENERIC_ERROR = "Oops. Something went wrong.";

    /* User registation constant */
    int Home = 0;
    int Cash_Back = 1;
    int Gift_Cards = 2;
    int Accounts = 3;
        int SUB_Accounts_1 = 0;
        int SUB_Accounts_2 = 1;
    int Money_Transfer = 4;
        int SUB_Money_Transfer_1 = 0;
        int SUB_Money_Transfer_2 = 1;
    int Transaction_Histroy = 5;
        int SUB_Transaction_Histroy_1 = 0;
        int SUB_Transaction_Histroy_2 = 1;
    int Airtime_Top_Up = 6;
    int Check_Deposit = 7;
    int Bills_Payment = 8;
    int Settings = 9;
    int About = 10;
    int Help = 11;

	/* Todo message constant */

    String TODO_DATE_BLANK = "Due date can't be blank";
    String TODO_PLEASE_SELECT_PRIORITY = "Please select priority";
    String TODO_PLEASE_SELECT_ASSIGNE = "Please select assign to user";
    String TODO_DATE_AND_TIME = "Due Date & Time should be greater than current time";
    String TODO_PRIORITY_CANT_BLANK = "Priority can't be blank";
    String TODO_DESCRIPTION_CANT_BLANK = "Description can not be null";
    String TODO_DELETE_FILDER = "Are you sure you want to delete this folder?";
    String TODO_SUBTASK_CREATE = "You can not create subtask here!";
    String TODO_FOLDER_BLANK = "Folder name can't be blank.";
    String TWO = "TGFrc2htaQ==";
    String TODO_FOLDER_EXIST = "Folder name already exist.";
    String TODO_TASK_DELETE_MSG = "Are you sure? You want to delete this task!";
    String TODO_TASK_DELETE_SUCCESS = "Task deleted successfully";
    String TODO_TASK_DELETE_FAILURE = "Unable to delete this task, please try latter";

	/*  Ewaal constant  */

    String EWALL_DELEDE_ATTACHMENT_SUCCESS = "Attachment Deleted Successfully";
    String EWALL_DELEDE_ATTACHMENT_FAILD = "Unable to Delete Attachment";
    String EWALL_DELETE_SUCCESS = "Post delete successfully";
    String EWALL_DELETE_FAILURE = "Unable to delete this post, please try latter";
    String EWALL_DELETE_MSG = "Are you sure? You want to delete this post!";
    String CANT_DELETE_THIS_POST = "You can not delete this post";

	/* Help Desk message constant */

    String HD_ISSUE_NAME_BLANK = "Issue name can not be blank";
    String HD_PLESE_SELECT_ISSUE_TYPE = "Plese select issue type";
    String HD_PLESE_SELECT_ISSUE_IMPACT = "Plese select issue impact";
    String HD_PLESE_SELECT_ISSUE_PRIORITY = "Plese select issue priority";
    String HD_DUE_DATE_BLANK = "Due date can not be blank";
    String HD_EX_DUE_DATE_BLANK = "Expected due date can't be blank";
    String HD_DUE_DATE_TIME = "Due Date & Time should be greater than current time";
    String HD_EX_DUE_DATE_TIME = " Expected due Date & Time should be greater than current time";
    String HD_RESOLVER_NAME = "Please select resolver ";
    String ISSUE_CREATED_SUCCESS = "Issue raised successfully";
    String ISSUE_UPDATED_SUCCESS = "Issue updated successfully";
    String UNLINK_TITLE = "Unlink Issue";
    String UNLINK_MSG = "Are you sure? You want to unlink this issue!";
    String DELETE_TITEL = "Delete ";
    String DELETE_MSG = "Are you sure? You want to delete this issue!";
    String ISSUE_DELETE_SUCCESS = "Issue delete successfully";
    String ISSUE_DELETE_FAILURE = "Unable to delete this issue, please try later";


    String ERROR_MSG = "This field is required";
    String TASK_CREATED_SUCCESS = "Task Created successfully";
    String TASK_UPDATED_SUCCESS = "Task updated successfully";
    String TASK_NAME_BLANK = "Task name can't be blank";
    String EWALL_DESCRIPTION_VALIDATION = "Description is required";
    String EWALL_USER_SELECTION_VALIDATION = "Invalid username";
    String EWALL_URGENCY_SELECTION_VALIDATION = "Invalid message type";
    String MAX_ATTACHMENT_VALIDATION = "You have attached maximum number of files.";


    String PATROLLER_ADDING_ALERT = "You are not priviledge with this action";
    String SPAN_DOWNLOAD_REFRESH = "Please select below action button to re-download or refresh Span data.";

    String CONNECTION_ERROR = "Please check your internet connection.";

}
