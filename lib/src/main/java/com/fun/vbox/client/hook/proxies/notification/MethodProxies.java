package com.fun.vbox.client.hook.proxies.notification;

import android.app.Notification;
import android.os.Build;

import com.fun.vbox.client.core.VCore;
import com.fun.vbox.client.hook.base.MethodProxy;
import com.fun.vbox.client.hook.utils.MethodParameterUtils;
import com.fun.vbox.client.ipc.VNotificationManager;
import com.fun.vbox.helper.compat.BuildCompat;
import com.fun.vbox.helper.utils.ArrayUtils;

import java.lang.reflect.Method;

/**
 * @author Lody
 */

@SuppressWarnings("unused")
class MethodProxies {

    static class EnqueueNotification extends MethodProxy {

        @Override
        public String getMethodName() {
            return "enqueueNotification";
        }

        @Override
        public Object call(Object who, Method method, Object... args) throws Throwable {
            String pkg = (String) args[0];
            if (getHostPkg().equals(pkg)) {
                return method.invoke(who, args);
            }
            int notificationIndex = ArrayUtils.indexOfFirst(args, Notification.class);
            int idIndex = ArrayUtils.indexOfFirst(args, Integer.class);
            int id = (int) args[idIndex];
            id = VNotificationManager.get().dealNotificationId(id, pkg, null, getAppUserId());
            args[idIndex] = id;
            Notification notification = (Notification) args[notificationIndex];
            if (!VNotificationManager.get().dealNotification(id, notification, pkg)) {
                return 0;
            }
            VNotificationManager.get().addNotification(id, null, pkg, getAppUserId());
            args[0] = getHostPkg();
            return method.invoke(who, args);
        }
    }

    /* package */ static class EnqueueNotificationWithTag extends MethodProxy {

        @Override
        public String getMethodName() {
            return "enqueueNotificationWithTag";
        }

        @Override
        public Object call(Object who, Method method, Object... args) throws Throwable {
            String pkg = (String) args[0];
            if (getHostPkg().equals(pkg)) {
                return method.invoke(who, args);
            }
            int notificationIndex = ArrayUtils.indexOfFirst(args, Notification.class);
            int idIndex = ArrayUtils.indexOfFirst(args, Integer.class);
            int tagIndex = (Build.VERSION.SDK_INT >= 18 ? 2 : 1);
            int id = (int) args[idIndex];
            String tag = (String) args[tagIndex];

            id = VNotificationManager.get().dealNotificationId(id, pkg, tag, getAppUserId());
            tag = VNotificationManager.get().dealNotificationTag(id, pkg, tag, getAppUserId());
            args[idIndex] = id;
            args[tagIndex] = tag;
            //key(tag,id)
            Notification notification = (Notification) args[notificationIndex];
            if (!VNotificationManager.get().dealNotification(id, notification, pkg)) {
                return 0;
            }
            VNotificationManager.get().addNotification(id, tag, pkg, getAppUserId());
            args[0] = getHostPkg();
            if (Build.VERSION.SDK_INT >= 18 && args[1] instanceof String) {
                args[1] = getHostPkg();
            }
            return method.invoke(who, args);
        }
    }

    /* package */ static class EnqueueNotificationWithTagPriority extends EnqueueNotificationWithTag {

        @Override
        public String getMethodName() {
            return "enqueueNotificationWithTagPriority";
        }
    }

    /* package */ static class CancelNotificationWithTag extends MethodProxy {

        @Override
        public String getMethodName() {
            return "cancelNotificationWithTag";
        }

        @Override
        public Object call(Object who, Method method, Object... args) throws Throwable {
            String pkg = MethodParameterUtils.replaceFirstAppPkg(args);
            if (getHostPkg().equals(pkg)) {
                return method.invoke(who, args);
            }
            char c2 = 1;
            if (BuildCompat.isR()) {
                args[1] = getHostPkg();
                c2 = 2;
            }
            String tag = (String) args[c2];
            args[c2] = VNotificationManager.get().dealNotificationTag(0, pkg, tag, getAppUserId());
            try {
                return method.invoke(who, args);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     *
     */
    /* package */ static class CancelAllNotifications extends MethodProxy {

        @Override
        public String getMethodName() {
            return "cancelAllNotifications";
        }

        @Override
        public Object call(Object who, Method method, Object... args) throws Throwable {
            String pkg = MethodParameterUtils.replaceFirstAppPkg(args);
            if (VCore.get().isAppInstalled(pkg)) {
                VNotificationManager.get().cancelAllNotification(pkg, getAppUserId());
                return 0;
            }
            return method.invoke(who, args);
        }
    }

    static class AreNotificationsEnabledForPackage extends MethodProxy {
        @Override
        public String getMethodName() {
            return "areNotificationsEnabledForPackage";
        }

        @Override
        public Object call(Object who, Method method, Object... args) throws Throwable {
            String pkg = (String) args[0];
            if (getHostPkg().equals(pkg)) {
                return method.invoke(who, args);
            }
            return VNotificationManager.get().areNotificationsEnabledForPackage(pkg, getAppUserId());
        }
    }

    static class SetNotificationsEnabledForPackage extends MethodProxy {
        @Override
        public String getMethodName() {
            return "setNotificationsEnabledForPackage";
        }

        @Override
        public Object call(Object who, Method method, Object... args) throws Throwable {
            String pkg = (String) args[0];
            if (getHostPkg().equals(pkg)) {
                return method.invoke(who, args);
            }
            int enableIndex = ArrayUtils.indexOfFirst(args, Boolean.class);
            boolean enable = (boolean) args[enableIndex];
            VNotificationManager.get().setNotificationsEnabledForPackage(pkg, enable, getAppUserId());
            return 0;
        }
    }

    static class GetAppActiveNotifications  extends MethodProxy{
        @Override
        public String getMethodName() {
            return "getAppActiveNotifications";
        }

        @Override
        public Object call(Object who, Method method, Object... args) throws Throwable {
            args[0] = getHostPkg();
            if (args.length > 1) {
                args[1] = 0;
            }
            return method.invoke(who, args);
        }
    }
}
