package com.example.mycafe;

public class Order implements Comparable<Order> {
    private static Order orderSync = null;
    private MenuItem[] order;
    private int orderNumber;
    private int size; //number of objects in the array
    private static final int INITIAL_CAPACITY = 4;
    private static final int GROWTH_FACTOR = 4;
    private static final int NOT_FOUND = -1;

    public Order(int orderNumber) {
        this.order = new MenuItem[0]; //CHECK IF RIGHT LATER
        this.size = 0;
        this.orderNumber = orderNumber;
    }

    public static Order getInstance() {
        synchronized (Order.class) {
            if (orderSync == null)
                orderSync = new Order(OrdersBase.getInstance().getCurrentOrderNumber());
            return orderSync;
        }
    }

    public MenuItem[] getMenuItems(){
        return this.order;
    }

    @Override
    public String toString(){
        return "Order Number " + orderNumber;
    }

    public int getOrderNumber(){
        return this.orderNumber;
    }

    public MenuItem getMenuItem(int itemNumber){
        return this.order[itemNumber];
    }

    public int getSize(){
        return this.size;
    }


    private int find (MenuItem menuItem) {
        for (int i = 0; i < size; i++){
            MenuItem currentMenuItem = order[i];
            boolean adds = true;
            if (menuItem.compareTo(currentMenuItem)==0) { return i; }
        }
        return NOT_FOUND;
    }


    private void grow() {
        int addedCapacity = order.length + GROWTH_FACTOR;
        MenuItem[] newMenuItems = new MenuItem[addedCapacity];

        // Copy elements from the old array to the new array
        for (int i = 0; i < order.length; i++) {
            newMenuItems[i] = order[i];
        }

        // Update reference to point to the new array
        this.order = newMenuItems;

    }


    public boolean add(MenuItem menuItem) {
        /*if (size > 0) {
            if (this.find(menuItem) >= 0) {
                return false; 
            }
        }*/  // we don't need to make sure of no repetition !
        if (size == order.length){
            this.grow();
            this.add(menuItem);
        }
        else {
            this.order[size] = menuItem;
            size += 1;
        }
        return true;
    }


    public boolean remove(MenuItem menuItem) {
        int menuItemIndex = this.find(menuItem);
        //Sys tem.out.println(menuItemIndex);
        if(menuItemIndex < 0) {
            return false;
        }
        else {
            this.order[menuItemIndex] = null;
            for(int i =0; i< this.order.length-1; i++){
                if (this.order[i] == null && this.order[i+1] != null){
                    this.order[i] = this.order[i+1];
                    this.order[i+1] = null;
                }
            }
        }
        size -= 1;
        return true;
    }

    @Override
    public int compareTo(Order order){
        if(this.orderNumber!=(order).getOrderNumber()){
            return -1;
        }
        return 0;
    }

}
