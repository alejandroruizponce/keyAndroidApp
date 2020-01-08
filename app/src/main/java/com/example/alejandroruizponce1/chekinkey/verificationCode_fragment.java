package com.example.alejandroruizponce1.chekinkey;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class verificationCode_fragment extends Fragment {

    private EditText editTextCode;
    private Button verifyButton;
    private Button resendButton;
    private TextView mobileNumber;
    private UserProfile profile = UserProfile.getInstance();

    public verificationCode_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verification_code, container, false);


        editTextCode = (EditText) view.findViewById(R.id.editTextCode);
        editTextCode.addTextChangedListener(filterTextWatcher);


        mobileNumber = (TextView) view.findViewById(R.id.textViewMobileNumber);
        mobileNumber.setText(profile.codePhone + " " + profile.phone);

        verifyButton = (Button) view.findViewById(R.id.verifyButton);
        verifyButton.setEnabled(false);
        verifyButton.getBackground().setAlpha(128);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("Hacemos la llamada a la API de validación con el código:" + editTextCode.getText().toString());
                ((MainActivity)getActivity()).validateVerificationCode(editTextCode.getText().toString());


            }
        });

        resendButton = (Button) view.findViewById(R.id.resendbutton);
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).sendVerification(profile.phone, profile.codePhone);
            }
        });


        editTextCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        return view;
    }





    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // DO THE CALCULATIONS HERE AND SHOW THE RESULT AS PER YOUR CALCULATIONS

            editTextCode.setBackgroundResource(R.drawable.editextborderwhite);

            if (s.toString().length() == 6){
                verifyButton.setEnabled(true);
                verifyButton.getBackground().setAlpha(255);
                hideKeyboard(getView());

            } else {
                verifyButton.setEnabled(false);
                verifyButton.getBackground().setAlpha(128);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getApplicationContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




}
