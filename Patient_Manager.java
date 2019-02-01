import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class Patient_Manager {
    private JTable table;
    private JPanel panel2;
    private JLabel summary_max;
    private JLabel summary_min;
    private JButton searchForAPatientButton;
    private JButton addANewPatientButton;
    public JPanel panel;
    private JLabel summary_total;
    private JScrollPane jsp;
    private JButton seeDistributionButton;
    private int did;

    public Patient_Manager(int id) {

        this.did = id;
        try {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            ArrayList<Utilities.PatientInfo> patients = util.allPatientInfo(did, con);
            int max = util.maxPatientAge(did,con);
            int min = util.minPatientAge(did,con);
            summary_total.setText("You have a total of: " + Integer.toString(patients.size()) + " patients");
            summary_max.setText("The maximum age of your patients is: " + Integer.toString(max));
            summary_min.setText("The minimum age of your patients is: " + Integer.toString(min));
            String[] cols = {"Personal_health_number", "Name"};
            String [][] rows = new String[patients.size()][2];
            if(patients.size() > 0) {
                for (int i = 0; i < patients.size(); i++) {
                    rows[i][0] = Integer.toString(patients.get(i).getPhn()).trim();
                    rows[i][1] = patients.get(i).getName().trim();
                }
            }
            DefaultTableModel model = new DefaultTableModel(rows, cols);
            table.setModel(model);

            searchForAPatientButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField name = new JTextField();
                    JTextField phn = new JTextField();
                    Object[] message = {
                            "Patient Name:", name,
                            "Health Number:", phn
                    };
                    int option = JOptionPane.showConfirmDialog(null,
                            message, "Search", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION)
                    {
                        try{
                            JFrame frame = new JFrame();
                            frame.setTitle("Patient_UI");
                            Patient_UI pui = new Patient_UI(Integer.parseInt(phn.getText().trim())
                                    ,name.getText().trim());
                            frame.setContentPane(pui.panel);
                            frame.pack();
                            frame.setVisible(true);
                            frame.setLocationRelativeTo(null);
                            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        }catch (Exception e1){
                            JOptionPane.showMessageDialog(null, "Not Found");
                        }
                    }
                }
            });

            addANewPatientButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        JFrame frame = new JFrame();
                        frame.setTitle("Registration");
                        Registration rg = new Registration(did);
                        frame.setContentPane(rg.panel1);
                        frame.pack();
                        frame.setVisible(true);
                        frame.setLocationRelativeTo(null);
                        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null, "Failed to Open");
                    }
                }
            });

            seeDistributionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int g_max = util.maxPatientAgeForDocs(con);
                        int g_min = util.minPatientAgeForDocs(con);
                        JLabel max = new JLabel();
                        JLabel min = new JLabel();
                        max.setText(Integer.toString(g_max));
                        min.setText(Integer.toString(g_min));
                        Object[] message = {
                                "The Global maximum age:", max,
                                "The Global minimum age:", min
                        };
                        JOptionPane.showMessageDialog(null,message);
                    }
                    catch (Exception e2)
                    {
                        JOptionPane.showMessageDialog(null,"Connection Failed in Distribution");
                    }
                }
            });
        }
        catch (Exception e1)
        {
            JOptionPane.showMessageDialog(null,"Connection Failed in PM");
        }

    }
}
