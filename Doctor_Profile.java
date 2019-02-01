import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Doctor_Profile {
    private JButton saveButton;
    private JTextField address;
    private JTextField speciality;
    public JPanel panel;
    private int d_id;

    public Doctor_Profile(int id) {
        this.d_id = id;
        try
        {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            String new_address = address.getText().trim();
            String new_speciality = speciality.getText().trim();
            int did = this.d_id;
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        if(util.updateDoctorInfo(did, new_address, new_speciality, con))
                        {
                            JOptionPane.showMessageDialog(null,"Success");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Not Saved");
                        }
                    }
                    catch (java.sql.SQLException e1){
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }
            });
        }
        catch(Exception e1){
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }
    }
}
