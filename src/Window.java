import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.Icon;

public class Window extends JFrame implements ActionListener, Runnable, ChangeListener {
    Game thisGame;
    boolean isFullscreen;
    Thread runningThis;


    int playBtnW = 120;
    int playBtnH = 40;
    int playBtnSpace = 10;
    int sidePanelW = 400;

    Container cp = getContentPane();
    Dimension lastSize = new Dimension(0,0);

    JPanel gamePanel = new JPanel();
    JPanel sidePanel = new JPanel();
    JPanel settingsPanel = new JPanel();

    JButton btnRaise = new JButton("Raise");
    JButton btnCall = new JButton("Call");
    JButton btnFold = new JButton("Fold");

    JSlider betSlider = new JSlider(JSlider.HORIZONTAL,50000,2000);
    JLabel betSliderLabel = new JLabel("$ 10000");


    JButton btnSettingsDeck = new JButton("D");
    JButton btnSettingsMusic = new JButton();
    JButton btnSettingsSound = new JButton();
    JButton btnSettingsFullscreen = new JButton();
    JButton btnSettingsLeave = new JButton("L");

    int settingsNum = 5;

    JPanel infoPanel = new JPanel();
    JLabel cashLabel = new JLabel("Cash: 346323");
    JLabel betLabel = new JLabel("Bet: 3241");
    Font cashFont = new Font("Verdana", Font.BOLD, 18);


    JTextArea chatArea;
    JScrollPane chatScroll;
    JTextField chatInput = new JTextField();
    JButton btnChatSend = new JButton("Send");




    TableComponent tableComponent = new TableComponent();

    public Window(boolean isFullscreen, Game thisGame) throws IOException {
        this.thisGame = thisGame;
        this.isFullscreen = isFullscreen;

        cp.setLayout(null);
        cp.add(gamePanel);
        cp.add(sidePanel);

        btnRaise.addActionListener(this);
        btnCall.addActionListener(this);
        btnFold.addActionListener(this);

        betSlider.setMajorTickSpacing(10000);
        betSlider.setMinorTickSpacing(1000);
        betSlider.setPaintTicks(true);
        betSlider.setPaintLabels(true);
        betSlider.setBackground(null);
        betSlider.addChangeListener(this);

        betSliderLabel.setVisible(true);

        gamePanel.setLayout(null);
        gamePanel.add(btnRaise);
        gamePanel.add(btnCall);
        gamePanel.add(btnFold);
        gamePanel.add(betSlider);
        gamePanel.add(betSliderLabel);
        gamePanel.add(tableComponent);
        gamePanel.setBackground(Main.game.backgroungColor);
        tableComponent.setBackground(null);

        sidePanel.setLayout(null);
        sidePanel.setBackground(Color.RED);
        settingsPanel.setVisible(true);
        settingsPanel.setBounds(0,0,sidePanelW,sidePanelW/settingsNum);
        settingsPanel.setBackground(null);
        settingsPanel.setLayout(null);
        sidePanel.add(settingsPanel);
        btnSettingsDeck.setBounds(0,0,sidePanelW/settingsNum,sidePanelW/settingsNum);
        btnSettingsMusic.setBounds(sidePanelW/settingsNum,0,sidePanelW/settingsNum,sidePanelW/settingsNum);
        btnSettingsSound.setBounds(sidePanelW/settingsNum*2,0,sidePanelW/settingsNum,sidePanelW/settingsNum);
        btnSettingsFullscreen.setBounds(sidePanelW/settingsNum*3,0,sidePanelW/settingsNum,sidePanelW/settingsNum);
        btnSettingsLeave.setBounds(sidePanelW/settingsNum*4,0,sidePanelW/settingsNum,sidePanelW/settingsNum);


        Image imgSettingsMusic = ImageIO.read(getClass().getResource("/resources/graphics/icons/icon_music.png")).getScaledInstance(40,40, Image.SCALE_SMOOTH);
        Icon iconSettingsMusic = new ImageIcon(imgSettingsMusic);

        Image imgSettingsSound = ImageIO.read(getClass().getResource("/resources/graphics/icons/icon_sound.png")).getScaledInstance(40,40, Image.SCALE_SMOOTH);
        Icon iconSettingsSound = new ImageIcon(imgSettingsSound);

        Image imgSettingsFullscreen;
        if (isFullscreen)
            imgSettingsFullscreen = ImageIO.read(getClass().getResource("/resources/graphics/icons/icon_fullscreen_exit.png")).getScaledInstance(40,40, Image.SCALE_SMOOTH);
        else
            imgSettingsFullscreen = ImageIO.read(getClass().getResource("/resources/graphics/icons/icon_fullscreen.png")).getScaledInstance(40,40, Image.SCALE_SMOOTH);
        Icon iconSettingsFullscreen = new ImageIcon(imgSettingsFullscreen);

        btnSettingsMusic.setIcon(iconSettingsMusic);
        btnSettingsSound.setIcon(iconSettingsSound);
        btnSettingsFullscreen.setIcon(iconSettingsFullscreen);


        settingsPanel.add(btnSettingsDeck);
        settingsPanel.add(btnSettingsMusic);
        settingsPanel.add(btnSettingsSound);
        settingsPanel.add(btnSettingsFullscreen);
        settingsPanel.add(btnSettingsLeave);

        btnSettingsDeck.addActionListener(this);
        btnSettingsMusic.addActionListener(this);
        btnSettingsSound.addActionListener(this);
        btnSettingsFullscreen.addActionListener(this);
        btnSettingsLeave.addActionListener(this);

        sidePanel.add(infoPanel);
        infoPanel.setBounds(0,80,400,200);
        infoPanel.setLayout(null);
        cashLabel.setFont(cashFont);
        infoPanel.add(cashLabel);
        cashLabel.setBounds(10,10,200,20);

        betLabel.setFont(cashFont);
        infoPanel.add(betLabel);
        betLabel.setBounds(10,30,200,20);

        chatArea = new JTextArea("",5,50);
        chatArea.setLineWrap(true);
        chatArea.setEditable(false);
        chatArea.setWrapStyleWord(true);
        chatScroll = new JScrollPane(chatArea);
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        btnChatSend.addActionListener(this);
        sidePanel.add(chatScroll);
        sidePanel.add(chatInput);
        sidePanel.add(btnChatSend);

        onSizeChange();
    }



    
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnSettingsFullscreen)){
            try {
                thisGame.createWindow(!isFullscreen, thisGame);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dispose();
        }
        else if(actionEvent.getSource().equals(btnRaise)){
            Main.connection.out.send("move raise "+betSlider.getValue());
        }
        else if(actionEvent.getSource().equals(btnCall)){
            Main.connection.out.send("move call");
        }
        else if(actionEvent.getSource().equals(btnFold)){
            Main.connection.out.send("move fold");
        }
        else if(actionEvent.getSource().equals(btnSettingsLeave)){
            Main.connection.out.send("leave");
        }
        else if(actionEvent.getSource().equals(btnChatSend)){
            if(chatInput.getText().length() > 0){
                if(chatInput.getText().charAt(0) == '/') {
                    Main.connection.out.send("command " + chatInput.getText().substring(1));
                    chatArea.append("\n" + chatInput.getText());
                }
                else
                    Main.connection.out.send("chat "+chatInput.getText());
                chatInput.setText("");
            }

        }
        else if(actionEvent.getSource().equals(btnSettingsDeck)){

        }
    }

    @Override
    public void run() {
        while(true){
            if(!cp.getSize().equals(lastSize)){
                onSizeChange();
                lastSize.setSize(cp.getSize());
            }
            tableComponent.repaint();
            betSliderLabel.setText(betSliderLabel.getText());
            betSliderLabel.setBounds(betSliderLabel.getBounds());
            if(Main.game.placeAtTable >= 0) {
                cashLabel.setText("Cash: " + Main.game.players.get(Main.game.placeAtTable).cash);
                betLabel.setText("Bet: " + Main.game.players.get(Main.game.placeAtTable).bet);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void onSizeChange(){
        gamePanel.setBounds(0, 0, cp.getWidth() - sidePanelW, cp.getHeight());
        sidePanel.setBounds(cp.getWidth()-sidePanelW,0,sidePanelW, cp.getHeight());


        btnRaise.setBounds(gamePanel.getWidth() / 2 - playBtnW * 3 / 2 - playBtnSpace, gamePanel.getHeight() - playBtnH - playBtnSpace, playBtnW, playBtnH);
        btnCall.setBounds(gamePanel.getWidth() / 2 - playBtnW / 2, gamePanel.getHeight() - playBtnH - playBtnSpace, playBtnW, playBtnH);
        btnFold.setBounds(gamePanel.getWidth() / 2 + playBtnW / 2 + playBtnSpace, gamePanel.getHeight() - playBtnH - playBtnSpace, playBtnW, playBtnH);

        betSlider.setBounds(50,cp.getHeight() - 2 * playBtnSpace - playBtnH - 40, gamePanel.getWidth() - 100, 50);

        betSliderLabel.setBounds(betSlider.getX() + betSlider.getWidth() * betSlider.getValue() / betSlider.getMaximum(),betSlider.getY() - 10,200,15);

        tableComponent.setBounds(0,0,gamePanel.getWidth(),betSliderLabel.getY());

        chatScroll.setBounds(0,300,sidePanelW,sidePanel.getHeight()-330);
        chatInput.setBounds(0,sidePanel.getHeight()-30,sidePanelW-100,30);
        btnChatSend.setBounds(sidePanelW-100,sidePanel.getHeight()-30,100,30);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        if (changeEvent.getSource().equals(betSlider)){
            betSliderLabel.setBounds(betSlider.getX() + betSlider.getWidth() * betSlider.getValue() / betSlider.getMaximum(),betSlider.getY() - 10,200,15);
            betSliderLabel.setText("$ "+betSlider.getValue());
            btnRaise.setEnabled(true);
            if (betSlider.getValue() == 0)
                btnRaise.setText("Can't raise 0");
            else if(betSlider.getValue() != betSlider.getMaximum())
                btnRaise.setText("Raise");
            else
                btnRaise.setText("ALL IN");
        }
    }
}
