package com.example.mycafe;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.DialogInterface;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DecimalFormat;
import java.util.Objects;


/**
 * Controller class that is responsible for handling coffee orders and interactions with the UI
 * Manages adding, removing and updating coffee orders
 * @author Aashita Pandey, Sage Drewke
 */
public class CoffeeController extends AppCompatActivity {
    private ArrayList<Coffee> coffeeOrder = new ArrayList<>();

    private static final int GROWTH_FACTOR = 4;
    private static final int NOT_FOUND = -1;
    private int size = 0;
    private OrdersBase ordersBase = null;
    private int currentOrderNumber;


    public CoffeeController(){
        ordersBase = OrdersBase.getInstance();
        currentOrderNumber = ordersBase.getCurrentOrderNumber();
    }

    public void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.no_more_than_five_coffees_of_the_same_type))
                .setCancelable(true)
                .setNegativeButton(getString((R.string.i_understand)), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }



    private int numberOfCoffees=1;

    private double subtotal=0;
    int selectedCoffee = -1;
    String selectedCoffeeType;


    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.coffee_order);
        populateCoffeeOrders();
        calculateSubTotal();

        Spinner spinnerCoffee = findViewById(R.id.coffeeSpinner);
        spinnerCoffee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCoffeeType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCoffeeType = getString(R.string.none);
            }
        });

        ArrayList<String> coffeeTypesList = new ArrayList<>();
        coffeeTypesList.add("Choose Size");
        coffeeTypesList.add("Short");
        coffeeTypesList.add("Tall");
        coffeeTypesList.add("Grande");
        coffeeTypesList.add("Venti");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coffeeTypesList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerCoffee.setAdapter(adapter);



        ((Button) findViewById(R.id.btnHome)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        ((Button)findViewById(R.id.btnAddCoffee)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                addCoffeeToCurrentOrder();
            }
        });

        ((Button)findViewById(R.id.btnIncreaseCoffee)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                increaseCoffee();
            }
        });

        ((Button)findViewById(R.id.btnDecreaseCoffee)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                decreaseCoffee();
            }
        });

        ((Button)findViewById(R.id.btnRemoveCoffee)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                // idk i guess i won't use this...
                if(selectedCoffee!=-1 && !coffeeOrder.isEmpty()){
                    ordersBase.getOrders()[currentOrderNumber].remove(coffeeOrder.get(selectedCoffee));
                    coffeeOrder.remove(selectedCoffee);
                }
                selectedCoffee = -1;
            }
        });

        ((EditText)findViewById(R.id.txtSubTotalCoffee)).setText(String.valueOf(subtotal));


        ((EditText)findViewById(R.id.txtMultipleCoffee)).setText(String.valueOf(numberOfCoffees));


        if(numberOfCoffees==1){
            ((Button)findViewById(R.id.btnDecreaseCoffee)).setEnabled(true);
        }else{
            ((Button)findViewById(R.id.btnDecreaseCoffee)).setEnabled(false);
        }


        ((ListView)findViewById(R.id.listViewCoffeeOrders)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ordersBase.removeSelectedMenuItemFromCurrentOrder((Coffee)coffeeOrder.get(position));
                //coffeeOrder.remove((Coffee)coffeeOrder.get(position));
                selectedCoffee = position;
                populateCoffeeOrders();
                calculateSubTotal();
            }
        });
    }

    /* Populates the list view of the current coffee orders and their items*/

    public void populateCoffeeOrders(){
        ListView listView = ((ListView)findViewById(R.id.listViewCoffeeOrders));
        List<String> stringCoffeeList = new ArrayList<>();
        //stringCoffeeList.add(String.valueOf("current order:" + ordersBase.getCurrentOrderNumber()));
        //stringCoffeeList.add(String.valueOf("order size:" + ordersBase.getOrder(currentOrderNumber).getSize()));
        if(ordersBase.getOrders()!=null && ordersBase.getOrders()[currentOrderNumber]!=null){
            for(MenuItem item : ordersBase.getOrders()[currentOrderNumber].getMenuItems()){
                if(item instanceof Coffee){
                    stringCoffeeList.add(item.toString());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringCoffeeList);
        ((ListView)findViewById(R.id.listViewCoffeeOrders)).setAdapter(adapter);
    }



    /**
     * Calculates the subtotal of the current coffee order and updates the UI */

    public void calculateSubTotal(){
        subtotal = 0;
        if(ordersBase.getOrders()!=null && ordersBase.getOrders()[currentOrderNumber]!=null){
            ((EditText)findViewById(R.id.txtSubTotalCoffee)).setText(R.string._0_00);
            double price;
            for(MenuItem item : ordersBase.getOrders()[currentOrderNumber].getMenuItems()){
                price = 0;
                if(item instanceof Coffee){
                    price = item.price();
                    subtotal = subtotal+price;
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formatted = df.format(subtotal);
                    ((EditText)findViewById(R.id.txtSubTotalCoffee)).setText("");
                    ((EditText)findViewById(R.id.txtSubTotalCoffee)).setText(formatted);
                }
            }
        }else{
            ((EditText)findViewById(R.id.txtSubTotalCoffee)).setText(R.string._0_00);
        }
    }



    /**
     * Checks if adding another coffee exceeds the max limit of 5 of the same type of coffee
     * @param coffee - the coffee being checked
     * @return - true if adding the coffee doesn't exceed the limit, false otherwise
     */
    public boolean checkIfNotFive(Coffee coffee){
        int orderSize = ordersBase.getOrders()[currentOrderNumber].getSize();
        Order order = ordersBase.getOrders()[currentOrderNumber];
        int countingSameCoffee = 0;
        for(int i = 0; i < orderSize; i++){
            MenuItem item = order.getMenuItem(i);
            if(item instanceof Coffee){
                if(coffee.compareTo(item)==0){ countingSameCoffee++; }
            }
        }
        if(countingSameCoffee>=5){
            return false;
        }else{return true;}
    }

    /**
     * Adds coffee to the current order with the given selected options by the user
     * Displays an error message if the limit of 5 coffees is met
     * Updates the UI after adding the coffee*/

    public void addCoffeeToCurrentOrder() {
        currentOrderNumber = ordersBase.getCurrentOrderNumber();
        if(ordersBase.getOrders()[currentOrderNumber]==null){
            ordersBase.getOrders()[currentOrderNumber] = new Order(currentOrderNumber);
        }
        boolean sweetCream = false;
        boolean frenchVanilla = false;
        boolean irishCream = false;
        boolean caramel = false;
        boolean mocha = false;

        if(((CheckBox)findViewById(R.id.cbSweetCream)).isChecked()){
            sweetCream = true;
        }if(((CheckBox)findViewById(R.id.cbFrenchVanilla)).isChecked()){
            frenchVanilla = true;
        }if(((CheckBox)findViewById(R.id.cbIrishCream)).isChecked()){
            irishCream = true;
        }if(((CheckBox)findViewById(R.id.cbCaramel)).isChecked()){
            caramel = true;
        }if(((CheckBox)findViewById(R.id.cbMocha)).isChecked()){
            mocha = true;
        }

        if (Objects.equals(selectedCoffeeType, "Short")) {
            ////((EditText)findViewById(R.id.txtCoffeeMessage)).setText("");
            //
            for (int i = 0; i < numberOfCoffees; i++){
                boolean checking = checkIfNotFive(new Short(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                if(checking){
                    ordersBase.addItemToOrder(currentOrderNumber, new Short(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                    coffeeOrder.add(new Short(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                }else{
                    alertDialog();
                }
            }
        } else if (Objects.equals(selectedCoffeeType, "Tall")) {
            ////((EditText)findViewById(R.id.txtCoffeeMessage)).setText("");
            for (int i = 0; i < numberOfCoffees; i++){
                boolean checking = checkIfNotFive(new Tall(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                if(checking){
                    ordersBase.addItemToOrder(currentOrderNumber, new Tall(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                    coffeeOrder.add(new Tall(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                }else{
                    alertDialog();
                }
            }
        } else if (Objects.equals(selectedCoffeeType, "Grande")) {
            //((EditText)findViewById(R.id.txtCoffeeMessage)).setText("");
            for (int i = 0; i < numberOfCoffees; i++){
                boolean checking = checkIfNotFive(new Grande(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                if(checking){
                    ordersBase.addItemToOrder(currentOrderNumber, new Grande(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                    coffeeOrder.add(new Grande(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                }else{
                    alertDialog();
                }
            }
        } else if (Objects.equals(selectedCoffeeType, "Venti")) {
            //((EditText)findViewById(R.id.txtCoffeeMessage)).setText("");
            for (int i = 0; i < numberOfCoffees; i++){
                boolean checking = checkIfNotFive(new Venti(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                if(checking){
                    ordersBase.addItemToOrder(currentOrderNumber, new Venti(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                    coffeeOrder.add(new Venti(sweetCream, frenchVanilla, irishCream, caramel, mocha));
                }else{
                    alertDialog();
                }
            }
        } else {
            Toast.makeText(this, R.string.coffee_type_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }

        populateCoffeeOrders();
        calculateSubTotal();
    }

    /**
     * Increases the amount of coffees to be ordered
     * Updates the UI
    */
    public void increaseCoffee(){
        numberOfCoffees++;
        ((EditText)findViewById(R.id.txtMultipleCoffee)).setText("");
        ((EditText)findViewById(R.id.txtMultipleCoffee)).setText(String.valueOf(numberOfCoffees));
        if(numberOfCoffees==1){
            ((Button)findViewById(R.id.btnDecreaseCoffee)).setEnabled(false);
        }else if(numberOfCoffees==5){
            ((Button)findViewById(R.id.btnIncreaseCoffee)).setEnabled(false);
        }else{
            ((Button)findViewById(R.id.btnDecreaseCoffee)).setEnabled(true);
        }
    }

    /**
     * Decreases the amount of coffees to be ordered
     * Updates the UI
     */
    public void decreaseCoffee(){
        if(numberOfCoffees>1){
            numberOfCoffees--;
            ((EditText)findViewById(R.id.txtMultipleCoffee)).setText("");
            ((EditText)findViewById(R.id.txtMultipleCoffee)).setText(String.valueOf(numberOfCoffees));
            if(numberOfCoffees==1){
                ((Button)findViewById(R.id.btnDecreaseCoffee)).setEnabled(false);
            }else if(numberOfCoffees==5){
                ((Button)findViewById(R.id.btnIncreaseCoffee)).setEnabled(false);
            }else{
                ((Button)findViewById(R.id.btnDecreaseCoffee)).setEnabled(true);
            }
        }
    }
}


