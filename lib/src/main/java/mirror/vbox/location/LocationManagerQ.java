package mirror.vbox.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.ArrayMap;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefObject;

public class LocationManagerQ {
    public static Class<?> TYPE = RefClass
            .load(LocationManagerQ.class, "android.location.LocationManager");
    public static RefObject<ArrayMap> mGnssNmeaListeners;
    public static RefObject<Object> mGnssStatus;
    public static RefObject<ArrayMap> mGnssStatusListeners;
    public static RefObject<ArrayMap> mGpsNmeaListeners;
    public static RefObject<Object> mGpsStatus;
    public static RefObject<ArrayMap> mGpsStatusListeners;
    public static RefObject<ArrayMap> mListeners;
    public static RefObject<ArrayMap> mNmeaListeners;

    public static class GnssStatusListenerTransport {
        public static Class<?> TYPE = RefClass.load(GnssStatusListenerTransport.class,
                "android.location.LocationManager$GnssStatusListenerTransport");
        public static RefObject<Object> mListener;
        public static RefObject<Object> mNmeaListener;
        @MethodParams({int.class})
        public static RefMethod<Void> onFirstFix;
        public static RefMethod<Void> onGnssStarted;
        @MethodParams({long.class, String.class})
        public static RefMethod<Void> onNmeaReceived;
        @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class})
        public static RefMethod<Void> onSvStatusChanged;
        public static RefObject<Object> this$0;
    }

    public static class GnssStatusListenerTransportV8 {
        public static Class<?> TYPE = RefClass.load(GnssStatusListenerTransportV8.class,
                "android.location.LocationManager$GnssStatusListenerTransport");
        public static RefObject<Object> mListener;
        public static RefObject<Object> mNmeaListener;
        @MethodParams({int.class})
        public static RefMethod<Void> onFirstFix;
        public static RefMethod<Void> onGnssStarted;
        @MethodParams({long.class, String.class})
        public static RefMethod<Void> onNmeaReceived;
        @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class,
                float[].class})
        public static RefMethod<Void> onSvStatusChanged;
        public static RefObject<Object> this$0;
    }

    public static class GpsStatusListenerTransport {
        public static Class<?> TYPE = RefClass.load(GpsStatusListenerTransport.class,
                "android.location.LocationManager$GpsStatusListenerTransport");
        public static RefObject<Object> mListener;
        public static RefObject<Object> mNmeaListener;
        @MethodParams({int.class})
        public static RefMethod<Void> onFirstFix;
        public static RefMethod<Void> onGpsStarted;
        @MethodParams({long.class, String.class})
        public static RefMethod<Void> onNmeaReceived;
        @MethodParams(
                {int.class, int[].class, float[].class, float[].class, float[].class, int.class,
                        int.class, int.class})
        public static RefMethod<Void> onSvStatusChanged;
        public static RefObject<Object> this$0;
    }

    public static class GpsStatusListenerTransportOPPO_R815T {
        public static Class<?> TYPE = RefClass.load(GpsStatusListenerTransportOPPO_R815T.class,
                "android.location.LocationManager$GpsStatusListenerTransport");
        @MethodParams(
                {int.class, int[].class, float[].class, float[].class, float[].class, int[].class,
                        int[].class, int[].class, int.class})
        public static RefMethod<Void> onSvStatusChanged;
    }

    public static class GpsStatusListenerTransportSumsungS5 {
        public static Class<?> TYPE = RefClass.load(GpsStatusListenerTransportSumsungS5.class,
                "android.location.LocationManager$GpsStatusListenerTransport");
        @MethodParams(
                {int.class, int[].class, float[].class, float[].class, float[].class, int.class,
                        int.class, int.class, int[].class})
        public static RefMethod<Void> onSvStatusChanged;
    }

    public static class GpsStatusListenerTransportVIVO {
        public static Class<?> TYPE = RefClass.load(GpsStatusListenerTransportVIVO.class,
                "android.location.LocationManager$GpsStatusListenerTransport");
        @MethodParams(
                {int.class, int[].class, float[].class, float[].class, float[].class, int.class,
                        int.class, int.class, long[].class})
        public static RefMethod<Void> onSvStatusChanged;
    }

    public static class ListenerTransport {
        public static Class<?> TYPE = RefClass.load(ListenerTransport.class,
                "android.location.LocationManager$ListenerTransport");
        public static RefObject<LocationListener> mListener;
        @MethodParams({Location.class})
        public static RefMethod<Void> onLocationChanged;
        @MethodParams({String.class})
        public static RefMethod<Void> onProviderDisabled;
        @MethodParams({String.class})
        public static RefMethod<Void> onProviderEnabled;
        @MethodParams({String.class, int.class, Bundle.class})
        public static RefMethod<Void> onStatusChanged;
        public static RefObject<Object> this$0;
    }
}