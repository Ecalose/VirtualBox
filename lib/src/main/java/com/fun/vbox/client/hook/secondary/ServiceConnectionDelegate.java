package com.fun.vbox.client.hook.secondary;

import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.fun.vbox.client.VClient;
import com.fun.vbox.client.core.VCore;
import com.fun.vbox.helper.collection.ArrayMap;
import com.fun.vbox.helper.compat.BuildCompat;
import com.fun.vbox.server.IBinderDelegateService;

import mirror.vbox.app.ActivityThread;
import mirror.vbox.app.ContextImpl;
import mirror.vbox.app.IServiceConnectionO;
import mirror.vbox.app.LoadedApk;

/**
 * @author Lody
 */

public class ServiceConnectionDelegate extends IServiceConnection.Stub {
    private final static ArrayMap<IBinder, ServiceConnectionDelegate> DELEGATE_MAP = new ArrayMap<>();
    private IServiceConnection mConn;

    private ServiceConnectionDelegate(IServiceConnection mConn) {
        this.mConn = mConn;
    }

    public static IServiceConnection getDelegate(Context context, ServiceConnection connection, int flags) {
        IServiceConnection sd = null;
        if (connection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        try {
            Object activityThread = ActivityThread.currentActivityThread.call();
            Object loadApk = ContextImpl.mPackageInfo.get(VCore.get().getContext());
            Handler handler = ActivityThread.getHandler.call(activityThread);
            sd = LoadedApk.getServiceDispatcher.call(loadApk, connection, context, handler, flags);
        } catch (Exception e) {
            Log.e("ConnectionDelegate", "getServiceDispatcher", e);
        }
        if (sd == null) {
            throw new RuntimeException("Not supported in system context");
        }
        return getDelegate(sd);
    }

    public static IServiceConnection removeDelegate(Context context, ServiceConnection conn) {
        IServiceConnection connection = null;
        try {
            Object loadApk = ContextImpl.mPackageInfo.get(VCore.get().getContext());
            connection = LoadedApk.forgetServiceDispatcher.call(loadApk, context, conn);
        } catch (Exception e) {
            Log.e("ConnectionDelegate", "forgetServiceDispatcher", e);
        }
        if (connection == null) {
            return null;
        }
        return ServiceConnectionDelegate.removeDelegate(connection);
    }

    public static ServiceConnectionDelegate getDelegate(IServiceConnection conn) {
        if (conn instanceof ServiceConnectionDelegate) {
            return (ServiceConnectionDelegate) conn;
        }
        IBinder binder = conn.asBinder();
        ServiceConnectionDelegate delegate = DELEGATE_MAP.get(binder);
        if (delegate == null) {
            delegate = new ServiceConnectionDelegate(conn);
            DELEGATE_MAP.put(binder, delegate);
        }
        return delegate;
    }

    public static ServiceConnectionDelegate removeDelegate(IServiceConnection conn) {
        return DELEGATE_MAP.remove(conn.asBinder());
    }

    @Override
    public void connected(ComponentName name, IBinder service) throws RemoteException {
        connected(name, service, false);
    }

    public void connected(ComponentName name, IBinder service, boolean dead) throws RemoteException {
        IBinderDelegateService delegateService = IBinderDelegateService.Stub.asInterface(service);
        if (delegateService != null) {
            name = delegateService.getComponent();
            service = delegateService.getService();
            IBinder proxy = ProxyServiceFactory.getProxyService(VClient.get().getCurrentApplication(), name, service);
            if (proxy != null) {
                service = proxy;
            }
        }

        if (BuildCompat.isOreo()) {
            IServiceConnectionO.connected.call(mConn, name, service, dead);
        } else {
            mConn.connected(name, service);
        }
    }
}
