package com.example.upcity.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.upcity.network.ApiService;
import com.example.upcity.network.RetrofitClient;
import com.example.upcity.utils.ResponseGooglePay;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GooglePay {

    private final Context context;
    private PaymentsClient paymentsClient;

    public GooglePay(Context context) {
        this.context = context;
    }

    public void initGooglePay() {
        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                .build();
        paymentsClient = Wallet.getPaymentsClient(context, walletOptions);
    }

    public void startPayment(Activity activity, int requestCode) {
        PaymentDataRequest request = PaymentDataRequest.fromJson(getPaymentDataRequestJson());
        Task<PaymentData> paymentDataTask = paymentsClient.loadPaymentData(request);
        AutoResolveHelper.resolveTask(paymentDataTask, activity, requestCode);
    }

    public void handlePaymentSuccess(PaymentData paymentData, PaymentCallback callback) {
        try {
            JSONObject jsonObject = new JSONObject(paymentData.toJson());
            String paymentToken = jsonObject
                    .getJSONObject("paymentMethodData")
                    .getJSONObject("tokenizationData")
                    .getString("token");

            sendPaymentTokenToServer(paymentToken, callback);
        } catch (JSONException e) {
            e.printStackTrace();
            if (callback != null) callback.onError("Помилка обробки платежу");
        }
    }

    private void sendPaymentTokenToServer(String paymentToken, PaymentCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);

        String authToken = "Bearer " + accessToken;
        Map<String, String> body = new HashMap<>();
        body.put("paymentToken", paymentToken);

        ApiService apiService = RetrofitClient.getInstance();
        Call<ResponseBody> call = apiService.sendPaymentToken(authToken, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonStr = response.body().string();
                        JSONObject json = new JSONObject(jsonStr);

                        String status = json.optString("status");
                        String message = json.optString("message");
                        int subscriptionId = json.optInt("subscription_id", -1);

                        ResponseGooglePay responseGooglePay;
                        if (subscriptionId != -1) {
                            responseGooglePay = new ResponseGooglePay(status, message, subscriptionId);
                        } else {
                            responseGooglePay = new ResponseGooglePay(status, message);
                        }

                        callback.onSuccess(responseGooglePay);

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onError("Помилка розбору відповіді сервера");

                    }
                } else {
                    String errorMessage = parseErrorMessage(response);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError("Помилка мережі: " + t.getMessage());
            }
        });
    }

    private String parseErrorMessage(Response<?> response) {
        try {
            String errorBody = response.errorBody().string();
            JSONObject json = new JSONObject(errorBody);
            Object detail = json.get("detail");

            if (detail instanceof JSONArray) {
                return ((JSONArray) detail).getJSONObject(0).getString("msg");
            }

            return detail.toString();
        } catch (Exception e) {
            return "Помилка";
        }
    }

    public interface PaymentCallback {
        void onSuccess(ResponseGooglePay response);
        void onError(String errorMessage);
    }

    private String getIsReadyToPayRequestJson() {
        return "{\n" +
                "  \"allowedPaymentMethods\": [\n" +
                "    {\n" +
                "      \"type\": \"CARD\",\n" +
                "      \"parameters\": {\n" +
                "        \"allowedAuthMethods\": [\"PAN_ONLY\", \"CRYPTOGRAM_3DS\"],\n" +
                "        \"allowedCardNetworks\": [\"VISA\", \"MASTERCARD\"]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    private String getPaymentDataRequestJson() {
        return "{\n" +
                "  \"apiVersion\": 2,\n" +
                "  \"apiVersionMinor\": 0,\n" +
                "  \"allowedPaymentMethods\": [\n" +
                "    {\n" +
                "      \"type\": \"CARD\",\n" +
                "      \"parameters\": {\n" +
                "        \"allowedAuthMethods\": [\"PAN_ONLY\", \"CRYPTOGRAM_3DS\"],\n" +
                "        \"allowedCardNetworks\": [\"VISA\", \"MASTERCARD\"]\n" +
                "      },\n" +
                "      \"tokenizationSpecification\": {\n" +
                "        \"type\": \"PAYMENT_GATEWAY\",\n" +
                "        \"parameters\": {\n" +
                "          \"gateway\": \"example\",\n" +
                "          \"gatewayMerchantId\": \"exampleMerchantId\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"merchantInfo\": {\n" +
                "    \"merchantName\": \"Example Merchant\"\n" +
                "  },\n" +
                "  \"transactionInfo\": {\n" +
                "    \"totalPriceStatus\": \"FINAL\",\n" +
                "    \"totalPrice\": \"10.00\",\n" +
                "    \"currencyCode\": \"USD\"\n" +
                "  }\n" +
                "}";
    }
}
