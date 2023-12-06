// IFewaPayService.aidl
package acquire.client_connection;
import acquire.client_connection.IPaymentCallback;
import acquire.client_connection.PaymentRequest;

// Declare any non-default types here with import statements

interface IFewaPayService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
        int getServiceId();
        void makePayment(IPaymentCallback callback,inout PaymentRequest payload, String clientPackageName);
        //printer
        boolean startPrinting( String imageBase64);
        int getPrinterValidWidth();
}