/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match the package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the PLUS button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the MINUS button is clicked.
     */
    public void decrement(View view) {
        if (quantity < 2) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the ORDER button is clicked.
     */
    public void submitOrder(View view) {

        EditText name = (EditText) findViewById(R.id.name);
        String userName = name.getText().toString();

        CheckBox checkWhippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = checkWhippedCream.isChecked();

        CheckBox checkChocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = checkChocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(userName, price, hasWhippedCream, hasChocolate);
//        displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String addresses = "jburke9@mail.ccsf.edu";
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order Summary for " + userName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        }

    /**
     * Calculate the pride of the order
     *
     * @param hasWhippedCream checks if user orders whipped cream
     * @param hasChocolate    checks if user orders chocolate
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // Price of 1 cup
        int basePrice = 5;

        //Add $1 for whipped cream
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }

        //Add $2 for chocolate
        if (hasChocolate) {
            basePrice += 2;
        }

        //Return total
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param userName        is the inputted name
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate    is whether or not the user wants chocolate topping
     * @param price           of the order
     * @return text summary
     */
    private String createOrderSummary(String userName, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = "Name: " + userName;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nWhipped cream? " + hasWhippedCream;
        priceMessage += "\nChocolate? " + hasChocolate;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the given text on the screen. This isn't used anymore but I'm keeping it for reference.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}