import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuWindow extends JFrame implements ActionListener {

    JPanel joinPanel = new JPanel();
    JPanel createPanel = new JPanel();
    JPanel gamePanel = new JPanel(null);

    ArrayList<GameListing> gameList = new ArrayList<GameListing>();


    JButton btnJoin = new JButton("Join game");
    JTextField gameCodeText = new JTextField();


    JButton btnCreate = new JButton("Create new game");

    MenuWindow(){
        Container cp = getContentPane();
        cp.setLayout(null);
        joinPanel.setBounds(0,0,600,600);
        joinPanel.setLayout(null);
        joinPanel.setBackground(Color.RED);
        cp.add(joinPanel);

        createPanel.setBounds(600,0,600,600);
        createPanel.setLayout(null);
        createPanel.setBackground(Color.GREEN);
        cp.add(createPanel);

        btnCreate.setBounds(0,0,200,200);
        createPanel.add(btnCreate);

        gamePanel.setLayout(null);

        joinPanel.add(gamePanel);
        gamePanel.setBounds(0,0,600,480);
        //gamePanel.setLayout(new GridLayout(0,1));

        btnJoin.setBounds(500,530,100,30);
        btnJoin.addActionListener(this);
        joinPanel.add(btnJoin);

        gameCodeText.setBounds(300,530,200,30);
        joinPanel.add(gameCodeText);


        btnCreate.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnJoin)){
            Main.connection.out.send("join 1");
        }
        else if(actionEvent.getSource().equals(btnCreate)){
            Main.connection.out.send("create AwesomeGame");
        }
    }




    public void joinGame(String gameCode){
        System.out.println("Joining game: " + gameCode);
    }




    public void createGame(String gameName, int maxPlayers, boolean isPublic){

    }

    public void addGame(){
        System.out.println("test test test");
        gameList.add(new GameListing("test game",3,8,"Gustav", "jhrhg74h", this));
        gameList.get(gameList.size()-1).setBounds(0,30,600,30);
        gamePanel.add(gameList.get(gameList.size()-1));
        gamePanel.repaint();
    }
}
