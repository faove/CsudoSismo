package com.csudo.sismo;

public class Fotos {
	
	public String getLat() {
	return lat;
	}
	public void setLat(String lat) {
	this.lat = lat;
	}
	public String getTelefono() {
	return lon;
	}
	public void setLon(String lon) {
	this.lon = lon;
	}
	public String getFecha() {
	return fecha;
	}
	public void setFecha(String fecha) {
	this.fecha = fecha;
	}
	
	public int getDisponible() {
	return disponible;
	}
	public void setDisponible(int disponible) {
	this.disponible = disponible;
	}
	
	public String getDistancia() {
	return distancia;
	}
	public void setDistancia(String distancia) {
	this.distancia = distancia;
	}
	public String getProf() {
	return prof;
	}
	public void setProf(String prof) {
	this.prof = prof;
	}
	
	public int getImageNumber() {
	return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
	this.imageNumber = imageNumber;
	}
	 
	private String lat ;
	private String lon;
	private String fecha;
	private String distancia,prof;
	private int imageNumber;
	private int disponible;

}
