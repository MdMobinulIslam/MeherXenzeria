import javax.swing.JFrame;

public class MeherXenzeriaGameFrame extends JFrame {

    public MeherXenzeriaGameFrame() {
        this.add(new MeherXenzeriaGamePanel());
        this.setTitle("MeherXenzeria");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new MeherXenzeriaGameFrame();
    }
}
