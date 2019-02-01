import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;

public class To_Know_Form {
    private JTable table1;
    public JPanel panel;
    private JLabel avg;
    private JLabel gavg;
    private JScrollPane sp;

    public To_Know_Form(int drug_id){

        try{
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            ArrayList<Utilities.PharmacistInfo> phams = util.findExperiencedPharmacist(con);
            int average = util.avgDosageForDrugs(drug_id,con);
            int global_average = util.avgDosageForDrugs(con);
            avg.setText("Average dosage for this drug is: " + Integer.toString(average));
            gavg.setText("Global average dosage for all drugs is: " + Integer.toString(global_average));
            String[] cols = {"Name", "Pharmacy ID"};
            String [][] rows = new String[phams.size()][2];
            if(phams.size() > 0) {
                for (int i = 0; i < phams.size(); i++) {
                    rows[i][0] = phams.get(i).getName().trim();
                    rows[i][1] = Integer.toString(phams.get(i).getPhid());
                }
            }
            DefaultTableModel model = new DefaultTableModel(rows, cols);
            table1.setModel(model);
            sp.setName("Other Experienced Pharmacists");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }
    }
}
