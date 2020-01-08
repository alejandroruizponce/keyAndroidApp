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

import com.rilixtech.CountryCodePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class verification_fragment extends Fragment {

    private EditText editText;
    private Button sendButton;
    private CountryCodePicker ccp;
    private UserProfile profile = UserProfile.getInstance();

    public verification_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verification, container, false);

        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);

        editText = (EditText) view.findViewById(R.id.editText);
        editText.addTextChangedListener(filterTextWatcher);



        sendButton = (Button) view.findViewById(R.id.button);
        sendButton.setEnabled(false);
        sendButton.getBackground().setAlpha(128);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hacemos la llamada a la API de verificacion al numero:" + ccp.getSelectedCountryCodeWithPlus() + editText.getText().toString());
                ((MainActivity)getActivity()).sendVerification(editText.getText().toString(), ccp.getSelectedCountryCodeWithPlus());
                //sendButton.setEnabled(false);


            }
        });


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }





    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // DO THE CALCULATIONS HERE AND SHOW THE RESULT AS PER YOUR CALCULATIONS

            editText.setBackgroundResource(R.drawable.editextborderwhite);

            if (s.toString().length() == 9){
                sendButton.setEnabled(true);
                sendButton.getBackground().setAlpha(255);
                hideKeyboard(getView());
            } else {
                sendButton.setEnabled(false);
                sendButton.getBackground().setAlpha(128);
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
