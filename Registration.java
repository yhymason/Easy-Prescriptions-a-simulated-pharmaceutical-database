import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registration {
    public JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JLabel firstname;
    private JLabel lastname;
    private JLabel address;
    private JLabel dob;
    private JLabel pid;
    private JTextField patient_fn;
    private JTextField patient_ln;
    private JTextField patient_address;
    private JPasswordField phn;
    private JTextField patient_dob;
    private JLabel phone_number;
    private JTextField patient_phonenum;
    private JButton backToLoginScreenButton;
    private JButton registerButton;
    private JTextField doctor_fn;
    private JTextField doctor_ln;
    private JTextField doctor_address;
    private JTextField speciality;
    private JPasswordField doctor_id;
    private JButton registerButton1;
    private JButton backToLoginScreenButton1;
    private JTextField phar_id;
    private JTextField ph_ln;
    private JTextField ph_fn;
    private JPasswordField ph_id;
    private JButton registerButton2;
    private JButton backToLoginScreenButton2;
    private int did;

    public Registration(int id) throws Exception{

        this.did = id;
        try {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String firstname = patient_fn.getText().trim();
                    String lastneme = patient_ln.getText().trim();
                    String fullname = firstname + " " + lastneme;
                    String address = patient_address.getText().trim();
                    String dob = patient_dob.getText().trim();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String phone_num = patient_phonenum.getText().trim();
                    Date date;
                    try {
                        date = formatter.parse(dob);
                        java.sql.Date d = new java.sql.Date(date.getTime());
                        int id = Integer.parseInt(new String(phn.getPassword()).trim());
                        try{
                            if ((firstname.length()!=0 && lastneme.length()!=0) && id>=0)
                            {
                                if(did > 0) {
                                    util.insert_patient(id, fullname, address, phone_num, d, did, con);
                                    JOptionPane.showMessageDialog(null,"Success");
                                }
                                else {
                                    util.insert_patient(id, fullname, address, phone_num, d, con);
                                    JOptionPane.showMessageDialog(null,"Success");
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"You must at least have a full name and id!");
                            }
                        }catch (Throwable err)
                        {
                            JOptionPane.showMessageDialog(null,"Error:"+err);
                        }
                    }catch(Exception e2){
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }
                }
            });

            registerButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String firstname = doctor_fn.getText().trim();
                    String lastneme = doctor_ln.getText().trim();
                    String fullname = firstname + " " + lastneme;
                    String address = doctor_address.getText().trim();
                    String spec = speciality.getText().trim();
                    int id = Integer.parseInt(new String(doctor_id.getPassword()).trim());
                    try{
                        if ((firstname.length()!=0 && lastneme.length()!=0) && id>=0)
                        {
                            util.insert_doctor(id,fullname,address,spec,con);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"You must at least have a full name and id!");
                        }
                    }catch (Throwable err)
                    {
                        JOptionPane.showMessageDialog(null,"Error:"+err);
                    }
                }
            });

            registerButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String firstname = ph_fn.getText().trim();
                    String lastneme = ph_ln.getText().trim();
                    String fullname = firstname + " " + lastneme;
                    int pharm_id = Integer.parseInt(phar_id.getText().trim());
                    int id = Integer.parseInt(new String(ph_id.getPassword()).trim());
                    try{
                        if ((firstname.length()!=0 && lastneme.length()!=0) && (id>=0 && pharm_id>=0))
                        {
                            util.insert_pharmacist(id,fullname,pharm_id,con);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"You must at least have a full name and id!");
                        }
                    }catch (Throwable err)
                    {
                        JOptionPane.showMessageDialog(null,"Error:"+err);
                    }
                }
            });

        }catch(Exception e1){
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }

        backToLoginScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = JFrames.get_frame();
                frame.setTitle("Login");
                frame.setContentPane(new Medical_Records_Manager().Login);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });


        backToLoginScreenButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = JFrames.get_frame();
                frame.setTitle("Login");
                frame.setContentPane(new Medical_Records_Manager().Login);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });

        backToLoginScreenButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = JFrames.get_frame();
                frame.setTitle("Login");
                frame.setContentPane(new Medical_Records_Manager().Login);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}
