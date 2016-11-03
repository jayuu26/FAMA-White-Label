package com.thunder.pay.fragments.moneytransfer.bkashtransfer;

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
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.thunder.pay.R;
import com.thunder.pay.constant.MessageConstant;
import com.thunder.pay.daomodel.DataHandler;
import com.thunder.pay.greendaodb.AdBeneficiaryDetails;
import com.thunder.pay.greendaodb.BankDetail;
import com.thunder.pay.greendaodb.ErrorModel;
import com.thunder.pay.greendaodb.FAMA;
import com.thunder.pay.greendaodb.Inventory;
import com.thunder.pay.greendaodb.TransferDetails;
import com.thunder.pay.rest.RestCall;
import com.thunder.pay.rest.RestClient;
import com.thunder.pay.util.AppUtills;
import com.thunder.pay.util.ErrorUtills;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.fragments.BackConfirmationFragment;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletToBkashkTransferFragment extends Fragment implements VerticalStepperForm {

    Context mContext;
    public static final String NEW_BENEFICIARY_ADDED = "new_beneficiary_added";

    ArrayList<BankDetail> accountNoList = new ArrayList<>();
    ArrayList<BankDetail> payeeList = new ArrayList<>();
    BankDetail bankDetail = new BankDetail();

    // Information about the steps/fields of the form
    private static final int SENDER_EMAIL_STEP_NUM = 0;
    private static final int AVAILABLE_BALANCE_STEP_NUM = 1;
    private static final int RECEIVER_BKASH_ACC_NO_STEP_NUM = 2;
    private static final int AMOUNT_STEP_NUM = 3;
    private static final int REMARK_STEP_NUM = 4;

    public String SENDER_EMAIL;
    public String BALANCE;
    public String RECIEVER_BKASH_ACC_NO;
    public String AMOUNT;
    public String REMARK;

    private EditText senderEmail;
    private EditText availBalance;
    private EditText recieverBkashAccNo;
    private EditText amount;
    private EditText remark;

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
    Inventory inventory;

    CheckBox checkTerms;

    public enum Single {
        INSTANCE;
        WalletToBkashkTransferFragment s = new WalletToBkashkTransferFragment();

        public WalletToBkashkTransferFragment getInstance() {
            return new WalletToBkashkTransferFragment();
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
        AppUtills.setActionBarTitle("Wallet2Bkash Transfer", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);

        return mainView;
//        return inflater.inflate(R.layout.about, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          inventory = DataHandler.Single.INSTANCE.getInstance().getInventory();//InventoryDBHelper.single.INSTANCE.getInstnce().getItemList(getActivity(), "");
        try {
            SENDER_EMAIL = inventory.getEmail();
            BALANCE = inventory.getFamaWallet().getCurrentAmount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeActivity(view);
    }

    private void initializeActivity(View view) {

        TextView termsCondition = (TextView) view.findViewById(R.id.termsCondition);
        checkTerms = (CheckBox) view.findViewById(R.id.checkTermsCondition);

        AppUtills.setUnderLine(termsCondition, "Terms & Condition");

        int colorPrimary = ContextCompat.getColor(mContext, R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(mContext, R.color.colorPrimaryDark);
        String[] stepsTitles = getResources().getStringArray(R.array.steps_w_to_bkash);

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
            case SENDER_EMAIL_STEP_NUM:
                view = createSenderEmail();
                break;
            case AVAILABLE_BALANCE_STEP_NUM:
                view = createBalance();
                break;
            case RECEIVER_BKASH_ACC_NO_STEP_NUM:
                view = createRecieverAccount();
                break;
            case AMOUNT_STEP_NUM:
                view = createAmount();
                break;
            case REMARK_STEP_NUM:
                view = createRemark();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case SENDER_EMAIL_STEP_NUM:
//                if (ErrorUtills.emailValidation(SENDER_EMAIL)) {
//                    verticalStepperForm.setActiveStepAsCompleted();
//                } else {
//                    verticalStepperForm.setActiveStepAsUncompleted(
//                            mContext.getResources().getString(R.string.error_select_acc_no));
//                }
                verticalStepperForm.setStepAsCompleted(stepNumber);
                verticalStepperForm.setStepSubtitle(stepNumber,""+SENDER_EMAIL);
                break;
            case AVAILABLE_BALANCE_STEP_NUM:
//                if (ErrorUtills.checkTextNull(BALANCE)) {
//                    verticalStepperForm.setStepSubtitle(stepNumber, "" + BALANCE);
//                    verticalStepperForm.setStepAsCompleted(stepNumber);
//                } else {
//                    verticalStepperForm.setActiveStepAsUncompleted("Low Balance");
//                }
                checkAvailableBalance();
//                verticalStepperForm.setStepAsCompleted(stepNumber);
//                verticalStepperForm.setStepSubtitle(stepNumber,""+ BALANCE);
                break;
            case RECEIVER_BKASH_ACC_NO_STEP_NUM:
                if (ErrorUtills.emailValidation(RECIEVER_BKASH_ACC_NO)) {
                    verticalStepperForm.setStepSubtitle(stepNumber, "" + RECIEVER_BKASH_ACC_NO);
                    verticalStepperForm.setStepAsCompleted(stepNumber);
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(
                            mContext.getResources().getString(R.string.error_enter_payee_acc_no));
                }
                break;
            case AMOUNT_STEP_NUM:
                if (ErrorUtills.checkTextMinLength(AMOUNT, 16)) {
                    verticalStepperForm.setStepSubtitle(stepNumber, "" + AMOUNT);
                    verticalStepperForm.setStepAsCompleted(stepNumber);
                } else {

                    verticalStepperForm.setActiveStepAsUncompleted(
                            mContext.getResources().getString(R.string.error_enter_amount));
                }
                break;
            case REMARK_STEP_NUM:
                if (ErrorUtills.checkTextMinLength(REMARK, 6)) {
                    if (!checkTerms.isChecked()) {
                        Toast.makeText(mContext, "Please agree to T&C", Toast.LENGTH_SHORT).show();
                        verticalStepperForm.setActiveStepAsUncompleted("Please agree to T&C");
                    } else {
                        verticalStepperForm.setStepSubtitle(stepNumber, "" + REMARK);
                        verticalStepperForm.setStepAsCompleted(stepNumber);
                    }
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(
                            mContext.getResources().getString(R.string.error_enter_remark));
                }
                break;
        }
    }

    private void initDataVar() {
        try {
            SENDER_EMAIL = senderEmail.getText().toString().trim();
            BALANCE = availBalance.getText().toString().trim();
            RECIEVER_BKASH_ACC_NO = recieverBkashAccNo.getText().toString().trim();
            AMOUNT = amount.getText().toString().trim();
            REMARK = remark.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendData() {

        try {
            initDataVar();

            TransferDetails transferDetails = new TransferDetails();
            transferDetails.setAmount(AMOUNT);
            transferDetails.setComment(REMARK);
            transferDetails.setReceiverBkash(RECIEVER_BKASH_ACC_NO);
            transferDetails.setSenderWalletEmail(SENDER_EMAIL);
            System.out.println(" FInal JSON " + new Gson().toJson(transferDetails));

            confirmTransfer(transferDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private View createSenderEmail() {
        senderEmail = new EditText(mContext);
        senderEmail.setHint(R.string.hint_enter_payee_acc_no);
        senderEmail.setEnabled(false);
        senderEmail.setSingleLine(true);
        senderEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
        senderEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(senderEmail, 30);
        senderEmail.setText(""+inventory.getEmail());
//        verticalStepperForm.setStepAsCompleted(SENDER_EMAIL_STEP_NUM);
//        verticalStepperForm.goToNextStep();
        senderEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.emailValidation(s.toString())) {
                    verticalStepperForm.setStepSubtitle(SENDER_EMAIL_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(SENDER_EMAIL_STEP_NUM);
                    RECIEVER_BKASH_ACC_NO = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(
                            mContext.getResources().getString(R.string.error_enter_email));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return senderEmail;
    }

    private View createRecieverAccount() {
        recieverBkashAccNo = new EditText(mContext);
        recieverBkashAccNo.setHint(R.string.hint_enter_payee_acc_no);
        recieverBkashAccNo.setSingleLine(true);
        recieverBkashAccNo.setInputType(InputType.TYPE_CLASS_NUMBER);
        recieverBkashAccNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(recieverBkashAccNo, 16);
        recieverBkashAccNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextMinLength(s.toString(), 16)) {
                    verticalStepperForm.setStepSubtitle(RECEIVER_BKASH_ACC_NO_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(RECEIVER_BKASH_ACC_NO_STEP_NUM);
                    RECIEVER_BKASH_ACC_NO = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(
                            mContext.getResources().getString(R.string.error_enter_payee_acc_no));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return recieverBkashAccNo;
    }

    private View createAmount() {
        amount = new EditText(mContext);
        amount.setInputType(InputType.TYPE_CLASS_NUMBER);
        amount.setHint(R.string.hint_enter_amount);
        amount.setSingleLine(true);
        amount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(amount, 16);
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                AMOUNT = s.toString();

                if (ErrorUtills.checkTextNull(s.toString())) {
                    checkTransferAmount(s.toString(),AMOUNT_STEP_NUM);

                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(
                            mContext.getResources().getString(R.string.error_enter_amount));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return amount;
    }


    private View createRemark() {
        remark = new EditText(mContext);
        remark.setHint(R.string.hint_enter_remark);
        remark.setSingleLine(false);
        remark.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(500,200); // Width , height
        remark.setLayoutParams(lparams);

        remark.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(remark, 50);
        remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextNull(s.toString())) {
                    verticalStepperForm.setStepSubtitle(REMARK_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(REMARK_STEP_NUM);
                    REMARK = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(
                            mContext.getResources().getString(R.string.error_enter_remark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return remark;
    }

    private View createBalance() {
        availBalance = new EditText(mContext);
        availBalance.setHint(R.string.hint_enter_amount);
        availBalance.setSingleLine(true);
        availBalance.setInputType(InputType.TYPE_CLASS_NUMBER);
        availBalance.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        availBalance.setEnabled(false);
        ErrorUtills.setEditTextLimit(availBalance, 50);
//        checkAvailableBalance();
        availBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextNull(s.toString())) {
                    verticalStepperForm.setStepSubtitle(AVAILABLE_BALANCE_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(AVAILABLE_BALANCE_STEP_NUM);
                    BALANCE = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted("Low Balance");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return availBalance;
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
            // Equivalent to: verticalStepperForm.setStepAsCompleted(SENDER_EMAIL_STEP_NUM);
        } else {
            String titleErrorString = getResources().getString(R.string.error_title_min_characters);
            String titleError = String.format(titleErrorString, 1);
            verticalStepperForm.setActiveStepAsUncompleted(titleError);
            // Equivalent to: verticalStepperForm.setStepAsUncompleted(SENDER_EMAIL_STEP_NUM, titleError);
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


    private void confirmTransfer(final TransferDetails transferDetails) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
        builder.setTitle(mContext.getResources().getString(R.string.confirmation));
        builder.setMessage(mContext.getResources().getString(R.string.amount_confirm));
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDataToServer(transferDetails);
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


    private void sendDataToServer(TransferDetails transferDetails) {

        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);

        if (AppUtills.isNetworkAvailable(mContext)) {
            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());
            final Call<ResponseBody> response = service.walletToBkashTransfer(transferDetails);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            String json = "";
                            if (response != null)
                                json = response.body().string();
                            System.out.println("sendDataToServer onResponse " + json);

                            if (json.contains("errorMsg")) {
                                try {
                                    ErrorModel errorModel = new ErrorModel();
                                    errorModel = new Gson().fromJson(json, ErrorModel.class);
                                    AppUtills.showErrorPopUpBack(getActivity(),""+errorModel.getErrorMsg());
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                    verticalStepperForm.goToPreviousStep();
                                }
                                } else {
                                try {
                                    FAMA famaWallet = new Gson().fromJson(json, FAMA.class);
                                    DataHandler.Single.INSTANCE.getInstance().setFamaWallet(famaWallet);
                                    FAMA fama1 = DataHandler.Single.INSTANCE.getInstance().getFamaWallet();
                                    Toast.makeText(mContext, mContext.getResources().getString(R.string.amount_transfer_success), Toast.LENGTH_LONG).show();
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
                    AppUtills.cancelProgressDialog(dialog);
                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                    verticalStepperForm.goToPreviousStep();
                }
            });

        }

    }

    public void checkTransferAmount(String transferAmount,int position) {
        double transAmount = Double.parseDouble(transferAmount);
        double availAmount = Double.parseDouble(BALANCE);
        if (availAmount < transAmount) {
            AppUtills.showLowBalance(getActivity(), mContext.getResources().getString(R.string.error_wallet_low_balance),false);
            verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_enter_amount));
        } else {
            verticalStepperForm.setStepSubtitle(position, "" + transAmount);
            verticalStepperForm.setStepAsCompleted(position);
        }
    }

    public void checkAvailableBalance() {

        FAMA fama = DataHandler.Single.INSTANCE.getInstance().getFamaWallet();
        String balanceAmount = fama.getCurrentAmount();
        double availAmount = 0;
        if(balanceAmount!=null && !balanceAmount.equalsIgnoreCase(""))
            availAmount = Double.parseDouble(balanceAmount);

            if (availAmount <= 0) {
                AppUtills.showLowBalance(getActivity(), mContext.getResources().getString(R.string.error_wallet_low_balance),true);
                verticalStepperForm.goToPreviousStep();
            } else {

                availBalance.setText(""+balanceAmount);
                verticalStepperForm.setStepAsCompleted(AVAILABLE_BALANCE_STEP_NUM);
                verticalStepperForm.setStepSubtitle(AVAILABLE_BALANCE_STEP_NUM,""+ availAmount);
                verticalStepperForm.goToNextStep();
                BALANCE = ""+availAmount;
            }

    }

}
