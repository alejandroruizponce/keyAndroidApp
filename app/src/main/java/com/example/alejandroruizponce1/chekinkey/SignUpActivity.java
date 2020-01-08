package com.example.alejandroruizponce1.chekinkey;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private Button continueButton;
    private EditText nameEditext;
    private EditText surnameEditext;
    private EditText sexEditext;
    private EditText nationalityEditext;
    private EditText typeDocumentEditext;
    private EditText codeDocumentEditext;
    private EditText birthDateEditext;
    private EditText expirationDate;
    private UserProfile profile = UserProfile.getInstance();
    private Dialog GenreDialog;
    private CircularImageView maleButton;
    private CircularImageView femaleButton;
    private Dialog DocumentDialog;
    private ImageButton idImage;
    private ImageButton passportImage;
    private ImageButton drivingImage;
    private ImageButton residenceImage;

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUI();

        GenreDialog = new Dialog(this);
        GenreDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        GenreDialog.setContentView(R.layout.genredialog);
        GenreDialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
        GenreDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        maleButton = GenreDialog.findViewById(R.id.maleCircular);
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexEditext.setText("Hombre");
                sexEditext.setBackgroundResource(R.drawable.editextborderwhite);
                GenreDialog.dismiss();

            }
        });
        femaleButton = GenreDialog.findViewById(R.id.femaleCircular);
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexEditext.setText("Mujer");
                sexEditext.setBackgroundResource(R.drawable.editextborderwhite);
                GenreDialog.dismiss();
            }
        });

        DocumentDialog = new Dialog(this);
        DocumentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DocumentDialog.setContentView(R.layout.documenttypedialog);
        DocumentDialog.getWindow().getAttributes().windowAnimations = R.style.MyAnimation_Window;
        DocumentDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        idImage = DocumentDialog.findViewById(R.id.DNIimageButton);
        idImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeDocumentEditext.setText("DNI");
                typeDocumentEditext.setBackgroundResource(R.drawable.editextborderwhite);
                DocumentDialog.dismiss();

            }
        });

        passportImage = DocumentDialog.findViewById(R.id.PassportimageButton);
        passportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeDocumentEditext.setText("Pasaporte");
                typeDocumentEditext.setBackgroundResource(R.drawable.editextborderwhite);
                DocumentDialog.dismiss();
            }
        });

        drivingImage = DocumentDialog.findViewById(R.id.DrivingimageButton);
        drivingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeDocumentEditext.setText("P.Conducir");
                typeDocumentEditext.setBackgroundResource(R.drawable.editextborderwhite);
                DocumentDialog.dismiss();
            }
        });

        residenceImage = DocumentDialog.findViewById(R.id.ResidenceimageButton2);
        residenceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeDocumentEditext.setText("P.Residencia");
                typeDocumentEditext.setBackgroundResource(R.drawable.editextborderwhite);
                DocumentDialog.dismiss();
            }
        });




        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.name = nameEditext.getText().toString();
                profile.surname = surnameEditext.getText().toString();

                Intent intent_next=new Intent(SignUpActivity.this, cameraDocumentActivity.class);
                startActivity(intent_next);
                SignUpActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

            }
        });





    }

    public void setupUI() {

        continueButton = findViewById(R.id.continueButton);
        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setEnabled(false);
        continueButton.getBackground().setAlpha(128);

        scrollView = findViewById(R.id.scrollViewID);

        nameEditext = findViewById(R.id.nameEditext);
        nameEditext.addTextChangedListener(new GenericTextWatcher(nameEditext));
        nameEditext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        surnameEditext = findViewById(R.id.surnameEditext);
        surnameEditext.addTextChangedListener(new GenericTextWatcher(surnameEditext));
        surnameEditext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        typeDocumentEditext = findViewById(R.id.typeDocumentEditext);
        typeDocumentEditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                nameEditext.clearFocus();
                surnameEditext.clearFocus();
                codeDocumentEditext.clearFocus();
                DocumentDialog.show();
                checkAllFields();
            }
        });


        codeDocumentEditext = findViewById(R.id.codeDocumentEditext);
        codeDocumentEditext.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        codeDocumentEditext.addTextChangedListener(new GenericTextWatcher(codeDocumentEditext));
        codeDocumentEditext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        sexEditext = findViewById(R.id.sexEditext);
        sexEditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                nameEditext.clearFocus();
                surnameEditext.clearFocus();
                codeDocumentEditext.clearFocus();
                GenreDialog.show();
                checkAllFields();
            }
        });

        birthDateEditext = findViewById(R.id.birthDateEditext);
        birthDateEditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEditext.clearFocus();
                surnameEditext.clearFocus();
                codeDocumentEditext.clearFocus();
                hideKeyboard(v);
                obtenerFechaNacimiento();

            }
        });

        expirationDate = findViewById(R.id.expirationDateEditext);
        expirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEditext.clearFocus();
                surnameEditext.clearFocus();
                codeDocumentEditext.clearFocus();
                hideKeyboard(v);
                obtenerFechaExpiracion();


            }
        });

        nationalityEditext = findViewById(R.id.nationalityEditext);
        nationalityEditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                nameEditext.clearFocus();
                surnameEditext.clearFocus();
                codeDocumentEditext.clearFocus();
                final CountryPicker picker = CountryPicker.newInstance("Elija un país");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        nationalityEditext.setText(name);
                        nationalityEditext.setBackgroundResource(R.drawable.editextborderwhite);
                        picker.dismiss();
                        checkAllFields();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

            }
        });




    }





    private void obtenerFechaNacimiento(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado

                birthDateEditext.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                birthDateEditext.setBackgroundResource(R.drawable.editextborderwhite);
                checkAllFields();


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.getDatePicker().setMaxDate(System.currentTimeMillis() -1000);
        recogerFecha.show();

    }

    private void obtenerFechaExpiracion(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado

                expirationDate.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                expirationDate.setBackgroundResource(R.drawable.editextborderwhite);
                checkAllFields();


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.getDatePicker().setMinDate(System.currentTimeMillis() -1000);
        recogerFecha.show();

    }

    public void checkAllFields () {
        System.out.println("El apellido es:(" + surnameEditext.getText().toString() + ")");
        System.out.println("El apellido es:(" + birthDateEditext.getText().toString() +")");
        if (nameEditext.getText().toString().length() > 0 &&
                surnameEditext.getText().toString() != "" &&
                sexEditext.getText().toString().length() > 0 &&
                birthDateEditext.getText().toString().length() > 0 &&
                typeDocumentEditext.getText().toString().length() > 0 &&
                codeDocumentEditext.getText().toString().length() > 0 &&
                nationalityEditext.getText().toString().length() > 0 &&
                expirationDate.getText().toString().length() > 0) {
            continueButton.setEnabled(true);
            continueButton.getBackground().setAlpha(255);
        } else {
            continueButton.setEnabled(false);
            continueButton.getBackground().setAlpha(128);
        }

    }






    private class GenericTextWatcher implements TextWatcher

    {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkAllFields();
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            view.setBackgroundResource(R.drawable.editextborderwhite);

        }

        public void afterTextChanged(Editable editable) {

        }

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}


