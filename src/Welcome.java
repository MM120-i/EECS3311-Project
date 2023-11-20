import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome {
    private JButton hiButton;
    private JPanel panel1;
    private JLabel wassup;

    public Welcome() {
        hiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wassup.setText("change");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Welcome");
        frame.setContentPane(new Welcome().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
