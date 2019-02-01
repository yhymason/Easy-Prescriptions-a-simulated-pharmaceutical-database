import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class Pharmacist_UI {
    private JLabel pname;
    private JLabel phid;
    private JLabel pid;
    private JButton searchForPrescriptionButton;
    private JButton searchForDrugButton;
    private JButton renewAPrescriptionButton;
    private JButton logoutButton;
    private JButton toKnowListButton;
    public JPanel panel;
    private int ph_id;
    private String ph_name;

    public Pharmacist_UI(int id, String name) {

        this.ph_id = id;
        this.ph_name = name;

        try{
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            Utilities.PharmacistInfo pharmacist = util.viewPharmacistInfo(ph_id,con);
            pname.setText("Name: " + ph_name);
            phid.setText("Pharmacist ID: " + Integer.toString(ph_id));
            pid.setText("Pharmacy ID: " + Integer.toString(pharmacist.getPhid()));

            searchForPrescriptionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField pres_id = new JTextField();
                    Object[] message = {
                            "Prescription ID:", pres_id
                    };
                    int option = JOptionPane.showConfirmDialog(null,
                            message, "Search", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try
                        {
                            Utilities.PrescriptionRow p = util.showPresByID(Integer.parseInt(pres_id.getText()),con);
                            JFrame frame = new JFrame();
                            frame.setTitle("Prescription Info");
                            Prescription_Form pf = new Prescription_Form(p,ph_id,0);
                            frame.setContentPane(pf.panel);
                            frame.pack();
                            frame.setVisible(true);
                            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            frame.setLocationRelativeTo(null);

                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, "Not Found");
                        }
                    }
                }
            });

            renewAPrescriptionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField pres_id = new JTextField();
                    Object[] message = {
                            "Prescription ID:", pres_id
                    };
                    int option = JOptionPane.showConfirmDialog(null,
                            message, "Search", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            Utilities.PrescriptionRow row = util.showPresByID(Integer.parseInt(pres_id.getText()), con);
                            JFrame frame = new JFrame();
                            frame.setTitle("Update prescription");
                            Prescription_Form pf = new Prescription_Form(row,ph_id,1);
                            frame.setContentPane(pf.panel);
                            frame.pack();
                            frame.setVisible(true);
                            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            frame.setLocationRelativeTo(null);
                        }
                        catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, "Not Found");
                        }
                    }

                }
            });

            searchForDrugButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField drug_id = new JTextField();
                    JTextField drug_name = new JTextField();
                    Object[] message = {
                            "Drug ID:", drug_id,
                            "Drug Name:", drug_name
                    };
                    int option = JOptionPane.showConfirmDialog(null,
                            message, "Search", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try
                        {
                            if(drug_id.getText().trim().length() > 0 ||
                                    drug_name.getText().trim().length() >0)
                            {
                                String dname = drug_name.getText().trim();
                                int did = 0;
                                if(drug_id.getText().trim().length() > 0)
                                    did = Integer.parseInt(drug_id.getText().trim());
                                ArrayList<Utilities.DrugInfo> drugs = util.searchByDrugIDorName(did,dname,con);
                                String[] cols = {"Drug ID", "Drug Name", "Effects", "Warnings"};
                                String [][] rows = new String[drugs.size()][4];
                                if(drugs.size()>0) {
                                    for (int i = 0; i < drugs.size(); i++) {
                                        rows[i][0] = Integer.toString(drugs.get(i).getDid());
                                        rows[i][1] = drugs.get(i).getName().trim();
                                        rows[i][2] = drugs.get(i).getEffects();
                                        rows[i][3] = drugs.get(i).getWarnings();
                                    }
                                }
                                DefaultTableModel model = new DefaultTableModel(rows, cols);
                                JTable t = new JTable();
                                t.setModel(model);
                                JOptionPane.showMessageDialog(null, new JScrollPane(t), "Results",
                                        JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                        catch (Exception e1)
                        {
                                JOptionPane.showMessageDialog(null, "Not Found");
                        }
                    }
                }
            });


            toKnowListButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField drug_id = new JTextField();
                    Object[] message = {
                            "Drug ID:", drug_id
                    };
                    int option = JOptionPane.showConfirmDialog(null,
                            message, "Search", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            JFrame frame = new JFrame();
                            frame.setTitle("To_Know_Form");
                            To_Know_Form tkf = new To_Know_Form(Integer.parseInt(drug_id.getText()));
                            frame.setContentPane(tkf.panel);
                            frame.pack();
                            frame.setVisible(true);
                            frame.setLocationRelativeTo(null);
                            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        }
                        catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, "Not Data Found");
                        }
                    }
                }
            });

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = JFrames.get_frame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Login");
                frame.setContentPane(new Medical_Records_Manager().Login);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });

    }
}
