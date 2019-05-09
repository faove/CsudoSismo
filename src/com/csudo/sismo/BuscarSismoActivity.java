package com.csudo.sismo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.libre.taxi.EnviarData;




public class BuscarSismoActivity extends Activity {
	private final static String TAG = "BuscarSismoActivity";
	private Context mContext;
	private Context Context;
	static boolean canGetLocation = false;
	final static private long ONE_SECOND = 1000;
    //final static private long TWENTY_SECONDS = ONE_SECOND * 500;
	final static private long TWENTY_SECONDS = ONE_SECOND * 50;
	//final static private long TWENTY_SECONDS = ONE_SECOND;
	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;     
	private LocationManager locationManager;
	private LocationListener locationListener;
	private String provider;
	private Boolean cancelar=false;
	private String boton=null;
    List<Address> user = null;
   // double lat;
    //double lng;
    double latitude; // latitude
    double longitude; // longitude
    double lat_a,lng_a,lat_b, lon_b;
 
	private static LatLng cum = new LatLng(10.471106, -64.160987);
	private GoogleMap mapa;
	private LatLng cum1,pos;
	JSONArray jsonSismoArray = null;
	JSONArray jsonArray = null;
	JSONArray jsonArrayLista = null;
	JSONArray jsonSismos = null;
	JSONArray jsonUsuario = null;
	JSONArray jsonListataxidisponible=null;
	TextView editText;
	String usuario="",password="";
	String usuarios="";
	String SesionUsuario="";
	String texto="";
	ImageButton btn;
	Bitmap bmp;
	Uri imagen=null;
	String [] arraydata;
			
			
	//@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	//@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    public void onCreate(Bundle savedInstanceState) {
         
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.activity_buscar_sismo);
				
	     		
		btn = (ImageButton)findViewById(R.id.BtnSismo);
		
		Toast.makeText(getApplicationContext(),"Buscando sismos de los últimos 30 días",Toast.LENGTH_LONG).show();
		
        btn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		 
        		 Toast.makeText(getApplicationContext(),"Sistema actualizando datos",Toast.LENGTH_LONG).show();
        		//if (usuario!=null) {
        			
        			
        			//Tengo la variable cancelar
        			//la cual indica si el usuario pulso el boton de cancelar
        			
        			/*if (cancelar.equals(false)){
        				
        				cancelar=true;
        				
	        			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cancelartaxi);
	        			
	            		btn.setImageBitmap(bmp);
	            		
	            		boton="2";*/
	            		
	            		//rotation=true;
	        			tareaPedirSismos taskPedir = new tareaPedirSismos();	        			
	       	     		taskPedir.execute(new String[] { "http://csudo.udo.edu.ve/consultasismoult.php" });
	       	     		
	       	     		//Crear lista de taxistas
	       	     		Toast.makeText(getApplicationContext(),"Buscando el último sismo, espere...",Toast.LENGTH_LONG).show();
	       	     		/*try {
							wait(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
	       	     		
	       	     		/*Intent intent = new Intent(BuscarTaxiActivity.this, SeleccionarTaxiActivity.class);
        
	       	     		startActivity(intent);*/
	       	     		
        			//}else{
        				
        				/*cancelar=false;
        				//rotation=false;
        				bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pedirtaxi);
	            		btn.setImageBitmap(bmp);
	            		boton="1";
        				//Aqui va el procedimiento de cambiar el la base de datos la disponibilidad
        				//del usuario a 0, esto cancela inmediatemente el servicio
        				tareaCancelarTaxi taskCancelar = new tareaCancelarTaxi();	        			
	       	     		taskCancelar.execute(new String[] { "http://www.libretaxi.com/cancelartaxi.php" });*/
        				
        			//}
       	     		//PedirTaxi();
        		//}else{
        			/*Intent intent = new Intent(BuscarTaxiActivity.this, LoginActivity.class);
            		//startActivityForResult
            		//startActivityForResult(intent,PICK_CONTACT);
            		startActivity(intent);*/
        		//}
        }
        });
		
		
	    
	    tareaGPS taskGPS = new tareaGPS();
	    taskGPS.execute(new String[] { "" + cum });
	      
	    Toast.makeText(getApplicationContext(),"El sistema esta localizando su posición, espere...",Toast.LENGTH_LONG).show();
	    
	     tarea task = new tarea();
	     task.execute(new String[] { "http://csudo.udo.edu.ve/datosismo.json" });
	     
	     mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				 .getMap();
	     
	     if (mapa!=null){
	    	 
			 mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 
			 mapa.setMyLocationEnabled(true);
		 
			 mapa.getUiSettings().setZoomControlsEnabled(false);
		 
			 mapa.getUiSettings().setCompassEnabled(true);
	 
	     }
	   
	 	
	}	
	public void onPause() {
    	super.onPause();
    	setup();
    }
    public void onRestart() {
    	super.onRestart();
    	setup();
    }
    
    public void onStop() {
    	super.onStop();
    	setup();
    }
    public void onResumen() {
    	super.onResume();
    	setup();
    }
    
    public void onStart() {
    	super.onStart();
    	setup();
    }
    
    private void setup() {
        br = new BroadcastReceiver() {
        String idevent="", ideventbefore="", ideventafter="";     

    		@Override
    		public void onReceive(Context arg0, Intent arg1) {
    			// TODO Auto-generated method stub
    			//Toast.makeText(arg0, "Rise and Shine!", Toast.LENGTH_LONG).show();
    			tarea task = new tarea();
    		     task.execute(new String[] { "http://csudo.udo.edu.ve/datosismo.json" });
    		    
    		  
    		 
    			}
         };
               
        registerReceiver(br, new IntentFilter("com.csudo.sismo") );
        
        pi = PendingIntent.getBroadcast( this, 0, new Intent("com.csudo.sismo"),0 );
        
        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
        
        am.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), TWENTY_SECONDS, pi);
       
        
        }
    
	 @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         //Alternativa 1
         getMenuInflater().inflate(R.menu.buscar_sismo, menu);
         return true;
     }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 double distancia;
		 String msjdistancia,msjayuda;
	     switch (item.getItemId()) {
	         case R.id.MnuOpc1:
	        	 //Toast.makeText(getApplicationContext(),"Opcion 1 pulsada!",Toast.LENGTH_LONG).show();
	             //lblMensaje.setText("Opcion 1 pulsada!");
	        	 
	        		 distancia=getDistance(lat_a,lng_a,lat_b, lon_b); 
	        	 	//msjdistancia=""+distancia+"";
	        		 msjdistancia=Distance(distancia) + " del Sismo";
	        	 	//msjdistancia=""+distancia+"";
	        	 	showNeutralAlert("Usted se encuentra a:",msjdistancia);
	        	 	return true;
	         case R.id.MnuOpc2:
	        	 //Toast.makeText(getApplicationContext(),"Opcion 2 pulsada!",Toast.LENGTH_LONG).show();
	             //lblMensaje.setText("Opcion 2 pulsada!");;
	        	/* msjayuda="Este sistema muestra los últimos sismos de la Región Nor-Oriental de Venezuela, Los sismos" +
	        	 		"son de los últimos 30 días, usted puede pulsar el cada sismo y observar su fecha y magnitud";
	        	 showNeutralAlert("Centro de Sismología UDO",msjayuda);*/
	        	//llamada a la pantalla de espera por confirmacion del taxis
					Intent intent = new Intent(this,ListaSismoActivity.class);
					
					//intent.putExtra("conductor",jsonSismoArray);
					String valores=String.valueOf(jsonArrayLista);
					intent.putExtra("array", valores);
		     		startActivity(intent);
	             return true;
	         case R.id.MnuOpc3:
	        	 //Toast.makeText(getApplicationContext(),"Opcion 3 pulsada!",Toast.LENGTH_LONG).show();
	        	 showYesNoAlert("Salir","Desea Salir del Sistema?");
	             //lblMensaje.setText("Opcion 3 pulsada!");;
	        	 
	             return true;
	         default:
	             return super.onOptionsItemSelected(item);
	     }
	 }
	     //jsonTaxis=task.getjson();
	     
	     //editText.setText(task.toString());
	     /*try {
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 
	     
	     
	     //String editTextStr = editText.getText().toString();
	     
	 
	/*
	@SuppressWarnings("deprecation")
	@Override
	public Object onRetainNonConfigurationInstance() {
		 return bmp;
	}*/
	
	/*public Object onRetainNonConfigurationInstance()
	{
	   return btn;
	}*/
	
	@Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("BOTON", boton);

	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
	    super.onRestoreInstanceState(savedInstanceState);
	    boton = savedInstanceState.getString("BOTON");
	    /*if( boton == null )
		{*/
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.buttonultsismo);
			
			btn.setImageBitmap(bmp);
		   //DisplayPhoto.setImageBitmap( (Bitmap) last );
		/*}else if (boton == "2"){
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cancelartaxi);
			btn.setImageBitmap(bmp);
		
		}else if (boton =="1"){
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pedirtaxi);
			btn.setImageBitmap(bmp);
		}*/
	    //tvCont.setText(String.valueOf(boton));
	}
	/*
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Intent i= getIntent();
		//usuario = i.getStringExtra("SesionUsuario");
		//Log.i(TAG, "On Resume .....");
	}
*/
	/* (non-Javadoc)
	* @see android.app.Activity#onStart()
	*/
	/*@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent i= getIntent();
		
		i.getExtras();
		
		Log.i(TAG, "On Start .....");

	}*/


	public void moveCamera(View view) {
	         mapa.moveCamera(CameraUpdateFactory.newLatLng(cum));
	   }
	 
	   public void animateCamera(View view) {
	      if (mapa.getMyLocation() != null)
	         mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
	            new LatLng( mapa.getMyLocation().getLatitude(),mapa.getMyLocation().getLongitude()), 7));
	   }
	 
	   public void addMarker(View view) {
	      mapa.addMarker(new MarkerOptions().position(
	           new LatLng(mapa.getCameraPosition().target.latitude,
	      mapa.getCameraPosition().target.longitude)));

	   }
	 
	   public void onMapClick(LatLng puntoPulsado) {
	      mapa.addMarker(new MarkerOptions().position(puntoPulsado).
	         icon(BitmapDescriptorFactory
	            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
	   }
	   
public  class MiLocationListener implements LocationListener
	  {
		  public void onLocationChanged(Location loc)
		  {
			  
			  loc.getLatitude();
			  
			  loc.getLongitude();
			  
			  String coordenadas = "Mis coordenadas son: " + "Latitud = " + loc.getLatitude() + "Longitud = " + loc.getLongitude();
			  
			  Toast.makeText( getApplicationContext(),coordenadas,Toast.LENGTH_LONG).show();
			  
			  //LatLng cum = new LatLng(loc.getLatitude(), loc.getLongitude());
			  //Marker newmarker = map.addMarker(new MarkerOptions().position(loc.getLatitude()).title("marker title").icon(BitmapDescriptorFactory.fromResource(R.drawable.here)));
		 
		  }
		  public void onProviderDisabled(String provider)
		  {
			  Toast.makeText( getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
		  }
		  public void onProviderEnabled(String provider)
		  {
			  Toast.makeText( getApplicationContext(),"Gps Activo",Toast.LENGTH_SHORT ).show();
		  }
		  
		  public void onStatusChanged(String provider, int status, Bundle extras){}
		  
		 
	  }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------INICIO DE LOS ASYNCTASK-----------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

/*
 * Procedimiento de buscar taxis disponibles 
 */

private class tareaBuscarTaxi extends AsyncTask<String, Void, Boolean> {
	Boolean datoenviado=false; 
    @Override
    protected Boolean doInBackground(String... urls){
    	//datoenviado=PedirSismos(datoenviado);
		return datoenviado;
    }
    
    protected  void onPostExecute(String result) {
    	
    }
}

/*
 * Procedimiento para pedir taxi 
 */

private class tareaPedirSismos extends AsyncTask<String, Void, String> {
	//Boolean datoenviado=false;
	JSONArray json;
	String var;
	@SuppressWarnings("unused")
	protected void onProgressUpdate(String... values) {
		editText.setText(values[0]);
    }
    @Override
    protected String doInBackground(String... urls){
    	try {
			//datoenviado=PedirSismos(datoenviado);
    		json=BuscarUltSismo(var);
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return json;
		return var;
    }    
    @Override
    protected void onPostExecute(String result) {
    	//result =json.toString();
    	//Este es la forma de sacar los varoles de un hilo al programa principal
    	//editText.setText(result);
    	jsonSismos= json;
    	GraficarSismoUlt(json);
    }
   
}

/*
 * Procedimiento para cancelar taxi 
 */

private class tareaCancelarTaxi extends AsyncTask<String, Void, Boolean> {
	Boolean datoenviado=false; 
    @Override
    protected Boolean doInBackground(String... urls){
    	datoenviado=CancelarTaxi(datoenviado);
		return datoenviado;
    }
    protected  void onPostExecute(String result) {
    	
    }
}

/*
 * Procedimiento crear lista de taxis disponibles 
 */
/*
private class tareaCrearListaTaxi extends AsyncTask<String, Void, Boolean> {
	Boolean datoenviado=false; 
	
  @Override
  protected Boolean doInBackground(String... urls){
	  try {
  		datoenviado=CrearListaTaxi(datoenviado);
  		jsonListataxidisponible=CrearListaTaxiDisponibles();
  		
	  } catch (IOException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	}
	  return datoenviado;
  }
  
  protected  void onPostExecute(String result) {
	  // json=BuscarTaxiDisponibles(var);
	  
	 		//jsonListataxidisponible=CrearListaTaxiDisponibles();
	 	
	 
  }
}*/

/*
 * Procedimiento de ubicar al usuario a traves de GPS o network
 */

private class tareaGPS extends AsyncTask<String, Void, LatLng> {
	Geocoder geocoder;
    String bestProvider;
	JSONArray json;
	String var;
	
    @Override
    protected LatLng doInBackground(String... urls){
    	
    	LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       	Criteria criteria = new Criteria();
       	criteria.setAccuracy(Criteria.ACCURACY_FINE);
       	//// criteria.setAccuracy(Criteria.ACCURACY_COARSE);
       	criteria.setAltitudeRequired(false);
       	criteria.setBearingRequired(false);
       	criteria.setCostAllowed(true);
       	criteria.setPowerRequirement(Criteria.POWER_LOW);
       	String provider = lm.getBestProvider(criteria, false);
       	LocationListener loc_listener=new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub

            }
        };
   	//String bestProvider = lm.getBestProvider(criteria,false);   
   	try{
   		Looper.prepare();
   		lm.requestLocationUpdates(provider, 0, 0, loc_listener);
    }catch(Exception e){
        e.printStackTrace();
    }
   	Location location=lm.getLastKnownLocation(provider);
   	
   	
    try {
		 if (location == null){
			 location = lm
                     .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			 BuscarSismoActivity.canGetLocation = true;
	    	 LatLng pos = new LatLng(0.0, 0.0);
	         //Toast.makeText(getApplicationContext(),"Location Not found",Toast.LENGTH_LONG).show();
		 
		 //if (provider.equals("gps")){
			 criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			 //// criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			 criteria.setAltitudeRequired(false);
			 criteria.setBearingRequired(false);
			 criteria.setCostAllowed(true);
			 criteria.setPowerRequirement(Criteria.POWER_LOW);
			 provider = lm.getBestProvider(criteria, false);
			 location=lm.getLastKnownLocation(provider);
			 geocoder = new Geocoder(getApplicationContext());
    
			 user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			 
			 latitude=(double)user.get(0).getLatitude();
			 
			 longitude=(double)user.get(0).getLongitude();
			 
			 lat_b=latitude;
			 lon_b=longitude;
			 
			 
		        //System.out.println(" DDD lat: " +lat+",  longitude: "+lng);
		        //LatLng pos = new LatLng((double)user.get(0).getLatitude(), (double)user.get(0).getLongitude());
			
		// }
		 }else{
			 latitude = location.getLatitude();
			 longitude = location.getLongitude();
			 lat_b= latitude;
			 lon_b= longitude;
		 }
		 
		
	 }catch (Exception e) {
	      e.printStackTrace();
	 }
    
      return pos;
    }
    
    @Override
    protected  void onPostExecute(LatLng result) {
    	//result =json.toString();
    	//Este es la forma de sacar los varoles de un hilo al programa principal
    	//editText.setText(result);
    	//jsonTaxis= json;
    	LatLng pos = new LatLng(latitude, longitude);
    	GraficarUsuario(pos);
    	//return pos;
    }
}


/*
 * Thears buscar taxis para mostrar en los mapa de google 
 */
private class tarea extends AsyncTask<String, Void, String> {
	JSONArray json;
	String var;
	
	
	@SuppressWarnings("unused")
	protected void onProgressUpdate(String... values) {
		editText.setText(values[0]);
    }
 
    @Override
    protected String doInBackground(String... urls) {
      //String result = "";
      try {
    	  json=BuscarSismosDisponibles(var);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
      return var;
    }
    
    @Override
    protected void onPostExecute(String result) {
    	//result =json.toString();
    	//Este es la forma de sacar los varoles de un hilo al programa principal
    	//editText.setText(result);
    	jsonSismos= json;
    	GraficarSismos(json);
    }
}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------FIN DE LOS ASYNCTASK -------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


public void showSettingsAlert(){
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
  
    // Setting Dialog Title
    alertDialog.setTitle("GPS is settings");

    // Setting Dialog Message
    alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

    // Setting Icon to Dialog
    //alertDialog.setIcon(R.drawable.delete);

    // On pressing Settings button
    alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(intent);
        }
    });

    // on pressing cancel button
    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        }
    });

    // Showing Alert Message
    alertDialog.show();
}
/*----------------------------------------------------------------------------------
 * Funcion que muestra un mensaje al usuario para salir del sistema.
 ----------------------------------------------------------------------------------*/

public void showYesNoAlert(String Titulo,String Mensaje){
	
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	
	builder.setMessage(Mensaje)
			.setTitle(Titulo)
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							
							Intent intent=new Intent( Intent.ACTION_MAIN); //Llamando a la activity principal
							startActivity(intent);
							finish();
						}
					})
			.setNeutralButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
	AlertDialog alert = builder.create();
	alert.show();
   
}
public void showNeutralAlert(String Titulo,String Mensaje){
	
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	
	builder.setMessage(Mensaje)
			.setTitle(Titulo)
			.setCancelable(false)			
			.setNeutralButton("Aceptar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
	AlertDialog alert = builder.create();
	alert.show();
   
}
private void GraficarUsuario(LatLng pos){
	if (pos!=null){
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 7));
		mapa.addMarker(new MarkerOptions()
           .position(pos)
           .title("Usted esta aquí!")
           .snippet(usuario)
           .icon(BitmapDescriptorFactory
                  .fromResource(R.drawable.yo))
           .anchor(0.5f, 0.5f));
	}
}
private JSONArray GraficarSismos(JSONArray json) {
	String magnivalue,lat,lon,locdatetime,email,nombre,disponible;
	String idevent,comments,depth;
	String mensaje;
	
	LatLng sismos = new LatLng(10.471106, -64.160987);	
	SimpleDateFormat dt = new SimpleDateFormat("d-M-y h:m:s");	
	Date fecha;
	
    try {
        //jsonArray = new JSONArray(result);
        
        arraydata = new String[json.length()];
        jsonArrayLista=json;
        for (int i = 0; i < json.length(); i++) {
        	
            JSONObject row = json.getJSONObject(i);
            
            idevent = row.getString("idevent");
	         
	         lat = row.getString("lat");
	         
	         //lat_a=Integer.parseInt(lat);
	         
	         lon = row.getString("lon");
	         
	         //lng_a=Integer.parseInt(lon);
	         
	         locdatetime = row.getString("locdatetime");
	         
	         comments = row.getString("comments");
	         
	         magnivalue = row.getString("magnivalue");
	         
	         depth = row.getString("depth");
	         
	         fecha=	dt.parse(locdatetime);
	         
	         mensaje="Fecha:"+locdatetime+" Magnitud:" +magnivalue+"";
            
            //arraydata[i]=tlf;
            sismos = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
            /*if (disponible.equals("0")){
	            mapa.addMarker(new MarkerOptions()
	            .position(sismos)
	            .title(locdatetime)
	            //.snippet("Usted esta aquí")
	            .icon(BitmapDescriptorFactory
	                   .fromResource(R.drawable.here))
	            .anchor(0.5f, 0.5f));
            }else{*/
        	    mapa.addMarker(new MarkerOptions()
	            .position(sismos)
	            .title(mensaje)
	            //.snippet("Usted esta aquí")
	            .icon(BitmapDescriptorFactory
	                   .fromResource(R.drawable.circlered43))
	            .anchor(0.5f, 0.5f));
           // }
        }
       
      } catch (Exception e) {
        e.printStackTrace();
      }
	return json;
	
}
private JSONArray GraficarSismoUlt(JSONArray json) {
	String magnivalue,lat,lon,locdatetime,email,nombre,disponible;
	String idevent,comments,depth;
	String mensaje;
	String [] arraydata;
	LatLng sismoult = new LatLng(10.471106, -64.160987);
	int imagen=R.drawable.startorange43;
	SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss"); 
	Date fecha;
	
    try {
        
        arraydata = new String[json.length()];
        
        for (int i = 0; i < json.length(); i++) {
        	
            JSONObject row = json.getJSONObject(i);
            
            idevent = row.getString("idevent");
	         
	         //lat = row.getString("lat");
	         
	         //lon = row.getString("lon");
            
	         lat = row.getString("lat");
	         
	         lat_a=Double.valueOf(lat);
	         
	         lon = row.getString("lon");
	         
	         lng_a=Double.valueOf(lon);
	         
	         locdatetime = row.getString("locdatetime");
	         
	         comments = row.getString("comments");
	         
	         magnivalue = row.getString("magnivalue");
	         
	         depth = row.getString("depth");
	         
	         fecha=	dt.parse(locdatetime);
	         
	         mensaje="Fecha:"+fecha+" Magnitud:" +magnivalue+"";
	        
            //arraydata[i]=tlf;
            sismoult = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
            
            //mapa.setMyLocationEnabled(true);
        	mapa.addMarker(new MarkerOptions()
            .position(sismoult)
            .title(mensaje)
            //.snippet(magnivalue)
            .icon(BitmapDescriptorFactory
                   .fromResource(imagen))
            .anchor(0.5f, 0.5f));
        	//mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.2,-64.2), 15), 4000, null);
        	mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(sismoult, 15), 4000, null);
        	//mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
    	    //        new LatLng( mapa.getMyLocation().getLatitude(),mapa.getMyLocation().getLongitude()), 7));
        	//mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
    	    //        new LatLng(lat,lon, 7));
        	//
            //29/01/2014 comente funcion error mapa
            //mostrarsismos(sismosmes,locdatetime,imagen);
            
            /*if (disponible.equals("0")){
	            mapa.addMarker(new MarkerOptions()
	            .position(sismos)
	            .title(locdatetime)
	            //.snippet("Usted esta aquí")
	            .icon(BitmapDescriptorFactory
	                   .fromResource(R.drawable.here))
	            .anchor(0.5f, 0.5f));
            }else{*/
        	    
           // }
        }
       
      } catch (Exception e) {
        e.printStackTrace();
      }
	return json;
	
}
private  void mostrarsismos(LatLng sismosmes,String locdatetime,int imagen){
	mapa.setMyLocationEnabled(true);
	mapa.addMarker(new MarkerOptions()
    .position(sismosmes)
    .title(locdatetime)
    //.snippet("Usted esta aquí")
    .icon(BitmapDescriptorFactory
           .fromResource(imagen))
    .anchor(0.5f, 0.5f));
}

private Boolean PedirSismos(Boolean datoenviado) throws IOException {
	
	//String action(){
   // br = new BroadcastReceiver() {
    	
    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
    
    int w=0;
    
	//@Override
	//public void onReceive(Context arg0, Intent arg1) {
	// TODO Auto-generated method stub
	//Toast.makeText(arg0, "Rise and Shine!", Toast.LENGTH_LONG).show();
	String lat = null,lon = null,telefono = null,disponible = null,hora = null,comments = null;
	
	
		EnviarData enviardata = new EnviarData();
		
		//enviardata.name="Francisco Alvarez";
		
		enviardata.usuario=usuario;
		
		enviardata.lat = "" + String.valueOf(latitude) + "";
		
		enviardata.lon = "" + String.valueOf(longitude) + "";
		
		//enviardata.codigo="5555";
		
		Calendar c = Calendar.getInstance();
	    
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    String formattedDate = df.format(c.getTime());
	    
		enviardata.comments=formattedDate;
		
		SimpleDateFormat dh = new SimpleDateFormat("HH:mm:ss");
	    
	    String formattedHora = dh.format(c.getTime());
	    
		//enviardata.hora = formattedHora;
		
		enviardata.disponible=true;
		
		enviardata.httpConn = null;
		
		//enviardata.url = "http://www.dissoft.info/dato.php;deviceside=true";
		//enviardata.url = "http://www.dissoft.info/datobb.php;deviceside=true";
		//enviardata.url = "http://150.186.92.201/datobbcsudo.php;deviceside=true";
		//enviardata.url = "http://www.dissoft.info/pedirtaxi.php";
		enviardata.url = "http://csudo.udo.edu.ve/pedirsismo.php";
		/*
		 * Datotaxi.json contiene todos los taxis disponibles y no disponibles
		 */
		//enviardata.sismojson = "http://www.dissoft.info/datotaxi.json";
		//enviardata.sismojson = "http://www.libretaxi.com/datotaxi.json";
		//enviardata.sismojson = "http://150.186.92.201/datosismo.json";
		//enviardata.urljson = "http://www.libretaxi.com/datocsudo.json";
		
		//enviardata.url = "http://150.186.92.201/";
		//enviardata.url = "http://www.dissoft.info/datobb.php";
		
		enviardata.is = null;
		
		enviardata.os = null;
		
		datoenviado=true;
		//modificado
		//enviardata.ConectarData(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.idevent, enviardata.usuario, enviardata.codigo, enviardata.lat, enviardata.lon,enviardata.comments,enviardata.hora,enviardata.disponible);
	
	return datoenviado;
}



private Boolean CancelarTaxi(Boolean datoenviado) {
	
    	
    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
    
    int w=0;
    
	
	String lat = null,lon = null,telefono = null,disponible = null,hora = null,comments = null;
	
	
		EnviarData enviardata = new EnviarData();
		
		//enviardata.name="Francisco Alvarez";
		
		enviardata.usuario=usuario;
		
		enviardata.lat = "" + String.valueOf(latitude) + "";
		
		enviardata.lon = "" + String.valueOf(longitude) + "";
		
		//enviardata.codigo="5555";
		
		Calendar c = Calendar.getInstance();
	    
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    String formattedDate = df.format(c.getTime());
	    
		enviardata.comments=formattedDate;
		
		SimpleDateFormat dh = new SimpleDateFormat("HH:mm:ss");
	    
	    String formattedHora = dh.format(c.getTime());
	    
		//enviardata.hora = formattedHora;
		
		enviardata.disponible=false;
		
		enviardata.httpConn = null;
		
		enviardata.url = "http://csudo.udo.edu.ve/cancelartaxi.php";

		
		enviardata.is = null;
		
		enviardata.os = null;
		
		datoenviado=true;
		//modificado
//	enviardata.ConectarData(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.name, enviardata.usuario, enviardata.codigo, enviardata.lat, enviardata.lon,enviardata.comments,enviardata.hora,enviardata.disponible);
	
	return datoenviado;
}

private JSONArray BuscarSismosDisponibles(String json) throws IOException {
	
	//String action(){
   // br = new BroadcastReceiver() {
    	
    String idevent="", ideventbefore="", ideventafter="",flag1="";  
    
    int w=0;
    
	//@Override
	String lat = null,lon = null,locdatetime = null,depth = null,magnivalue = null,comments = null;
	
	EnviarData enviardata = new EnviarData();
	
	//enviardata.name="Francisco Alvarez";
	
	enviardata.usuario=usuario;
	
	//enviardata.lat = "" + String.valueOf(10.2556) + "";
	
	//enviardata.lon = "" + String.valueOf(-62.5265) + "";
	
	//enviardata.codigo="5555";
	
	enviardata.httpConn = null;
	
	/*
	 * Datosismos.json contiene todos los sismos disponibles
	 */
	enviardata.sismojson = "http://csudo.udo.edu.ve/datosismomes.json";
	
	enviardata.is = null;
	
	enviardata.os = null;
	
	//ObtenerJSON
	jsonSismoArray=enviardata.ObtenerJSON(enviardata.sismojson);
	
	GraficarSismos(jsonSismoArray);
	
	
		return jsonSismoArray;  
		   
    
    }
private JSONArray BuscarUltSismo(String json) throws IOException {
	

    String idevent="", ideventbefore="", ideventafter="",flag1="";  
    
    int w=0;
    
	String lat = null,lon = null,locdatetime = null,depth = null,magnivalue = null,comments = null;
	
	EnviarData enviardata = new EnviarData();

	enviardata.usuario=usuario;
	
	enviardata.httpConn = null;
	
	/*
	 * Datosismos.json contiene todos los sismos disponibles
	 */
	enviardata.sismojson = "http://csudo.udo.edu.ve/datosismo.json";
	
	enviardata.is = null;
	
	enviardata.os = null;
	
	//ObtenerJSON
	jsonSismoArray=enviardata.ObtenerJSON(enviardata.sismojson);
	
	GraficarSismoUlt(jsonSismoArray);
	
	
	return jsonSismoArray;  
		  
    
    }

public  Double getDistance(double lat_a,double lng_a,double lat_b,double lon_b){
  double Radius = 6371000; //Radio de la tierra
  double lat1;
  double lat2;
  double lon1;
  double lon2;
  double dLat;
  double dLon;
  double a;
  double c = 0;
  Location here = new Location("Current");
  
  Location dest = new Location("Destination2");
  
	if (!Double.isNaN(lat_a)){
		
		//if (Double.isNaN(lat_a)){
			if (!Double.isInfinite(lat_a)){
				
				if (lat_a!= 0.0){
					
					final float[] results= new float[3];
				    Location.distanceBetween(lat_a, lng_a, lat_b, lon_b, results);
				    /*Log.d("GPS", "results[0]: " + results[0]);
				    Log.d("GPS", "results[1]: " + results[1]);
				    Log.d("GPS", "results[2]: " + results[2]);*/


				    //Location here = new Location("Current");
				    here.setLatitude(lat_a);
				    here.setLongitude(lng_a);

				   // Location dest = new Location("Destination2");
				    dest.setLatitude(lat_b);
				    dest.setLongitude(lon_b);


				    Log.d("GPS", "Bearing to dest: " + here.bearingTo(dest));
				    Log.d("GPS", "Distance to dest: " + here.distanceTo(dest));
				   
					  /*lat1 = lat_a / 1E6;
					  lat2 = lat_b / 1E6;
					  lon1 = lng_a / 1E6;
					  lon2 = lon_b / 1E6;
					  dLat = Math.toRadians(lat2-lat1);
					  dLon = Math.toRadians(lon2-lon1);
					  a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon /2) * Math.sin(dLon/2);
					  c = 2 * Math.asin(Math.sqrt(a));*/
				
				}else{
					Toast.makeText(getApplicationContext(),"Debe pulsar algun sismo, para dar la distancia",Toast.LENGTH_LONG).show();
					
					}
					
			}	
		//}
				
	}
	    
	 return (double) here.distanceTo(dest);  
	//return (double) (Radius * c);
}

public static String Distance(double distance){  
	  if(distance >= 1000){
	   double k = distance / 1000;
	   double m = distance - (k*1000);
	   m = m / 100;
	   return String.valueOf(k) + (m>0?("."+String.valueOf(m)):"") + " Km" +(k>1?"s":"");
	  } else {
	   return String.valueOf(distance) + " metro"+(distance==1?"":"s");
	  }  
}

private JSONArray EnviarSismo(String json) {
	
	
    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
    
    int w=0;
    
	
	String lat = null,lon = null,telefono = null,disponible = null,hora = null,fecha = null;
	
	
	EnviarData enviardata = new EnviarData();
	
	
	enviardata.usuario=usuario;
	
	enviardata.lat = "" + String.valueOf(latitude) + "";
	
	enviardata.lon = "" + String.valueOf(longitude) + "";
	
	//crear json de los disponibles
	enviardata.disponible=true;
	
	enviardata.httpConn = null;
	
	enviardata.url = "http://sismologico.udo.edu.ve/agregarsismo.php";//listataxidisponibles.php

	
	enviardata.is = null;
	
	enviardata.os = null;
	
	try {
		
		datoenviado=true;
		
		enviardata.ConectarDataLista(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.usuario, enviardata.lat, enviardata.lon,enviardata.disponible);
	
	} catch (IOException e) {
		
		datoenviado=false;
		
		e.printStackTrace();
	}
	
	return datoenviado;
  }
}
	

