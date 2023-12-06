package com.example.clientapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import acquire.client_connection.IFewaPayService;
import acquire.client_connection.IPaymentCallback;
import acquire.client_connection.PaymentRequest;

public class AppService implements ServiceConnection {
    private static final String TAG = "TMSCallHelper";
    private static final String INTENT_ACTION = "Your-Action_filter-name";
    private static final String PACKAGE_NAME = "Your-package-name";
    private static volatile AppService instance;
    private int retry = 0;
    private static final int MAX_RETRY_COUNT = 3;
    private static final long RETRY_INTERVALS = 3000;
    private IFewaPayService iFewaPayService;
    private boolean isServiceConnected;
    private Context context;

    public static AppService me() {
        if (instance == null) {
            synchronized (AppService.class) {
                if (instance == null) {
                    instance = new AppService();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        retry = 0;
        iFewaPayService = IFewaPayService.Stub.asInterface(iBinder);
        isServiceConnected = true;
        Log.d(TAG, "TMS Call service connected");
        bindService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        isServiceConnected = false;
        iFewaPayService = null;
//        context.unbindService(null);
        Log.d(TAG, "TMS Call service disconnected");
    }

    public void bindService() {
        if (isServiceConnected) {
            return;
        }

        Intent service = new Intent(INTENT_ACTION);
        service.setPackage(PACKAGE_NAME);
        boolean bindSucc = context.bindService(service, me(), Context.BIND_AUTO_CREATE);
        Log.i(TAG, "Bind Service: " + bindSucc);
        if (!bindSucc && retry++ < MAX_RETRY_COUNT) {
            Log.e(TAG, "=> bind fail, rebind (" + retry + ")");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            }, RETRY_INTERVALS);
        }
    }


    public void makePayment(PaymentRequest request, String packageName, IPaymentCallback callback) {
        Log.d("PaymentRequest", "Make payment");
        if (iFewaPayService == null || !isServiceConnected) {
            bindService();
            return;
        }

        try {
            Log.d("PaymentRequest", "Make payment call");
            iFewaPayService.makePayment(callback, request, packageName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean printBitmap(String base64String) {
        boolean isValid = BaseUtils.isValidBase64(base64String);

        if (isValid) {
            try {
                return iFewaPayService.startPrinting(base64String);
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public int getPrintValidWidth() {
        try {
            return iFewaPayService.getPrinterValidWidth();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isServiceConnected(){
        return isServiceConnected;
    };
}
