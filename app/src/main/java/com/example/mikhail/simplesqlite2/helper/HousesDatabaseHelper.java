package com.example.mikhail.simplesqlite2.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mikhail.simplesqlite2.model.Todo.Building;
import com.example.mikhail.simplesqlite2.model.Todo.House;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail on 03.11.2014.
 * Descibes all methods with houses database (CRUD)
 */
public class HousesDatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "LotOfHouses";

    // Table Names
    private static final String TABLE_HOUSE = "houses";                          // table for houses
    private static final String TABLE_ADDBUILDING = "addbuildings";              // table for additional buildings
    private static final String TABLE_HOUSE_ADDBUILDINGS = "house_addbuildings"; // table for housesId and addbuildingsId

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // TABLE_HOUSE Table - column nmaes
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_SQUARE = "square";
    private static final String KEY_ADDBUILDING = "optionalBuilding";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_TEXTDESCRIPTION = "textDescription";

    // TABLE_ADDBUILDING Table - column names
    private static final String KEY_ADDBUILDING_TITLE = "addbuilding_title";

    // TABLE_HOUSE_ADDBUILDINGS Table - column names
    private static final String KEY_HOUSE_ID = "house_id";
    private static final String KEY_ADDBUILDING_ID = "addbuilding_id";

    // Table Create Statements
    // Addbuilding table create statement
    private static final String CREATE_TABLE_ADDBUILDING = "CREATE TABLE "
            + TABLE_ADDBUILDING + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ADDBUILDING_TITLE
            + " TEXT" + ")";

    // House table create statement
    private static final String CREATE_TABLE_HOUSE = "CREATE TABLE " + TABLE_HOUSE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT,"
            + KEY_SQUARE + " INTEGER," + KEY_TITLE + " TEXT,"
            + KEY_ADDRESS + " TEXT," + KEY_TEXTDESCRIPTION + " TEXT" + ")";

    // House_Addbuilding table create statement
    private static final String CREATE_TABLE_HOUSE_ADDBUILDING = "CREATE TABLE "
            + TABLE_HOUSE_ADDBUILDINGS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_HOUSE_ID + " INTEGER," + KEY_ADDBUILDING_ID + " INTEGER" + ")";


    public HousesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_HOUSE);
        db.execSQL(CREATE_TABLE_ADDBUILDING);
        db.execSQL(CREATE_TABLE_HOUSE_ADDBUILDING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDBUILDING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE_ADDBUILDINGS);
        // create new tables
        onCreate(db);
    }


    // ------------------------ methods which service the House table ----------------//

	/*
	 * Creating an additional building (addbuilding stands for additional building)
	 */

    // Create house and return house_id
    // Assign the house_id and addbuilding_id
    public long createHouse (House house, long[] addbuilding_ids){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put (KEY_CATEGORY, house.getCategory());
        contentValues.put (KEY_SQUARE,house.getSquare());
        contentValues.put (KEY_TITLE, house.getTitle());
      //  contentValues.put (KEY_ADDRESS,house.getAddress());
        contentValues.put (KEY_TEXTDESCRIPTION,house.getTextDescription());

        // insert row
        long house_id = db.insert(TABLE_HOUSE,null,contentValues);


        // assigning addbuildings with house
        for (long building_id: addbuilding_ids){
            createHouseAddbuilding(house_id,building_id);
        }
        return house_id;
    }

    /*
    * get single house
    */
    public House getHouse (long house_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_HOUSE + " WHERE " + KEY_ID + " = " + house_id;
        Log.e(LOG,selectQuery);

        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        House house = new House();
        house.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));                      // set Id for house.Id
        house.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));       // set category for house.category
        house.setSquare(cursor.getInt(cursor.getColumnIndex(KEY_SQUARE)));              // set square for house.square
        house.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));             // set title for house.title
        house.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));         // set address for house.address
        house.setTextDescription(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS))); // set textDescription for house.textDescription

        return house;
    }

    /*
    * getting all houses
    */
    public List<House> getAllHouses(){
        List<House> listOfhouses = new ArrayList<House>();
        String selectQuery = "SELECT * FROM " + TABLE_HOUSE;

        // write it in the LOG
        Log.e(LOG,selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){                                                              // if cursor is not empty then add data to list
            do{
                House house = new House();
                house.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));                      // set Id for house.Id
                house.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));       // set category for house.category
                house.setSquare(cursor.getInt(cursor.getColumnIndex(KEY_SQUARE)));              // set square for house.square
                house.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));             // set title for house.title
               // house.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));         // set address for house.address
                house.setTextDescription(cursor.getString(cursor.getColumnIndex(KEY_TEXTDESCRIPTION))); // set textDescription for house.textDescription

                // adding to list
                listOfhouses.add(house);
            }while (cursor.moveToNext());
        }
        return listOfhouses;
    }

    /*
    * getting count of houses
    */
    public int getHousesCount(){
        String countQuery = "SELECT  * FROM " + TABLE_HOUSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /*
    * getting all houses under single addbuilding
    * it can be useful when we want select houses with some additional buildings (with garden, terrace or sauna)
    *
    * Example:
    * SELECT * FROM houses hs, addbuildings adbuild, houses_addbuildings hs_adbuild WHERE
    * adbuild.title = 'Garden' AND adbuild.id = hs_adbuild.addbuilding_id AND hs.id =hs_adbuild.house_id;
     */
    public List<House> getAllHousesByBuilding(String addbuilding_title){
        List<House> houses = new ArrayList<House>();

        String selectQuery = "SELECT * FROM "+ TABLE_HOUSE + " hs, "
                + TABLE_ADDBUILDING + " adbuild, " + TABLE_HOUSE_ADDBUILDINGS + " hs_adbuild WHERE adbuild."
                + KEY_ADDBUILDING_TITLE + " = '"+addbuilding_title + "'" + " AND adbuild." + KEY_ID
                + " = " + "hs_adbuild." + KEY_ADDBUILDING_ID + " AND hs." + KEY_ID + " = "
                + "hs_adbuild." + KEY_HOUSE_ID;

        Log.e (LOG,selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                House house = new House();
                house.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));                      // set Id for house.Id
                house.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));       // set category for house.category
                house.setSquare(cursor.getInt(cursor.getColumnIndex(KEY_SQUARE)));              // set square for house.square
                house.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));             // set title for house.title
               // house.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));         // set address for house.address
                house.setTextDescription(cursor.getString(cursor.getColumnIndex(KEY_TEXTDESCRIPTION))); // set textDescription for house.textDescription

                // adding to house list
                houses.add(house);
            }while (cursor.moveToNext());
        }
        return houses;
    }

    /*
    * Deleting a house
    */
public void deleteHouse(long house_id){
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_HOUSE,KEY_ID + " = ?",
            new String[]{ String.valueOf(house_id)});
}
/*
* Delete All houses
 */
    public void deleteAllHouses (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HOUSE, null,null);
    }



    // ------------------------ Addbuildings table methods ----------------//

    public long createAddbuilding(Building building){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ADDBUILDING_TITLE, building.getTitle());

        // insert row
        long building_id = db.insert(TABLE_ADDBUILDING,null,contentValues);
        return building_id;
    }


    /*
    * getting addbuildings count
    */
    public int getAddbuildingCount(){
        String countQuery ="SELECT * FROM " + TABLE_ADDBUILDING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }


    /*
    * getting all addbuildings
    */
    public List<Building> getAllBuildings(){
        List<Building> buildings = new ArrayList<Building>();
        String selectQuery = "SELECT * FROM " + TABLE_ADDBUILDING;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Building building = new Building();
                building.setId(cursor.getInt(0));
                building.setTitle(cursor.getString(1));

                // add to list of buildings
                buildings.add(building);
            }while(cursor.moveToNext());
        }
        return buildings;
    }

   /*
   * delete all additional buildings
   */
    public void deleteAllAddbuildings()
    {
     SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ADDBUILDING,null,null);
    }

    /*
    * Deleting a building
    */
    public void deleteBuilding (Building building, boolean should_delete_all_house_buildings){
        SQLiteDatabase db = this.getWritableDatabase();
        if(should_delete_all_house_buildings){
            // get all houses under this addbuilding
            List<House> allHouses = getAllHousesByBuilding(building.getTitle());
            // delete all houses
            for (House house:allHouses){
                //deleting house
                deleteHouse(house.getId());
            }
        }
        //now delete the building
        db.delete(TABLE_ADDBUILDING,KEY_ID + " = ?",new String[]{String.valueOf(building.getId())});

    }

    /*
    * Creating house_addbuilding
    */
    public long createHouseAddbuilding(long house_id, long building_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_HOUSE_ID,house_id);
        contentValues.put(KEY_ADDBUILDING_ID,building_id);
        long id = db.insert(TABLE_HOUSE_ADDBUILDINGS,null,contentValues);

        return id;

    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
