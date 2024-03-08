import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.EventObject;

public class MainProgram {

  //Thành phần GUI
  static JFrame frameTrailer;
  static JCanvas canvas1;
  static JButton EasyButton1;
  static JButton NormalButton1;
  static JButton HardButton1;
  static JButton pvPButton1;
  static JButton AboutButton1;
  static JLabel jLabelIcon1;
  static JLabel jLabelIcon2;
  static ImageIcon imageIcon1;
  static ImageIcon imageIcon2;
  static JLabel jtitleTrailer;

  static JEventQueue EventsTrailer;

  static Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  static TitledBorder titlePanel = BorderFactory.createTitledBorder(loweredetched, "Cài đặt");

  //GUI Các hằng sử dụng 

  static int marginTable = 10;
  static int sizeTable = 600;
  static int widthCanvas = sizeTable + 2 * marginTable;
  static int widthPanel = 340;
  static int widthFrame = widthCanvas + widthPanel;
  static int height = widthCanvas;
  static int widthButton = 120;
  static int heightButton = 30;

  static int MaxN = 30;
  static int Default_N = 20;

  static int N = Default_N;
  //Tạo GUI và vẽ bàn cờ trò chơi
  public static void InitGUITrailer() {
    frameTrailer = new JFrame();
    frameTrailer.setTitle("Caro Game");
    frameTrailer.setSize(widthCanvas, height);
    frameTrailer.setResizable(false);
    frameTrailer.setLocationRelativeTo(null);
    frameTrailer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    canvas1 = new JCanvas();
    canvas1.setBounds(0, 0, widthCanvas, height);
    frameTrailer.add(canvas1);

    EasyButton1 = new JButton("Đấu với máy");
    EasyButton1.setBounds(widthCanvas - 350, height - 450, widthButton, heightButton);
    canvas1.add(EasyButton1);

    pvPButton1 = new JButton("Đấu đôi (PVP)");
    pvPButton1.setBounds(widthCanvas - 350, height - 400, widthButton, heightButton);
    canvas1.add(pvPButton1);

    AboutButton1 = new JButton("Luật chơi");
    AboutButton1.setBounds(widthCanvas - 350, height - 350, widthButton, heightButton);
    canvas1.add(AboutButton1);

    imageIcon1 = scaleImage(new ImageIcon("img/1.png"), 200, 200);
    jLabelIcon1 = new JLabel();
    jLabelIcon1.setIcon(imageIcon1);
    jLabelIcon1.setBounds(20, height - 500, widthButton + 200, heightButton + 200);
    canvas1.add(jLabelIcon1);

    imageIcon2 = scaleImage(new ImageIcon("img/2.png"), 200, 200);
    jLabelIcon2 = new JLabel();
    jLabelIcon2.setIcon(imageIcon2);
    jLabelIcon2.setBounds(400, height - 500, widthButton + 200, heightButton + 200);
    canvas1.add(jLabelIcon2);

    jtitleTrailer = new JLabel("Game Cờ Caro");
    jtitleTrailer.setFont(new Font("Calibri", Font.BOLD, 40));
    jtitleTrailer.setBounds(widthCanvas - 400, 20, widthButton + 200, heightButton + 200);
    canvas1.add(jtitleTrailer);


    frameTrailer.setVisible(true);
    frameTrailer.setResizable(true);
  }

//Chọn chế độ chơi
  public static void InitEventListenerTrailer() {
    EventsTrailer = new JEventQueue();
    EventsTrailer.listenTo(EasyButton1, "EasyLevel");
    EventsTrailer.listenTo(pvPButton1, "PvPMode");
    EventsTrailer.listenTo(AboutButton1, "About");
  }

//Vẽ bàn cờ
  public static void drawTableTrailer() {
    int lengthCell = sizeTable / N;
    int x1 = marginTable;
    int x2 = marginTable + sizeTable;
    int y1 = marginTable;
    int y2 = marginTable + sizeTable;
    canvas1.setColor(Color.ORANGE);
    for (int i = 0; i <= N; i++) {
      canvas1.drawLine(x1, y1 + i * lengthCell, x2, y1 + i * lengthCell);
      canvas1.drawLine(x1 + i * lengthCell, y1, x1 + i * lengthCell, y2);
    }
  }

//Chọn màu sắc
  public static Color getColor(String s) {
    if (s.equals("Đỏ")) return Color.red;
    if (s.equals("Xanh lá")) return Color.green;
    if (s.equals("Xanh dương")) return Color.blue;
    if (s.equals("Vàng")) return Color.yellow;
    if (s.equals("Cam")) return Color.orange;
    return Color.gray;
  }

  public static void eventButtonActionTrailer(String name) {

    if (name.equals("About")) {
      JOptionPane.showMessageDialog(frameTrailer, "Hai người lần lượt đánh các quan cờ X/O trên bàn cờ," +
          "\n người dành chiến thắng là người xếp được 5 quân cờ của mình thẳng hàng nhau theo đường ngang, dọc hoặc chéo");
      return;
    }

    PlayCaro.InitGUI();
    PlayCaro.InitEventListener();

    if (name.equals("EasyLevel")) {
      PlayCaro.AILevel = 1;
    }

    if (name.equals("PvPMode")) {
      PlayCaro.playWithAI = false;
      PlayCaro.disableSetingAIButton();
      PlayCaro.player1Flag = 1;
      PlayCaro.AboutButton.setText("Đấu với máy");
      PlayCaro.GamePlayingWithPlayer();
    }

    frameTrailer.setVisible(false);

    if (PlayCaro.playWithAI) {
      PlayCaro.GamePlayingAI();
    } else {
      PlayCaro.GamePlayingWithPlayer();
    }

  }

  public static ImageIcon scaleImage(ImageIcon icon, int w, int h)
  {
    int nw = icon.getIconWidth();
    int nh = icon.getIconHeight();

    if(icon.getIconWidth() > w)
    {
      nw = w;
      nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
    }

    if(nh > h)
    {
      nh = h;
      nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
    }

    return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
  }



  //Hàm main

  public static void main(String[] args) {
    InitGUITrailer();
    drawTableTrailer();
    InitEventListenerTrailer();
    while (true) {
      EventObject AnEvent = EventsTrailer.waitEvent();
      String name = JEventQueue.getName(AnEvent);
      eventButtonActionTrailer(name);
    }
  }

}
