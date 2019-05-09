package com.csudo.sismo;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



public class ListaSismoActivity extends Activity{
	double latitude;
    double longitude;
    JSONArray jsonsismoArray = null;
	JSONArray jsonListasismodisponible=null;
	JSONArray jsonUsuario = null;
	String texto="",conductor="";
	String usuario="",password="";
	String SesionUsuario="";
	ListView listView;
	ArrayList<String> results;
	ArrayList<Fotos> result;
	Boolean presionarbtn = false;
	String  array;
	JSONArray json_data;
	JSONObject json_array;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lista_sismo);
		
		Bundle reicieveParams = getIntent().getExtras();
	    
		//params.setText(reicieveParams.getString("conductor"));
		array=reicieveParams.getString("array");
		/*try {

		    JSONObject obj = new JSONObject(array);

		    Log.d("My App", obj.toString());

		} catch (Throwable t) {
		    Log.e("My App", "Could not parse malformed JSON: \"" + array + "\"");
		}*/
		/*
		try {
			json_data = json_array.getJSONArray(array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*ArrayList<String> results = GetSearchResults();
		
		lista(results);*/
		setupListView(array);
		
	}
	 @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         //Alternativa 1
         getMenuInflater().inflate(R.menu.lista_sismo, menu);
         return true;
     }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 
	     switch (item.getItemId()) {
	         case R.id.MnuOpc1:
	        	 //Toast.makeText(getApplicationContext(),"Opcion 1 pulsada!",Toast.LENGTH_LONG).show();
	             //lblMensaje.setText("Opcion 1 pulsada!");
	        	 /*
	        		 distancia=getDistance(lat_a,lng_a,lat_b, lon_b); 
	        	 	//msjdistancia=""+distancia+"";
	        		 msjdistancia=Distance(distancia) + " del Sismo";
	        	 	//msjdistancia=""+distancia+"";
	        	 	showNeutralAlert("Usted se encuentra a:",msjdistancia);
	        	 	return true;*/
	        	 	Intent intent = new Intent(this,BuscarSismoActivity.class);
					
					//intent.putExtra("conductor",jsonSismoArray);
					//String valores=String.valueOf(jsonArrayLista);
					//intent.putExtra("array", valores);
		     		startActivity(intent);
		     		
	             return true;
	         case R.id.MnuOpc2:
	        	 //Toast.makeText(getApplicationContext(),"Opcion 2 pulsada!",Toast.LENGTH_LONG).show();
	             //lblMensaje.setText("Opcion 2 pulsada!");;
	        	/* msjayuda="Este sistema muestra los últimos sismos de la Región Nor-Oriental de Venezuela, Los sismos" +
	        	 		"son de los últimos 30 días, usted puede pulsar el cada sismo y observar su fecha y magnitud";
	        	 showNeutralAlert("Centro de Sismología UDO",msjayuda);*/
	        	//llamada a la pantalla de espera por confirmacion del taxis
					/*Intent intent = new Intent(this,ListaSismoActivity.class);
					
					//intent.putExtra("conductor",jsonSismoArray);
					String valores=String.valueOf(jsonArrayLista);
					intent.putExtra("array", valores);
		     		startActivity(intent);
	             return true;*/
	        	 
	        	 showYesNoAlert("Salir","Desea Salir del Sistema?");
	             //lblMensaje.setText("Opcion 3 pulsada!");;
	        	 
	             return true;
	         
	         default:
	             return super.onOptionsItemSelected(item);
	     }
	 }
	 /*----------------------------------------------------------------------------------
	  * Funcion que muestra un mensaje al usuario para salir del sistema.
	  * Pasar esto a una interface es decir otra clase
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
	private void setupListView(String array)
	{
		
		String magnivalue,lat,lon,locdatetime,email,nombre,disponible;
		
		String idevent,comments,depth;
		
		String[] listItems= {};
		
		//JSONObject jsonObject;
		
		//LatLng sismos = new LatLng(10.471106, -64.160987);	
			
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");	
		
		Date fecha;
		
		try {
			//jsonObject = new JSONObject(array);
			
			//String Data=response.getEntity().getText().toString(); // reading the string value
			//JSONObject obj = new JSONObject(array);
			
			//lat= obj.toString();
			//JSONObject json = (JSONObject) new JSONParser().parse(array);
			/*String x=(String) json.get("phonetype");
			System.out.println("Check Data"+x);
			String y=(String) json.get("cat");
			System.out.println("Check Data"+y);*/
		
		//JSONObject obj = new JSONObject(array);
		//JSONArray json = new JSONArray();  
		
	        //jsonArray = new JSONArray(result);
	        
	        //arraydata = new String[json.length()];
	        
			JSONArray jsonArray = new JSONArray(array);
			
			listItems=new String[jsonArray.length()];
			
	        for (int i = 0; i < jsonArray.length(); i++) {
	        	
	            JSONObject row = jsonArray.getJSONObject(i);
	            
	            idevent = row.getString("idevent");
	            	            
		         lat = row.getString("lat");
		         
		         lon = row.getString("lon");
		         
		         locdatetime = row.getString("locdatetime");
		         
		         comments = row.getString("comments");
		         
		         magnivalue = row.getString("magnivalue");
		         
		         depth = row.getString("depth");
		         
		         fecha=	dt.parse(locdatetime);
		         
		         //listItems[i] = row.getString("idevent");
		         listItems[i] = "Fecha:"+locdatetime+" Magnitud:" +magnivalue+" Comentarios: "+comments+"";
		         
		         
		         //mensaje="Fecha:"+fecha+" Magnitud:" +magnivalue+"";
	            
		         //listItems=mensaje.toString();
	            //arraydata[i]=tlf;
	            //sismos = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
	            
	        }
	      
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
		//return json;
	
	ArrayAdapter listItemAdapter =	new ArrayAdapter(this,android.R.layout.simple_list_item_1,listItems);
	
	ListView lv = (ListView)this.findViewById(R.id.listViewSismos);
	
	lv.setAdapter(listItemAdapter);
	}

private ArrayList<String> lista(ArrayList<String> results){
	
	if (results == null){
		
		
		//Crear lista de taxistas
		Toast.makeText(getApplicationContext(),"No hay Sismos ...",Toast.LENGTH_LONG).show();
     		
	}else{
	
		/*if (results.equals(null)){
			ArrayList<Fotos> results = GetSearchResults();
		} else{
			ArrayList<Fotos>  results;
		} */
		// verifica si jsonListataxidisponible tiene valor se la tarea se ha realizado
		if (jsonListasismodisponible!=null){
			
			final ListView lv1 = (ListView) findViewById(R.id.listViewSismos);
			//ListView myListView = (ListView)findViewById(R.id.myListView);
			//final EditText myEditText = (EditText)findViewById(R.id.myEditText);
			// Create the array list of to do items
			//final ArrayList<String> todoItems = new ArrayList<String>();
			// Create the array adapter to bind the array to the listview
			final ArrayAdapter<String> aa;
			aa = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1,results);
			
			// Bind the array adapter to the listview.
			lv1.setAdapter(aa);
			/*
			lv1.setAdapter(new ItemListBaseAdapter(ListaSismoActivity.this, results));
			 
			lv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				/*Object o = lv1.getItemAtPosition(position);
				Fotos obj_Fotos = (Fotos)o;
				Toast.makeText(SeleccionarTaxiActivity.this, "El taxi que eligio : " + " " + obj_Fotos.getConductor(), Toast.LENGTH_LONG).show();
				conductor=obj_Fotos.getConductor();
				//Procedimiento para notificar al taxista que un usuario lo llama
				tareaTaxiSeleccionado taskTaxiSeleccionado = new tareaTaxiSeleccionado();		       	  
				
				taskTaxiSeleccionado.execute(new String[] { "http://www.libretaxi.com/taxiseleccionado.php" });
				
				//llamada a la pantalla de espera por confirmacion del taxis
				Intent intent = new Intent(SeleccionarTaxiActivity.this,EsperarConfirmacionTaxiActivity.class);
				
				intent.putExtra("conductor",conductor);
				
	     		startActivity(intent);*/
			/*} 
			});*/
		}
	}
	return results;
}
private ArrayList<String> GetSearchResults(){
	
	// falta validar codigo si json es vacio
	
	ArrayList<String> results = new ArrayList<String>();
	/*
	 * Si el json existe entoncews envia el json a SeleccionartaxiActivity.class
	 * 		  
	 */
	//ArrayList<Fotos> results = new ArrayList<Fotos>();
	 
	Fotos item_details = new Fotos();
	//ItemDetails item_details = new ItemDetails();
	
	if (jsonListasismodisponible!=null){
		
	     for (int i = 0; i < jsonListasismodisponible.length(); i++) {
	    	 
	    	 try{
	    		 
	    	 JSONObject row = jsonListasismodisponible.getJSONObject(i);
	    	 
	    	 //user_conductor = ;
	         
	         //lat = row.getString("lat");
	         
	         //lon = row.getString("lon");
	         
	         //telefono = ;
	         
	         //fecha = ;
	         
	         //hora = row.getString("hora");
	         
	         //disponible = row.getString("disponible");
	         
	         item_details.setLat(row.getString("lat"));
	         
			// item_details.setTelefono(row.getString("telefono"));
			 
			 //item_details.setFecha(row.getString("fecha"));
			 
			 item_details.setImageNumber(1);
			 
			// results.add(item_details);
			 
			 item_details = new Fotos();
			 
	    	 } catch (Exception e) {
			     e.printStackTrace();
			}
			 
	         
	     }
	/*Fotos item_details = new Fotos();
	item_details.setName("Pizza");
	item_details.setItemDescription("Spicy Chiken Pizza");
	item_details.setPrice("RS 310.00");
	item_details.setImageNumber(1);
	results.add(item_details);
	 
	item_details = new Fotos();
	item_details.setName("Burger");
	item_details.setItemDescription("Beef Burger");
	item_details.setPrice("RS 350.00");
	item_details.setImageNumber(2);
	results.add(item_details);
	 
	item_details = new Fotos();
	item_details.setName("Pizza");
	item_details.setItemDescription("Chiken Pizza");
	item_details.setPrice("RS 250.00");
	item_details.setImageNumber(3);
	results.add(item_details);
	 
	item_details = new Fotos();
	item_details.setName("Burger");
	item_details.setItemDescription("Chicken Burger");
	item_details.setPrice("RS 350.00");
	item_details.setImageNumber(4);
	results.add(item_details);
	 
	item_details = new Fotos();
	item_details.setName("Burger");
	item_details.setItemDescription("Fish Burger");
	item_details.setPrice("RS 310.00");
	item_details.setImageNumber(5);
	results.add(item_details);
	 
	item_details = new Fotos();
	item_details.setName("Mango");
	item_details.setItemDescription("Mango Juice");
	item_details.setPrice("RS 250.00");
	item_details.setImageNumber(6);
	results.add(item_details);*/
	
	}
return results;
 	
}
}