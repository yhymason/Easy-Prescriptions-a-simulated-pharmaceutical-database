import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.concurrent.TimeUnit;


public class Test {

    private static Connection con = null;
    private static Utilities util;

    public void test_patient_login(int personal_health_num, String name){
    	try{
    		if(util.patient_login(personal_health_num, name, con)){
    			System.out.println("patient_login succesful");
    		}
    		else{
    			System.out.println("patient_login rejected");
    		}
    	}
    	catch(SQLException e){
    		System.out.println("test_patient_login" + e.getMessage());
    	}
    }

    public void test_doctor_login(int doctor_id, String name){
    	try{
    		if(util.doctor_login(doctor_id, name, con)){
    			System.out.println("doctor_login succesful");
    		}
    		else{
    			System.out.println("doctor_login rejected");
    		}
    	}
    	catch(SQLException e){
    		System.out.println("test_doctor_login" + e.getMessage());
    	}
    }


    public void test_pharmacist_login(int pharmacist_id, String name){
    	try{
    		if(util.pharmacist_login(pharmacist_id, name, con)){
    			System.out.println("pharmacist_login succesful");
    		}
    		else{
    			System.out.println("pharmacist_login rejected");
    		}
    	}
    	catch(SQLException e){
    		System.out.println("test_pharmacist_login" + e.getMessage());
    	}
    }


    public void test_insert_pharmacy(int pharmID, String name, String address, String phoneNum){
    	try{
    		util.insert_pharmacy(pharmID, name, address, phoneNum, con);
    	}
    	catch(SQLException e){
    		System.out.println("test_insert_pharmacy" + e.getMessage());

    	}
    }
   

     public void test_insert_pharmacist(int pharmID, String name, int pharmacyID){
        try{
    		util.insert_pharmacist(pharmID, name, pharmacyID, con);
    	}
    	catch(SQLException e){
    		System.out.println("test_insert_pharmacist" + e.getMessage());

    	}
    }

    public void test_insert_doctor(int doctorID, String name, String address, String speciality){
        try{
    		util.insert_doctor(doctorID, name,address, speciality, con);
    	}
    	catch(SQLException e){
    		System.out.println("test_insert_doctor" + e.getMessage());

    	}
    }
    public void test_insert_test(int phn, Timestamp date, String type){
        try{
    		util.insert_test(phn, date, type, con);
    	}
    	catch(SQLException e){
    		System.out.println("test_insert_test" + e.getMessage());

    	}
    }

    public void test_insert_drug(int drugID, String name, String effects, String warnings){
        try{
    		util.insert_drug(drugID, name, effects, warnings, con);
    	}
    	catch(SQLException e){
    		System.out.println("test_insert_drug" + e.getMessage());

    	}
    }

    public void test_insert_doctor_visit(int phn, int doctorID, Date date, String reason, String notes){
        try{
    		util.insert_doctor_visit(phn, doctorID, date, reason, notes, con);
    	}
    	catch(SQLException e){
    		System.out.println("test_insert_doctor_visit" + e.getMessage());

    	} 
    }

    public void test_insert_prescription(int prescriptionID, Date date, int drugID, int personalHealthNumber,
                                    int doctorID, int pharmacistID,
                                    int dosage, String duration){
        try{
    		util.insert_prescription(prescriptionID, date, drugID, 
    			personalHealthNumber, doctorID, pharmacistID, dosage, duration, con);
    	}
    	catch(SQLException e){
    		System.out.println("test_insert_prescription" + e.getMessage());

    	}
    }

    public void test_insert_patient(int personalHealthNumber, String name, String address,
                               String phoneNum, Date birthday, int familyDoctorID){
   		try{
   			util.insert_patient(personalHealthNumber, name, address, phoneNum, birthday, familyDoctorID, con);

   		}
   		catch(SQLException e){
   			System.out.println("test_insert_patient" +e.getMessage());
   		}
    }



		public static void main(String[] args) throws SQLException, ClassNotFoundException {
    	Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection(
               "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_o7p0b", "a10467158");

	    util = new Utilities();
	    Timestamp timeS = new Timestamp(1234);
	    Date date = new Date(1234);

	    Test test = new Test();

	    test.test_insert_pharmacy(1234, "Shoppers", "123 West 5th Ave, Vancouver, BC", "7781234567");
	    test.test_insert_pharmacist(10013, "John Smith", 1234);
	    
	    test.test_insert_drug(10098, "Tylenol 3", "Relieves pain", "Overdose is possible");
	    
	    test.test_insert_doctor(10800, "Sarah Smith", "5423 East 49th Ave, Vancouver, BC", "GP");


	    test.test_insert_patient(101010, "Mary Lou", "779 Heather St, Vancouver BC", "6048937896", date, 10800);
	    test.test_insert_test(101010, timeS, "XRay");
	    

	    test.test_insert_prescription(67836, date, 10098, 101010, 10800, 0, 40, "2 weeks");
	    test.test_insert_doctor_visit(101010, 10800, date, "Suspected hyperthyroidism", "Order blood tests");


	    test.test_patient_login(101010, "Mary Lou");
	    test.test_doctor_login(10800, "Sarah Smith");
	    test.test_pharmacist_login(10013, "John Smith");

	    con.close();


	}

}