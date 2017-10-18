package justynachrustna.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameCheckBox = (EditText) findViewById(R.id.edit_text_name);
        String name = nameCheckBox.getText().toString();
        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String message = crateOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    private int calculatePrice(boolean hasChocolate, boolean hasWhippedCream) {
        int basePrice = 5;
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        return basePrice * quantity;
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            quantity = 1;
            Toast.makeText(this, R.string.toast_less, Toast.LENGTH_SHORT).show();
        }
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else {
            quantity = 100;
            Toast.makeText(this, R.string.toast_more, Toast.LENGTH_SHORT).show();
        }
    }

    private String crateOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String priceMessage =  getString(R.string.order_sumary_name)+name;
        priceMessage+="\n"+getString(R.string.add_whipped_cream)+hasWhippedCream;
        priceMessage+="\n"+ getString(R.string.add_chocolate)+hasChocolate;
        priceMessage+="\n"+ getString(R.string.order_quantity)+quantity;
        priceMessage+="\n"+getString(R.string.total)+NumberFormat.getCurrencyInstance().format(price);
        priceMessage+="\n"+getString(R.string.thank_you);

        return priceMessage;


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
