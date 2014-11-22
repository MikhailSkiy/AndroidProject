package com.example.mikhail.simplesqlite2.model.Todo;

/**
 * Created by Mikhail on 03.11.2014.
 * Describe optional buildings in house like garden, terrace, sauna, swimming pool etc.
 * One house can have many optional buildings
 */
public class Building {
    int id;
    String title;

    // constructors
    public Building(){
    }

    public Building(String title){
        this.title = title;
    }

    public Building(int id, String title){
        this.id = id;
        this.title = title;
    }

    // setters

    public void setId (int id){this.id = id;}
    public void setTitle (String title){this.title = title;}

    // getters
    public int getId(){return this.id;}
    public String getTitle(){return this.title;}

}
