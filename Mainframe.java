import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent; 
public class Mainframe extends JFrame {
    public Mainframe() {
         ImageIcon image = new ImageIcon("logos\\pixelboxdfinallogo.png");
         ImageIcon image1 = new ImageIcon("logos\\cool.jpeg");
        this.setSize(1600, 800);
        this.setLocationRelativeTo(null);
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(20, 29, 48));
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JButton games = new JButton("Games", image1);
games.setBounds(400, 600, 165, 165);

games.setFont(new Font("SansSerif", Font.BOLD, 18));
games.setForeground(Color.WHITE);
games.setBackground(new Color(45, 75, 120));
games.setOpaque(true);
games.setFocusPainted(false); // We'll draw our own focus style.
games.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(new Color(100, 150, 220), 2, true),
    BorderFactory.createEmptyBorder(12, 12, 12, 12)
));
games.setHorizontalTextPosition(SwingConstants.CENTER);
games.setVerticalTextPosition(SwingConstants.BOTTOM);
games.setIconTextGap(10);
games.setToolTipText("Open the games library");

games.addFocusListener(new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        games.setBackground(new Color(65, 105, 165));
        games.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 210, 255), 3, true),
            BorderFactory.createEmptyBorder(11, 11, 11, 11)
        ));
    }

    @Override
    public void focusLost(FocusEvent e) {
        games.setBackground(new Color(45, 75, 120));
        games.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 220), 2, true),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
    }
});

this.add(games);
        this.setVisible(true);

    }
}
