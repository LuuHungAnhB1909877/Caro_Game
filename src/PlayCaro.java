import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.EventObject;

public class PlayCaro {

//Thành phần GUI 

  static JFrame frame;
  static JPanel panel;
  static JPanel lPanel;
  static JCanvas canvas;
  static JButton NewGameButton;
  static JButton UndoButton;
  static JButton AboutButton;
  static JLabel LevelLabel;
  static JLabel ColorXLabel;
  static JLabel ColorOLabel;
  static JLabel whoFirstLabel;
  static JLabel RepresentLabel;
  static JLabel TableSizeLabel;
  static JComboBox LevelBox;
  static JComboBox ColorXBox;
  static JComboBox ColorOBox;
  static JComboBox whoFirstBox;
  static JComboBox RepresentBox;
  static JComboBox TableSizeBox;
  static JTextField ScoreText;

  static JEventQueue Events;

  static Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  static TitledBorder titlePanel = BorderFactory.createTitledBorder(loweredetched, "Cài đặt");

//GUI Các hằng sử dụng

  static int thicknessX = 4;
  static int thicknessO = 4;
  static int marginTableCell = 3;
  static int marginTable = 10;
  static int sizeTable = 600;
  static int widthCanvas = sizeTable + 2 * marginTable;
  static int widthPanel = 340;
  static int widthFrame = widthCanvas + widthPanel * 2 ;
  static int height = widthCanvas;
  static int widthButton = 120;
  static int heightButton = 30;
  static int marginButton = 50;
  static int whoFirstBox_width = 200;
  static int ScoreText_width = 250;

  static Color Default_colorX = Color.orange;
  static Color Default_colorO = Color.green;
  static Color Default_colorTable = Color.GRAY;
  static Color colorX = Default_colorX;
  static Color colorO = Default_colorO;
  static Color colorTable = Default_colorTable;

  static String[] LevelData = {"Dễ", "Trung Bình", "Khó"};
  static String[] ColorSelectionData = {"Mặc định", "Đỏ", "Xanh lá", "Xanh dương", "Vàng", "Cam", "Xám"};
  static String[] whoFirstData = {"Người chơi đi trước", "Máy đi trước"};
  static String[] RepresentData = {"Chơi với X", "Chơi với O"};
  static String[] TableSizeData = {"Mặc định", "10 x 10", "15 x 15", "20 x 20", "25 x 25", "30 x 30"};

  static int MaxN = 30;
  static int Default_N = 20;
  static int whoFirst = -1;
  static boolean UserX = true;
  static boolean playWithAI = true;
  static int AILevel = 1;
  static int LengthWin;

  static int nUserWin = 0;
  static int nComputerWin = 0;

  static int player1Flag = 0;
  static int player2Flag = 0;
  static int player1Win = 0;
  static int player2Win = 0;

  //Mô tả bàn chơi

  static int N = Default_N;
  static int nSteps;
  static int[] x = new int[MaxN * MaxN];
  static int[] y = new int[MaxN * MaxN];
  static boolean[][] used = new boolean[MaxN][MaxN];

  public static void InitGUI() {
    System.out.println("Software: Caro");
    System.out.println("Tác giả: Lưu Hùng Anh ");
    System.out.println("Khóa: 45 ");
    System.out.println("Trường: ĐH Cần Thơ ");
    System.out.println("Email: anhb1909877@student.ctu.edu.vn ");

    frame = new JFrame();
    frame.setTitle("Caro Game");
    frame.setSize(widthFrame, height);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    lPanel = new JPanel();
    lPanel.setBounds(0, 0, widthPanel, height);
    lPanel.setLayout(null);
    frame.add(lPanel);

    int ButtonPositionX = (widthPanel - widthButton) / 2;
    NewGameButton = new JButton("Game Mới");
    NewGameButton.setBounds(ButtonPositionX, marginButton, widthButton, heightButton);
    lPanel.add(NewGameButton);

    UndoButton = new JButton("Đi lại");
    UndoButton.setBounds(ButtonPositionX, 2 * marginButton, widthButton, heightButton);
    lPanel.add(UndoButton);

    AboutButton = new JButton("Đấu đôi (PvP)");
    AboutButton.setBounds(ButtonPositionX, 3 * marginButton, widthButton, heightButton);
    lPanel.add(AboutButton);


    canvas = new JCanvas();
    canvas.setBounds(widthPanel, 0, widthCanvas, height);
    frame.add(canvas);

    panel = new JPanel();
    panel.setBounds(widthCanvas, 0, widthPanel, height);
    titlePanel.setTitleJustification(TitledBorder.RIGHT);
    panel.setBorder(titlePanel);
    panel.setLayout(null);
    frame.add(panel);

    int LabelPositionX = widthPanel + widthCanvas + marginButton;
    int BoxPositionX = widthPanel + widthCanvas + (widthPanel - whoFirstBox_width) / 2;

    LevelLabel = new JLabel("Cấp độ");
    LevelLabel.setBounds(LabelPositionX,  marginButton, widthButton, heightButton);
    LevelLabel.setOpaque(true);
    panel.add(LevelLabel);

    LevelBox = new JComboBox(LevelData);
    LevelBox.setBounds(LabelPositionX + widthButton,  marginButton, widthButton, heightButton);
    panel.add(LevelBox);

    TableSizeLabel = new JLabel("Kích thước bàn:");
    TableSizeLabel.setBounds(LabelPositionX, 2 * marginButton, widthButton, heightButton);
    TableSizeLabel.setOpaque(true);
    panel.add(TableSizeLabel);

    TableSizeBox = new JComboBox(TableSizeData);
    TableSizeBox.setBounds(LabelPositionX + widthButton, 2 * marginButton, widthButton, heightButton);
    panel.add(TableSizeBox);

    whoFirstLabel = new JLabel("Người hoặc máy đi trước:");
    whoFirstLabel.setBounds(LabelPositionX, 3 * marginButton, 2 * widthButton, heightButton);
    whoFirstLabel.setOpaque(true);
    panel.add(whoFirstLabel);

    whoFirstBox = new JComboBox(whoFirstData);
    whoFirstBox.setBounds(BoxPositionX, 3 * marginButton, whoFirstBox_width, heightButton);
    panel.add(whoFirstBox);

    RepresentLabel = new JLabel("Người chơi:");
    RepresentLabel.setBounds(LabelPositionX, 4 * marginButton, widthButton, heightButton);
    RepresentLabel.setOpaque(true);
    panel.add(RepresentLabel);

    RepresentBox = new JComboBox(RepresentData);
    RepresentBox.setBounds(LabelPositionX + widthButton, 4 * marginButton, widthButton, heightButton);
    panel.add(RepresentBox);

    ColorXLabel = new JLabel("Màu của X:");
    ColorXLabel.setBounds(LabelPositionX, 5 * marginButton, widthButton, heightButton);
    ColorXLabel.setOpaque(true);
    panel.add(ColorXLabel);

    ColorXBox = new JComboBox(ColorSelectionData);
    ColorXBox.setBounds(LabelPositionX + widthButton, 5 * marginButton, widthButton, heightButton);
    panel.add(ColorXBox);

    ColorOLabel = new JLabel("Màu của Y:");
    ColorOLabel.setBounds(LabelPositionX, 6 * marginButton, widthButton, heightButton);
    ColorOLabel.setOpaque(true);
    panel.add(ColorOLabel);

    ColorOBox = new JComboBox(ColorSelectionData);
    ColorOBox.setBounds(LabelPositionX + widthButton, 6 * marginButton, widthButton, heightButton);
    panel.add(ColorOBox);

    int TextPositionX = widthCanvas + widthPanel + (widthPanel - ScoreText_width) / 2;
    ScoreText = new JTextField();
    ScoreText.setBounds(TextPositionX, 7 * marginButton, ScoreText_width, heightButton);
    ScoreText.setEditable(false);
    ScoreText.setHorizontalAlignment(JTextField.CENTER);
    ScoreText.setText("Người chơi: 0 - 0 :Máy");
    panel.add(ScoreText);

    frame.setVisible(true);
    frame.setResizable(true);
  }

  public static void InitEventListener() {
    Events = new JEventQueue();
    Events.listenTo(canvas, "canvas");
    Events.listenTo(NewGameButton, "NewGame");
    Events.listenTo(UndoButton, "Undo");
    Events.listenTo(AboutButton, "PvP");
    Events.listenTo(LevelBox, "Level");
    Events.listenTo(TableSizeBox, "TableSize");
    Events.listenTo(whoFirstBox, "whoFirst");
    Events.listenTo(RepresentBox, "Represent");
    Events.listenTo(ColorXBox, "ColorX");
    Events.listenTo(ColorOBox, "ColorO");
  }

  //Xóa bàn cờ trò chơi
  public static void clearTable() {
    canvas.setBackground(panel.getBackground());
  }

  //Vẽ bàn cờ trò chơi
  public static void drawTable() {
    int lengthCell = sizeTable / N;
    int x1 = marginTable;
    int x2 = marginTable + sizeTable;
    int y1 = marginTable;
    int y2 = marginTable + sizeTable;
    canvas.setColor(colorTable);
    for (int i = 0; i <= N; i++) {
      canvas.drawLine(x1, y1 + i * lengthCell, x2, y1 + i * lengthCell);
      canvas.drawLine(x1 + i * lengthCell, y1, x1 + i * lengthCell, y2);
    }
  }

  //Vẽ một X trên bàn cờ ở một tọa độ được chỉ định
  public static void drawX(int tableX, int tableY) {
    int lengthCell = sizeTable / N;
    int x1 = marginTable + tableX * lengthCell;
    int y1 = marginTable + tableY * lengthCell;
    int x2 = x1 + lengthCell;
    int y2 = y1 + lengthCell;
    x1 += marginTableCell;
    y1 += marginTableCell;
    x2 -= marginTableCell;
    y2 -= marginTableCell;
    canvas.setColor(colorX);
    for (int i = 0; i <= thicknessX; i++) {
      canvas.drawLine(x1, y1 + i, x2 - i, y2);
      canvas.drawLine(x1 + i, y1, x2, y2 - i);
      canvas.drawLine(x1, y2 - i, x2 - i, y1);
      canvas.drawLine(x1 + i, y2, x2, y1 + i);
    }
  }

  //Vẽ một O trên bàn cờ ở một tọa độ được chỉ định
  public static void drawO(int tableX, int tableY) {
    int lengthCell = sizeTable / N;
    int x = marginTable + tableX * lengthCell + marginTableCell;
    int y = marginTable + tableY * lengthCell + marginTableCell;
    int diameter = lengthCell - 2 * marginTableCell;
    canvas.setColor(colorO);
    for (int i = 0; i <= thicknessO; i++)
      canvas.drawOval(x + i, y + i, diameter - 2 * i, diameter - 2 * i);
  }

  //vẽ lại tất cả các X trên bàn cờ
  public static void reDrawX() {
    for (int i = 0; i < nSteps; i++)
      if (whoFirst == 1) {
        if ((i % 2 == 0) && (!UserX)) drawX(x[i], y[i]);
        if ((i % 2 == 1) && (UserX)) drawX(x[i], y[i]);
      } else {
        if ((i % 2 == 0) && (UserX)) drawX(x[i], y[i]);
        if ((i % 2 == 1) && (!UserX)) drawX(x[i], y[i]);
      }
  }

  public static void reDrawO() {
    boolean UserO = false;
    if (!UserX) UserO = true;

    for (int i = 0; i < nSteps; i++)
      if (whoFirst == 1) {
        if ((i % 2 == 0) && (!UserO)) drawO(x[i], y[i]);
        if ((i % 2 == 1) && (UserO)) drawO(x[i], y[i]);
      } else {
        if ((i % 2 == 0) && (UserO)) drawO(x[i], y[i]);
        if ((i % 2 == 1) && (!UserO)) drawO(x[i], y[i]);
      }
  }

  public static void clearCell(int tableX, int tableY) {
    int lengthCell = sizeTable / N;
    int x1 = marginTable + tableX * lengthCell;
    int y1 = marginTable + tableY * lengthCell;
    int length = lengthCell - 2;
    canvas.setColor(panel.getBackground());
    canvas.fillRect(x1 + 1, y1 + 1, length, length);
  }

  public static void reDrawXO() {
    for (int i = 0; i < nSteps; i++)
      clearCell(x[i], y[i]);
    reDrawX();
    reDrawO();
  }

  public static void UpdateMove(int nextMoveX, int nextMoveY) {
    used[nextMoveX][nextMoveY] = true;
    x[nSteps] = nextMoveX;
    y[nSteps] = nextMoveY;
    nSteps++;
    if (UserX) drawO(nextMoveX, nextMoveY);
    else
      drawX(nextMoveX, nextMoveY);
  }

  public static Color getColor(String s) {
    if (s.equals("Đỏ")) return Color.red;
    if (s.equals("Xanh lá")) return Color.green;
    if (s.equals("Xanh dương")) return Color.blue;
    if (s.equals("Vàng")) return Color.yellow;
    if (s.equals("Cam")) return Color.orange;
    return Color.gray;
  }

  public static void change_colorX(int index) {
    String color = ColorSelectionData[index];
    if (color.equals("Mặc định"))
      colorX = Default_colorX;
    else
      colorX = getColor(color);
    reDrawX();
  }

  public static void change_colorO(int index) {
    String color = ColorSelectionData[index];
    if (color.equals("Mặc định"))
      colorO = Default_colorO;
    else
      colorO = getColor(color);
    reDrawO();
  }

  public static int getTableSize(int index) {
    if (index == 0) return Default_N;
    if (index == 1) return 10;
    if (index == 2) return 15;
    if (index == 3) return 20;
    if (index == 4) return 25;
    return 30;
  }

  public static void DeleteMove(int MoveX, int MoveY) {
    used[MoveX][MoveY] = false;
    clearCell(MoveX, MoveY);
    nSteps--;
  }

  public static void UndoMove() {
    if (playWithAI) {
      if (nSteps == 0) return;
      if (nSteps == 1) {
        JOptionPane.showMessageDialog(frame, "Bạn không thể đi lại!", "Chú ý", JOptionPane.ERROR_MESSAGE);
        return;
      }
      DeleteMove(x[nSteps - 1], y[nSteps - 1]);
    }
    DeleteMove(x[nSteps - 1], y[nSteps - 1]);

  }

  public static boolean CheckFinalState() {
    int result = ArtificialIntelligence.CheckWinner(N, nSteps, x, y, whoFirst, LengthWin);
    if (result != 0) {
      if (playWithAI) {
        if (result == 1) {
          JOptionPane.showMessageDialog(frame, "Máy tính đã thắng bạn!");
          nComputerWin++;
        } else {
          JOptionPane.showMessageDialog(frame, "Bạn đã thắng!");
          nUserWin++;
        }
      } else {
        if (result == 1) {
          JOptionPane.showMessageDialog(frame, "Người chơi 2 đã dành chiến thằng");
          player2Win++;
        } else {
          JOptionPane.showMessageDialog(frame, "Người chơi 1 đã dành chiến thằng");
          player1Win++;
        }
      }

      if (playWithAI) {
        ScoreText.setText("Người chơi: " + nUserWin + " - " + nComputerWin + " :Máy");
      } else {
        ScoreText.setText("Người chơi 1: " + player1Win + " - " + player2Win + " :Người chơi 2");
      }

      return true;
    }
    if (nSteps == N * N) {
      JOptionPane.showMessageDialog(frame, "Trận này hòa do hết bàn cờ!");
      return true;
    }
    return false;
  }





  //CHƠI VỚI AI
  public static void GamePlayingAI() {
    int nextMoveX, nextMoveY;

    clearTable();
    drawTable();

    nSteps = 0;
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        used[i][j] = false;

    LengthWin = Math.min(5, N);

    if (whoFirst == 1) {
      ArtificialIntelligence.findNextMove(N, nSteps, x, y, whoFirst, AILevel);
      nextMoveX = ArtificialIntelligence.getNextMoveX();
      nextMoveY = ArtificialIntelligence.getNextMoveY();
      UpdateMove(nextMoveX, nextMoveY);
    }

    while (true) {
      EventObject AnEvent = Events.waitEvent();

      if (JEventQueue.isMouseEvent(AnEvent))
        if (JEventQueue.isMousePressed(AnEvent)) {
          int MouseX = JEventQueue.getMouseX(AnEvent);
          int MouseY = JEventQueue.getMouseY(AnEvent);
          if ((MouseX > marginTable) && (MouseX < marginTable + sizeTable))
            if ((MouseY > marginTable) && (MouseY < marginTable + sizeTable)) {
              int lengthCell = sizeTable / N;
              int TableX = (MouseX - marginTable) / lengthCell;
              int TableY = (MouseY - marginTable) / lengthCell;

              if (!used[TableX][TableY]) {
                used[TableX][TableY] = true;
                x[nSteps] = TableX;
                y[nSteps] = TableY;
                nSteps++;
                if (UserX) drawX(TableX, TableY);
                else
                  drawO(TableX, TableY);

                if (CheckFinalState()) {
                  GamePlayingAI();
                  return;
                }

                ArtificialIntelligence.findNextMove(N, nSteps, x, y, whoFirst, AILevel);
                nextMoveX = ArtificialIntelligence.getNextMoveX();
                nextMoveY = ArtificialIntelligence.getNextMoveY();
                UpdateMove(nextMoveX, nextMoveY);

                if (CheckFinalState()) {
                  GamePlayingAI();
                  return;
                }
              }
            }
        }

      String name = JEventQueue.getName(AnEvent);
      eventButtonAction(name);
    }
  }




  //CHƠI VỚI NGƯỜI
  public static void GamePlayingWithPlayer() {
    clearTable();
    drawTable();

    nSteps = 0;
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        used[i][j] = false;

    LengthWin = Math.min(5, N);

    while (true) {
      EventObject AnEvent = Events.waitEvent();

      if (JEventQueue.isMouseEvent(AnEvent))
        if (JEventQueue.isMousePressed(AnEvent) && player1Flag == 1 ) {
          int MouseX = JEventQueue.getMouseX(AnEvent);
          int MouseY = JEventQueue.getMouseY(AnEvent);
          if ((MouseX > marginTable) && (MouseX < marginTable + sizeTable))
            if ((MouseY > marginTable) && (MouseY < marginTable + sizeTable)) {
              int lengthCell = sizeTable / N;
              int TableX = (MouseX - marginTable) / lengthCell;
              int TableY = (MouseY - marginTable) / lengthCell;

              if (!used[TableX][TableY]) {
                used[TableX][TableY] = true;
                x[nSteps] = TableX;
                y[nSteps] = TableY;
                nSteps++;
                drawX(TableX, TableY);

                player1Flag = 0;
                player2Flag = 1;

                if (CheckFinalState()) {
                  GamePlayingWithPlayer();
                  return;
                }

              }
            }
        }

      if (JEventQueue.isMousePressed(AnEvent) && player2Flag == 1 ) {
        int MouseX = JEventQueue.getMouseX(AnEvent);
        int MouseY = JEventQueue.getMouseY(AnEvent);
        if ((MouseX > marginTable) && (MouseX < marginTable + sizeTable))
          if ((MouseY > marginTable) && (MouseY < marginTable + sizeTable)) {
            int lengthCell = sizeTable / N;
            int TableX = (MouseX - marginTable) / lengthCell;
            int TableY = (MouseY - marginTable) / lengthCell;

            if (!used[TableX][TableY]) {
              used[TableX][TableY] = true;
              x[nSteps] = TableX;
              y[nSteps] = TableY;
              nSteps++;
              drawO(TableX, TableY);

              player2Flag = 0;
              player1Flag = 1;
              if (CheckFinalState()) {
                GamePlayingWithPlayer();
                return;
              }

            }
          }
      }

      String name = JEventQueue.getName(AnEvent);
      eventButtonAction(name);
    }
  }
//

  
  public static void eventButtonAction(String name) {
    if (name.equals("NewGame")) {
      if (playWithAI) {
        GamePlayingAI();
      } else {
        GamePlayingWithPlayer();
      }
      return;
    }

    if (name.equals("Undo")) {
      UndoMove();
      return;
    }

    if (name.equals("PvP")) {
      playWithAI = !playWithAI;
      if (!playWithAI) {
        disableSetingAIButton();
        player1Flag = 1;
        AboutButton.setText("Đấu với máy");
        GamePlayingWithPlayer();
      } else {
        showSetingAIButton();
        player1Flag = 0;
        player2Flag = 0;
        AboutButton.setText("Đấu đôi (PvP)");
        GamePlayingAI();
      }
      return;
    }

    if (name.equals("Level")) {
      AILevel = LevelBox.getSelectedIndex() + 1;
      if (playWithAI) {
        GamePlayingAI();
      } else {
        GamePlayingWithPlayer();
      }
      return;
    }

    if (name.equals("ColorX")) {
      change_colorX(ColorXBox.getSelectedIndex());
      return;
    }

    if (name.equals("ColorO")) {
      change_colorO(ColorOBox.getSelectedIndex());
      return;
    }

    if (name.equals("whoFirst")) {
      if (whoFirstBox.getSelectedIndex() == 0) {
        if (whoFirst == 1) {
          whoFirst = -1;
          if (playWithAI) {
            GamePlayingAI();
          } else {
            GamePlayingWithPlayer();
          }
          return;
        }
      } else if (whoFirst == -1) {
        whoFirst = 1;
        if (playWithAI) {
          GamePlayingAI();
        } else {
          GamePlayingWithPlayer();
        }
        return;
      }
    }

    if (name.equals("Represent")) {
      int index = RepresentBox.getSelectedIndex();
      if (index == 0) {
        if (!UserX) {
          UserX = true;
          reDrawXO();
        }
      } else if (UserX) {
        UserX = false;
        reDrawXO();
      }
      return;
    }

    if (name.equals("TableSize")) {
      int n = getTableSize(TableSizeBox.getSelectedIndex());
      if (n != N) {
        N = n;
        if (playWithAI) {
          GamePlayingAI();
        } else {
          GamePlayingWithPlayer();
        }
      }
    }
  }

  public static void disableSetingAIButton() {
    LevelBox.setVisible(false);
    whoFirstBox.setVisible(false);
    RepresentBox.setVisible(false);

    LevelLabel.setVisible(false);
    whoFirstLabel.setVisible(false);
    RepresentLabel.setVisible(false);

    whoFirst = -1;
    player1Win = 0;
    player2Win = 0;
    ScoreText.setText("Người chơi 1: 0 - 0 :Người chơi 2");
  }

  public static void showSetingAIButton() {
    LevelBox.setVisible(true);
    whoFirstBox.setVisible(true);
    RepresentBox.setVisible(true);

    LevelLabel.setVisible(true);
    whoFirstLabel.setVisible(true);
    RepresentLabel.setVisible(true);


    nUserWin = 0;
    nComputerWin = 0;
    ScoreText.setText("Người chơi: 0 - 0 :Máy");
  }

}
