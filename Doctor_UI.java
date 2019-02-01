import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Doctor_UI {
    public JPanel panel;
    private JButton editProfile;
    private JLabel d_id;
    private JLabel d_name;
    private JLabel d_address;
    private JLabel d_spec;
    private JButton createPrescriptionsButton;
    private JButton viewTestsButton;
    private JButton viewEditPatientProfilesButton;
    private JButton viewEditVisitRecordsButton;
    private JButton logoutButton;
    private int doctor_id;
    private String doctor_name;

    public Doctor_UI(int id, String name) {

        this.doctor_id = id;
        this.doctor_name = name;

        try {

            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            Utilities.DoctorInfo doctor = util.viewDoctorInfo(doctor_id,con);
            d_id.setText(Integer.toString(doctor_id));
            d_name.setText(doctor_name);
            d_address.setText(doctor.getAddress());
            d_spec.setText(doctor.getSpec());

            editProfile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        JFrame frame = new JFrame();
                        frame.setTitle("Your Profile");
                        Doctor_Profile profile = new Doctor_Profile(id);
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


            createPrescriptionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame();
                    frame.setTitle("Add a new prescription");
                    Prescription_Form pf = new Prescription_Form(id);
                    frame.setContentPane(pf.panel);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                }
            });

            viewTestsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Utilities.TestInfo> tests = new ArrayList<>();
                    try{
                        tests = util.showAllTestsForDoctor(doctor_id, con);
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
                        JOptionPane.showMessageDialog(null, new JScrollPane(t), "Patient Tests",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                    catch (Exception e2){
                        JOptionPane.showMessageDialog(null, "No Data Found");
                    }
                }
            });


        }
        catch (Exception e1)
        {
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

        viewEditPatientProfilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setTitle("Patient Manager");
                Patient_Manager pm = new Patient_Manager(id);
                frame.setContentPane(pm.panel);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
            }
        });

        viewEditVisitRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setTitle("Visit Manager");
                Visit_Manager vm = new Visit_Manager(id);
                frame.setContentPane(vm.panel);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}
