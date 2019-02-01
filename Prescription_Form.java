import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Prescription_Form {
    public JPanel panel;
    private JTextField pres_id_in;
    private JTextField date_in;
    private JTextField drugId_in;
    private JTextField pid_in;
    private JTextField dosage_in;
    private JTextField duration_in;
    private JButton saveButton;
    private JLabel pres_id;
    private JLabel date;
    private JLabel drug_id;
    private JLabel pid;
    private JLabel dosage;
    private JLabel duration;

    public Prescription_Form(int id) {
        pres_id.setText("");
        date.setText("");
        drug_id.setText("");
        pid.setText("");
        dosage.setText("");
        duration.setText("");
        try
        {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int prescription_id;
                    int drugId;
                    int patient_id;
                    if(pres_id_in.getText().trim().length() > 0)
                        prescription_id = Integer.parseInt(pres_id_in.getText().trim());
                    else
                        prescription_id = 0;
                    if(drugId_in.getText().trim().length() > 0)
                        drugId = Integer.parseInt(drugId_in.getText().trim());
                    else
                        drugId = 0;
                    if(pid_in.getText().trim().length() > 0)
                        patient_id = Integer.parseInt(pid_in.getText().trim());
                    else
                        patient_id = 0;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    int drug_dosage = Integer.parseInt(dosage_in.getText().trim());
                    String prescription_duration = duration_in.getText().trim();
                    try {
                        Date d;
                        if (date_in.getText().trim().length() > 0)
                        {
                            d = formatter.parse(date_in.getText().trim());
                            java.sql.Date prescription_date = new java.sql.Date(d.getTime());
                            boolean state = prompt_confirmation(patient_id,drugId,util,con);
                            if(id > 0 && prescription_id > 0 && patient_id > 0 && state) {
                                util.insert_prescription(prescription_id, prescription_date,
                                        drugId, patient_id, id, drug_dosage, prescription_duration, con);
                                JOptionPane.showMessageDialog(null,"Success");
                            }
                            else if(!state){
                                JOptionPane.showMessageDialog(null,"You chose not to submit!");
                            }
                            else
                                JOptionPane.showMessageDialog(null,"You must at least have all the IDs!");
                        }
                        else
                            JOptionPane.showMessageDialog(null,"You must Enter a date");

                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }

                }
            });
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }
    }


    private boolean prompt_confirmation(int phn, int drug_id, Utilities util, Connection con)throws Exception
    {
        ArrayList<Utilities.PrescriptionRow> ps = util.show_prescription_drugs(phn,drug_id,con);
        String[] cols = {"Date","Duration","Dosage","Drug"};
        String [][] rows = new String[ps.size()][4];
        for(int i = 0; i < ps.size(); i++){
            rows[i][0] = ps.get(i).getDate().toString().trim();
            rows[i][1] = ps.get(i).getDuration().trim();
            rows[i][2] = Integer.toString(ps.get(i).getDosage());
            rows[i][3] = ps.get(i).getDrug().trim();
        }
        DefaultTableModel model = new DefaultTableModel(rows, cols);
        JTable table = new JTable();
        table.setModel(model);
        int button = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null,
                new JScrollPane(table), "Previous records with this drug",
                button);
        if(result == JOptionPane.YES_OPTION)
            return true;
        else
            return false;

    }

    public Prescription_Form(Utilities.PrescriptionRow row, int ph_id, int flag) {

        pres_id.setText(Integer.toString(row.getPres_id()));
        date.setText(row.getDate().toString());
        drug_id.setText(Integer.toString(row.getDrug_id()));
        pid.setText(Integer.toString(row.getPhn()));
        dosage.setText(Integer.toString(row.getDosage()));
        duration.setText(row.getDuration());
        pres_id_in.setEditable(false);
        drugId_in.setEditable(false);
        pid_in.setEditable(false);
        dosage_in.setEditable(false);
        duration_in.setEditable(false);
        if(flag == 0) {
            date_in.setEditable(false);
            saveButton.setEnabled(false);
        }
        try
        {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try
                    {
                        Date d;
                        if (date_in.getText().trim().length() > 0) {
                            d = formatter.parse(date_in.getText().trim());
                            java.sql.Date prescription_date = new java.sql.Date(d.getTime());
                            if(util.updatePrescriptionInfo(ph_id, row.getPres_id(), prescription_date, con))
                            {
                                JOptionPane.showMessageDialog(null,"Success");
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"Not Saved");
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(null,"You must Enter a date");
                    }
                    catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }
            });
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }
    }
}
