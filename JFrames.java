
import javax.swing.*;

public class JFrames {
    private static JFrame frame = null;

    private JFrames (){
        frame = new JFrame();
    }

    public static JFrame get_frame(){
        if(frame == null) {
            new JFrames();
        }
        return frame;
    }
}
