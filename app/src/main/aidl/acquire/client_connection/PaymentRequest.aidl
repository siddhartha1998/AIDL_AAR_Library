// PaymentRequest.aidl
package acquire.client_connection;

// Declare any non-default types here with import statements


parcelable PaymentRequest {
    long amount;
    String type;
    String invoiceNo;
}