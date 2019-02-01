import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Patient_UI {
    private JLabel patient_id;
    private JLabel patient_name;
    private JLabel patient_address;
    private JButton editUpdateProfileButton;
    private JLabel patient_number;
    private JButton viewYourDoctorSButton;
    private JButton viewYourPrescriptionsButton;
    private JButton deregisterYourAccountButton;
    private JButton viewYourMedicalTestsButton;
    public JPanel panel;
    private JButton logoutButton;
    private int phn;
    private String pname;

    public Patient_UI(int id, String name) {

        this.phn = id;
        this.pname = name;

        try
        {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            Utilities.PatientInfo patient = util.viewPatientInfo(phn,con);
            patient_id.setText(Integer.toString(phn));
            patient_name.setText(pname);
            patient_address.setText(patient.getAddress());
            patient_number.setText(patient.getPhone_number());

            editUpdateProfileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        JFrame frame = new JFrame();
                        frame.setTitle("Your Profile");
                        Patient_Profile profile = new Patient_Profile(phn);
                        frame.setContentPane(profile.panel);
                        frame.pack();
                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        frame.setLocationRelativeTo(null);
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null, "Failed");
                    }
                }
            });

            viewYourMedicalTestsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Utilities.TestInfo> tests = new ArrayList<>();
                    int test_count;
                    try{
                        tests = util.showAllTestsForPatient(phn, con);
                        test_count = util.findNumberTestsForPatient(phn,con);
                        String[] cols = {"Test_Time", "Type", "Result_Available","Description"};
                        String [][] rows = new String[tests.size()][4];
                        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        if(tests.size() > 0) {
                            for (int i = 0; i < tests.size(); i++) {
                                String s1 = f.format(tests.get(i).getTestTimeS()).trim();
                                rows[i][0] = s1;
                                rows[i][1] = tests.get(i).getType().trim();
                                String s2;
                                if (tests.get(i).getResultTimeS() == null)
                                    s2 = " ";
                                else
                                    s2 = f.format(tests.get(i).getResultTimeS()).trim();
                                rows[i][2] = s2;
                                String s3;
                                if (tests.get(i).getResultDesc() == null)
                                    s3 = " ";
                                else
                                    s3 = tests.get(i).getResultDesc().trim();
                                rows[i][3] = s3;
                            }
                        }
                        JTable t = new JTable(rows,cols);
                        JOptionPane.showMessageDialog(null, new JScrollPane(t), "You have " +
                                test_count + " tests", JOptionPane.PLAIN_MESSAGE);
                    }
                    catch (Exception e2){
                        JOptionPane.showMessageDialog(null, "Failed");
                    }
                }
            });

            viewYourPrescriptionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Utilities.PrescriptionRow> prescriptions = new ArrayList<>();
                    try{
                        prescriptions = util.showAllPrescriptionsForPatient(phn, con);
                        int pres_count = prescriptions.size();
                        String[] cols = {"Date", "Duration", "Dosage","Drug"};
                        String [][] rows = new String[prescriptions.size()][4];
                        if(prescriptions.size() > 0) {
                            for (int i = 0; i < prescriptions.size(); i++) {
                                rows[i][0] = prescriptions.get(i).getDate().toString().trim();
                                rows[i][1] = prescriptions.get(i).getDuration().trim();
                                rows[i][2] = Integer.toString(prescriptions.get(i).getDosage()).trim();
                                rows[i][3] = prescriptions.get(i).getDrug().trim();
                            }
                        }
                        JTable t = new JTable(rows,cols);
                        JOptionPane.showMessageDialog(null, new JScrollPane(t), "You have " +
                                pres_count + " prescriptions", JOptionPane.PLAIN_MESSAGE);
                    }
                    catch (Exception e2){
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                }
            });

            deregisterYourAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try
                    {
                        int button = JOptionPane.YES_NO_OPTION;
                        int result = JOptionPane.showConfirmDialog(null,
                                "Do you confirm to delete your account?", "Warning",
                                button);
                        if(result == JOptionPane.YES_OPTION)
                        {
                            util.self_unregister(phn,con);
                            JFrame frame = JFrames.get_frame();
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.setTitle("Login");
                            frame.setContentPane(new Medical_Records_Manager().Login);
                            frame.pack();
                            frame.setVisible(true);
                            frame.setLocationRelativeTo(null);
                        }
                    }
                    catch (Exception e4)
                    {
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                }
            });
        }
        catch (Exception e1)
        {
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }

        viewYourDoctorSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFrame frame = new JFrame();
                    frame.setTitle("Your Family Doctor");
                    FamilyDoctor_Profile profile = new FamilyDoctor_Profile(phn);
                    frame.setContentPane(profile.panel);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                }catch (Exception e3){
                    JOptionPane.showMessageDialog(null, "Not Data Found");
                }
            }
        });

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
