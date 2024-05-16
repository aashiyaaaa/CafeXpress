package com.example.mycafe;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.RadioAccessSpecifier;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Text;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
import android.widget.RadioButton;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DecimalFormat;


/**
 * Controller class that is responsible for handling sandwich orders and interactions with the UI
 * Manages adding, removing and updating sandwich orders
 * @author Aashita Pandey, Sage Drewke
 */
public class SandwichController extends AppCompatActivity {
    private static final int GROWTH_FACTOR = 4;
    private ArrayList<Sandwich> sandwichOrder = new ArrayList<>();
    private static final int NOT_FOUND = -1;
    private int size = 0;
    private OrdersBase ordersBase = null;
    private int currentOrderNumber;
    private int BAGEL = 1;
    private int WHEAT_BREAD = 2;
    private int SOUR_DOUGH = 3;

    public SandwichController(){
        ordersBase = OrdersBase.getInstance();
        currentOrderNumber=ordersBase.getCurrentOrderNumber();
    }



    private int numberOfSandwiches=1;

    private double subtotal=0;
    int selectedSandwich = -1;


    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.sandwich_orders);

        populateSandwichOrders();
        calculateSubTotal();

        ((Button) findViewById(R.id.btnHome)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        ((Button)findViewById(R.id.btnAddSandwich)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                addSandwichToCurrentOrder();
            }
        });

        ((Button)findViewById(R.id.btnIncreaseSandwich)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                increaseSandwich();
            }
        });

        ((Button)findViewById(R.id.btnDecreaseSandwich)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                decreaseSandwich();
            }
        });

        ((Button)findViewById(R.id.btnRemoveSandwich)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                // idk i guess i won't use this...
                if(selectedSandwich!=-1 && !sandwichOrder.isEmpty()){
                    ordersBase.removeSelectedMenuItemFromCurrentOrder((Sandwich) sandwichOrder.get(selectedSandwich));
                    sandwichOrder.remove(sandwichOrder.get(selectedSandwich));
                }
                selectedSandwich = -1;
            }
        });

        ((EditText)findViewById(R.id.txtSubTotalSandwich)).setText(String.valueOf(subtotal));


        ((EditText)findViewById(R.id.txtMultipleSandwich)).setText(String.valueOf(numberOfSandwiches));


        if(numberOfSandwiches==1){
            ((Button)findViewById(R.id.btnDecreaseSandwich)).setEnabled(true);
        }else{
            ((Button)findViewById(R.id.btnDecreaseSandwich)).setEnabled(false);
        }


        ((ListView)findViewById(R.id.listViewSandwichOrders)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSandwich = position;
                //ordersBase.removeSelectedMenuItemFromCurrentOrder((Sandwich) sandwichOrder.get(position));
                //sandwichOrder.remove(sandwichOrder.get(position));
                populateSandwichOrders();
                calculateSubTotal();
            }
        });

    }




    public void placeOrder(){
        //((ListView)findViewById(R.id.listViewSandwichOrders)).getItems().clear();
        ListView listView = ((ListView)findViewById(R.id.listViewSandwichOrders));
        List<String> stringSandwichList = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringSandwichList);
        ((ListView)findViewById(R.id.listViewSandwichOrders)).setAdapter(adapter);

        adapter.clear();

        adapter.notifyDataSetChanged();
    }

    /* Populates the list view of the current sandwich orders and their items*/

    public void populateSandwichOrders(){
        ListView listView = ((ListView)findViewById(R.id.listViewSandwichOrders));
        List<String> stringSandwichList = new ArrayList<>();
        //stringSandwichList.add(String.valueOf("current order:" + ordersBase.getCurrentOrderNumber()));
        //stringSandwichList.add(String.valueOf("order size:" + ordersBase.getOrder(currentOrderNumber).getSize()));
        if(ordersBase.getOrders()!=null && ordersBase.getOrders()[currentOrderNumber]!=null){
            for(MenuItem item : ordersBase.getOrders()[currentOrderNumber].getMenuItems()){
                if(item instanceof Sandwich){
                    stringSandwichList.add(item.toString());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringSandwichList);
        ((ListView)findViewById(R.id.listViewSandwichOrders)).setAdapter(adapter);

    }



    /**
     * Calculates the subtotal of the current sandwich order and updates the UI */

    public void calculateSubTotal(){
        subtotal = 0;
        if(ordersBase.getOrders()!=null && ordersBase.getOrders()[currentOrderNumber]!=null){
            ((EditText)findViewById(R.id.txtSubTotalSandwich)).setText("0.00");
            double price;
            for(MenuItem item : ordersBase.getOrders()[currentOrderNumber].getMenuItems()){
                price = 0;
                if(item instanceof Sandwich){
                    price = item.price();
                    subtotal = subtotal+price;
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formatted = df.format(subtotal);
                    ((EditText)findViewById(R.id.txtSubTotalSandwich)).setText("");
                    ((EditText)findViewById(R.id.txtSubTotalSandwich)).setText(formatted);
                }
            }
        }else{
            ((EditText)findViewById(R.id.txtSubTotalSandwich)).setText("0.00");
        }
    }



    /**
     * Checks if adding another sandwich exceeds the max limit of 5 of the same type of sandwich
     * @param sandwich - the sandwich being checked
     * @return - true if adding the sandwich doesn't exceed the limit, false otherwise
     */


    /**
     * Adds sandwich to the current order with the given selected options by the user
     * Displays an error message if the limit of 5 sandwichs is met
     * Updates the UI after adding the sandwich*/

    public void addSandwichToCurrentOrder() {
        currentOrderNumber=ordersBase.getCurrentOrderNumber();
        if(ordersBase.getOrders()[currentOrderNumber]==null){
            ordersBase.getOrders()[currentOrderNumber] = new Order(currentOrderNumber);
        }
        boolean cheese = false;
        boolean lettuce = false;
        boolean tomatoes = false;
        boolean onions = false;
        int breadType = -1;
        //System.out.println("running add sandwich");

        if(((CheckBox)findViewById(R.id.cbCheese)).isChecked()){
            cheese = true;
        }
        if(((CheckBox)findViewById(R.id.cbLettuce)).isChecked()){
            lettuce = true;
        }
        if(((CheckBox)findViewById(R.id.cbTomatoes)).isChecked()){
            tomatoes = true;
        }
        if(((CheckBox)findViewById(R.id.cbOnions)).isChecked()){
            onions = true;
        }

        if(((RadioButton)findViewById(R.id.rbBagel)).isChecked()){
            breadType = BAGEL;
            //((EditText)findViewById(R.id.txtSandwichMessage)).setText("");
        }else if(((RadioButton)findViewById(R.id.rbWheatBread)).isChecked()){
            breadType = WHEAT_BREAD;
            //((EditText)findViewById(R.id.txtSandwichMessage)).setText("");
        }else if(((RadioButton)findViewById(R.id.rbSourDough)).isChecked()){
            breadType = SOUR_DOUGH;
            //((EditText)findViewById(R.id.txtSandwichMessage)).setText("");
        }else{
            Toast.makeText(this,"Bread not selected", Toast.LENGTH_SHORT).show();
            return;
            ////System.out.println("not adding bread");
            //((EditText)findViewById(R.id.txtSandwichMessage)).setText("Sandwich type not selected!");
        }

        if(((RadioButton)findViewById(R.id.rbBeef)).isChecked() && breadType!=NOT_FOUND){
            for(int i = 0;i<numberOfSandwiches;i++){
                ordersBase.addItemToOrder(currentOrderNumber,new BeefSandwich(breadType, cheese, lettuce, tomatoes, onions));
                sandwichOrder.add(new BeefSandwich(breadType, cheese, lettuce, tomatoes, onions));
            }
            ////((EditText)findViewById(R.id.txtSandwichMessage)).setText("");
        }else if(((RadioButton)findViewById(R.id.rbChicken)).isChecked() && breadType!=NOT_FOUND){
            for(int i = 0;i<numberOfSandwiches;i++){
                ordersBase.addItemToOrder(currentOrderNumber,new ChickenSandwich(breadType, cheese, lettuce, tomatoes, onions));
                sandwichOrder.add(new ChickenSandwich(breadType, cheese, lettuce, tomatoes, onions));
            }
            //((EditText)findViewById(R.id.txtSandwichMessage)).setText("");
        }else if(((RadioButton)findViewById(R.id.rbFish)).isChecked() && breadType!=NOT_FOUND){
            for(int i = 0;i<numberOfSandwiches;i++){
                ordersBase.addItemToOrder(currentOrderNumber,new FishSandwich(breadType, cheese, lettuce, tomatoes, onions));
                sandwichOrder.add(new FishSandwich(breadType, cheese, lettuce, tomatoes, onions));
            }
            //((EditText)findViewById(R.id.txtSandwichMessage)).setText("");
        }else{
            Toast.makeText(this,"Meat not selected", Toast.LENGTH_SHORT).show();
            return;
            //((EditText)findViewById(R.id.txtSandwichMessage)).setText("Sandwich type not selected!");
            //System.out.println("not adding meat");
        }

        populateSandwichOrders();
        calculateSubTotal();
    }

    /**
     * Increases the amount of sandwichs to be ordered
     * Updates the UI
     */
    public void increaseSandwich(){
        numberOfSandwiches++;
        ((EditText)findViewById(R.id.txtMultipleSandwich)).setText("");
        ((EditText)findViewById(R.id.txtMultipleSandwich)).setText(String.valueOf(numberOfSandwiches));
        if(numberOfSandwiches==1){
            ((Button)findViewById(R.id.btnDecreaseSandwich)).setEnabled(false);
        }else if(numberOfSandwiches==5){
            ((Button)findViewById(R.id.btnIncreaseSandwich)).setEnabled(false);
        }else{
            ((Button)findViewById(R.id.btnDecreaseSandwich)).setEnabled(true);
        }
    }

    /**
     * Decreases the amount of sandwichs to be ordered
     * Updates the UI
     */
    public void decreaseSandwich(){
        if(numberOfSandwiches>1){
            numberOfSandwiches--;
            ((EditText)findViewById(R.id.txtMultipleSandwich)).setText("");
            ((EditText)findViewById(R.id.txtMultipleSandwich)).setText(String.valueOf(numberOfSandwiches));
            if(numberOfSandwiches==1){
                ((Button)findViewById(R.id.btnDecreaseSandwich)).setEnabled(false);
            }else if(numberOfSandwiches==5){
                ((Button)findViewById(R.id.btnIncreaseSandwich)).setEnabled(false);
            }else{
                ((Button)findViewById(R.id.btnDecreaseSandwich)).setEnabled(true);
            }
        }
    }
}





    