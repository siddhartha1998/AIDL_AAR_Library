// IPaymentCallback.aidl
package acquire.client_connection;
import acquire.client_connection.PaymentResponse;

// Declare any non-default types here with import statements

interface IPaymentCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void onResponse(inout PaymentResponse response);
}