import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Window extends JFrame implements ActionListener, Runnable, ChangeListener {
    boolean isFullscreen;

    Image imgSettingsFullscreen = ImageIO.read(getClass().getResource("graphics/icons/icon_fullscreen.png"));

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
    JButton btnSettingsMusic = new JButton("M");
    JButton btnSettingsSound = new JButton("S");
    JButton btnSettingsFullscreen = new JButton("F");
    JButton btnSettingsLeave = new JButton("L");

    int settingsNum = 5;


    TableComponent tableComponent = new TableComponent();

    public Window() throws IOException {
        cp.setLayout(null);
        cp.add(gamePanel);
        cp.add(sidePanel);

        betSlider.setMajorTickSpacing(10000);
        betSlider.setMinorTickSpacing(1000);
        betSlider.setPaintTicks(true);
        betSlider.setPaintLabels(true);
        betSlider.setBackground(Color.YELLOW);
        betSlider.addChangeListener(this);

        betSliderLabel.setVisible(true);

        gamePanel.setLayout(null);
        gamePanel.add(btnRaise);
        gamePanel.add(btnCall);
        gamePanel.add(btnFold);
        gamePanel.add(betSlider);
        gamePanel.add(betSliderLabel);
        gamePanel.add(tableComponent);
        gamePanel.setBackground(Color.GREEN);

        sidePanel.setLayout(null);
        sidePanel.setBackground(Color.RED);
        settingsPanel.setVisible(true);
        settingsPanel.setLayout(null);
        settingsPanel.setBounds(0,0,sidePanelW,sidePanelW/settingsNum);
        settingsPanel.setBackground(null);
        sidePanel.add(settingsPanel);
        btnSettingsDeck.setBounds(0,0,sidePanelW/settingsNum,sidePanelW/settingsNum);
        btnSettingsMusic.setBounds(sidePanelW/settingsNum,0,sidePanelW/settingsNum,sidePanelW/settingsNum);
        btnSettingsSound.setBounds(sidePanelW/settingsNum*2,0,sidePanelW/settingsNum,sidePanelW/settingsNum);
        btnSettingsFullscreen.setBounds(sidePanelW/settingsNum*3,0,sidePanelW/settingsNum,sidePanelW/settingsNum);
        btnSettingsLeave.setBounds(sidePanelW/settingsNum*4,0,sidePanelW/settingsNum,sidePanelW/settingsNum);

        try{
            btnSettingsFullscreen.setIcon(new ImageIcon(imgSettingsFullscreen));
        }catch(Exception e){}


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



        onSizeChange();
    }




    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnSettingsFullscreen)){
            try {
                Main.createWindow(!isFullscreen);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dispose();
        }
    }

    @Override
    public void run() {
        while(true){
            if(!cp.getSize().equals(lastSize)){
                onSizeChange();
                lastSize.setSize(cp.getSize());
                System.out.println("Window has been resized");
            }
            tableComponent.repaint();
            betSliderLabel.setText(betSliderLabel.getText());
            betSliderLabel.setBounds(betSliderLabel.getBounds());


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
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        if (changeEvent.getSource().equals(betSlider)){
            betSliderLabel.setBounds(betSlider.getX() + betSlider.getWidth() * betSlider.getValue() / betSlider.getMaximum(),betSlider.getY() - 10,200,15);
            if (betSlider.getValue() != betSlider.getMaximum())
                betSliderLabel.setText("$ "+betSlider.getValue());
            else
                betSliderLabel.setText("ALL IN");
        }
    }
}
