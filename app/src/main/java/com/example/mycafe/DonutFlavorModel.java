package com.example.mycafe;

import android.widget.Button;
import android.widget.EditText;

public class DonutFlavorModel {
    String donutName;
    String donutPrice;
    int image;
    Button btnDonutIncrease, btnDonutDecrease;
    EditText txtDonutMultiple;
    plusClickListener plusClickListener;
    minusClickListener minusClickListener;

    public interface plusClickListener{
        void onItemClick(int position);
    }
    public interface minusClickListener{
        void onItemClick(int position);

    }

    public String getDonutName() {
        return donutName;
    }

    public int getImage() {
        return image;
    }

    public Button getBtnDonutIncrease() {
        return btnDonutIncrease;
    }

    public Button getBtnDonutDecrease() {
        return btnDonutDecrease;
    }

    public EditText getTxtDonutMultiple() {
        return txtDonutMultiple;
    }

    public DonutFlavorModel(String donutName, String donutPrice, int image){//}, EditText txtDonutMultiple){
                            //Button btnDonutIncrease, Button btnDonutDecrease) {
        this.donutName = donutName;
        this.donutPrice = donutPrice;
        this.image = image;
        //this.btnDonutIncrease = btnDonutIncrease;
        //this.btnDonutDecrease = btnDonutDecrease;
        //this.txtDonutMultiple = txtDonutMultiple;

        //this.item

        //btnDonutIncrease.setOnClickListener(this);
    }

    public String getDonutPrice() {
        return donutPrice;
    }
}
