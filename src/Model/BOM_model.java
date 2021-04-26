package Model;//the BOM Model
import java.sql.Connection; 			//vendor model is what does the communication with the db
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class BOM_model {
	private Connection dbconn;
	public BOM_model(Connection db) {
		this.dbconn = db;
		
			
	}
	public ArrayList <String> Searchdb(String str) {
		
		ArrayList<String> rtn = new ArrayList <String>(); 
		PreparedStatement stmt;
		try {
			stmt = dbconn.prepareStatement(
					"SELECT bom_id,component_product_id,COALESCE(vc.description,i.product_description) FROM `bom_detail`bd\r\n"
					+ "LEFT OUTER JOIN pur_vendor_catalog vc\r\n"
					+ "ON bd.component_product_id = vc.product_id\r\n"
					+ "LEFT OUTER JOIN item_master i \r\n"
					+ "ON bd.component_product_id = i.product_id\r\n"
					+ "WHERE bd.component_product_id is not NULL AND (vc.description LIKE ? OR i.product_description LIKE ?)"	//no question marks
				);
			stmt.setString(1,"%"+ str + "%");
			stmt.setString(2,"%"+ str + "%");                // need two of these lines because you have two question marks
			ResultSet r = stmt.executeQuery();
			r.last();
			System.out.println("searched "+str+","+ r.getRow()+" rows");
			r.beforeFirst();
			while (r.next()) {
				rtn.add(r.getInt(1)+":"+r.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return rtn;
		
	}//https://www.tutorialspoint.com/jdbc/jdbc-select-records.htm
}
