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
    Marker mark;
    CameraUpdate update;
    ImageView listImage, normal, satellite, hybird, terrain, bino, binoclose, close, closeinfo, info, up, down;
    TextView textView;
    Geocoder geocoder;
    List<Address> addresses;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


        // listview1 = (ListView) findViewById(R.id.listview1);
    //    up = (ImageView) findViewById(R.id.up);
      //  down = (ImageView) findViewById(R.id.down);
        rvhorizon = (RelativeLayout) findViewById(R.id.rvhorizon);
        rvhorizon2 = (RelativeLayout) findViewById(R.id.rvhorizon2);
        rvhorizon2.setVisibility(View.INVISIBLE);
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
        double lat[] = new double[]{12.9719400, 12.960000, 12.950000, 12.940000, 12.930000, 13.00000, 13.02000, 13.02530, 13.06000, 13.07000};
        double lng[] = new double[]{77.5936900, 77.5836900, 77.5636900, 77.5536900, 77.5336900, 78.0000000, 78.0200000, 78.0250000, 78.06000, 78.07000};
        String names[] = new String[]{"Adukudi",  "Koramangakla",  "Silk board",  "Place 4",  "Place 5", "Place 6", "Place 7", "Place 8", "Place 9", "Place 10"};
        for(int i=0; i<10; i++) {
            imageArry.add(new LandPlace(names[i], lat[i], lng[i]));
        }

        imageAdapter = new ListImageAdapter(this, R.layout.image_list, imageArry);
        listview.setAdapter(imageAdapter);
        highlightAdapter = new HighlightAdapter(this, R.layout.highlight, imageArry);
        HorizontalListView listview2 = (HorizontalListView) findViewById(R.id.listview2);
        listview2.setAdapter(highlightAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {

                double latitudezoom = imageArry.get(position).getLatitude();
                double longtitudezoom = imageArry.get(position).getLongtitude();

                for (int i = 0; i < imageAdapter.getCount(); i++) {
                    View item = listview.getChildAt(i);
                    if (item != null) {
                        ImageView imageInsideView = (ImageView) item.findViewById(R.id.imglist);
                        imageInsideView.setAlpha(1.0f);
                        TextView textLabel = (TextView)item.findViewById(R.id.textid);
                        textLabel.setTextColor(Color.BLACK);
                    }
                }
                ImageView imageInsideView = (ImageView) v.findViewById(R.id.imglist);
                imageInsideView.setAlpha(0.6f);
                TextView textLabel = (TextView)v.findViewById(R.id.textid);
                textLabel.setTextColor(Color.RED);
                CameraUpdate update1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latitudezoom, longtitudezoom), 15);
                mMap.animateCamera(update1);
               // rvhorizon.setVisibility(View.INVISIBLE);
               // rvhorizon2.setVisibility(View.VISIBLE);

                try {
                    addresses = geocoder.getFromLocation( latitudezoom,  longtitudezoom, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    textView.setText(imageArry.get(position).getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        for(int i=0;i<lat.length;i++)
        {
            mark = mMap.addMarker(new MarkerOptions().position(new LatLng(lat[i], lng[i])).icon(BitmapDescriptorFactory.fromResource(R.drawable.loco)));
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
            mMap.setTrafficEnabled(true);
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
            try {
                addresses = geocoder.getFromLocation( latitude,  longtitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                textView.setText(address);
            } catch (IOException e) {
                e.printStackTrace();
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