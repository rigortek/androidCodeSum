package com.cw.providercall;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {

    class ServiceStubImpl extends IMyServiceInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String bindCall(int parameter) throws RemoteException {
            if (0 == parameter) {
                throw new NullPointerException("test NullPointerException");
            } else if (1 == parameter) {
                throw new UnsatisfiedLinkError("test UnsatisfiedLinkError");
            } else if (2 == parameter) {
                throw new Resources.NotFoundException("test UnsatisfiedLinkError");
            }  else if (3 == parameter) {
                throw new RemoteException("test RemoteException");
            }
            return "unknown";
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mServiceStubImpl;
    }

    ServiceStubImpl mServiceStubImpl = new ServiceStubImpl();
}
