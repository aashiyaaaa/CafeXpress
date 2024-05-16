package com.example.mycafe;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrdersBase {
    //private Order orderSync = Order.getInstance();
    private static OrdersBase instance;
    private static final int GROWTH_FACTOR = 4;
    private static final int NOT_FOUND = -1;
    private Order[] orders;
    private int currentOrderNumber = 0;
    private int size = 0;
    private static final int BAGEL = 1;
    private static final int WHEAT_BREAD = 2;
    private static final int SOUR_DOUGH = 3;
    double subtotal = 0;
    int [] donutNums = new int[15];
    int chosen = -1;

    int donutNum = 1;

    public void addDonutNum(int i){
        donutNums[i]++;
    }
    public void remDonutNum(int i){
        if(donutNums[i]!=0){donutNums[i]--;}
    }
    public int getDonutNum(int i){
        return donutNums[i];
    }

    private OrdersBase() {
        // Initialization code
        orders = new Order[GROWTH_FACTOR];
    }

    public static synchronized OrdersBase getInstance() {
        if (instance == null) {
            instance = new OrdersBase();
        }
        return instance;
    }

    /**
     * Retrieves the array of orders
     * @return - the array of orders
     */
    public Order[] getOrders(){
        return this.orders;
    }

    public void setOrders(Order[] newOrders){
        orders = newOrders;
    }

    public void removeChosenOrder(Order selectedItem) {
        
        //MultipleSelectionModel<Order> selectionModel = ((ListView)findViewById(R.id.listViewOrders)).getSelectionModel();
        //Order selectedItem = selectionModel.getSelectedItem();

        if (selectedItem != null) {
            int chosenOrderNumber = selectedItem.getOrderNumber();
            boolean removed = remove(selectedItem);
            if (removed) {
                currentOrderNumber = size;
            }
        }
    }

    public void newOrder(){
        size++;
        currentOrderNumber = size;
        orders[currentOrderNumber] = new Order(currentOrderNumber);
    }

    public void addItemToOrder(int orderNumber, MenuItem item){
        //orders[orderNumber].add(item);
        orders[orderNumber].add(item);
    }

    /**
     * Calculates the total cost of the order based on the given order number
     * @param orderNumber - the order number for which the total is calculated
     */
    public void calculateTotal(int orderNumber){
        subtotal = 0;
        if(orders!=null && orders[orderNumber]!=null){
            int orderSize = orders[orderNumber].getSize();
            Order order = orders[orderNumber];
            for(int i = 0; i < orderSize; i++){
                MenuItem item = order.getMenuItem(i);
                if(item!=null){
                    double price = order.getMenuItem(i).price();
                    subtotal = subtotal+price;
                    DecimalFormat df = new DecimalFormat("#.##");
                }
            }
        }else{
        }
    }

    /**
     * Calculates the total cost of the chosen order based on the given order number
     * @param orderNumber - the order number for which the total cost is calculated
     */
    public void calculateTotalChosen(int orderNumber){
        subtotal = 0;
        if(orders!=null && orders[orderNumber]!=null){
            int orderSize = orders[orderNumber].getSize();
            Order order = orders[orderNumber];
            for(int i = 0; i < orderSize; i++){
                MenuItem item = order.getMenuItem(i);
                if(item!=null){
                    double price = order.getMenuItem(i).price();
                    subtotal = subtotal+price;
                    DecimalFormat df = new DecimalFormat("#.##");
                }
            }
        }else{
        }
    }

    /**
     * Removes the selected menu item from the current order
     */
    public void removeSelectedMenuItemFromCurrentOrder(MenuItem selectedItem) {
        //MultipleSelectionModel<MenuItem> selectionModel = ((ListView)findViewById(R.id.listViewCurrentOrder)).getSelectionModel();
        //MenuItem selectedItem = selectionModel.getSelectedItem();
        // AASHITA FINISH THIS YOU DID NOT FINISH THIS YOU NEED TO MAKE THIS INTO A THING WHERE U CAN SELECT THEN A BUTTON DOES STUFF OK
        orders[currentOrderNumber].remove(selectedItem);
        calculateTotal(currentOrderNumber);
    }

    public int getCurrentOrderNumber(){
        return currentOrderNumber;
    }

    /**
     * Retrieves the size of the orders array
     * @return - the size of the orders array
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Retrieves the order object with the specified order number from the orders array
     * @param orderNumber - the order number of the order to retrieve
     * @return - The order object with the specified order number
     */
    public Order getOrder(int orderNumber){
        return this.orders[orderNumber];
    }

    /**
     * Searches for the specified order in the orders array
     * @param order - the order to search for in the orders array
     * @return - The index of the order if found, else return NOT_FOUND
     */
    private int find (Order order) {
        for (int i = 0; i < size; i++){
            Order currentOrder = orders[i];
            if (order.compareTo(currentOrder)==0) { return i; }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the capacity of the orders array
     */
    private void grow() {
        int addedCapacity = orders.length + GROWTH_FACTOR;
        Order[] newOrders = new Order[addedCapacity];

        for (int i = 0; i < orders.length; i++) {
            newOrders[i] = orders[i];
        }

        this.setOrders(newOrders);

    }

    /**
     * Adds the specified order to the orders array
     * @param order - the order to be added to the orders array
     * @return - True if the order is added, else false
     */
    public boolean add(Order order) {
        if (size > 0) {
            if (this.find(order) > -1) {
                return false;
            }
        }
        if (size == orders.length) {
            this.grow();
        }
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) {
                orders[i] = order;
                size++;
                currentOrderNumber = size;
                return true;
            }
        }
        return false;
    }

    public String orderToString(Order order){
        return "Order Number " + find(order);
    }

    /**
     * Removes the specified order from the orders array
     * @param order - orders to be removed from the orders array
     * @return - True if the order is removed, else false
     */
    public boolean remove(Order order) {
        int orderIndex = find(order);
        if (orderIndex == NOT_FOUND) {
            return false;
        }
        for (int i = orderIndex; i < size - 1; i++) {
            orders[i] = orders[i + 1];
        }
        orders[size - 1] = null;
        size--;
        return true;

    }
    
    
    
    





}
