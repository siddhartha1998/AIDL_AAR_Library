// PaymentResponse.aidl
package acquire.client_connection;

// Declare any non-default types here with import statements

parcelable PaymentResponse {
    long amount;
    String resultCode;
    String message;
    String invoiceNumber;
    String transactionDate;
}