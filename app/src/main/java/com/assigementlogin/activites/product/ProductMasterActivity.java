package com.assigementlogin.activites.product;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.assigementlogin.R;
import com.assigementlogin.activites.product.model.Product;
import com.assigementlogin.database.SqliteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductMasterActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;

    //Declaration EditTexts
    @BindView(R.id.editTextProductCode)
    protected EditText editTextProductCode;
    @BindView(R.id.editTextProductName)
    protected EditText editTextProductName;
    @BindView(R.id.editTextProductUnits)
    protected EditText editTextProductUnits;
    @BindView(R.id.editTextProductMRP)
    protected EditText editTextProductMRP;

    //Declaration TextInputLayout
    @BindView(R.id.textInputLayoutProductCode)
    protected TextInputLayout textInputLayoutProductCode;
    @BindView(R.id.textInputLayoutProductName)
    protected TextInputLayout textInputLayoutProductName;
    @BindView(R.id.textInputLayoutProductUnits)
    protected TextInputLayout textInputLayoutProductUnits;
    @BindView(R.id.textInputLayoutProductMRP)
    protected TextInputLayout textInputLayoutProductMRP;

    @BindView(R.id.buttoncreate)
    protected AppCompatButton buttoncreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_master);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Product Master");

        sqliteHelper = new SqliteHelper(ProductMasterActivity.this);
        initClickEvent();
    }

    private void initClickEvent() {
        buttoncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productCode = editTextProductCode.getText().toString();
                String productName = editTextProductName.getText().toString();
                String productUnit = editTextProductUnits.getText().toString();
                String productMRP = editTextProductMRP.getText().toString();
                if (validate(productCode,productName,productUnit,productMRP)) {

                    //Check in the database is there any Product associated with  this productCode
                    if (!sqliteHelper.isProductExists(productCode)) {

                        //productCode does not exist now add new Product to database
                        sqliteHelper.addProduct(new Product(null, productCode, productName, productUnit,productMRP));
                        Snackbar.make(buttoncreate, "Product created successfully! ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        //product exists with productCode input provided so show error Product already exist
                        Snackbar.make(buttoncreate, "Product already exists with same Product Code ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
    }
    //This method is used to validate input given by user
    public boolean validate(String productCode,String productName,String productUnit,String productMRP) {
        boolean valid = false;

        //Handling validation for productCode field
        if (productCode.isEmpty()) {
            valid = false;
            textInputLayoutProductCode.setError("Please enter Product Code!");
        }else {
            valid = true;
            textInputLayoutProductCode.setError(null);
        }

        if (productName.isEmpty()){
            valid = false;
            textInputLayoutProductName.setError("Please enter Product Name!");
        }else {
            valid = true;
            textInputLayoutProductName.setError(null);
        }
        if (productUnit.isEmpty()){
            valid = false;
            textInputLayoutProductUnits.setError("Please enter Product Unit !");
        }else {
            valid = true;
            textInputLayoutProductUnits.setError(null);
        }
        if (productMRP.isEmpty()){
            valid = false;
            textInputLayoutProductMRP.setError("Please enter Prduct MRP");
        }else {
            valid = true;
            textInputLayoutProductMRP.setError(null);
        }

        return valid;
    }
}
