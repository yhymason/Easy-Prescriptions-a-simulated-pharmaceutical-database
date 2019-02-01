import javax.swing.*;
import java.sql.Connection;

public class FamilyDoctor_Profile {
    public JPanel panel;
    private JLabel fd_name;
    private JLabel fd_address;
    private JLabel fd_spec;

    private int phn;

    public FamilyDoctor_Profile(int phn) {
        this.phn = phn;
        try
        {
            Connection con = Connections.getConnection();
            Utilities util = new Utilities();
            Utilities.DoctorInfo doctor = util.viewFamilyDoctor(phn, con);
            fd_name.setText(doctor.getName());
            fd_address.setText(doctor.getAddress());
            fd_spec.setText(doctor.getSpec());
        }
        catch (Exception e1){
            JOptionPane.showMessageDialog(null,"Connection Failed");
        }
    }
}
