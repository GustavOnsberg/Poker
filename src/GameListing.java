import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameListing extends JPanel implements ActionListener {

    String gameName;
    int players;
    int maxPlayers;
    long sessiopnId;
    MenuWindow menuWindow;

    JLabel labelName;
    JLabel labelPlayers;
    JButton btnInfo = new JButton("More info");
    JButton btnJoin = new JButton("Join game");

    public GameListing(String gameName, int players, int maxPlayers, long sessiopnId, MenuWindow menuWindow){
        this.menuWindow = menuWindow;
        this.players = players;
        this.maxPlayers = maxPlayers;
        this.sessiopnId = sessiopnId;
        this.gameName = gameName;

        setLayout(null);

        labelName = new JLabel(gameName);
        labelName.setBounds(0,0,350,30);


        labelPlayers = new JLabel(players+" / "+maxPlayers);
        labelPlayers.setBounds(350,0,50,30);


        btnInfo.setBounds(400,0,100,30);
        btnInfo.addActionListener(this);
        btnJoin.setBounds(500,0,100,30);
        btnJoin.addActionListener(this);

        add(labelName);
        add(labelPlayers);
        //add(btnInfo);
        add(btnJoin);

        setBackground(Color.YELLOW);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(btnJoin)){
            menuWindow.joinGame(sessiopnId);
        }
    }
}
