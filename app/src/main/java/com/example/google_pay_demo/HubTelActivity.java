package com.example.google_pay_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hubtel.payments.Exception.HubtelPaymentException;
import com.hubtel.payments.HubtelCheckout;
import com.hubtel.payments.Interfaces.OnPaymentResponse;
import com.hubtel.payments.SessionConfiguration;


public class HubTelActivity extends AppCompatActivity {

    private static final String TAG = "HubTelActivity";

    TextView paymentStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_tel);
        paymentStatus = findViewById(R.id.paymentstatus);
    }

    public void initPayment(View v){
        try {
            paymentStatus.setText("");
            SessionConfiguration sessionConfiguration = new SessionConfiguration()
                    .Builder()
                    .setClientId(getString(R.string.client_id))
                    .setSecretKey(getString(R.string.client_secret_key))
                    .setMerchantAccountNumber(getString(R.string.merchant_account_number))
                    .build();
            HubtelCheckout hubtelPayments = new HubtelCheckout(sessionConfiguration);
            hubtelPayments.setPaymentDetails(0.1, "This is a demo payment");
            hubtelPayments.Pay(this);
            hubtelPayments.setOnPaymentCallback(new OnPaymentResponse() {
                @Override
                public void onFailed(String token, String reason) {
                    paymentStatus.setText("Payment failed: \nReason: " + reason);
                    Log.d(TAG, "onFailed: Payment failed: \nReason: " + reason);
                }

                @Override
                public void onCancelled() {
                    paymentStatus.setText("Payment was cancelled.");
                    Log.d(TAG, "onCancelled: Payment was cancelled");
                }

                @Override
                public void onSuccessful(String token) {
                    paymentStatus.setText("Payment was successful. \nToken: " + token);
                    Log.d(TAG, "onSuccessful: Payment was successful. \nToken: " + token);
                }
            });
        }catch (HubtelPaymentException ex){
            Log.e("test", ex.getMessage());
        }
    }

    public void openActivity(View view){
        startActivity(new Intent(this,CheckoutActivity.class));
    }
}