package com.example.mahe.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText et = (EditText) findViewById(R.id.name_edittxt);
        Editable customerName= et.getText();
        CheckBox cb1 = (CheckBox) findViewById(R.id.whipped_cream);
        boolean bool1= cb1.isChecked();
        CheckBox cb2 = (CheckBox) findViewById(R.id.chocolate_topping);
        boolean bool2= cb2.isChecked();

        int totalPrice = calculatePrice(bool1,bool2);
        String message = createOrderSummary(totalPrice,bool1,bool2,customerName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra (Intent.EXTRA_SUBJECT, "JustJava order for "+customerName);
        intent.putExtra (Intent.EXTRA_TEXT , message);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if(quantity>=20)
        {
            Toast noMore = Toast.makeText(this, "Sorry, you cannot order more than 20 cups", Toast.LENGTH_SHORT);
              noMore.show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity<=1)
        {
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int calculatePrice(boolean boolWP, boolean boolCT) {
        int baseprice=5;
        if(boolWP)
        {
            baseprice = baseprice + 1 ;
        }
        if(boolCT)
        {
            baseprice = baseprice + 2 ;
        }
        int price = quantity * baseprice;
        return price;
    }

    private String createOrderSummary(int price, boolean boolWP, boolean boolCT, Editable name) {
        String details = "Name: "+name;
        details= details + "\n" + getString(R.string.add, boolWP, boolCT);
        details = details+"\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!";
        return details;
    }
}


