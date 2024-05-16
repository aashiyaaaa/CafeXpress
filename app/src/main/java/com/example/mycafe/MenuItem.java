package com.example.mycafe;

/**
 * Abstract class representing a menu item
 * @author Aashita Pandey, Sage Drewke
 */
public abstract class MenuItem{

    /**
     * Returns the price of the menu item
     * @return - the price of the item
     */
    public abstract double price();

    /**
     * Compares the menu item to another menu item
     * @param menuItem - the menu item to compare to
     * @return - 0 if the menu items are equal, -1 otherwise
     */
    public abstract int compareTo(MenuItem menuItem);

    /**
     * Returns a string representation of the menu item
     * @return - the string representation of the menu item
     */
    public abstract String toString();

}

/**
 * Class representing a donut on the menu
 */
class Donut extends MenuItem {
    private String vanilla = "vanilla";
    private String chocolate = "chocolate";
    private String strawberry = "strawberry";
    private String blueberry = "blueberry";
    private String banana = "banana";
    private String flavor;

    /**
     * Constructs a donut with a specified flavor
     * @param flavor - the specified flavor
     */
    public Donut(String flavor){
        this.flavor = flavor;
    }

    /**
     * Gets the specified flavor of the donut
     * @return - flavor of the donut
     */
    public String getFlavor(){
        return this.flavor;
    }

    /**
     * Gets price of a donut
     * @return - the price of a donut
     */
    @Override
    public double price() {
        return 0;
    }

    /**
     * Compares a donut to another menu item
     * @param donut - the menu item to compare to
     * @return 0 if the items are equal, else -1
     */
    @Override
    public int compareTo(MenuItem donut) {
        if(this instanceof YeastDonut && donut instanceof YeastDonut){
            if (this.flavor==((YeastDonut) donut).getFlavor()){return 0;}
            return -1;
        }

        if(this instanceof CakeDonut && donut instanceof CakeDonut){
            if (this.flavor==((CakeDonut) donut).getFlavor()){return 0;}
            return -1;
        }

        if(this instanceof DonutHole && donut instanceof DonutHole){
            if (this.flavor==((DonutHole) donut).getFlavor()){return 0;}
            return -1;
        }
        return -1;
    }

    /**
     * Returns a string representation of a cake donut
     * @return - the string representation of a cake donut
     */
    public String toString (){
        StringBuilder stringBuilder = new StringBuilder();

        if(this instanceof YeastDonut){
            stringBuilder.append("Yeast Donut ");
        }

        if(this instanceof CakeDonut ){
            stringBuilder.append("Cake Donut ");
        }

        if(this instanceof DonutHole){
            stringBuilder.append("Donut Hole ");
        }

        stringBuilder.append("("+flavor+")\n");

        String result = stringBuilder.toString();
        return result;
    }
}

/**
 * Class representing a yeast donut
 */
class YeastDonut extends Donut{

    /**
     * Constructor that creates a yeast donut with a specified flavor
     * @param flavor - flavor for the yeast donut
     */
    public YeastDonut(String flavor){
        super(flavor);
    }

    /**
     * Gets the price for the yeast donut
     * @return - the price of the yeast donut
     */
    @Override
    public double price() {
        return 1.79;
    }
}

/**
 * Class representing a cake donut
 */
class CakeDonut extends Donut{
    /**
     * Constructor that creates a cake donut with a specified flavor
     * @param flavor - flavor for the cake donut
     */
    public CakeDonut(String flavor){
        super(flavor);
    }

    /**
     * Gets the price for a cake donut
     * @return - price for cake donut
     */
    @Override
    public double price() {
        return 1.89;
    }

}

/**
 * Class representing donut hole
 */
class DonutHole extends Donut{
    /**
     * Constructor that creates a donut hole with a specified flavor
     * @param flavor - flavor for the donut hole
     */
    public DonutHole(String flavor){
        super(flavor);
    }

    /**
     * Gets the price of the donut hole
     * @return - the price of the donut hole
     */
    @Override
    public double price() {
        return 0.39;
    }

}

/**
 * Class representing a sandwich
 */
class Sandwich extends MenuItem  {
    private double      price;
    private int         bread;
    private boolean     cheese;
    private boolean     lettuce;
    private boolean     tomatoes;
    private boolean     onions;

    private static final int BAGEL = 1;
    private static final int WHEAT_BREAD = 2;
    private static final int SOUR_DOUGH = 3;

    /**
     * Constructor that creates a sandwich with the following parameters
     * @param bread - the bread for the sandwich
     * @param cheese - the cheese for the sandwich
     * @param lettuce - the lettuce for the sandwich
     * @param tomatoes - the tomatoes for the sandwich
     * @param onions - the onions for the sandwich
     */
    public Sandwich (int bread, boolean cheese, boolean lettuce, boolean tomatoes, boolean onions){
        this.bread = bread;
        this.cheese = cheese;
        this.lettuce = lettuce;
        this.tomatoes = tomatoes;
        this.onions = onions;
    }

    /**
     * Sets the price of the sandwich based on the selected ingredients
     * If cheese is selected +1
     * If lettuce, tomatoes, or onions are chose, +0.30 each
     * @param price - base price of the sandwich
     */
    public void setPrice(double price){
        if(cheese){price++;}
        if(lettuce){price+=0.30;}
        if(tomatoes){price+=0.30;}
        if(onions){price+=0.30;}
        this.price = price;
    }

    /**
     * Calculates the price of the sandwich
     * @return - the price of the sandwich
     */
    @Override
    public double price() {
        return 0;
    }

    /**
     * Gets the cheese for sandwich if selected
     * @return  True if cheese is included, false otherwise
     */
    public boolean getCheese(){return this.cheese;}

    /**
     * Gets the status of lettuce in the sandwich
     * @return - true if lettuce is included, false otherwise
     */
    public boolean getLettuce(){return this.lettuce;}

    /**
     * Gets the tomatoes for sandwich if selected
     * @return - true if tomatoes are included, false otherwise
     */
    public boolean getTomatoes(){return this.tomatoes;}

    /**
     * Gets the onions for the sandwich if selected
     * @return - true if onions are included, false otherwise
     */
    public boolean getOnions(){return this.onions;}

    /**
     * Compares the current sandwich with another menu item
     * @param sandwich - the menu item to compare to
     * @return - 0 if the items are equal, else -1
     */
    @Override
    public int compareTo(MenuItem sandwich){
        if(this instanceof BeefSandwich && !(sandwich instanceof BeefSandwich)){
            return -1;
        }

        if(this instanceof ChickenSandwich && !(sandwich instanceof ChickenSandwich)){
            return -1;
        }

        if(this instanceof FishSandwich && !(sandwich instanceof FishSandwich)){
            return -1;
        }
        
        if(((Sandwich)sandwich).getCheese() != this.cheese){return -1;}
        if(((Sandwich)sandwich).getLettuce() != this.lettuce){return -1;}
        if(((Sandwich)sandwich).getTomatoes() != this.tomatoes){return -1;}
        if(((Sandwich)sandwich).getOnions() != this.onions){return -1;}
        return 0;
    }

    /**
     * Creates a string representation of a sandwich including its selections
     * @return - a string representation of a sandwich
     */
    public String toString (){
        StringBuilder stringBuilder = new StringBuilder();

        if(this instanceof BeefSandwich){
            stringBuilder.append("Beef Sandwich ");
        }

        if(this instanceof ChickenSandwich ){
            stringBuilder.append("Chicken Sandwich ");
        }

        if(this instanceof FishSandwich){
            stringBuilder.append("Fish Sandwich ");
        }
        int counter=0;
        if(cheese){counter++;}
        if(lettuce){counter++;}
        if(tomatoes){counter++;}
        if(onions){counter++;}
        if(cheese || lettuce || tomatoes || onions){
            stringBuilder.append("(");
            if(cheese){
                stringBuilder.append("cheese");
                if(counter>1){
                    stringBuilder.append(", ");
                    counter--;
                }
            }
            if(lettuce){
                stringBuilder.append("lettuce");
                if(counter>1){
                    stringBuilder.append(", ");
                    counter--;
                }
            }
            if(tomatoes){
                stringBuilder.append("tomatoes");
                if(counter>1){
                    stringBuilder.append(", ");
                }
            }
            if(onions){
                stringBuilder.append("onions");
            }
            stringBuilder.append(")\n");
        }else{
            stringBuilder.append("(no add-ons)\n");
        }

        String result = stringBuilder.toString();
        return result;
    }

}

/**
 * Represents a beef sandiwch menu item
 */
class BeefSandwich extends Sandwich{

    /**
     * Creates a beef sandwich with the following parameters
     * @param bread - the type of bread for the sandwich
     * @param cheese - whether cheese is selected or not
     * @param lettuce - whether lettuce is selected or not
     * @param tomatoes - whether tomatoes are selected or not
     * @param onions - whether onions are selected or not
     */
    public BeefSandwich(int bread, boolean cheese, boolean lettuce, boolean tomatoes, boolean onions){
        super(bread, cheese, lettuce, tomatoes, onions);
    }

    /**
     * Retrieves the price of the beef sandwich
     * @return - the price of the beef sandwich
     */
    @Override
    public double price() {
        return 10.99;
    }

}

/**
 * Represents a chicken sandwich
 */
class ChickenSandwich extends Sandwich{
    /**
     * Constructs a chicken sandwich with the specified parameters
     * @param bread - the type of bread for the sandwich
     * @param cheese - whether cheese was selected or not
     * @param lettuce - whether lettuce was selected or not
     * @param tomatoes - whether tomatoes were selected or not
     * @param onions - whether onions were selected or not
     */
    public ChickenSandwich(int bread, boolean cheese, boolean lettuce, boolean tomatoes, boolean onions){
        super(bread, cheese, lettuce, tomatoes, onions);
    }

    /**
     * Retrieves the price for the chicken sandwich
     * @return - price for chicken sandwich
     */
    @Override
    public double price() {
        return 8.99;
    }

}

/**
 * Represents a fish sandwich
 */
class FishSandwich extends Sandwich{
    /**
     * Creates a fish sandwich with the following parameters
     * @param bread - the type of bread for the sandwich
     * @param cheese - whether cheese is selected or not
     * @param lettuce - whether lettuce is selected or not
     * @param tomatoes - whether tomatoes are selected or not
     * @param onions - whether onions are selected or not
     */
    public FishSandwich(int bread, boolean cheese, boolean lettuce, boolean tomatoes, boolean onions){
        super(bread, cheese, lettuce, tomatoes, onions);
    }

    /**
     * retrieves the price for fish sandwich
     * @return - price of a fish sandwich
     */
    @Override
    public double price() {
        return 9.99;
    }

}

/**
 * Represents a coffee menu item
 */
class Coffee extends MenuItem {
    private double      price;
    private boolean     sweetCream;
    private boolean     frenchVanilla;
    private boolean     irishCream;
    private boolean     caramel;
    private boolean     mocha;

    /**
     * Constructs a coffee with the following parameters
     * @param sweetCream - whether sweet cream was selected or not
     * @param frenchVanilla - whether french vanilla was selected or not
     * @param irishCream - whether irish cream was selected or not
     * @param caramel - whether caramel was selected or not
     * @param mocha - whether mocha was selected or not
     */
    public Coffee (boolean sweetCream, boolean frenchVanilla, boolean irishCream, boolean caramel, boolean mocha){
        this.sweetCream = sweetCream;
        this.frenchVanilla = frenchVanilla;
        this.irishCream = irishCream;
        this.caramel = caramel;
        this.mocha = mocha;
    }

    /**
     * Sets the prices of the coffee based on the add ins
     * @param price - price of the coffee
     */
    public void setPrice(double price){
        if(sweetCream){price+=0.30;}
        if(frenchVanilla){price+=0.30;}
        if(irishCream){price+=0.30;}
        if(mocha){price+=0.30;}
        if(caramel){price+=0.30;}
        this.price = price;
    }

    /**
     * Retrieves whether sweet cream is added
     * @return - true if sweet cream is added, false otherwise
     */
    public boolean getSweetCream(){return this.sweetCream;}

    /**
     * Retrieves whether french vanilla was added
     * @return - true if french vanill awas added, false otherwise
     */
    public boolean getFrenchVanilla(){return this.frenchVanilla;}

    /**
     * Retrieves whether irish cream was added
     * @return - true if irish cream was added, false otherwise
     */
    public boolean getIrishCream(){return this.irishCream;}

    /**
     * Retrieves whether caramel was added or not
     * @return - true if caramel is added, false otherwise
     */
    public boolean getCaramel(){return this.caramel;}

    /**
     * Retrieves whether mocha was added
     * @return - true if mocha was added, false otherwise
     */
    public boolean getMocha(){return this.mocha;}

    /**
     * Retrieves the price of the coffee
     * @return - price of the coffee
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Retrieves the base price of the coffee
     * @return - base price of the coffee
     */
    @Override
    public double price() {
        return 0;
    }

    /**
     * Compares this coffee with another coffee based on their ads-ins
     * @param coffee - the menu item to compare to
     * @return - 0 if the coffees have the same add-ins, -1 otherfile
     */
    @Override
    public int compareTo(MenuItem coffee) {
        if(this instanceof Short && coffee instanceof Short){

        }else if(this instanceof Tall && coffee instanceof Tall){

        }else if(this instanceof Grande && coffee instanceof Grande){

        }else if(this instanceof Venti && coffee instanceof Venti){

        }else{
            return -1;
        }
        if(((Coffee)coffee).getSweetCream() != this.sweetCream){return -1;}
        if(((Coffee)coffee).getFrenchVanilla() != this.frenchVanilla){return -1;}
        if(((Coffee)coffee).getIrishCream() != this.irishCream){return -1;}
        if(((Coffee)coffee).getCaramel() != this.caramel){return -1;}
        if(((Coffee)coffee).getMocha() != this.mocha){return -1;}
        return 0;
    }

    /**
     * Generates a string representation of the coffee including its parameters
     * @return - string representation of the coffee
     */
    public String toString (){
        StringBuilder stringBuilder = new StringBuilder();

        if(this instanceof Short){
            stringBuilder.append("Short Coffee ");
        }

        if(this instanceof Tall ){
            stringBuilder.append("Tall Coffee ");
        }

        if(this instanceof Grande){
            stringBuilder.append("Grande Coffee ");
        }

        if(this instanceof Venti){
            stringBuilder.append("Venti Coffee ");
        }
        int counter = 0;
        if(sweetCream){counter++;}
        if(frenchVanilla){counter++;}
        if(irishCream){counter++;}
        if(caramel){counter++;}
        if(mocha){counter++;}
        if(sweetCream || frenchVanilla || irishCream || caramel || mocha){
            stringBuilder.append("(");
            if(sweetCream){stringBuilder.append("sweet cream");
                if(counter>1){
                    stringBuilder.append(", ");
                    counter--;
                }}
            if(frenchVanilla){stringBuilder.append("french vanilla");
                if(counter>1){
                    stringBuilder.append(", ");
                    counter--;
                }}
            if(irishCream){stringBuilder.append("irish cream");
                if(counter>1){
                    stringBuilder.append(", ");
                    counter--;
                }}
            if(caramel){stringBuilder.append("caramel");
                if(counter>1){
                    stringBuilder.append(", ");
                }}
            if(mocha){stringBuilder.append("mocha");}
            stringBuilder.append(")\n");
        }else{
            stringBuilder.append("(no add-ins)\n");
        }

        String result = stringBuilder.toString();
        return result;
    }
}

/**
 * Represents a short coffee
 */
class Short extends Coffee{
    /**
     * Constructs a short coffee with the given parameters
     * @param sweetCream - whether short cream is added
     * @param frenchVanilla - whether french vanilla is added
     * @param irishCream - whether irish cream is added
     * @param caramel - whether caramel is added
     * @param mocha - whether mocha is added
     */
    public Short(boolean sweetCream, boolean frenchVanilla, boolean irishCream, boolean caramel, boolean mocha){
        super(sweetCream, frenchVanilla, irishCream, caramel, mocha);
    }

    /**
     * Retrieves the price of the short coffee
     * @return - the price of the short coffee
     */
    @Override
    public double price() {
        return 1.99;
    }

}

/**
 * Represents a tall coffee
 */
class Tall extends Coffee{
    /**
     * Constructs a tall coffee with the following parameters
     * @param sweetCream - whether sweet cream is selected
     * @param frenchVanilla - whether french vanilla is selected
     * @param irishCream - whether irish cream is selected
     * @param caramel - whether caramel is selected
     * @param mocha - whether mocha is selected
     */
    public Tall(boolean sweetCream, boolean frenchVanilla, boolean irishCream, boolean caramel, boolean mocha){
        super(sweetCream, frenchVanilla, irishCream, caramel, mocha);
    }

    /**
     * Retrieves price of tall coffee
     * @return - base price of tall coffee
     */
    @Override
    public double price() {
        return 2.49;
    }

}

/**
 * Represents a grande coffee
 */
class Grande extends Coffee{
    /**
     * Constructs a tall coffee with the following parameters
     * @param sweetCream - whether sweet cream is selected
     * @param frenchVanilla - whether french vanilla is selected
     * @param irishCream - whether irish cream is selected
     * @param caramel - whether caramel is selected
     * @param mocha - whether mocha is selected
     */
    public Grande(boolean sweetCream, boolean frenchVanilla, boolean irishCream, boolean caramel, boolean mocha){
        super(sweetCream, frenchVanilla, irishCream, caramel, mocha);
    }

    /**
     * Retrieves price of a grande coffee
     * @return - price of a grande coffee
     */
    @Override
    public double price() {
        return 2.99;
    }

}

/**
 * Represents a venti coffee
 */
class Venti extends Coffee{
    /**
     * Constructs a tall coffee with the following parameters
     * @param sweetCream - whether sweet cream is selected
     * @param frenchVanilla - whether french vanilla is selected
     * @param irishCream - whether irish cream is selected
     * @param caramel - whether caramel is selected
     * @param mocha - whether mocha is selected
     */
    public Venti(boolean sweetCream, boolean frenchVanilla, boolean irishCream, boolean caramel, boolean mocha){
        super(sweetCream, frenchVanilla, irishCream, caramel, mocha);
    }

    /**
     * Retrieves price of a venti coffee
     * @return - price of a venti coffee
     */
    @Override
    public double price() {
        return 3.49;
    }
}
