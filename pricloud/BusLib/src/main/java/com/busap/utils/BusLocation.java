package com.busap.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import java.lang.ref.WeakReference;

/**
 * Created by hsx on 17-8-9.
 */

public class BusLocation {
    public static SimpleLocation mSimpleLocation = null;

    public static synchronized SimpleLocation getSimpleLocationInstance(Context c) {
        if (mSimpleLocation == null) {
            mSimpleLocation = new Simple(c);
        }

        return mSimpleLocation;
    }

    private static class Simple implements SimpleLocation {
        private WeakReference<Context> mCtx = null;
        private LocationManager localMan = null;
        private String bestProvider = null;
        private Location location = null;
        private Criteria criteria = null;

        public Simple(Context c) {
            if (c != null)
                mCtx = new WeakReference<Context>(c);

            localMan = (LocationManager) mCtx.get().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_LOW);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);
            criteria.setSpeedRequired(false);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
//            criteria.set
        }

        @Override
        public double getLongitude() {
            if (localMan == null)
                return 0;

            bestProvider = localMan.getBestProvider(criteria, false);
            location = (Location) localMan.getLastKnownLocation(bestProvider);

            return location.getLongitude();
        }

        @Override
        public double getLatitude() {
            if (localMan == null)
                return 0;

            bestProvider = localMan.getBestProvider(criteria, false);
            location = (Location) localMan.getLastKnownLocation(bestProvider);

            return location.getLatitude();
        }

        @Override
        public long getTimestamp() {
            if (localMan == null)
                return 0;

            bestProvider = localMan.getBestProvider(criteria, false);
            location = (Location) localMan.getLastKnownLocation(bestProvider);

            return location.getTime();
        }
    }

    public interface SimpleLocation {
        double getLongitude();
        double getLatitude();
        long getTimestamp();
    }
}
