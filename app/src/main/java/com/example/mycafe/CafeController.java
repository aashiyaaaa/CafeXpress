package com.example.mycafe;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller class for managing the cafe orders
 * Handles UI events and interactions for adding, removing, and exporting orders
 * @author Aashita Pandey, Sage Drewke
 */
public class CafeController extends AppCompatActivity {

    private ArrayList<MenuItem> currentOrderList = new ArrayList<>();
    
    private OrdersBase ordersBase;
    double subtotal = 0;
    int selectedOrderNumber = -1;
    int selectedMenuItem = -1;
    int currentOrderNumber = OrdersBase.getInstance().getCurrentOrderNumber();
    //int selectedOrderNumber = -1;

    public CafeController(){
        ordersBase = OrdersBase.getInstance();
    }

    public void initialize(){
        //populateAllOrders();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allorders);
        initialize();


        ((Button)findViewById(R.id.btnRemoveOrder)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                if(selectedOrderNumber!=-1){
                    removeChosenOrder(ordersBase.getOrders()[selectedOrderNumber]);
                    ((EditText)findViewById(R.id.txtTotalChosenOrderCost)).setText(R.string._0_00);
                }
                selectedOrderNumber = -1;
                refresh();
                // i do not know how this would work
            }
        });//currentOrderList
        ((Button)findViewById(R.id.btnRemoveMenuItem)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                if(selectedMenuItem!=-1){
                    ordersBase.removeSelectedMenuItemFromCurrentOrder(currentOrderList.get(selectedMenuItem));
                    currentOrderList.remove(selectedMenuItem);
                }
                selectedMenuItem = -1;
                refreshCurrent();
                // refreshCurrent();
                // i do not know how this would work
            }
        });
        ((Button) findViewById(R.id.btnHome)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        ((Button)findViewById(R.id.btnRefreshCurrent)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                refreshCurrent();
            }
        });
        ((Button)findViewById(R.id.btnRefreshOrders)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                refresh();
            }
        });
        ((Button)findViewById(R.id.btnPlaceOrder)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View currentView) {
                placeOrder();
            }
        });

        ((ListView)findViewById(R.id.listViewOrders)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderNumber = position;
                view(ordersBase.getOrders()[position]);

            }
        });
        ((ListView)findViewById(R.id.listViewChosenOrder)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //populateChosenOrder(position, ordersBase.getOrders()[position]);
            }
        });
        ((ListView)findViewById(R.id.listViewCurrentOrder)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMenuItem = position;
                //populateChosenOrder(position, ordersBase.getOrders()[position]);
            }
        });
        
    }

    
    /**
     * Refreshes the current order.
     */
    public void refreshCurrent(){
        List<String> stringCurrentOrderList = new ArrayList<>();

        for (MenuItem item : ordersBase.getOrder(ordersBase.getCurrentOrderNumber()).getMenuItems()) {
            if(item!=null) {
                stringCurrentOrderList.add(item.toString());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringCurrentOrderList);
        ((ListView)findViewById(R.id.listViewCurrentOrder)).setAdapter(adapter);
        adapter.clear();

        //ordersBase.getOrders()[currentOrderNumber] = new Order(ordersBase.getCurrentOrderNumber());

        populateCurrentOrder();
        calculateTotal(ordersBase.getCurrentOrderNumber());
    }

    /**
     * Refreshes all orders
     */
    public void refresh(){
        ArrayAdapter<Order> adapter = (ArrayAdapter<Order>) ((ListView)findViewById(R.id.listViewChosenOrder)).getAdapter(); // POSSIBLE ERROR: "Adapter is not an ArrayAdapter"
        if(adapter!=null){
            adapter.clear();
        }
        ArrayAdapter<Order> adapter2 = (ArrayAdapter<Order>) ((ListView)findViewById(R.id.listViewOrders)).getAdapter(); // POSSIBLE ERROR: "Adapter is not an ArrayAdapter"
        if(adapter2!=null){
            adapter2.clear();
        }

        //((ListView)findViewById(R.id.listViewChosenOrder)).getItems().clear();
        //((ListView)findViewById(R.id.listViewOrders)).getItems().clear();

        //ordersBase.getOrders()[currentOrderNumber] = new Order(ordersBase.getCurrentOrderNumber());

        populateCurrentOrder();
        populateAllOrders();
        calculateTotal(ordersBase.getCurrentOrderNumber());
    }

    /**
     * Views the details of the selected order
     */
    public void view(Order selectedOrder){ //txtTotalCurrentOrderCost
        //if(selectedOrder!=null){
            //ordersBase.getOrders()[selectedOrder.getOrderNumber()] = new Order(selectedOrder.getOrderNumber());
            populateChosenOrder(selectedOrder.getOrderNumber(), selectedOrder);
            calculateTotalChosen(selectedOrderNumber);
            //populateChosenOrder(selectedOrder.getOrderNumber(), selectedOrder);
        //}
    }


    public void populateAllOrders(){
        List<String> stringAllOrdersList = new ArrayList<>();
        //stringChosenList.add(String.valueOf("current order:" + ordersBase.getCurrentOrderNumber()));
        //stringChosenList.add(String.valueOf("order size" + ordersBase.getOrder(currentOrderNumber).getSize()));
        
        // populate current order into ((ListView)findViewById(R.id.listViewChosenOrder))
        int orderNum = 0;
        if(ordersBase.getOrders()!=null){
            for(Order order : ordersBase.getOrders()){
                if(order != null && order.getSize()!=0){
                    stringAllOrdersList.add(String.valueOf("Order Number " + orderNum));
                    orderNum++;
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringAllOrdersList);
        ((ListView)findViewById(R.id.listViewOrders)).setAdapter(adapter);
    }


    public void populateChosenOrder(int chosenOrderNumber, Order selectedOrder){//orders[position]
        List<String> stringChosenList = new ArrayList<>();
        //stringChosenList.add(String.valueOf("chosen order:" + ordersBase.getOrder(chosenOrderNumber).getOrderNumber()));
        //stringChosenList.add(String.valueOf("order size" + ordersBase.getOrder(chosenOrderNumber).getSize()));


        // populate current order into ((ListView)findViewById(R.id.listViewChosenOrder))
        if(ordersBase.getOrders()!=null && selectedOrder!=null){
            for(MenuItem item : selectedOrder.getMenuItems()){
                if(item != null){
                    stringChosenList.add(item.toString());
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringChosenList);
        ((ListView)findViewById(R.id.listViewChosenOrder)).setAdapter(adapter);

    }


    public void removeChosenOrder(Order selectedItem) {
        List<String> stringOrdersList = new ArrayList<>();
        List <String> stringChosenOrderList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringOrdersList);
        ((ListView)findViewById(R.id.listViewOrders)).setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringChosenOrderList);
        ((ListView)findViewById(R.id.listViewChosenOrder)).setAdapter(adapter2);
        //adapter.clear(); adapter2.clear();
        
        if (selectedItem != null) {
            int chosenOrderNumber = selectedItem.getOrderNumber();
            boolean removed = ordersBase.remove(selectedItem);
            if (removed) {
                adapter.clear();
                adapter2.clear();
                populateCurrentOrder();
                populateAllOrders();
            }
        }
    }


    public void placeOrder(){
        List <String> stringCurrentOrder = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringCurrentOrder);
        ((ListView)findViewById(R.id.listViewCurrentOrder)).setAdapter(adapter);
        adapter.clear();
        //size = ordersBase.getSize();
        currentOrderNumber = ordersBase.getCurrentOrderNumber();
        ordersBase.add(new Order(currentOrderNumber));
        populateAllOrders();
        calculateTotal(ordersBase.getCurrentOrderNumber());
    }


    public void calculateTotal(int orderNumber){
        subtotal = 0;
        if(ordersBase.getOrders()!=null && ordersBase.getOrders()[orderNumber]!=null){
            int orderSize = ordersBase.getOrders()[orderNumber].getSize();
            ((EditText)findViewById(R.id.txtTotalCurrentOrderCost)).setText(R.string._0_00);
            double price;
            for(MenuItem item : ordersBase.getOrders()[orderNumber].getMenuItems()){
                if(item!=null){
                    price = item.price();
                    subtotal = subtotal + price;
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formatted = df.format(subtotal);
                    ((EditText)findViewById(R.id.txtTotalCurrentOrderCost)).setText(formatted);
                }
            }
        }else{
            ((EditText)findViewById(R.id.txtTotalCurrentOrderCost)).setText(R.string._0_00);
        }
    }


    public void calculateTotalChosen(int orderNumber){
        subtotal = 0;
        if(ordersBase.getOrders()!=null && ordersBase.getOrders()[orderNumber]!=null){
            int orderSize = ordersBase.getOrders()[orderNumber].getSize();
            Order order = ordersBase.getOrders()[orderNumber];
            ((EditText)findViewById(R.id.txtTotalChosenOrderCost)).setText(R.string._0_00);
            for(int i = 0; i < orderSize; i++){
                MenuItem item = order.getMenuItem(i);
                if(item!=null){
                    double price = order.getMenuItem(i).price();
                    subtotal = subtotal+price;
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formatted = df.format(subtotal);
                    ((EditText)findViewById(R.id.txtTotalChosenOrderCost)).setText(formatted);
                }
            }
        }else{
            ((EditText)findViewById(R.id.txtTotalChosenOrderCost)).setText(R.string._0_00);
        }
    }




    public void populateCurrentOrder(){

        List<String> stringCurrentList = new ArrayList<>();
        //stringCurrentList.add(String.valueOf("current order:" + ordersBase.getCurrentOrderNumber()));
        //stringCurrentList.add(String.valueOf("order size" + ordersBase.getOrder(currentOrderNumber).getSize()));


        // populate current order into ((ListView)findViewById(R.id.listViewChosenOrder))
        if(ordersBase.getOrders()!=null && ordersBase.getOrders()[currentOrderNumber]!=null){
            for(MenuItem item : ordersBase.getOrders()[currentOrderNumber].getMenuItems()){
                if(item != null){
                    stringCurrentList.add(item.toString());
                    currentOrderList.add(item);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringCurrentList);
        ((ListView)findViewById(R.id.listViewCurrentOrder)).setAdapter(adapter);

    }


}