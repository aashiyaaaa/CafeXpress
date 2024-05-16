package com.example.mycafe;

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


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller class that manages the donut orders
 * Handles adding, removing, and displaying donuts in the GUI application
 * @author Aashita Pandey, Sage Drewke
 */
public class DonutController extends AppCompatActivity implements RecyclerViewInterface{
    private ArrayList<Donut> donutOrder;
    private static final int GROWTH_FACTOR = 4;
    private static final int NOT_FOUND = -1;
    private int size = 0;
    private OrdersBase ordersBase;
    private int currentOrderNumber;
    
    public DonutController(){
        ordersBase = OrdersBase.getInstance();
        currentOrderNumber = ordersBase.getCurrentOrderNumber();
    }


    //private ImageView donutImage;
    //private Button btnIncreaseDonut, btnDecreaseDonut, btnAddDonut, btnRemoveDonut;
    //private ListView listViewDonutOrders;
    //private RadioButton rbYeastDonut, rbCakeDonut, rbDonutHole;
    //private RadioGroup donutTypeToggleGroup;
    //private RadioButton rbVanilla, rbChocolate, rbStrawberry, rbBlueberry, rbBanana;
    private int numberOfDonuts[] = new int[15];
    private double subtotal = 0;

    ArrayList<DonutFlavorModel> donutFlavorModels = new ArrayList<>();
    int[] donutFlavorImages = {
            R.drawable.vanilla_cake_donut, R.drawable.chocolate_cake_donut, R.drawable.strawberry_cake_donut,
            R.drawable.blueberry_cake_donut, R.drawable.banana_cake_donut, R.drawable.vanilla_yeast_donut,
            R.drawable.chocolate_yeast_donut, R.drawable.strawberry_yeast_donut, R.drawable.blueberry_yeast_donut,
            R.drawable.banana_yeast_donut, R.drawable.vanilla_donut_hole, R.drawable.chocolate_donut_hole,
            R.drawable.strawberry_donut_hole, R.drawable.blueberry_donut_hole, R.drawable.banana_donut_hole
    };
    //ArrayList<EditText> texts = new ArrayList<>();

    //((Button)findViewById(R.id.btnAdd1))
    @Override
    protected void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        setContentView(R.layout.donut_orders);
        calculateSubTotal();

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        setUpDonutFlavorModels();

        DF_RecyclerViewAdapter adapter = new DF_RecyclerViewAdapter(this,
                donutFlavorModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ((Button) findViewById(R.id.btnHome)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View currentView) {
                Intent myIntent = new Intent(currentView.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });


        initialize();


    }

    private void setUpDonutFlavorModels(){
        String[] donutFlavorNames = getResources().getStringArray(R.array.donutOptions);
        String[] donutPrices = getResources().getStringArray(R.array.donut_prices);

        for (int i = 0; i < donutFlavorNames.length; i++) {
            donutFlavorModels.add(new DonutFlavorModel(donutFlavorNames[i],
                    donutPrices[i], donutFlavorImages[i]));
        }
    }


    /**
     * Initializes the donut controller
     */
    public void initialize(){

        ((EditText)findViewById(R.id.txtSubTotalDonut)).setText(String.valueOf(subtotal));

    }

    /**
     * Retrieves the array of the donut orders
     * @return - the array of donut orders
     */
    public Order[] getDonutOrders(){
        return ordersBase.getOrders();
    }

   

    /**
     * Calculates the subtotal for the current donut order
     */
    public void calculateSubTotal(){
        subtotal = 0;
        if(ordersBase.getOrders()!=null && ordersBase.getOrders()[currentOrderNumber]!=null){
            int orderSize = ordersBase.getOrders()[currentOrderNumber].getSize();
            Order order = ordersBase.getOrders()[currentOrderNumber];
            ((EditText)findViewById(R.id.txtSubTotalDonut)).setText("0");
            for(int i = 0; i < orderSize; i++){
                MenuItem item = order.getMenuItem(i);
                double price = order.getMenuItem(i).price();
                if(item instanceof Donut){
                    subtotal = subtotal+price;
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formatted = df.format(subtotal);
                    ((EditText)findViewById(R.id.txtSubTotalDonut)).setText(formatted);
                }
            }
        }else{
            ((EditText)findViewById(R.id.txtSubTotalDonut)).setText("0");
        }
    }

    /**
     * Adds a donut to the current order based on user selections
     */

    /**
     * Removes the specified donut from the current order
     * @param donut - the donut to be removed from the current order
     */
    public void removeDonutFromCurrentOrder(Donut donut) {
        ordersBase.getOrders()[currentOrderNumber].remove(donut);
        calculateSubTotal();
    }

    /**
     * Retrieves the size of the array of donut orders
     * @return = the size of the array of donut orders
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Retrieves the donut order with the specified order number
     * @param orderNumber - the order number of the order to be retrieved
     * @return - the donut order with the specified order number
     */
    public Order getOrder(int orderNumber){
        return this.ordersBase.getOrders()[orderNumber];
    }

    /**
     * Searches for the specified order in the array of donut orders
     * @param order - the order to search for
     * @return - the index of the order if found, otherwise returns NOT_FOUND
     */
    private int find (Order order) {
        for (int i = 0; i < size; i++){
            Order currentOrder = ordersBase.getOrders()[i];
            boolean adds = true;
            if (order.compareTo(currentOrder)==0) { return i; }
        }
        return NOT_FOUND;
    }

    @Override
    public void onItemClick(int position) {

    }
    String[] donutTypes = {"Cake Donut", "Cake Donut", "Cake Donut", "Cake Donut", "Cake Donut", "Yeast Donut", "Yeast Donut", "Yeast Donut", "Yeast Donut", "Yeast Donut", "Donut Hole", "Donut Hole", "Donut Hole", "Donut Hole", "Donut Hole"};

    String[] flavors = {"Vanilla", "Chocolate", "Strawberry", "Blueberry", "Banana", "Vanilla", "Chocolate", "Strawberry", "Blueberry", "Banana", "Vanilla", "Chocolate", "Strawberry", "Blueberry", "Banana"};

    public void onPlusClick(int position) {
        if(ordersBase.getOrders()[currentOrderNumber]==null){
            ordersBase.getOrders()[currentOrderNumber] = new Order(currentOrderNumber);
        }
        currentOrderNumber=ordersBase.getCurrentOrderNumber();
        numberOfDonuts[position]++;

        if(donutTypes[position].equals("Cake Donut")){
            ordersBase.addItemToOrder(currentOrderNumber,new CakeDonut(flavors[position]));
            //donutOrder.add(new CakeDonut(flavors[position]));
        }else if(donutTypes[position].equals("Yeast Donut")){
            ordersBase.addItemToOrder(currentOrderNumber,new YeastDonut(flavors[position]));
            //donutOrder.add(new YeastDonut(flavors[position]));
        }else if(donutTypes[position].equals("Donut Hole")){
            ordersBase.addItemToOrder(currentOrderNumber,new DonutHole(flavors[position]));
            //donutOrder.add(new DonutHole(flavors[position]));
        }
        calculateSubTotal();
    }

    public void onMinClick(int position) {
        currentOrderNumber=ordersBase.getCurrentOrderNumber();
        if(numberOfDonuts[position]!=0){numberOfDonuts[position]--;}
        if(donutTypes[position].equals("Cake Donut")){
            ordersBase.removeSelectedMenuItemFromCurrentOrder(new CakeDonut(flavors[position]));
            //donutOrder.remove(new CakeDonut(flavors[position]));
        }else if(donutTypes[position].equals("Yeast Donut")){
            ordersBase.removeSelectedMenuItemFromCurrentOrder(new YeastDonut(flavors[position]));
            //donutOrder.remove(new YeastDonut(flavors[position]));
        }else if(donutTypes[position].equals("Donut Hole")){
            ordersBase.removeSelectedMenuItemFromCurrentOrder(new DonutHole(flavors[position]));
            //donutOrder.remove(new DonutHole(flavors[position]));
        }
        calculateSubTotal();
    }

    /**
     * Displays donut images based on the selection
     */
    //FXML

    /*
    public void displaySelectedDonutImage() {
        if (((RadioButton)findViewById(R.id.rbYeastDonut)).isSelected()) {
            //Image yeastDonutImage = new Image("https://cheflolaskitchen.com/wp-content/uploads/2015/11/Doughnut-recipe.jpg");
            //donutImage.setImage(yeastDonutImage);
        } else if (((RadioButton)findViewById(R.id.rbCakeDonut)).isSelected()) {
            //Image cakeDonutImage = new Image("https://sugargeekshow.com/wp-content/uploads/2020/09/cake_donut_recipe_featured2.jpg");
            //donutImage.setImage(cakeDonutImage);
        } else if (((RadioButton)findViewById(R.id.rbDonutHole)).isSelected()) {
            //Image donutHoleImage = new Image("https://sugarspunrun.com/wp-content/uploads/2022/08/Fried-Donut-Holes-No-Yeast-1-of-1.jpg");
            //donutImage.setImage(donutHoleImage);
        }
    }

     */ //FIX THIS...
}