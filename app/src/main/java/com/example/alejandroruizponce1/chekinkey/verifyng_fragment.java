package com.example.alejandroruizponce1.chekinkey;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 */
public class verifyng_fragment extends Fragment {


    private UserProfile profile = UserProfile.getInstance();


    public verifyng_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View verifyngView = inflater.inflate(R.layout.fragment_verifyng, container, false);




        return verifyngView;
    }



}
