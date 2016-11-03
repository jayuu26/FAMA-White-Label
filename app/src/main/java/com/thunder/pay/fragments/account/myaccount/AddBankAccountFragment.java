package com.thunder.pay.fragments.account.myaccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.thunder.pay.R;
import com.thunder.pay.adapter.commonadaptter.MyCustomAdapter;
import com.thunder.pay.constant.MessageConstant;
import com.thunder.pay.daomodel.DataHandler;
import com.thunder.pay.greendaodb.AdBeneficiaryDetails;
import com.thunder.pay.greendaodb.BankDetail;
import com.thunder.pay.greendaodb.ErrorModel;
import com.thunder.pay.greendaodb.User;
import com.thunder.pay.rest.RestCall;
import com.thunder.pay.rest.RestClient;
import com.thunder.pay.util.AppUtills;
import com.thunder.pay.util.ErrorUtills;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.fragments.BackConfirmationFragment;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBankAccountFragment extends Fragment implements VerticalStepperForm {

    Context mContext;
    ViewGroup mainView;

    // Information about the steps/fields of the form
    private static final int BANK_NAME_STEP_NUM = 0;
    private static final int BRANCH_NAME_STEP_NUM = 1;
    private static final int ACC_NO_STEP_NUM = 2;
    private static final int CUSTOMER_ID_STEP_NUM = 3;
    private static final int PASSWORD_STEP_NUM = 4;

    private Spinner spinnerBankName;
    private Spinner spinnerBranchName;
    private EditText customerId;
    private EditText accountNumber;
    private EditText password;

    public String BANK_NAME;
    public String BRANCH_NAME;
    public String ACCOUNT_NUMBER;
    public String CUSTOMER_ID;
    public String PASSSWORD;


    private boolean confirmBack = true;
    private VerticalStepperFormLayout verticalStepperForm = null;

    CheckBox checkTerms;

    ArrayList<BankDetail> bankNameList = new ArrayList<>();
    ArrayList<BankDetail> branchNameList = new ArrayList<>();
    BankDetail bankDetail = new BankDetail();

    public enum Single {
        INSTANCE;
        AddBankAccountFragment s = new AddBankAccountFragment();

        public AddBankAccountFragment getInstance() {
            return new AddBankAccountFragment();
//            if (s == null)
//                return new AddBankAccountFragment();
//            else return s;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        mainView = (ViewGroup) inflater.inflate(R.layout.fama_vertical_stepper_form, container, false);
        AppUtills.setActionBarTitle("Add Account", ((AppCompatActivity) getActivity()).getSupportActionBar(), getActivity(), true);
        bankNameList.removeAll(bankNameList);
        bankDetail.setDescription("Please Select");
        branchNameList.add(0, bankDetail);

        return mainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadBankList();
        initializeActivity(view);

    }

    private void initializeActivity(View view) {

        TextView termsCondition = (TextView) view.findViewById(R.id.termsCondition);
        checkTerms = (CheckBox) view.findViewById(R.id.checkTermsCondition);

        AppUtills.setUnderLine(termsCondition, "Terms & Condition");

        int colorPrimary = ContextCompat.getColor(mContext, R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(mContext, R.color.colorPrimaryDark);
        String[] stepsTitles = getResources().getStringArray(R.array.steps_add_account);

        // Here we find and initialize the form
        verticalStepperForm = (VerticalStepperFormLayout) view.findViewById(R.id.vertical_stepper_form);
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, stepsTitles, this, getActivity())
                .stepsSubtitles(stepsTitles)
                .materialDesignInDisabledSteps(false) // false by default
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
            case BANK_NAME_STEP_NUM:
                view = createBankNameSpinner();
                break;
            case BRANCH_NAME_STEP_NUM:
                view = createBranchNameSpinner();
                break;
            case ACC_NO_STEP_NUM:
                view = createBankAccountNo();
                break;
            case CUSTOMER_ID_STEP_NUM:
                view = createCustomerId();
                break;
            case PASSWORD_STEP_NUM:
                view = createPasssword();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        try {
            switch (stepNumber) {
                case BANK_NAME_STEP_NUM:
                    if (ErrorUtills.isSpinnerValid(spinnerBankName)) {
                        verticalStepperForm.setActiveStepAsCompleted();
                    } else {
                        verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_select_bank_name));
                    }
                    break;
                case BRANCH_NAME_STEP_NUM:
                    if (ErrorUtills.checkTextNull(BRANCH_NAME)) {
                        verticalStepperForm.setStepAsCompleted(stepNumber);
                    } else {
                        verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_select_branch_name));
                    }
                    break;
                case ACC_NO_STEP_NUM:
                    if (ErrorUtills.checkTextMinLength(ACCOUNT_NUMBER, 16)) {
                        verticalStepperForm.setStepSubtitle(stepNumber, "" + ACCOUNT_NUMBER);
                        verticalStepperForm.setStepAsCompleted(stepNumber);
                    } else {
                        verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_enter_acc_no));
                    }
                    break;
                case CUSTOMER_ID_STEP_NUM:
                    if (ErrorUtills.checkTextMinMaxLength(CUSTOMER_ID, 6)) {
                        verticalStepperForm.setStepSubtitle(stepNumber, "" + CUSTOMER_ID);
                        verticalStepperForm.setStepAsCompleted(stepNumber);
                    } else {

                        verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_enter_customer_no));
                    }
                    break;
                case PASSWORD_STEP_NUM:
                    if (ErrorUtills.checkTextMinLength(PASSSWORD, 8)) {
                        if (!checkTerms.isChecked()) {
                            Toast.makeText(mContext, "Please agree to T&C", Toast.LENGTH_SHORT).show();
                            verticalStepperForm.setActiveStepAsUncompleted("Please agree to T&C");
                        } else {
                            verticalStepperForm.setStepSubtitle(stepNumber, "" + PASSSWORD);
                            verticalStepperForm.setStepAsCompleted(stepNumber);
                        }
                    } else {
                        verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_enter_password));
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // OTHER METHODS USED TO MAKE THIS EXAMPLE WORK


    private View createBankNameSpinner() {

        spinnerBankName = new Spinner(mContext);
        MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), bankNameList, "Name");
        if (bankNameList != null)
            spinnerBankName.setAdapter(adapter);
        spinnerBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    BANK_NAME = bankNameList.get(position).getBankName();
                    verticalStepperForm.setStepSubtitle(BANK_NAME_STEP_NUM, "" + BANK_NAME);
                    verticalStepperForm.setActiveStepAsCompleted();
                    loadBranchList("" + bankNameList.get(position).getId());
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(mContext.getResources().getString(R.string.error_select_bank_name));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return spinnerBankName;
    }

    private View createBranchNameSpinner() {
        spinnerBranchName = new Spinner(mContext);
        MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), branchNameList, "Branch");
        spinnerBranchName.setAdapter(adapter);
        spinnerBranchName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    BRANCH_NAME = branchNameList.get(position).getDescription();
                    verticalStepperForm.setStepSubtitle(BRANCH_NAME_STEP_NUM, "" + BRANCH_NAME);
                    verticalStepperForm.setActiveStepAsCompleted();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_select_branch_name));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return spinnerBranchName;
    }

    private View createBankAccountNo() {
        accountNumber = new EditText(mContext);
        accountNumber.setHint(mContext.getResources().getString(R.string.hint_enter_account_no));
        accountNumber.setSingleLine(true);
        accountNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        accountNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(accountNumber, 16);
        accountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextMinMaxLength(s.toString(), 16)) {
                    verticalStepperForm.setStepSubtitle(ACC_NO_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(ACC_NO_STEP_NUM);
                    ACCOUNT_NUMBER = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_enter_acc_no));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return accountNumber;
    }

    private View createCustomerId() {
        customerId = new EditText(mContext);
        customerId.setHint(mContext.getResources().getString(R.string.hint_enter_customer_id));
        customerId.setSingleLine(true);
        customerId.setInputType(InputType.TYPE_CLASS_NUMBER);
        customerId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(customerId, 6);
        customerId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextMinMaxLength(s.toString(), 6)) {
                    verticalStepperForm.setStepSubtitle(CUSTOMER_ID_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(CUSTOMER_ID_STEP_NUM);
                    ACCOUNT_NUMBER = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_enter_customer_no));
                }
            }
        });

        return customerId;
    }

    private View createPasssword() {
        password = new EditText(mContext);
        password.setHint(mContext.getResources().getString(R.string.hint_enter_password));
        password.setSingleLine(true);
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        ErrorUtills.setEditTextLimit(password, 20);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ErrorUtills.checkTextMinLength(s.toString(), 8)) {
                    verticalStepperForm.setStepSubtitle(PASSWORD_STEP_NUM, "" + s.toString());
                    verticalStepperForm.setStepAsCompleted(PASSWORD_STEP_NUM);
                    ACCOUNT_NUMBER = s.toString();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(""+mContext.getResources().getString(R.string.error_enter_password));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return password;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && confirmBack) {
//             confirmBack();
            return true;
        }
        return false;
    }

    private void loadBranchList(String id) {

        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);

        if (AppUtills.isNetworkAvailable(mContext)) {
            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());

            final Call<List<BankDetail>> repos = service.getAllBankDetailsByBankId(id);

            repos.enqueue(new Callback<List<BankDetail>>() {
                @Override
                public void onResponse(Call<List<BankDetail>> call, Response<List<BankDetail>> response) {
                    branchNameList.removeAll(branchNameList);
                    bankDetail.setBankName("Please Select");
                    branchNameList.add(0, bankDetail);
                    int code = response.code();
                    if (code == 200) {
                        List<BankDetail> bankDatas = response.body();
                        branchNameList.addAll((ArrayList<BankDetail>) bankDatas);
                        System.out.println("onResponse " + new Gson().toJson(branchNameList.get(0)));
                        MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), branchNameList, "Branch");
                        adapter.notifyDataSetChanged();
                        spinnerBranchName.setAdapter(adapter);

                    } else {
                        AppUtills.showErrorPopUpBack(getActivity(),""+mContext.getResources().getString(R.string.error_fetch_list));
                        Toast.makeText(getActivity(), "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
                    }
                    AppUtills.cancelProgressDialog(dialog);
                }

                @Override
                public void onFailure(Call<List<BankDetail>> call, Throwable t) {
                    System.out.println("onFailure branchNameList " + t.getMessage());
                    AppUtills.cancelProgressDialog(dialog);
                    AppUtills.showErrorPopUpBack(getActivity(),""+mContext.getResources().getString(R.string.error_fetch_list));
                }
            });
        }
    }


    private void loadBankList() {

        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);

        if (AppUtills.isNetworkAvailable(mContext)) {
            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());

            final Call<List<BankDetail>> repos = service.findAll();

            repos.enqueue(new Callback<List<BankDetail>>() {
                @Override
                public void onResponse(Call<List<BankDetail>> call, Response<List<BankDetail>> response) {
                    bankNameList.removeAll(bankNameList);
                    bankDetail.setBankName("Please Select");
                    bankNameList.add(0, bankDetail);
                    int code = response.code();
                    if (code == 200) {
                        List<BankDetail> bankDatas = response.body();
                        bankNameList.addAll((ArrayList<BankDetail>) bankDatas);
                        MyCustomAdapter adapter = new MyCustomAdapter(getActivity(), bankNameList, "Name");
                        adapter.notifyDataSetChanged();
                        spinnerBankName.setAdapter(adapter);
                        System.out.println("onResponse " + new Gson().toJson(bankNameList.get(0)));
                    } else {
                        AppUtills.showErrorPopUpBack(getActivity(),""+mContext.getResources().getString(R.string.error_fetch_list));
                    }
                    AppUtills.cancelProgressDialog(dialog);
                }

                @Override
                public void onFailure(Call<List<BankDetail>> call, Throwable t) {
                    AppUtills.cancelProgressDialog(dialog);
                    AppUtills.showErrorPopUpBack(getActivity(),""+mContext.getResources().getString(R.string.error_fetch_list));
                }
            });
        }
    }


    private void initDataVar() {

        try {
            BANK_NAME = bankNameList.get(spinnerBankName.getSelectedItemPosition()).getBankName().trim();
            BRANCH_NAME = branchNameList.get(spinnerBranchName.getSelectedItemPosition()).getDescription().trim();
            ACCOUNT_NUMBER = accountNumber.getText().toString().trim();
            CUSTOMER_ID = customerId.getText().toString().trim();
            PASSSWORD = password.getText().toString().trim();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void sendData() {

        try {
            initDataVar();

            AdBeneficiaryDetails adBeneficiaryDetails = new AdBeneficiaryDetails();
            adBeneficiaryDetails.setPassword(PASSSWORD);
            adBeneficiaryDetails.setCustomerId(CUSTOMER_ID);
            adBeneficiaryDetails.setAccountNumber(ACCOUNT_NUMBER);

             User user =  new User();
             user.setUserid(DataHandler.Single.INSTANCE.getInstance().getInventory().getUserid()+"");
             BankDetail bankDetail = new BankDetail();
            bankDetail.setId(bankNameList.get(spinnerBankName.getSelectedItemPosition()).getId());
            adBeneficiaryDetails.setUser(user);
            adBeneficiaryDetails.setBankDetail(bankDetail);

            System.out.println(" Final JSON Add Account" + new Gson().toJson(adBeneficiaryDetails));

            confirmAdd(adBeneficiaryDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void sendDataToServer(AdBeneficiaryDetails bankDetail) {

        RestCall service = RestClient.Single.INSTANCE.getInstance().getRestCallsConnection(mContext);

        if (AppUtills.isNetworkAvailable(mContext)) {
            final ProgressDialog dialog = AppUtills.showProgressDialog(getActivity());
            final Call<ResponseBody> response = service.addAccount(bankDetail);
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
//                                    AdBeneficiaryDetails details = new AdBeneficiaryDetails();
//                                    details = new Gson().fromJson(json, AdBeneficiaryDetails.class);
                                    Toast.makeText(mContext, mContext.getResources().getString(R.string.add_acc_success), Toast.LENGTH_LONG).show();
                                    getActivity().onBackPressed();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                                    verticalStepperForm.goToPreviousStep();
                                }
                            }
                        }else{
                            Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                            verticalStepperForm.goToPreviousStep();                        }

                    } catch (IOException e) {
                        Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                        verticalStepperForm.goToPreviousStep();
                        e.printStackTrace();
                    }
                    AppUtills.cancelProgressDialog(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(mContext, "" + MessageConstant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
                    verticalStepperForm.goToPreviousStep();
                    AppUtills.cancelProgressDialog(dialog);
                }
            });

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        System.out.println("onDetach");
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

    private void confirmAdd(final AdBeneficiaryDetails adBeneficiaryDetails) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
        builder.setTitle(mContext.getResources().getString(R.string.confirmation));
        builder.setMessage(mContext.getResources().getString(R.string.add_acc_confirm));
        builder.setPositiveButton("Ok",
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

}


