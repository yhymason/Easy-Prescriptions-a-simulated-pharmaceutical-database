import java.sql.*;
import java.util.ArrayList;

public class Utilities {

    public boolean patient_login(int personal_health_num, String name, Connection con) throws SQLException
    {
        String p_name = "";
        PreparedStatement ps = con.prepareStatement
                ("SELECT NAME " +
                        "FROM ORA_O7P0B.PATIENT " +
                        "WHERE PERSONAL_HEALTH_NUMBER = ?");
        ps.setInt(1, personal_health_num);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            p_name = temp.getString("NAME");
        }
        ps.close();
        return p_name.trim().equals(name);
    }

    public boolean doctor_login(int doctor_id, String name, Connection con)throws SQLException
    {
        String d_name = "";
        PreparedStatement ps = con.prepareStatement
                ("SELECT NAME " +
                        "FROM ORA_O7P0B.DOCTOR " +
                        "WHERE DOCTOR_ID = ?");
        ps.setInt(1, doctor_id);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            d_name = temp.getString("NAME");
        }
        ps.close();
        return d_name.trim().equals(name);
    }

    public boolean pharmacist_login(int pharmacist_id, String name, Connection con)throws SQLException{
        String ph_name = "";
        PreparedStatement ps = con.prepareStatement
                ("SELECT NAME " +
                        "FROM ORA_O7P0B.PHARMACIST " +
                        "WHERE PHARMACIST_ID = ?");
        ps.setInt(1, pharmacist_id);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            ph_name = temp.getString("NAME");
        }
        ps.close();
        return ph_name.trim().equals(name);
    }

    // This function is for loading basic info on the Doctor UI
    public PharmacistInfo viewPharmacistInfo(int id, Connection con) throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM PHARMACIST WHERE PHARMACIST_ID = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        PharmacistInfo row = new PharmacistInfo();
        while (rs.next()) {
            row.setPid(rs.getInt(1));
            row.setName(rs.getString(2));
            row.setPhid(rs.getInt(3));
        }
        ps.close();
        return row;
    }

    // This function is for loading basic info on the Doctor UI
    public DoctorInfo viewDoctorInfo(int id, Connection con) throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM DOCTOR WHERE DOCTOR_ID = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        DoctorInfo row = new DoctorInfo();
        while (rs.next()) {
            row.setDid(rs.getInt(1));
            row.setName(rs.getString(2));
            row.setAddress(rs.getString(3));
            row.setSpec(rs.getString(4));
        }
        ps.close();
        return row;
    }

    // This function is for loading basic info on the patient UI
    public PatientInfo viewPatientInfo(int phn, Connection con) throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM patient WHERE PERSONAL_HEALTH_NUMBER = ?");
        ps.setInt(1, phn);
        ResultSet rs = ps.executeQuery();
        PatientInfo row = new PatientInfo();
        while (rs.next()) {
            row.setPhn(rs.getInt(1));
            row.setName(rs.getString(2));
            row.setAddress(rs.getString(3));
            row.setPhone_number(rs.getString(4));
            row.setDob(rs.getString(5));
        }
        ps.close();
        return row;
    }

    // This function is used for loading a patient's family doctor info on the patient UI
    public DoctorInfo viewFamilyDoctor(int phn, Connection con) throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "SELECT DOCTOR.NAME, DOCTOR.ADDRESS,DOCTOR.SPECIALITY " +
                        "FROM DOCTOR,PATIENT WHERE DOCTOR.DOCTOR_ID = PATIENT.FAMILYDOCTOR_ID" +
                        " AND PATIENT.PERSONAL_HEALTH_NUMBER = ?");
        ps.setInt(1, phn);
        ResultSet rs = ps.executeQuery();
        DoctorInfo row = new DoctorInfo();
        while (rs.next()) {
            row.setName(rs.getString(1));
            row.setAddress(rs.getString(2));
            row.setSpec(rs.getString(3));
        }
        ps.close();
        return row;
    }

    public void insert_prescription(int prescriptionID, Date date, int drugID, int personalHealthNumber,
                                    int doctorID, int pharmacistID,
                                    int dosage, String duration,Connection con)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.PRESCRIPTION " +
                "(PRESCRIPTION_ID, PRES_DATE, DRUG_ID, PERSONAL_HEALTH_NUMBER, DOCTOR_ID, PHARMACIST_ID, DOSAGE, DURATION) " +
                "VALUES (?,?,?,?,?,?,?,?)");
        ps.setInt(1,prescriptionID);
        ps.setDate(2,date);
        ps.setInt(3,drugID);
        ps.setInt(4,personalHealthNumber);
        ps.setInt(5,doctorID);
        ps.setInt(6,pharmacistID);
        ps.setInt(7,dosage);
        ps.setString(8,duration);
        ps.executeUpdate();
        ps.close();
    }


    public void insert_prescription(int prescriptionID, Date date, int drugID, int personalHealthNumber,
                                    int doctorID, int dosage, String duration,Connection con)
            throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.PRESCRIPTION " +
                "(PRESCRIPTION_ID, PRES_DATE, DRUG_ID, PERSONAL_HEALTH_NUMBER, " +
                "DOCTOR_ID, DOSAGE, DURATION) " +
                "VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1,prescriptionID);
        ps.setDate(2,date);
        ps.setInt(3,drugID);
        ps.setInt(4,personalHealthNumber);
        ps.setInt(5,doctorID);
        ps.setInt(6,dosage);
        ps.setString(7,duration);
        ps.executeUpdate();
        ps.close();
    }

    public void insert_patient(int personalHealthNumber, String name, String address,
                               String phoneNum, Date birthday, int familyDoctorID,Connection con)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.PATIENT " +
                "(PERSONAL_HEALTH_NUMBER, NAME, ADDRESS, PHONE_NUMBER, DATE_OF_BIRTH, FAMILYDOCTOR_ID) " +
                "VALUES (?,?,?,?,?,?)");
        ps.setInt(1,personalHealthNumber);
        ps.setString(2,name);
        ps.setString(3,address);
        ps.setString(4,phoneNum);
        ps.setDate(5,birthday);
        ps.setInt(6,familyDoctorID);
        ps.executeUpdate();
        ps.close();
    }

    public void insert_patient(int personalHealthNumber, String name, String address,
                               String phoneNum, Date birthday, Connection con)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.PATIENT " +
                "(PERSONAL_HEALTH_NUMBER, NAME, ADDRESS, PHONE_NUMBER, DATE_OF_BIRTH) " +
                "VALUES (?,?,?,?,?)");
        ps.setInt(1,personalHealthNumber);
        ps.setString(2,name);
        ps.setString(3,address);
        ps.setString(4,phoneNum);
        ps.setDate(5,birthday);
        ps.executeUpdate();
        ps.close();
    }

    public void insert_doctor(int doctorID, String name, String address, String speciality,Connection con)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.DOCTOR " +
                "(DOCTOR_ID, NAME, ADDRESS, SPECIALITY) " +
                "VALUES (?,?,?,?)");
        ps.setInt(1,doctorID);
        ps.setString(2,name);
        ps.setString(3,address);
        ps.setString(4,speciality);
        ps.executeUpdate();
        ps.close();

    }

    public void insert_pharmacist(int pharmID, String name, int pharmacyID,Connection con)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.PHARMACIST" +
                "(PHARMACIST_ID, NAME, PHARMACY_ID) " +
                "VALUES (?,?,?)");
        ps.setInt(1,pharmID);
        ps.setString(2,name);
        ps.setInt(3,pharmacyID);
        ps.executeUpdate();
        ps.close();
    }

    public void insert_doctor_visit(int phn, int doctorID, Date date, String reason, String notes,Connection con)
            throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.DOCTORVISIT" +
                "(PERSONAL_HEALTH_NUMBER,DOCTOR_ID,VISIT_DATE,REASON,NOTES) " +
                "VALUES(?,?,?,?,?)");
        ps.setInt(1,phn);
        ps.setInt(2,doctorID);
        ps.setDate(3,date);
        ps.setString(4,reason);
        ps.setString(5,notes);
        ps.executeUpdate();
        ps.close();
    }

    public void insert_test(int phn, Timestamp tStamp, String type,Connection con)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.TEST" +
                "(PERSONAL_HEALTH_NUMBER,TEST_TIMESTAMP,TYPE) " +
                "VALUES (?,?,?)");
        ps.setInt(1,phn);
        ps.setTimestamp(2,tStamp);
        ps.setString(3,type);
        ps.executeUpdate();
        ps.close();
    }

    public void insert_drug(int drugID, String name, String effects, String warnings,Connection con)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.DRUG" +
                "(DRUG_ID,NAME,EFFECTS,WARNINGS) " +
                "VALUES(?,?,?,?)");
        ps.setInt(1,drugID);
        ps.setString(2,name);
        ps.setString(3,effects);
        ps.setString(4,warnings);
        ps.executeUpdate();
        ps.close();
    }

    public void insert_pharmacy(int pharmID, String name, String address, String phoneNum,Connection con)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORA_O7P0B.PHARMACY" +
                "(PHARMACY_ID,NAME,ADDRESS,PHONE_NUMBER) " +
                "VALUES(?,?,?,?)");
        ps.setInt(1,pharmID);
        ps.setString(2,name);
        ps.setString(3,address);
        ps.setString(4,phoneNum);
        ps.executeUpdate();
        ps.close();
    }

    // This function is used for patients to delete their accounts
    public void self_unregister(int phn, Connection con) throws SQLException{
        PreparedStatement ps = con.prepareStatement("DELETE FROM PATIENT " +
                "WHERE PERSONAL_HEALTH_NUMBER = ?");
        ps.setInt(1,phn);
        ps.executeUpdate();
        ps.close();
    }


    public ArrayList<TestInfo> showAllTestsForDoctor(int did, Connection con) throws SQLException{
        ArrayList<TestInfo> tests = new ArrayList<TestInfo>();
        PreparedStatement ps = con.prepareStatement("select test.test_timestamp, test.type, " +
                "test.result_timestamp, test.result_description " +
                "from test " +
                "where personal_health_number in (select personal_health_number " +
                "from patient where familydoctor_id = ?)");
        ps.setInt(1,did);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            TestInfo row = new TestInfo();
            row.setTestTimeS(rs.getTimestamp(1));
            row.setType(rs.getString(2));
            row.setResultTimeS(rs.getTimestamp(3));
            row.setResultDesc(rs.getString(4));
            tests.add(row);
        }
        ps.close();
        return tests;
    }





    //For patient with phn, show all tests performed
    //test_timestamp timestamp,
    // type char(20),
    //result_timestamp timestamp,
    //        result_description
    public ArrayList<TestInfo> showAllTestsForPatient(int phn, Connection con) throws SQLException{
        ArrayList<TestInfo> tests = new ArrayList<TestInfo>();
        PreparedStatement ps = con.prepareStatement(
                "select test.test_timestamp, test.type, test.result_timestamp, test.result_description " +
                        "from ORA_O7P0B.test " +
                        "where test.personal_health_number = ?"
        );
        ps.setInt(1, phn);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            TestInfo row = new TestInfo();
            row.setTestTimeS(rs.getTimestamp(1));
            row.setType(rs.getString(2));
            row.setResultTimeS(rs.getTimestamp(3));
            row.setResultDesc(rs.getString(4));
            tests.add(row);
        }
        ps.close();

        return tests;
    }
    //For patient with phn, show all prescriptions
    public ArrayList<PrescriptionRow> showAllPrescriptionsForPatient(int phn, Connection con) throws SQLException{
        ArrayList<PrescriptionRow> prescriptions = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(
                "select prescription.pres_date, prescription.duration, prescription.dosage, drug.name " +
                        "from ORA_O7P0B.prescription, ORA_O7P0B.drug " +
                        "where prescription.personal_health_number = ? and " +
                        "prescription.drug_id = drug.drug_id "
        );
        ps.setInt(1, phn);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PrescriptionRow row = new PrescriptionRow();
            row.setDate(rs.getDate(1));
            row.setDuration(rs.getString(2));
            row.setDosage(rs.getInt(3));
            row.setDrug(rs.getString(4));
            prescriptions.add(row);
        }
        ps.close();
        return prescriptions;
    }


    // find all prescriptions for a given patient for drugs with a certain name
    public ArrayList<PrescriptionRow> show_prescription_drugs(int phn, int drug_id, Connection con) throws SQLException {
        ArrayList<PrescriptionRow> prescriptions = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(
                "select prescription.pres_date, prescription.duration, prescription.dosage, drug.name " +
                        "from ORA_O7P0B.prescription, ORA_O7P0B.drug " +
                        "where prescription.personal_health_number = ? and " +
                        "prescription.drug_id = drug.drug_id " +
                        "and drug.DRUG_ID = ?"
        );
        ps.setInt(1, phn);
        ps.setInt(2, drug_id);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            PrescriptionRow row = new PrescriptionRow();
            row.setDate(rs.getDate(1));
            row.setDuration(rs.getString(2));
            row.setDosage(rs.getInt(3));
            row.setDrug(rs.getString(4));
            prescriptions.add(row);
        }

        ps.close();

        return prescriptions;
    }
    //Get the name and patient number of all patients for a given doctor.
    //SELECT personal_health_number, name FROM patient WHERE familydoctor_id = ?

    public ArrayList<PatientInfo> allPatientInfo(int doctorID, Connection con) throws SQLException{
        ArrayList<PatientInfo> patients = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(
                "SELECT personal_health_number, name FROM patient WHERE familydoctor_id = ?");
        ps.setInt(1, doctorID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            PatientInfo row = new PatientInfo();
            row.setPhn(rs.getInt(1));
            row.setName(rs.getString(2));
            patients.add(row);
        }

        ps.close();
        return patients;
    }


    /*Find min/max average age of patients for each doctor (Doctors can run this query -- or patients?)
        Min:
        SELECT MIN(avg_age) from
            (SELECT familydoctor_id, AVG(((SELECT sysdate FROM dual) - date_of_birth)/365) AS avg_age
            FROM patient GROUP BY familydoctor_id);
        Max:
        SELECT MAX(avg_age) from
            (SELECT familydoctor_id, AVG(((SELECT sysdate FROM dual) - date_of_birth)/365) AS avg_age
            FROM patient GROUP BY familydoctor_id);
    */

    public int maxPatientAge(int did, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("" +
                "SELECT max(((SELECT sysdate FROM dual) - date_of_birth)/365) " +
                "FROM patient WHERE familydoctor_id = ?");
        ps.setInt(1, did);
        ResultSet rs = ps.executeQuery();
        int maxAge = 0;
        while (rs.next()) {
            maxAge = rs.getInt(1);
        }
        ps.close();
        return maxAge;
    }

    public int minPatientAge(int did, Connection con) throws SQLException{
        PreparedStatement ps = con.prepareStatement("" +
                "SELECT min(((SELECT sysdate FROM dual) - date_of_birth)/365) " +
                        "from patient where familydoctor_id = ?");
        ps.setInt(1,did);
        ResultSet rs = ps.executeQuery();
        int minAge = 0;
        while (rs.next()) {
            minAge = rs.getInt(1);
        }
        ps.close();
        return minAge;
    }


    /*Find min/max average age of patients for each doctor (Doctors can run this query -- or patients?)
        Min:
        SELECT MIN(avg_age) from
            (SELECT familydoctor_id, AVG(((SELECT sysdate FROM dual) - date_of_birth)/365) AS avg_age
            FROM patient GROUP BY familydoctor_id);
        Max:
        SELECT MAX(avg_age) from
            (SELECT familydoctor_id, AVG(((SELECT sysdate FROM dual) - date_of_birth)/365) AS avg_age
            FROM patient GROUP BY familydoctor_id);
    */

    public int maxPatientAgeForDocs(Connection con) throws SQLException{
        int maxAge = 0;
        PreparedStatement ps = con.prepareStatement(
                "SELECT MAX(avg_age) from " +
                        "(SELECT familydoctor_id, AVG(((SELECT sysdate FROM dual) - date_of_birth)/365) " +
                        "AS avg_age FROM patient GROUP BY familydoctor_id)");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            maxAge = rs.getInt(1);
        }
        ps.close();
        return maxAge;
    }

    public int minPatientAgeForDocs(Connection con) throws SQLException{
        int minAge = 0;
        PreparedStatement ps = con.prepareStatement(
                "SELECT MIN(avg_age) from " +
                        "(SELECT familydoctor_id, AVG(((SELECT sysdate FROM dual) - date_of_birth)/365) " +
                        "AS avg_age FROM patient GROUP BY familydoctor_id)");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            minAge = rs.getInt(1);
        }
        ps.close();
        return minAge;
    }


    /*Patient can see the number of tests they’ve had.
    SELECT count(*) FROM test WHERE personal_health_number = ?
    */
    public int findNumberTestsForPatient(int phn,Connection con) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT count(*) FROM test WHERE personal_health_number = ?");
        ps.setInt(1, phn);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        if(rs.next()){
            count = rs.getInt(1);
        }
        return count;
    }


    // This function is used to update patient's info on the patient profiles
    public boolean updatePatientInfo(int phn, String phone_number, String address, Connection con) throws SQLException{
        PreparedStatement ps;
        if(phone_number.length()>0 && address.length()>0) {
            ps = con.prepareStatement("UPDATE PATIENT SET PHONE_NUMBER = ?, ADDRESS = ? WHERE PERSONAL_HEALTH_NUMBER = ?");
            ps.setString(1,phone_number);
            ps.setString(2,address);
            ps.setInt(3,phn);
        }
        else if(phone_number.length()>0) {
            ps = con.prepareStatement("UPDATE PATIENT SET PHONE_NUMBER = ? WHERE PERSONAL_HEALTH_NUMBER = ?");
            ps.setString(1,phone_number);
            ps.setInt(2,phn);
        }
        else if(address.length() > 0){
            ps = con.prepareStatement("UPDATE PATIENT SET ADDRESS = ? WHERE PERSONAL_HEALTH_NUMBER = ?");
            ps.setString(1,address);
            ps.setInt(2,phn);
        }
        else
            return false;

        ps.executeUpdate();
        ps.close();

        return true;
    }


    // This function is used for updating doctor's profile on doctor UI
    public boolean updateDoctorInfo(int id, String address, String speciality, Connection con) throws SQLException{
        PreparedStatement ps;
        if(address.length()>0 && speciality.length()>0) {
            ps = con.prepareStatement("UPDATE DOCTOR SET ADDRESS = ?, SPECIALITY = ? WHERE DOCTOR_ID = ?");
            ps.setString(1,address);
            ps.setString(2,speciality);
            ps.setInt(3,id);
        }
        else if(address.length()>0) {
            ps = con.prepareStatement("UPDATE DOCTOR SET ADDRESS = ? WHERE DOCTOR_ID = ?");
            ps.setString(1,address);
            ps.setInt(2,id);
        }
        else if(speciality.length() > 0){
            ps = con.prepareStatement("UPDATE DOCTOR SET SPECIALITY = ? WHERE DOCTOR_ID = ?");
            ps.setString(1,speciality);
            ps.setInt(2,id);
        }
        else
            return false;

        ps.executeUpdate();
        ps.close();

        return true;
    }

    /*functions to allow doctors to view and to update or create visit records*/

    public DoctorVisit showDocVisitForPatient(int docID, int phn, Connection con) throws SQLException{
        DoctorVisit docVisit = new DoctorVisit();
        PreparedStatement ps = con.prepareStatement(
                "SELECT visit_date, reason, notes from DoctorVisit where doctor_id = ? and personal_health_number = ?"
        );
        ps.setInt(1, docID);
        ps.setInt(2, phn);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            docVisit.setVisitDate(rs.getDate(1));
            docVisit.setReason(rs.getString(2));
            docVisit.setNotes(rs.getString(3));
        }
        ps.close();
        return docVisit;
    }

    public ArrayList<DoctorVisit> showAllDocVisits(int docID, Connection con) throws SQLException{
        ArrayList<DoctorVisit> docVisit = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(
                "SELECT DoctorVisit.personal_health_number, Patient.name, " +
                        "DoctorVisit.visit_date, DoctorVisit.reason, DoctorVisit.notes " +
                        "from DoctorVisit, Patient " +
                        "where DoctorVisit.doctor_id = ? " +
                        "and DoctorVisit.personal_health_number = Patient.personal_health_number "
        );
        ps.setInt(1, docID);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            DoctorVisit row = new DoctorVisit();
            row.setPhn(rs.getInt(1));
            row.setName(rs.getString(2));
            row.setVisitDate(rs.getDate(3));
            row.setReason(rs.getString(4));
            row.setNotes(rs.getString(5));
            docVisit.add(row);
        }
        ps.close();
        return docVisit;
    }


    //Doctor can update notes and/or reasons of doctor’s visit:
    //Update doctorvisit set notes = ?, reason = ? where personal_health_number = ? and doctor_id = ? and visit_date = ?
    public boolean updateDoctorVisit(String notes, String reason, int phn, int docID,
                                     Date visitDate, Connection con) throws SQLException{
        PreparedStatement ps;
        if(notes.length()>0 && reason.length()>0) {
            ps = con.prepareStatement("Update doctorvisit set notes = ?, reason = ? " +
                    "where personal_health_number = ? and doctor_id = ? and visit_date = ?");
            ps.setString(1,notes);
            ps.setString(2,reason);
            ps.setInt(3,phn);
            ps.setInt(4, docID);
            ps.setDate(5, visitDate);
        }
        else if(notes.length()>0) {
            ps = con.prepareStatement("Update doctorvisit set notes = ? " +
                    "where personal_health_number = ? and doctor_id = ? and visit_date = ?");
            ps.setString(1,notes);
            ps.setInt(2,phn);
            ps.setInt(3, docID);
            ps.setDate(4, visitDate);
        }
        else if(reason.length() > 0){
            ps = con.prepareStatement("Update doctorvisit set reason = ? " +
                    "where personal_health_number = ? and doctor_id = ? and visit_date = ?");
            ps.setString(1,reason);
            ps.setInt(2,phn);
            ps.setInt(3, docID);
            ps.setDate(4, visitDate);
        }
        else
            return false;

        ps.executeUpdate();
        ps.close();

        return true;
    }

    //Find prescription details by ID
    public PrescriptionRow showPresByID(int presID, Connection con) throws SQLException{
        PreparedStatement ps = con.prepareStatement(
                "select prescription.pres_date, prescription.drug_id, drug.name, " +
                        "prescription.personal_health_number, patient.name, prescription.doctor_id, " +
                        "prescription.pharmacist_id, prescription.dosage, prescription.duration " +
                        "from prescription, drug, patient " +
                        "where prescription.prescription_id = ? and " +
                        "prescription.personal_health_number = patient.personal_health_number and " +
                        "prescription.drug_id = drug.drug_id "
        );
        ps.setInt(1, presID);
        ResultSet rs = ps.executeQuery();
        PrescriptionRow row = new PrescriptionRow();
        if(rs.next()){
            row.setPres_id(presID);
            row.setDate(rs.getDate(1));
            row.setDrug_id(rs.getInt(2));
            row.setDrug(rs.getString(3));
            row.setPhn(rs.getInt(4));
            row.setName(rs.getString(5));
            row.setDoctor_id(rs.getInt(6));
            row.setPharmacist_id(rs.getInt(7));
            row.setDuration(rs.getString(9));
            row.setDosage(rs.getInt(8));
        }
        ps.close();
        return row;
    }

    /*functions to search for a drug either by its id or name,
    so the results should either be a list or a single drug info object.
    You need to overload one single function to do the job. */

    public ArrayList<DrugInfo> searchByDrugIDorName(int drugID, String name,
                                                    Connection con) throws SQLException{
        ArrayList<DrugInfo> drug = new ArrayList<>();
        PreparedStatement ps;
        if(drugID > 0){
            ps = con.prepareStatement("select * from drug where drug_id = ?");
            ps.setInt(1, drugID);
        }
        else{
            ps = con.prepareStatement("select * from drug where NAME LIKE ?");
            ps.setString(1, "%"+name+"%");
        }
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            DrugInfo d = new DrugInfo();
            d.setDid(rs.getInt(1));
            d.setName(rs.getString(2));
            d.setEffects(rs.getString(3));
            d.setWarnings(rs.getString(4));
            drug.add(d);
        }
        ps.close();
        return drug;
    }

    // Calculate drug dosage average
    public int avgDosageForDrugs(Connection con) throws SQLException{
        int avg = 0;
        PreparedStatement ps = con.prepareStatement
                ("SELECT AVG(DOSAGE) FROM PRESCRIPTION");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            avg = rs.getInt(1);
        return avg;
    }

    public int avgDosageForDrugs(int drugID,Connection con) throws SQLException{
        int avg = 0;
        PreparedStatement ps = con.prepareStatement
                ("SELECT AVG(DOSAGE) FROM PRESCRIPTION WHERE DRUG_ID = ?");
        ps.setString(1,Integer.toString(drugID));
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            avg = rs.getInt(1);
        }
        return avg;
    }

    // Function to update a patients current prescription date
    public boolean updatePrescriptionInfo(int pharmacist_id,
                                          int prescriptionID,
                                          Date date, Connection con) throws SQLException{

        PreparedStatement ps;
        if(prescriptionID >= 0){ // No need to validate date as its checked in the SQL Table
            ps = con.prepareStatement("UPDATE PRESCRIPTION SET PRES_DATE = ?, PHARMACIST_ID = ? " +
                    "WHERE PRESCRIPTION_ID = ?");
            ps.setDate(1,date);
            ps.setInt(2,pharmacist_id);
            ps.setInt(3,prescriptionID);
        }
        else
            return false;

        ps.executeUpdate();
        ps.close();

        return true;
    }

    // Select * from pharmacist p where not exists ((select drug.drug_id from drug) minus
    //(select prescription.drug_id from prescription where prescription.pharmacist_id = p.pharmacist_id))
    public ArrayList<PharmacistInfo> findExperiencedPharmacist(Connection con) throws SQLException{
        ArrayList<PharmacistInfo> list = new ArrayList<PharmacistInfo>();
        PreparedStatement ps = con.prepareStatement
                ("Select * from pharmacist p " +
                        "where not exists ((select drug.drug_id from drug) " +
                        "minus (select prescription.drug_id " +
                        "from prescription " +
                        "where prescription.pharmacist_id = p.pharmacist_id))");
        ResultSet rs = ps.executeQuery();
        PharmacistInfo row;
        while(rs.next()){
            row = new PharmacistInfo();
            row.setName(rs.getString(2));
            row.setPhid(rs.getInt(3));
            list.add(row);
        }
        ps.close();
        return list;
    }

    public class PharmacistInfo{

        private int pid;
        private int phid;
        private String name;
        public void setPid(int id){
            this.pid = id;
        }
        public void setPhid(int id){
            this.phid = id;
        }
        public void setName(String n){
            this.name = n;
        }

        public String getName() {
            return name;
        }

        public int getPhid() {
            return phid;
        }

        public int getPid() {
            return pid;
        }
    }

    public class DrugInfo{
        private int did;
        private String name;
        private String effects;
        private String warnings;

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEffects() {
            return effects;
        }

        public void setEffects(String effects) {
            this.effects = effects;
        }

        public String getWarnings() {
            return warnings;
        }

        public void setWarnings(String warnings) {
            this.warnings = warnings;
        }
        public void print() {
            System.out.println(this.did+" "+this.name+" "+this.effects+" "+this.warnings);
        }
    }

    public class TestInfo{
        private Timestamp testTimeS;
        private String type;
        private Timestamp resultTimeS;
        private String resultDesc;

        public void setTestTimeS(Timestamp timeS){
            this.testTimeS = timeS;
        }
        public void setType(String type){
            this.type = type;
        }
        public void setResultTimeS(Timestamp timeS){
            this.resultTimeS = timeS;
        }
        public void setResultDesc(String desc){
            this.resultDesc = desc;
        }
        public Timestamp getTestTimeS(){
            return this.testTimeS;
        }
        public String getType(){
            return this.type;
        }
        public Timestamp getResultTimeS(){
            return this.resultTimeS;
        }
        public String getResultDesc(){
            return this.resultDesc;
        }
    }


    public class DoctorInfo{
        private int did;
        private String name;
        private String address;
        private String spec;

        public void setDid(int id){
            this.did = id;
        }

        public void setName(String n){
            this.name = n;
        }

        public void setAddress(String adr){
            this.address = adr;
        }

        public void setSpec(String s){
            this.spec = s;
        }

        public int getDid(){
            return this.did;
        }

        public String getName(){
            return this.name;
        }

        public String getAddress(){
            return this.address;
        }

        public String getSpec(){
            return this.spec;
        }
    }


    public class PatientInfo{
        private int phn;
        private String name;
        private String address;
        private String phone_number;
        private String dob;

        public void setPhn(int p){
            this.phn = p;
        }
        public void setName(String n){
            this.name = n;
        }
        public void setAddress(String adr){
            this.address = adr;
        }

        public void setPhone_number(String pn){
            this.phone_number = pn;
        }

        public void setDob(String d){
            this.dob = d;
        }

        public int getPhn(){
            return this.phn;
        }

        public String getName(){
            return this.name;
        }

        public String getAddress(){
            return this.address;
        }

        public String getPhone_number(){
            return this.phone_number;
        }

        public String getDob(){
            return this.dob;
        }
    }

    public class PrescriptionRow {
        private int pres_id;
        private int doctor_id;
        private int pharmacist_id;
        private int phn;
        private int drug_id;
        private Date date;
        private String duration;
        private int dosage;
        private String drug;
        private String name;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }

        public void setPhn(int phn) {
            this.phn = phn;
        }

        public int getPhn(){
            return this.phn;
        }

        public void setDrug_id(int drug_id) {
            this.drug_id = drug_id;
        }

        public int getDrug_id(){
            return this.drug_id;
        }

        public void setDate(Date date){
            this.date = date;
        }
        public void setDuration(String duration){
            this.duration = duration;
        }
        public void setDosage(int dosage){
            this.dosage = dosage;
        }
        public void setDrug(String drug){
            this.drug = drug;
        }

        public Date getDate(){
            return this.date;
        }
        public String getDuration(){
            return this.duration;
        }
        public int getDosage(){
            return this.dosage;
        }
        public String getDrug(){
            return this.drug;
        }

        public void setDoctor_id(int doctor_id) {
            this.doctor_id = doctor_id;
        }
        public void setPharmacist_id(int ph_id){
            this.pharmacist_id = ph_id;
        }

        public void setPres_id(int p_id){
            this.pres_id = p_id;
        }

        public int getPres_id()
        {
            return this.pres_id;
        }

        public int getDoctor_id(){
            return this.doctor_id;
        }

        public int getPharmacist_id(){
            return this.pharmacist_id;
        }
        public void print(){
            System.out.println(this.pres_id+
                    " "+this.doctor_id+
                    " "+this.drug_id+" "+this.pharmacist_id+" "+this.phn+
                    " "+this.name+
                    " "+this.dosage+" "+this.duration+" "+this.drug);
        }

    }

    public class DoctorVisit{
        private int phn;
        private String name;
        private Date visitDate;
        private String reason;
        private String notes;

        public void setPhn(int phn) {
            this.phn = phn;
        }
        public void setName(String name){
            this.name = name;
        }
        public void setVisitDate(Date visitDate) {
            this.visitDate = visitDate;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public int getPhn() {
            return phn;
        }

        public String getName() {
            return name;
        }

        public Date getVisitDate() {
            return visitDate;
        }

        public String getNotes() {
            return notes;
        }

        public String getReason() {
            return reason;
        }

        public void print(){
            System.out.println(this.phn + " " +this.name+ " " +this.visitDate+" "+this.reason+" "+this.notes);
        }
    }


}