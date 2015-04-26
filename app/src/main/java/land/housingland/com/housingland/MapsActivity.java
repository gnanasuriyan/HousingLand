package land.housingland.com.housingland;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    ArrayList<LandPlace> imageArry = new ArrayList<LandPlace>();

    Button add, travelbutton;
    RelativeLayout rl, rv4, rvinfo, rvhorizon,rvhorizon2;
    EditText ttext, travel;
    double latitude;
    double longtitude;
    ListView listview1;
    double latfind;
    double langfind;

    // ArrayList<Contact> imageArry = new ArrayList<Contact>();
    ListImageAdapter imageAdapter;
    HighlightAdapter highlightAdapter;
    private static final int CAMERA_REQUEST = 1;

    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    //ListView dataList;
    byte[] imageName;
    int imageId;
    Bitmap theImage;
//    DataBaseHandler db, dbimage;
    Bitmap[] yourSelected;
    String val;
    Bitmap resized;
    private LatLng ASD;
    Location mCurrentLocation;
    Marker mark,mark1,mark2;
    CameraUpdate update;
    ImageView listImage, normal, satellite, hybird, terrain, bino, binoclose, close, closeinfo, info, up, down;
    TextView textView;
    Geocoder geocoder;
    List<Address> addresses;
    ImageView school,bus,train,hospital;



    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    double lat[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        school= (ImageView) findViewById(R.id.school);
        bus= (ImageView) findViewById(R.id.bus);
        train= (ImageView) findViewById(R.id.train);
        hospital= (ImageView) findViewById(R.id.hospital);
        textView= (TextView) findViewById(R.id.textView1);
        final double lat[] = new double[]{12.971893,13.021989,13.013794,12.936176,12.977999,12.919780};
        final double lng[] = new double[]{ 77.581994, 77.600362, 77.549207, 77.610661, 77.701299, 77.670743};
        String names[] = new String[]{"Rs.30Lacs",  "Rs.23Lacs",  "Rs.40Lacs",  "Rs.1Cr",  "4Lacs","17Lacs"};
        final double latSchool[]=new double[]{12.972787,12.973864,12.970382,12.971893,12.971843};
        final double lngSchool[]=new double[]{77.581994,77.582627,77.581093,77.581994,77.581994};
        final double latbus[]=new double[]{12.972782,12.973867,12.970344,12.971874,12.971845};
        final double lngbus[]=new double[]{77.531984,77.572647,77.581083,77.541954,77.501984};
        final double lathos[]=new double[]{12.970500,12.9720700,12.971344,12.9710850,12.971245};
        final double lnghos[]=new double[]{77.582484,77.582647,77.586083,77.582754,77.582584};
        final double latSchool1[]=new double[]{13.022670177,13.0244050,13.022315,13.021646,13.024552};
        final double lngSchool1[]=new double[]{77.0602926,77.600620,599139,602991,77.603559};
        final double latbus1[]=new double[]{13.023371,13.022367,13.022543,13.022042,13.020653};
        final double lngbus1[]=new double[]{77.600738,77.600856,77.601886,77.599590,77.599450};
        final double latschool2[]=new double[]{13.014578,13.013031};
        final double lngschool2[]=new double[]{77.50661,77.552120};
        final double latbus2[]=new double[]{13.016637,13.013281,13.012550};
        final double lngbus2[]=new double[]{77.552807,77.544384,77.552259};
        final double latschool3[]=new double[]{12.935833,12.933664,12.935237,12.934202};
        final double lngschool3[]=new double[]{77.610437,77.611703,77.613205,77.614170};
        final double latbus3[]=new double[]{12.935833,12.933664,12.934202};
        final double lngbus3[]=new double[]{77.610437,77.611703,77.614170};
        final double latschool4[]=new double[]{12.978649,12.977687,12.975269,12981576};
        final double lngschool4[]=new double[]{77.703509,77.697114,77.704238,77.696342};
        final double latbus4[]=new double[]{12.978649,12.975269,12981576};
        final double lngbus4[]=new double[]{77.703509,77.704238,77.696342};
        final double latschool5[]=new double[]{12.922223,12.924377,12.920654,12.921616};
        final double lngschool5[]=new double[]{77.669591,77.668733,77.666673,77.673539};
        final double latbus5[]=new double[]{12.922223,12.924377,12.920654};
        final double lngbus5[]=new double[]{77.669591,77.668733,77.666673};
        textView.setText("My Current Location");
school.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
mMap.clear();
        for(int i=0;i<lat.length;i++)
        {
            mark = mMap.addMarker(new MarkerOptions().position(new LatLng(lat[i], lng[i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.green)));
        }

        for(int i=0;i<latSchool.length;i++)
         {



              mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latSchool[i], lngSchool  [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));



          }
        for(int i=0;i<latSchool.length;i++)
        {



            mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latSchool1[i], lngSchool1  [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));


        }

        for(int i=0;i<latschool2.length;i++)
        {



            mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latschool2[i], lngschool2  [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));


        }
        for(int i=0;i<latschool3.length;i++)
        {



            mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latschool3[i], lngschool3  [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));


        }
        for(int i=0;i<latschool4.length;i++)
        {



            mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latschool4[i], lngschool4 [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));


        }
        for(int i=0;i<latschool5.length;i++)
        {



            mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latschool5[i], lngschool5 [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));


        }
    }
});
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mMap.clear();
                for(int i=0;i<lat.length;i++)
                {
                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(lat[i], lng[i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.green)));
                }

                for(int i=0;i<latSchool.length;i++)

                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus[i], lngbus
                            [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

                }
                for(int i=0;i<latSchool.length;i++)

                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus1[i], lngbus1
                            [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

                }
                for(int i=0;i<latbus2.length;i++)

                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus2[i], lngbus2
                            [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

                }
                for(int i=0;i<latbus3.length;i++)

                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus3[i], lngbus3
                            [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

                }
                for(int i=0;i<latbus4.length;i++)
                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus4[i], lngbus4 [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));


                }
                for(int i=0;i<latbus5.length;i++)
                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus5[i], lngbus5 [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));


                }
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                for (int i = 0; i < lat.length; i++) {
                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(lat[i], lng[i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.green)));
                }


            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                for (int i = 0; i < lat.length; i++) {
                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(lat[i], lng[i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.green)));
                }

                for (int i = 0; i < latSchool.length; i++)

                {


                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(lathos[i], lnghos
                            [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));

                }
                for(int i=0;i<latSchool.length;i++)

                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus1[i], lngbus1
                            [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));

                }
                for(int i=0;i<latbus2.length;i++)

                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus2[i], lngbus2
                            [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));

                }
                for(int i=0;i<latbus3.length;i++)

                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latbus3[i], lngbus3
                            [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));

                }
                for(int i=0;i<latschool4.length;i++)
                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latschool4[i], lngschool4 [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));


                }
                for(int i=0;i<latschool5.length;i++)
                {



                    mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latschool5[i], lngschool5 [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));


                }
            }
        });
             //   for(int i=0;i<latSchool.length;i++)
               // {



              //      mark = mMap.addMarker(new MarkerOptions().position(new LatLng(latSchool[i], lngschool
              //              [i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));
             //   }


        // listview1 = (ListView) findViewById(R.id.listview1);
    //    up = (ImageView) findViewById(R.id.up);
      //  down = (ImageView) findViewById(R.id.down);
        final RelativeLayout highlighter= (RelativeLayout) findViewById(R.id.highlight);
        rvhorizon = (RelativeLayout) findViewById(R.id.rvhorizon);
        highlighter.setVisibility(View.INVISIBLE);

       // rvhorizon2 = (RelativeLayout) findViewById(R.id.rvhorizon2);
      //  rvhorizon2.setVisibility(View.INVISIBLE);
        //rvhorizon.animate().alpha(0.3f);

       // up.setVisibility(View.INVISIBLE);
      //  down.setOnClickListener(new View.OnClickListener() {
//            @Override
            //public void onClick(View v) {

        //        up.setVisibility(View.VISIBLE);
       //         down.setVisibility(View.INVISIBLE);

     //           rvhorizon.setVisibility(View.INVISIBLE);
     //       }
     //   });
//        up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                up.setVisibility(View.INVISIBLE);
//                down.setVisibility(View.VISIBLE);
//                rvhorizon.setVisibility(View.VISIBLE);
//            }
//        });
        final HorizontalListView listview = (HorizontalListView) findViewById(R.id.listview1);
        //listview.animate().alpha(1.0f);

       // dbimage = new DataBaseHandler(this);
//        List<LandPlace> landPlaces = null;
//        for (LandPlace con : landPlaces) {
//
//            imageArry.add(con);
//        }

        for(int i=0; i<lat.length; i++) {
            imageArry.add(new LandPlace(names[i], lat[i], lng[i]));
        }

        imageAdapter = new ListImageAdapter(this, R.layout.image_list, imageArry);
        listview.setAdapter(imageAdapter);



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {

                double latitudezoom = imageArry.get(position).getLatitude();
                double longtitudezoom = imageArry.get(position).getLongtitude();

                highlighter.setVisibility(View.VISIBLE);
                for (int i = 0; i < imageAdapter.getCount(); i++) {
                    View item = listview.getChildAt(i);
                    if (item != null) {
                        ImageView imageInsideView = (ImageView) item.findViewById(R.id.imglist);
                        imageInsideView.setAlpha(1.0f);
                        TextView textLabel = (TextView)item.findViewById(R.id.textid);
                        textLabel.setTextColor(Color.WHITE);
                        try {
                            double latitudeaddress=imageArry.get(position).getLatitude();
                            double longitudeADRESS=imageArry.get(position).getLongtitude();
                            addresses = geocoder.getFromLocation( latitudeaddress,  longitudeADRESS, 1);
                            String address = addresses.get(0).getAddressLine(0);
                            textView.setText(address);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                ImageView imageInsideView = (ImageView) v.findViewById(R.id.imglist);
                imageInsideView.setAlpha(0.6f);
                TextView textLabel = (TextView)v.findViewById(R.id.textid);
                textLabel.setTextColor(Color.GREEN);
                CameraUpdate update1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latitudezoom, longtitudezoom), 15);
                mMap.animateCamera(update1);
               // rvhorizon.setVisibility(View.INVISIBLE);
               // rvhorizon2.setVisibility(View.VISIBLE);


            }
        });
        for(int i=0;i<lat.length;i++)
        {
            mark = mMap.addMarker(new MarkerOptions().position(new LatLng(lat[i], lng[i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.green)));
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
      //  mMap.setMyLocationEnabled(true);
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        geocoder = new Geocoder(this, Locale.getDefault());
        textView= (TextView) findViewById(R.id.textView1);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }

    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {


          //  ASD = new LatLng(cn._latitude, cn._longtitude);

            // Toast.makeText(getApplicationContext(),  "Latitude  " + cn._latitude + "Longtitude" + cn._longtitude, Toast.LENGTH_LONG).show();


            mMap.setBuildingsEnabled(true);
            mMap.setIndoorEnabled(true);
            mMap.setMyLocationEnabled(true);
        mMap.addPolyline(new PolylineOptions().color(0xffffffff));
           // mMap.setTrafficEnabled(true);
            mMap.setContentDescription(String.valueOf(true));
         //   mMap.addMarker(new MarkerOptions().position(ASD).title(cn.getName()).icon(BitmapDescriptorFactory.fromBitmap(resized)));
        }




    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            latitude = mCurrentLocation.getLatitude();
            longtitude = mCurrentLocation.getLongitude();

            mark.setPosition(new LatLng(latitude, longtitude));
            if (update == null) {
                update = CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 11);
                mMap.animateCamera(update);
            }



        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }
}