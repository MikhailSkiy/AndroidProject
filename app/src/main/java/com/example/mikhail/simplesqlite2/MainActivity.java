package com.example.mikhail.simplesqlite2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mikhail.simplesqlite2.helper.DatabaseHelper;
import com.example.mikhail.simplesqlite2.helper.HousesDatabaseHelper;
import com.example.mikhail.simplesqlite2.model.Todo.Building;
import com.example.mikhail.simplesqlite2.model.Todo.House;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    // Database Helper
    DatabaseHelper db;
    HousesDatabaseHelper houseDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // db = new DatabaseHelper(getApplicationContext());

       houseDb = new HousesDatabaseHelper(getApplicationContext());


        // create some additional buildings
        Building garden_building = new Building("Garden");
        Building sauna_building = new Building("Sauna");
        Building swimming_pool_building = new Building("Swimming pool");
        Building terrace_building = new Building("Terrace");
        Building mansard_building = new Building("Mansard");

        long garden_building_id = houseDb.createAddbuilding(garden_building);
        long sauna_building_id = houseDb.createAddbuilding(sauna_building);
        long swimming_pool_id = houseDb.createAddbuilding(swimming_pool_building);
        long terrace_building_id = houseDb.createAddbuilding(terrace_building);
        long mansard_building_id = houseDb.createAddbuilding(mansard_building);


        Log.d("Additional buildings count","Additional building count" + houseDb.getAddbuildingCount());

        // Creating houses
        House house1 = new House("Singlefloor",20, "Best building","Taganrog,Nekrasovskiy street 12", "This house is suitable for big famili. It is located near bus station");
        House house2 = new House("Doublefloor", 60, "Big house", "Hamburg, Borfelderstrasse 14", "Big house for many people");

        // Inserting houses in database
        long house1_id = houseDb.createHouse(house1,new long []{garden_building_id}); // Create house with garden
        long house2_id = houseDb.createHouse(house2,new long []{sauna_building_id});  // Create house with sauna

        // Inserting tags in db
        // long tag1_id = db.createTag(tag1);
        // long tag2_id = db.createTag(tag2);
        // long tag3_id = db.createTag(tag3);
        // long tag4_id = db.createTag(tag4);

        // Log.d("Tag Count", "Tag Count: " + db.getAllTags().size());
        Log.d("Houses count", "House count" + houseDb.getAllHouses().size());

//         Creating ToDos
//        Todo todo1 = new Todo("iPhone 5S", 0);
//        Todo todo2 = new Todo("Galaxy Note II", 0);
//        Todo todo3 = new Todo("Whiteboard", 0);
//
//        Todo todo4 = new Todo("Riddick", 0);
//        Todo todo5 = new Todo("Prisoners", 0);
//        Todo todo6 = new Todo("The Croods", 0);
//        Todo todo7 = new Todo("Insidious: Chapter 2", 0);
//
//        Todo todo8 = new Todo("Don't forget to call MOM", 0);
//        Todo todo9 = new Todo("Collect money from John", 0);
//
//        Todo todo10 = new Todo("Post new Article", 0);
//        Todo todo11 = new Todo("Take database backup", 0);
//
//        // Inserting todos in db
//        // Inserting todos under "Shopping" Tag
//        long todo1_id = db.createToDo(todo1, new long[] { tag1_id });
//        long todo2_id = db.createToDo(todo2, new long[] { tag1_id });
//        long todo3_id = db.createToDo(todo3, new long[] { tag1_id });
//
//        // Inserting todos under "Watchlist" Tag
//        long todo4_id = db.createToDo(todo4, new long[] { tag3_id });
//        long todo5_id = db.createToDo(todo5, new long[] { tag3_id });
//        long todo6_id = db.createToDo(todo6, new long[] { tag3_id });
//        long todo7_id = db.createToDo(todo7, new long[] { tag3_id });
//
//        // Inserting todos under "Important" Tag
//        long todo8_id = db.createToDo(todo8, new long[] { tag2_id });
//        long todo9_id = db.createToDo(todo9, new long[] { tag2_id });
//
//        // Inserting todos under "Androidhive" Tag
//        long todo10_id = db.createToDo(todo10, new long[] { tag4_id });
//        long todo11_id = db.createToDo(todo11, new long[] { tag4_id });

        //Log.e("Todo Count", "Todo count: " + db.getToDoCount());

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        // db.createTodoTag(todo10_id, tag2_id);

        // Assigning house1 with swimming pool
        houseDb.createHouseAddbuilding(house1_id,swimming_pool_id);

        // Getting all tag names
//        Log.d("Get Tags", "Getting All Tags");
//
//        List<String> listOfNames = new ArrayList<String>();
//        List<Tag> allTags = db.getAllTags();
//        for (Tag tag : allTags) {
//            Log.d("Tag Name", tag.getTagName());
//            listOfNames.add(tag.getTagName());
//
//        }

        // Getting all buildings name
        Log.d ("Get all add buildings titles","Getting All buildinigs");

        List<String> listOfBuildingsName = new ArrayList<String>();
        List<Building> allBuildings = houseDb.getAllBuildings();
        for (Building building : allBuildings){
            Log.d("Building name", building.getTitle());
            listOfBuildingsName.add(building.getTitle());
        }

        // Get the element View
        /*
        ListView tagList = (ListView) findViewById(R.id.listResult);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOfBuildingsName);
        tagList.setAdapter(adapter);
        */

        ListView tagList = (ListView) findViewById(R.id.listResult);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOfBuildingsName);
        tagList.setAdapter(adapter);


        // Getting all Todos
        Log.d("Get Houses", "Getting All Houses");

        List<House> allHouses = houseDb.getAllHouses();
        for (House house : allHouses){
            Log.d("Category", house.getCategory());
           // Log.d("Address", house.getAddress() );
            Log.d("Descriprion", house.getTextDescription());
        }

        // Get the house with garden
        Log.d("House", "Get house with garden");

        List<House> houseWatchList = houseDb.getAllHousesByBuilding(garden_building.getTitle());
        for (House house: houseWatchList){
            Log.d("House WatchList. Category:", house.getCategory());
           // Log.d("Address", house.getAddress());
            Log.d("Description", house.getTextDescription());
        }


//        List<Todo> allToDos = db.getAllToDos();
//        for (Todo todo : allToDos) {
//            Log.d("ToDo", todo.getNote());
//        }

        // Getting todos under "Watchlist" tag name
//        Log.d("ToDo", "Get todos under single Tag name");
//
//        List<Todo> tagsWatchList = db.getAllToDosByTag(tag3.getTagName());
//        for (Todo todo : tagsWatchList) {
//            Log.d("ToDo Watchlist", todo.getNote());
//        }


        // Deleting a house
        Log.d("Delete House", "Deleting a House");
        Log.d("House count", "House count before deleting: "+ houseDb.getHousesCount());

        houseDb.deleteHouse(house2_id);

        Log.d("Count of houses", "Count of houses after deleting: "+ houseDb.getHousesCount());

        // Deleting all house with "Garden"
        Log.d("Building count","Building Count Before Deleting houses with Garden: "+ houseDb.getAddbuildingCount());
        houseDb.deleteBuilding(garden_building,true);
        Log.d("Building count","Building count after deleting Garden house" + houseDb.getAddbuildingCount());

        // Delete all houses
        // Delete all addbuildings
        houseDb.deleteAllHouses();
        houseDb.deleteAllAddbuildings();

        // Deleting a ToDo
//
//        Log.d("Delete ToDo", "Deleting a Todo");
//        Log.d("Tag Count", "Tag Count Before Deleting: " + db.getToDoCount());
//
//        db.deleteToDo(todo8_id);
//
//        Log.d("Tag Count", "Tag Count After Deleting: " + db.getToDoCount());

        // Deleting all Todos under "Shopping" tag
//        Log.d("Tag Count",
//                "Tag Count Before Deleting 'Shopping' Todos: "
//                        + db.getToDoCount());
//
//        db.deleteTag(tag1, true);



//        Log.d("Tag Count",
//                "Tag Count After Deleting 'Shopping' Todos: "
//                        + db.getToDoCount());

        // Updating tag name
//        tag3.setTagName("Movies to watch");
//        db.updateTag(tag3);

//        db.deleteAllTags();


        // Don't forget to close database connection
        houseDb.closeDB();

        // For MENU

        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View itemClicked, int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString(); // получаем текст нажатого элемента

                if(strText.equalsIgnoreCase(getResources().getString(R.string.singleFloor))) {
                    // Запускаем активность, связанную с определенным элементом списка
                    Intent intent = new Intent(itemClicked.getContext(),SingleFloor.class);
                    startActivity(intent);
                }
            }
        });

    }

}