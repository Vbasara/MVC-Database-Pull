package Model;//the Model provides the controller with information when it requests it
import java.sql.Connection; 			//vendor model is what does the communication with the db
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class VendorModel {
	private Connection dbconn;
	public VendorModel(Connection db) {
		this.dbconn = db;
		
		
	}
	public ArrayList <String> Searchdb(String str) {
		
		ArrayList<String> rtn = new ArrayList <String>(); 
		PreparedStatement stmt;
		try {
			stmt = dbconn.prepareStatement(
					"SELECT vendor_catalog_id,product_id,vendor_item_number, description FROM pur_vendor_catalog "
					+ "WHERE description LIKE ? OR vendor_item_number LIKE ?"	
				);
			stmt.setString(1,"%"+ str + "%");
			stmt.setString(2,"%"+ str + "%");                // need two of these lines because you have two question marks
			ResultSet r = stmt.executeQuery();
			r.last();
			System.out.println("searched "+str+","+ r.getRow()+" rows");
			r.beforeFirst();
			while (r.next()) {
				rtn.add(r.getInt(1)+":"+r.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return rtn;
		
	}
}
