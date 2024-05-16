package com.example.mycafe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;

import java.io.IOException;


/**
 * A JavaFX application that manages a cafe ordering system
 * @author Aashita Pandey, Sage Drewke
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Sets up the JavaFX application
     * @throws IOException - If an input/output exception happens when loading the FXML file.
     */
    @Override
    protected void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        setContentView(R.layout.home_page);



        Button currentAllOrders = (Button) findViewById(R.id.btn_currentAllOrders);
        currentAllOrders.setOnClickListener(new View.OnClickListener() {

            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), CafeController.class);
                startActivityForResult(myIntent, 0);
            }

        });

        Button coffee = (Button) findViewById(R.id.btn_coffee);
        coffee.setOnClickListener(new View.OnClickListener() {

            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), CoffeeController.class);
                startActivityForResult(myIntent, 0);
            }

        });

        Button donut = (Button) findViewById(R.id.btn_donut);
        donut.setOnClickListener(new View.OnClickListener() {

            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), DonutController.class); // i think this is right?
                startActivityForResult(myIntent, 0);
            }

        });

        Button sandwich = (Button) findViewById(R.id.btn_sandwich);
        sandwich.setOnClickListener(new View.OnClickListener() {

            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), SandwichController.class);
                startActivityForResult(myIntent, 0);
            }

        });
    }
}