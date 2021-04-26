package Controllers;//Handles user input.
					//Requests information from the model and sends
					//and sends data to the views.


import java.sql.Connection;
import java.util.ArrayList;

import Model.BOM_model;

import Model.VendorModel;
//no need for scanner because view provides the user input

public class VendorController {
	

	//controller creates the db connection and pass the connection into the models
	
	private Connection dbconn;
	private VendorModel vendormodel;
	public  BOM_model bom_model;
	private ArrayList <String> productList;
	
	public VendorController(Connection db) {
		this.dbconn = db;
		this.vendormodel = new VendorModel(db);
		this.bom_model = new BOM_model(db);
		//this.po_header = new PO_header(db);
		//this.po_detail = new PO_detail(db);
		productList= new ArrayList <String>();
		
	}
	public void SearchDatabase(String str) {  // function that will accept user input from textfield1
		this.productList=this.vendormodel.Searchdb(str);
	}
	public void SearchBOM(String str) {
		this.productList=this.bom_model.Searchdb(str);
	}
//	public void PO(String str) {
//		this.productList=this.bom_model.SearchAL(str);
//	}
	public int getProductListSize() {
		return this.productList.size();
	}
	public String getProductFromList(int index) {
		return this.productList.get(index);
	}
}
//pur_vendor_catalog