package com.fama.app.fragments.account.managebeneficiary;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.fama.app.R;
import com.fama.app.adapter.commonadaptter.MyCustomAdapter;
import com.fama.app.constant.MessageConstant;
import com.fama.app.daomodel.DataHandler;
import com.fama.app.dbhelper.InventoryDBHelper;
import com.fama.app.greendaodb.AccountDetail;
import com.fama.app.greendaodb.AdBeneficiaryDetails;
import com.fama.app.greendaodb.BankDetail;
import com.fama.app.greendaodb.ErrorModel;
import com.fama.app.greendaodb.Inventory;
import com.fama.app.greendaodb.User;
import com.fama.app.rest.RestCall;
import com.fama.app.rest.RestClient;
import com.fama.app.util.AppUtills;
import com.fama.app.util.ErrorUtills;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.fragments.BackConfirmationFragment;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBeneficiaryFragment extends Fragment implements VerticalStepperForm {

    Context mContext;
    public static final String NEW_BENEFICIARY_ADDED = "new_beneficiary_added";

    ArrayList<BankDetail> accountNoList = new ArrayList<>();
    BankDetail bankDetail = new BankDetail();

    // Information about the steps/fields of the form
    private static final int BANK_ACC_NO_STEP_NUM = 0;
    private static final int BENEFICIARY_NAME_STEP_NUM = 1;
    private static final int BENEFICIARY_ACC_NO_STEP_NUM = 2;
    private static final int BENEFICIARY_ACC_NO_CONFIRM_STEP_NUM = 3;
    private static final int BANK_CODE_STEP_NUM = 4;

    // BANK_NAME_STEP
    private Spinner spinnerAccNo;
    public  String ACC_NUMBER;
    // BRANCH_NAME
    private EditText beneficiaryName;
    public  String BENEFICIARY_NAME;
    // BENEFICIARY_ACC_NO
    private EditText beneficiaryAccNo;
    public  String BENEFICIARY_ACCOUNT_NUMBER;
    // BENEFICIARY_ACC_NO_CONFIRM
    private EditText beneficiaryAccNoConfirm;
    public  String BENEFICIARY_ACCOUNT_NUMBER_CONFIRM;
    // PASSWORD
    private EditText bankCode;
    public  String BANK_CODE;

    // Time step
    private TextView dateTimeTextView;
    private TimePickerDialog timePicker;
    private DatePickerDialog datePicker;

    public static final String STATE_DAY = "day";
    public static final String STATE_MONTH = "month";
    public static final String STATE_YEAR = "year";

    private Pair<Integer, Integer> time;
    public static final String STATE_TIME_HOUR = "time_hour";
    public static final String STATE_TIME_MINUTES = "time_minutes";

    private boolean confirmBack = true;
    private VerticalStepperFormLayout verticalStepperForm;

    CheckBox checkTerms;

    public enum Single {
        INSTANCE;
        AddBeneficiaryFragment s = new AddBeneficiaryFragment();

        public AddBeneficiaryFragment getInstance() {
            return new AddBeneficiaryFragment();
//            if (s == null)
//                return new AddBeneficiaryFragment();
//            else return s;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        ViewGroup mainView = (ViewGroup) inflater.inflate(R.layout.fama_vertical_stepper_form, container, false);
        if (mainView != null) {
            mainView.setFocusableInTouchMode(true);
            mainView.requestFocus();
            mainView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                    }
                    return false;
                }
            });
        }
        AppUtills.setActionBarTitle("Add Beneficiary", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

        return mainView;
//        return inflater.inflate(R.layout.about, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Inventory inventory = InventoryDBHelper.single.INSTANCE.getInstnce().getItemList(getActivity(),"");
        if(inventory!=null){
            loadBankList(""+inventory.getUserid());
        }
        initializeActivity(view);
    }

    private void initializeActivity(View view) {

        TextView termsCondition =(TextView)view.findViewById(R.id.termsCondition);
        checkTerms = (CheckBox) view.findViewById(R.id.checkTermsCondition);

        AppUtills.setUnderLine(termsCondition,"Terms & Condition");

        int colorPrimary = ContextCompat.getColor(mContext, R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(mContext, R.color.colorPrimaryDark);
        String[] stepsTitles = getResources().getStringArray(R.array.steps_add_beneficiary);

        // Here we find and initialize the form
        verticalStepperForm = (VerticalStepperFormLayout) view.findViewById(R.id.vertical_stepper_form);
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, stepsTitles, this, getActivity())
                .stepsSubtitles(stepsTitles)
//                .materialDesignInDisabledSteps(true) // false by default
                .showVerticalLineWhenStepsAreCollapsed(true) // false by default
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false)
                .init();

        initDataVar();
    }

    // METHODS THAT HAVE TO BE IMPLEMENTED TO MAKE THE LIBRARY WORK
    // (Implementation of the interface "VerticalStepperForm")

    @Override
    public View createStepContentView(int stepNumber) {
        // Here we generate the content view of the correspondent step and we return it so it gets
        // automatically added to the step layout (AKA stepContent)
        View view = null;
        switch (stepNumber) {
            case BANK_ACC_NO_STEP_NUM:
                view = createBankNameSpinner();
                break;
            case BENEFICIARY_NAME_STEP_NUM:
                view = createBeneficiaryName();
                break;
            case BENEFICIARY_ACC_NO_STEP_NUM:
                view = createBankAccountNo();
                break;
            case BENEFICIARY_ACC_NO_CONFIRM_STEP_NUM:
                view = createBankAccountNoConfirm();
                break;
            case BANK_CODE_STEP_NUM:
                view = createBankCode();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case BANK_ACC_NO_STEP_NUM:
                if (ErrorUtills.isSpinnerValid(spinnerAccNo)) {
//                    verticalStepperForm.setStepSubtitle(stepNumber, "" + SENDER_EMAIL);
                    verticalStepperForm.setActiveStepAsCompleted();
                }else{
                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_select_acc_no));
                }
                break;
            case BENEFICIARY_NAME_STEP_NUM:
                if (ErrorUtills.checkTextNull(BENEFICIARY_NAME)) {
                    verticalStepperForm.setStepSubtitle(stepNumber, "" + BENEFICIARY_NAME);
                    verticalStepperForm.setStepAsCompleted(stepNumber);
                }else{
                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_enter_beneficiary_name));
                }
                break;
            case BENEFICIARY_ACC_NO_STEP_NUM:
                if (ErrorUtills.checkTextMinLength(BENEFICIARY_ACCOUNT_NUMBER, 16)) {
                    verticalStepperForm.setStepSubtitle(stepNumber, "" + BENEFICIARY_ACCOUNT_NUMBER);
                    verticalStepperForm.setStepAsCompleted(stepNumber);
                }else{
                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_enter_beneficiary_acc_no));
                }
                break;
            case BENEFICIARY_ACC_NO_CONFIRM_STEP_NUM:
                if (ErrorUtills.checkTextMinLength(BENEFICIARY_ACCOUNT_NUMBER_CONFIRM, 16)) {
                    verticalStepperForm.setStepSubtitle(stepNumber, "" + BENEFICIARY_ACCOUNT_NUMBER_CONFIRM);
                    verticalStepperForm.setStepAsCompleted(stepNumber);
                }else{

                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_reenter_beneficiary_acc_no));
                }
                break;
            case BANK_CODE_STEP_NUM:
                if (ErrorUtills.checkTextMinLength(BANK_CODE, 6)) {
                    if(!checkTerms.isChecked()){
                        Toast.makeText(mContext, "Please agree to T&C", Toast.LENGTH_SHORT).show();
                        verticalStepperForm.setActiveStepAsUncompleted("Please agree to T&C");
                    }else {
                        verticalStepperForm.setStepSubtitle(stepNumber, "" + BANK_CODE);
                        verticalStepperForm.setStepAsCompleted(stepNumber);
                    }
                }else{
                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_enter_bank_code));
                }
                break;
        }
    }

    private void initDataVar(){
        try {
            ACC_NUMBER = accountNoList.get(spinnerAccNo.getSelectedItemPosition()).getAccountNumber().trim();
            BENEFICIARY_NAME = beneficiaryName.getText().toString().trim();
            BENEFICIARY_ACCOUNT_NUMBER = beneficiaryAccNo.getText().toString().trim();
            BENEFICIARY_ACCOUNT_NUMBER_CONFIRM = beneficiaryAccNoConfirm.getText().toString().trim();
            BANK_CODE = bankCode.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendData() {

        try {
            initDataVar();

            AdBeneficiaryDetails adBeneficiaryDetails = new AdBeneficiaryDetails();
            adBeneficiaryDetails.setIfscCode(BANK_CODE);
            adBeneficiaryDetails.setAccountHolderName(BENEFICIARY_NAME);
            adBeneficiaryDetails.setAccountNumber(BENEFICIARY_ACCOUNT_NUMBER);


            User user = new User();
            user.setUserid(DataHandler.Single.INSTANCE.getInstance().getInventory().getUserid()+"");
            AccountDetail accountDetail = new AccountDetail();
            accountDetail.setId(accountNoList.get(spinnerAccNo.getSelectedItemPosition()).getId()+"");
            adBeneficiaryDetails.setUser(user);
            adBeneficiaryDetails.setAccountDetail(accountDetail);
            adBeneficiaryDetails.setIfscCode(BANK_CODE);
            System.out.println(" FInal JSON "+ new Gson().toJson(adBeneficiaryDetails));

            confirmAdd(adBeneficiaryDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // OTHER METHODS USED TO MAKE THIS EXAMPLE WORK


    private View createBankNameSpinner() {
        spinnerAccNo = new Spinner(mContext);
        MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), accountNoList, "Account");
        if (accountNoList != null)
            spinnerAccNo.setAdapter(adapter);
        spinnerAccNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    ACC_NUMBER = accountNoList.get(position).getAccountNumber();
                    verticalStepperForm.setStepSubtitle(BANK_ACC_NO_STEP_NUM, "" + ACC_NUMBER);
                    verticalStepperForm.setActiveStepAsCompleted();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted("Select Account Number");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return spinnerAccNo;
    }

    private View createBeneficiaryName() {
        beneficiaryName = new EditText(mContext);
        beneficiaryName.setHint(R.string.hint_enter_beneficiary_name);
        beneficiaryName.setSingleLine(true);
        ErrorUtills.setEditTextLimit(beneficiaryName,20);

        beneficiaryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextNull(s.toString())) {
                    verticalStepperForm.setStepSubtitle(BENEFICIARY_NAME_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setActiveStepAsCompleted();
                    BENEFICIARY_NAME = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted("Enter Beneficiary Name");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return beneficiaryName;
    }


    private View createBankAccountNo() {
        beneficiaryAccNo = new EditText(mContext);
        beneficiaryAccNo.setHint(R.string.hint_enter_account_no);
        beneficiaryAccNo.setSingleLine(true);
        beneficiaryAccNo.setInputType(InputType.TYPE_CLASS_NUMBER);
        beneficiaryAccNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(beneficiaryAccNo,16);
        beneficiaryAccNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextMinMaxLength(s.toString(),16)) {
                    verticalStepperForm.setStepSubtitle(BENEFICIARY_ACC_NO_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(BENEFICIARY_ACC_NO_STEP_NUM);
                    BENEFICIARY_ACCOUNT_NUMBER = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_enter_acc_no));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return beneficiaryAccNo;
    }

    private View createBankAccountNoConfirm() {
        beneficiaryAccNoConfirm = new EditText(mContext);
        beneficiaryAccNoConfirm.setInputType(InputType.TYPE_CLASS_NUMBER);
        beneficiaryAccNoConfirm.setHint(R.string.hint_reenter_account_no);
        beneficiaryAccNoConfirm.setSingleLine(true);
        beneficiaryAccNoConfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(beneficiaryAccNoConfirm,16);
        beneficiaryAccNoConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                BENEFICIARY_ACCOUNT_NUMBER_CONFIRM = s.toString();

                if (ErrorUtills.checkTextMinMaxLength(s.toString(),16)) {
                    if(BENEFICIARY_ACCOUNT_NUMBER.equalsIgnoreCase(BENEFICIARY_ACCOUNT_NUMBER_CONFIRM)) {
                        verticalStepperForm.setStepSubtitle(BENEFICIARY_ACC_NO_CONFIRM_STEP_NUM, "" + s.toString());
                        verticalStepperForm.setStepAsCompleted(BENEFICIARY_ACC_NO_CONFIRM_STEP_NUM);
                    }else{
                        verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_account_mismatch));
                    }
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_enter_acc_no));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return beneficiaryAccNoConfirm;
    }


    private View createBankCode() {
        bankCode = new EditText(mContext);
        bankCode.setHint(R.string.hint_enter_bank_code);
        bankCode.setSingleLine(true);
        bankCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        bankCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(bankCode,6);
        bankCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextMinMaxLength(s.toString(),6)) {
                    verticalStepperForm.setStepSubtitle(BANK_CODE_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(BANK_CODE_STEP_NUM);
                    BANK_CODE = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_enter_bank_code));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return bankCode;
    }


    private View createDateTimeStep() {
        // This step view is generated by inflating a layout XML file
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.step_time_layout, null, false);
        dateTimeTextView = (TextView) timeStepContent.findViewById(R.id.time);
        dateTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show();
            }
        });
        return timeStepContent;
    }


    private void setTimePicker(int hour, int minutes) {
        timePicker = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTime(hourOfDay, minute);
                    }
                }, hour, minutes, true);
    }

    private void setDatePicker() {

        Calendar newCalendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                System.out.println(newDate.getTime());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private boolean checkBankNameStep(String title) {
        boolean titleIsCorrect = false;
        if (title.length() >= 1) {
            titleIsCorrect = true;
            verticalStepperForm.setActiveStepAsCompleted();
            // Equivalent to: verticalStepperForm.setStepAsCompleted(BANK_ACC_NO_STEP_NUM);
        } else {
            String titleErrorString = getResources().getString(R.string.error_title_min_characters);
            String titleError = String.format(titleErrorString, 1);
            verticalStepperForm.setActiveStepAsUncompleted(titleError);
            // Equivalent to: verticalStepperForm.setStepAsUncompleted(BANK_ACC_NO_STEP_NUM, titleError);
        }
        return titleIsCorrect;
    }

    private void setTime(int hour, int minutes) {
        time = new Pair<>(hour, minutes);
        String hourString = ((time.first > 9) ?
                String.valueOf(time.first) : ("0" + time.first));
        String minutesString = ((time.second > 9) ?
                String.valueOf(time.second) : ("0" + time.second));
        String time = hourString + ":" + minutesString;
        dateTimeTextView.setText(time);
    }


    private void confirmBack() {
        if (confirmBack && verticalStepperForm.isAnyStepCompleted()) {
            BackConfirmationFragment backConfirmation = new BackConfirmationFragment();
            backConfirmation.setOnConfirmBack(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmBack = true;
                }
            });
            backConfirmation.setOnNotConfirmBack(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmBack = false;
                    getActivity().onBackPressed();
                }
            });
            backConfirmation.show(getActivity().getSupportFragmentManager(), null);
        } else {
            confirmBack = false;
            getActivity().onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && confirmBack) {
            return true;
        }
        return false;
    }

    private void loadBankList(String userId) {

        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);
        final Type listType = new TypeToken<List<BankDetail>>() {
        }.getType();

        if (AppUtills.isNetworkAvailable(mContext)) {
            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());

            final Call<ResponseBody> repos = service.getAccountDetailByUserId(userId);

            repos.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    accountNoList.removeAll(accountNoList);
                    bankDetail.setAccountNumber("Please Select");
                    accountNoList.add(0, bankDetail);
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        try {
                            String json = "";
                            if (response != null) json = response.body().string();
                            System.out.println("onResponse " + json);

                            if (json.contains("errorMsg")) {
                                try {
                                    ErrorModel errorModel = new ErrorModel();
                                    errorModel = new Gson().fromJson(json, ErrorModel.class);
                                    AppUtills.showErrorPopUp(getActivity(), errorModel.getErrorMsg());
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                List<BankDetail> bankDatas = new Gson().fromJson(json, listType);
                                accountNoList.addAll(bankDatas);
                                MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), accountNoList, "Account");
                                adapter.notifyDataSetChanged();
                                spinnerAccNo.setAdapter(adapter);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), MessageConstant.GENERIC_ERROR, Toast.LENGTH_LONG).show();
                    }
                    AppUtills.cancelProgressDialog(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AppUtills.cancelProgressDialog(dialog);
                    Toast.makeText(getActivity(), MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


    private void confirmAdd(final AdBeneficiaryDetails adBeneficiaryDetails) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
        builder.setTitle(mContext.getResources().getString(R.string.confirmation));
        builder.setMessage(mContext.getResources().getString(R.string.add_beneficiary_confirm));
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDataToServer(adBeneficiaryDetails);
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        verticalStepperForm.goToPreviousStep();
                    }
                });
        builder.create();
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    }


    private void sendDataToServer(AdBeneficiaryDetails bankDetail) {

        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);

        if (AppUtills.isNetworkAvailable(mContext)) {
            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());
            final Call<ResponseBody> response = service.associateAccount(bankDetail);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            String json = response.body().string();
                            System.out.println("sendDataToServer onResponse " + json);

                            if (json.contains("errorMsg")) {
                                try {
                                    ErrorModel errorModel = new ErrorModel();
                                    errorModel = new Gson().fromJson(json, ErrorModel.class);
                                    AppUtills.showErrorPopUp(getActivity(), errorModel.getErrorMsg());
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                    verticalStepperForm.goToPreviousStep();
                                }
                            } else {
                                try {
                                    AdBeneficiaryDetails details = new AdBeneficiaryDetails();
                                    details = new Gson().fromJson(json, AdBeneficiaryDetails.class);
                                    Toast.makeText(mContext, mContext.getResources().getString(R.string.add_beneficiary_success), Toast.LENGTH_LONG).show();
                                    getActivity().onBackPressed();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                    verticalStepperForm.goToPreviousStep();
                                }
                            }
                        }else{
                            Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                            verticalStepperForm.goToPreviousStep();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                        verticalStepperForm.goToPreviousStep();
                    }
                    AppUtills.cancelProgressDialog(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("sendDataToServer onResponse " + t.getMessage());
                    t.printStackTrace();
                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                    verticalStepperForm.goToPreviousStep();
                    AppUtills.cancelProgressDialog(dialog);
                }
            });

        }

    }

}
