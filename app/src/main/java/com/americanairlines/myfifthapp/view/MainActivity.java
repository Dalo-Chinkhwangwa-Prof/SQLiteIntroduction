package com.americanairlines.myfifthapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.americanairlines.myfifthapp.R;
import com.americanairlines.myfifthapp.model.data.Pizza;
import com.americanairlines.myfifthapp.model.db.PizzaDatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.americanairlines.myfifthapp.model.db.PizzaDatabaseHelper.COLUMN_IMAGE_URL;
import static com.americanairlines.myfifthapp.model.db.PizzaDatabaseHelper.COLUMN_PIZZA_CALORIES;
import static com.americanairlines.myfifthapp.model.db.PizzaDatabaseHelper.COLUMN_PIZZA_FLAVOR;
import static com.americanairlines.myfifthapp.model.db.PizzaDatabaseHelper.COLUMN_PIZZA_PRICE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pizza_flavor_edittext)
    public EditText pizzaFlavorEditText;

    @BindView(R.id.pizza_calories_edittext)
    public EditText caloriesEditText;

    @BindView(R.id.pizza_price_edittext)
    public EditText priceEditText;

    @BindView(R.id.pizza_url_edittext)
    public EditText urlEditText;


    private PizzaDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        databaseHelper = new PizzaDatabaseHelper(this);
        readOrders();
    }

    @OnClick(R.id.make_order_button)
    public void onMakeOrder(View view){

        if(checkInput()){
            String flavor = pizzaFlavorEditText.getText().toString().trim();
            int calories = Integer.parseInt( caloriesEditText.getText().toString().trim() );

            String price = priceEditText.getText().toString().trim();
            String url = urlEditText.getText().toString().trim();

            Pizza pizza = new Pizza(flavor, Double.parseDouble(price), calories, "", url);
            databaseHelper.insertPizzaOrder(pizza);

            Toast.makeText(this, "Pizza Inserted", Toast.LENGTH_SHORT).show();
            clearFields();
            readOrders();
        }
    }

    //READ from database
    private void readOrders() {

        Cursor orders = databaseHelper.getAllPizzaOrders();
        orders.move(-1);

        while(orders.moveToNext()){
            String pizzaName = orders.getString(orders.getColumnIndex(COLUMN_PIZZA_FLAVOR));
            String pizzaPrice = orders.getString(orders.getColumnIndex(COLUMN_PIZZA_PRICE));
            int pizzaCalories = orders.getInt(orders.getColumnIndex(COLUMN_PIZZA_CALORIES));
            String imageUrl = orders.getString(orders.getColumnIndex(COLUMN_IMAGE_URL));
            Log.d("TAG_X", pizzaName + ", " + pizzaCalories + ", $" + pizzaPrice + " "+ imageUrl);
        }

    }

    private void clearFields() {
        pizzaFlavorEditText.setText("");
        caloriesEditText.setText("");
        priceEditText.setText("");
        urlEditText.setText("");
    }

    private boolean checkInput() {
        if(pizzaFlavorEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Pizza Flavor can't be empty.", Toast.LENGTH_SHORT).show();
        } else if(priceEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Pizza Price can't be empty.", Toast.LENGTH_SHORT).show();
        } else if(caloriesEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Pizza Calories can't be empty.", Toast.LENGTH_SHORT).show();
        } else if(urlEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Pizza Picture can't be empty.", Toast.LENGTH_SHORT).show();
        }else
            return true;
        return false;
    }

}
















