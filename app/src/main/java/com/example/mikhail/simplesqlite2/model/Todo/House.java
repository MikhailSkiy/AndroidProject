package com.example.mikhail.simplesqlite2.model.Todo;

/**
 * Created by Mikhail on 02.11.2014.
 * Class which describe house
 */
public class House {
    int id;
    String category;            // Category (singleFloor, doubleFloor)
    int square;                 // square
   // String optionalBuilding;    // some optional buildings like with garden or with terrace
    String title;               // title
    String address;             // address like street, number of house
    String textDescription;     // some text description about this house

    // constructors
    public House(){}

    public House(String category,
                 int square,
                // String optionalBuilding,
                 String title,
                 String address,
                 String textDescription){
        this.category = category;
       // this.optionalBuilding = optionalBuilding;
        this.square = square;
        this.title = title;
        this.address = address;
        this.textDescription = textDescription;
        }

    public House(int id,
                 String category,
                 int square,
                // String optionalBuilding,
                 String title,
                 String address,
                 String textDescription){
        this.id = id;
        this.category = category;
       // this.optionalBuilding = optionalBuilding;
        this.square = square;
        this.title = title;
        this.address = address;
        this.textDescription = textDescription;
    }

    // setters
    public void setId(int id){this.id = id;}
    public void setCategory(String category){this.category = category;}
    public void setSquare(int square){this.square = square;};
   // public void setOptionalBuilding(String optionalBuilding){this.optionalBuilding=optionalBuilding;}
    public void setTitle(String title){this.title = title;}
    public void setAddress(String address){this.address = address;}
    public void setTextDescription(String textDescription){this.textDescription = textDescription;}

   // getters
    public int getId(){return this.id;}
    public String getCategory(){return this.category;}
    public int getSquare(){return this.square;}
 //   public String getOptionalBuilding(){return this.getOptionalBuilding();}
    public String getTitle () {return this.title;}
    public String getAddress() {return this.address;}
    public String getTextDescription(){return this.textDescription;}

}
