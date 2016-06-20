/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import Packet.Packet_Table;
import javafx.scene.chart.PieChart;

/**
 *
 * @author Sue
 */
public class MainView extends javax.swing.JFrame {

   /**
    * Creates new form MainView
    */

   /**
    * @author 정찬우
    * @serial 2016.05.30 패킷 내용 출력을 위한 Controller.MainController 변수 선언문 추가 시작버튼
    *         이벤트 리스너에 컨트롤러를 조작하는 구문 추가
    */

   private Controller.MainController mainController;

   public MainView() {
      mainController = new Controller.MainController(this);

      /* Set the Nimbus look and feel */
      // <editor-fold defaultstate="collapsed" desc=" Look and feel setting
      // code (optional) ">
      /*
       * If Nimbus (introduced in Java SE 6) is not available, stay with the
       * default look and feel. For details see
       * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
       * html
       */
      try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }

      try {
         initComponents();
      } catch (IOException e) {
         //
         e.printStackTrace();
      }
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = new Dimension(1200, 700);

      super.setTitle("1조 IDS");
      super.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      super.setVisible(true);

   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    * 
    * @throws IOException
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">
   private void initComponents() throws IOException {

	   //환경설정패널
	   TotalPanel totalPanel = new TotalPanel();
	   
      panel_logo = new javax.swing.JPanel();
      logo_lebel = new javax.swing.JLabel();
      panel_tree = new javax.swing.JPanel();
      treeLabel = new javax.swing.JLabel();
      scrollPane_iptree = new javax.swing.JScrollPane();
      tree_ip = new javax.swing.JTree();
      panel_start_stop = new javax.swing.JPanel();
      label_start_stop = new javax.swing.JLabel();
      tabbedPane_main = new javax.swing.JTabbedPane();
      panel_dashBoard = new javax.swing.JPanel();
      panel_information = new javax.swing.JPanel();
      label_information = new javax.swing.JLabel();
      label_info_startT = new javax.swing.JLabel();
      label_info_duT = new javax.swing.JLabel();
      label_info_type = new javax.swing.JLabel();
      label_info_startT_edit = new javax.swing.JLabel();
      label_info_duT_edit = new javax.swing.JLabel();
      label_info_type_edit = new javax.swing.JLabel();
      panel_graph = new javax.swing.JPanel();
      panel_analysis = new javax.swing.JPanel();
      label_analysis = new javax.swing.JLabel();
      panel_traffic = new javax.swing.JPanel();
      label_traffic = new javax.swing.JLabel();
      label_eventLog = new javax.swing.JLabel();
      scrollPane_eventLog = new javax.swing.JScrollPane();
      table_eventLog = new javax.swing.JTable();
      panel_preferences = totalPanel;

      
      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
      setResizable(false);

      panel_logo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

      logo_lebel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      logo_lebel.setIcon(new javax.swing.ImageIcon(Resource.ImageResource.mainView_title)); // NOI18N
      logo_lebel.setToolTipText("");

      javax.swing.GroupLayout panel_logoLayout = new javax.swing.GroupLayout(panel_logo);
      panel_logo.setLayout(panel_logoLayout);
      panel_logoLayout.setHorizontalGroup(
            panel_logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(logo_lebel,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
      panel_logoLayout.setVerticalGroup(
            panel_logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(logo_lebel,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

      panel_tree.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

      treeLabel.setFont(new java.awt.Font("맑은 고딕", 3, 14)); // NOI18N
      treeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      treeLabel.setText("IP Tree");

      javax.swing.GroupLayout panel_treeLayout = new javax.swing.GroupLayout(panel_tree);
      panel_tree.setLayout(panel_treeLayout);
      panel_treeLayout.setHorizontalGroup(panel_treeLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(treeLabel,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrollPane_iptree));
      panel_treeLayout
            .setVerticalGroup(panel_treeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(panel_treeLayout.createSequentialGroup()
                        .addComponent(treeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                              javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPane_iptree)));

      // 트리노드 만들기

      DefaultMutableTreeNode treeNode = setMutableTreeNode();
      tree_ip.setModel(new DefaultTreeModel(treeNode));
      scrollPane_iptree.setViewportView(tree_ip);

      // 트리 이벤트 리스너
      tree_ip.addTreeSelectionListener(new TreeSelectionListener() {
         // TODO : IP TreeNode Event Listener
         public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree_ip.getLastSelectedPathComponent();
            /* if nothing is selected */
            if (node == null)
               return;
            /* retrieve the node that was selected */
            selectedIp = node.getUserObject().toString();
            JOptionPane.showMessageDialog(null, selectedIp, "Title", JOptionPane.INFORMATION_MESSAGE, null);
            /* React to the node selection. */
         }
      });

      label_start_stop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      label_start_stop.setIcon(new javax.swing.ImageIcon(Resource.ImageResource.mainView_start)); // NOI18N
      label_start_stop.addMouseListener(new java.awt.event.MouseAdapter() {
         // TODO : start_stop event listener
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            label_start_stopMouseClicked(evt);

         }

         public void mouseEntered(java.awt.event.MouseEvent evt) {
            label_start_stopMouseEntered(evt);
         }

         private void label_start_stopMouseEntered(MouseEvent evt) {
            //
            label_start_stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         private void label_start_stopMouseClicked(MouseEvent evt) {

            if (check_start_stop) {
               check_start_stop = false;

               mainController.main(false);

               label_start_stop.setIcon(new javax.swing.ImageIcon(Resource.ImageResource.mainView_start)); // NOI18N
               tabbedPane_main.setSelectedIndex(0);
               JOptionPane.showMessageDialog(null, "종료", "Title", JOptionPane.INFORMATION_MESSAGE, null);

            } else {
               check_start_stop = true;

               mainController.main(true);

               label_start_stop.setIcon(new javax.swing.ImageIcon(Resource.ImageResource.mainView_stop)); // NOI18N
               tabbedPane_main.setSelectedIndex(0);

               //JOptionPane.showMessageDialog(null, "시작", "Title", JOptionPane.INFORMATION_MESSAGE, null);

            }

         }
      });

      javax.swing.GroupLayout panel_start_stopLayout = new javax.swing.GroupLayout(panel_start_stop);
      panel_start_stop.setLayout(panel_start_stopLayout);
      panel_start_stopLayout.setHorizontalGroup(panel_start_stopLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(label_start_stop,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
      panel_start_stopLayout.setVerticalGroup(
            panel_start_stopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                  label_start_stop, javax.swing.GroupLayout.Alignment.TRAILING,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

      panel_information.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
      panel_information.setPreferredSize(new java.awt.Dimension(411, 259));

      label_information.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      label_information.setIcon(new javax.swing.ImageIcon(Resource.ImageResource.mainView_info_title)); // NOI18N
      label_information.setText("정보");

      label_info_startT.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
      label_info_startT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      label_info_startT.setText("시작 시간 :");

      label_info_duT.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
      label_info_duT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      label_info_duT.setText("변경 시간 :");

      label_info_type.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
      label_info_type.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      label_info_type.setText("   유  형   :");

      label_info_startT_edit.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
      label_info_startT_edit.setText("00:00:00");

      label_info_duT_edit.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
      label_info_duT_edit.setText("00:00:00");

      label_info_type_edit.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
      label_info_type_edit.setText("Nomal");

      javax.swing.GroupLayout panel_informationLayout = new javax.swing.GroupLayout(panel_information);
      panel_information.setLayout(panel_informationLayout);
      panel_informationLayout.setHorizontalGroup(panel_informationLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_information, javax.swing.GroupLayout.DEFAULT_SIZE,
                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_informationLayout.createSequentialGroup().addGap(31, 31, 31)
                  .addGroup(panel_informationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_informationLayout.createSequentialGroup()
                              .addComponent(label_info_type, javax.swing.GroupLayout.PREFERRED_SIZE, 103,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addComponent(label_info_type_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 310,
                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_informationLayout.createSequentialGroup()
                              .addComponent(label_info_duT, javax.swing.GroupLayout.PREFERRED_SIZE, 103,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addComponent(label_info_duT_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 310,
                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_informationLayout.createSequentialGroup()
                              .addComponent(label_info_startT, javax.swing.GroupLayout.PREFERRED_SIZE, 103,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addComponent(label_info_startT_edit, javax.swing.GroupLayout.PREFERRED_SIZE,
                                    321, javax.swing.GroupLayout.PREFERRED_SIZE)))
                  .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
      panel_informationLayout
            .setVerticalGroup(panel_informationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(panel_informationLayout.createSequentialGroup()
                        .addComponent(label_information, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                              javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panel_informationLayout
                              .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(label_info_startT, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(label_info_startT_edit, javax.swing.GroupLayout.PREFERRED_SIZE,
                                    60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(
                              panel_informationLayout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(label_info_duT, javax.swing.GroupLayout.PREFERRED_SIZE,
                                          60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_info_duT_edit,
                                          javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                          javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel_informationLayout
                              .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                              .addComponent(label_info_type, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(label_info_type_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(25, Short.MAX_VALUE)));

      panel_graph.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
      panel_graph.setPreferredSize(new java.awt.Dimension(411, 259));

      panel_analysis.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

      // TODO : drawChart() 호출부
      chart_analysis = drawPieChart("ANALYSIS", createPieDataset(1, 1, 1));
 /*
      JFreeChart chart_analysis = drawPieChart("ANALYSIS", 
    		  createPieDataset(
    				  mainController.main_rulesetController.getInformation()/mainController.main_rulesetController.getCount()
    				  ,mainController.main_rulesetController.getNotice()/mainController.main_rulesetController.getCount()
    				  ,mainController.main_rulesetController.getWarning()/mainController.main_rulesetController.getCount()
    				  ,mainController.main_rulesetController.getCritical()/mainController.main_rulesetController.getCount()));
 */
 //     ChartPanel cp_analysis = new ChartPanel(chart_analysis);
      cp_analysis = new ChartPanel(chart_analysis);
      
      panel_analysisLayout = new javax.swing.GroupLayout(panel_analysis);
      panel_analysis.setLayout(panel_analysisLayout);
      panel_analysisLayout.setHorizontalGroup(panel_analysisLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(cp_analysis,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
      panel_analysisLayout
            .setVerticalGroup(panel_analysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(cp_analysis, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE));
      panel_traffic.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

      JFreeChart chart_traffic = drawXYChart("TRAFFIC", createXYDataset());
      ChartPanel cp_traffic = new ChartPanel(chart_traffic);

      javax.swing.GroupLayout panel_trafficLayout = new javax.swing.GroupLayout(panel_traffic);
      panel_traffic.setLayout(panel_trafficLayout);
      panel_trafficLayout.setHorizontalGroup(panel_trafficLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(cp_traffic,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
      panel_trafficLayout.setVerticalGroup(panel_trafficLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(cp_traffic,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

      javax.swing.GroupLayout panel_graphLayout = new javax.swing.GroupLayout(panel_graph);
      panel_graph.setLayout(panel_graphLayout);
      panel_graphLayout.setHorizontalGroup(panel_graphLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_traffic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                  Short.MAX_VALUE)
            .addComponent(panel_analysis, javax.swing.GroupLayout.Alignment.TRAILING,
                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
      panel_graphLayout
            .setVerticalGroup(panel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(panel_graphLayout.createSequentialGroup()
                        .addComponent(panel_analysis, javax.swing.GroupLayout.PREFERRED_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_traffic, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

      label_eventLog.setIcon(new javax.swing.ImageIcon(Resource.ImageResource.mainView_eventLog)); // NOI18N
      table_eventLog.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

      tableModel = new javax.swing.table.DefaultTableModel(null,
            new String[] { "위험도", "시간", "프로토콜", "출발지", "도착지", "이벤트" }) {
         Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
               java.lang.String.class, java.lang.String.class, java.lang.String.class };
         boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

         public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
         }

         public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
         }
      };
      
      table_eventLog.setModel(tableModel);

      // jtable 중앙정렬
      DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table_eventLog.getTableHeader()
            .getDefaultRenderer();
      renderer.setHorizontalAlignment(SwingConstants.CENTER);
      table_eventLog.getTableHeader().setDefaultRenderer(renderer);

      table_eventLog.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
      table_eventLog.setDragEnabled(true);
      DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
      celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
      scrollPane_eventLog.setViewportView(table_eventLog);
      if (table_eventLog.getColumnModel().getColumnCount() > 0) {
         table_eventLog.getColumnModel().getColumn(0).setPreferredWidth(10);
         table_eventLog.getColumnModel().getColumn(0).setCellRenderer(celAlignCenter);
         table_eventLog.getColumnModel().getColumn(1).setPreferredWidth(50);
         table_eventLog.getColumnModel().getColumn(1).setCellRenderer(celAlignCenter);
         table_eventLog.getColumnModel().getColumn(2).setPreferredWidth(10);
         table_eventLog.getColumnModel().getColumn(2).setCellRenderer(celAlignCenter);
         table_eventLog.getColumnModel().getColumn(3).setPreferredWidth(10);
         table_eventLog.getColumnModel().getColumn(3).setCellRenderer(celAlignCenter);
         table_eventLog.getColumnModel().getColumn(4).setPreferredWidth(10);
         table_eventLog.getColumnModel().getColumn(4).setCellRenderer(celAlignCenter);
         table_eventLog.getColumnModel().getColumn(5).setPreferredWidth(220);
         table_eventLog.getColumnModel().getColumn(5).setCellRenderer(celAlignCenter);
      }

      javax.swing.GroupLayout panel_dashBoardLayout = new javax.swing.GroupLayout(panel_dashBoard);
      panel_dashBoard.setLayout(panel_dashBoardLayout);
      panel_dashBoardLayout
            .setHorizontalGroup(panel_dashBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(panel_dashBoardLayout.createSequentialGroup().addGroup(panel_dashBoardLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(scrollPane_eventLog).addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                              panel_dashBoardLayout.createSequentialGroup().addGroup(panel_dashBoardLayout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(panel_information, javax.swing.GroupLayout.PREFERRED_SIZE,
                                          484, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_eventLog, javax.swing.GroupLayout.DEFAULT_SIZE,
                                          javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(panel_graph, javax.swing.GroupLayout.PREFERRED_SIZE, 485,
                                          javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)));
      panel_dashBoardLayout
            .setVerticalGroup(
                  panel_dashBoardLayout
                        .createParallelGroup(
                              javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_dashBoardLayout.createSequentialGroup().addGroup(panel_dashBoardLayout
                              .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(panel_graph, javax.swing.GroupLayout.DEFAULT_SIZE, 355,
                                    Short.MAX_VALUE)
                              .addGroup(panel_dashBoardLayout.createSequentialGroup()
                                    .addComponent(panel_information, javax.swing.GroupLayout.DEFAULT_SIZE,
                                          305, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(label_eventLog, javax.swing.GroupLayout.PREFERRED_SIZE,
                                          40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addComponent(scrollPane_eventLog, javax.swing.GroupLayout.PREFERRED_SIZE, 234,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)));
      panel_dashBoard.setName("대쉬보드");
      tabbedPane_main.addTab("대쉬보드", panel_dashBoard);

      javax.swing.GroupLayout panel_preferencesLayout = new javax.swing.GroupLayout(panel_preferences);
      panel_preferences.setLayout(panel_preferencesLayout);
      panel_preferencesLayout.setHorizontalGroup(panel_preferencesLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 976, Short.MAX_VALUE));
      panel_preferencesLayout.setVerticalGroup(panel_preferencesLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 595, Short.MAX_VALUE));
      panel_preferences.setName("환경설정");
      tabbedPane_main.addTab("환경설정", panel_preferences);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                  Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panel_tree, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel_start_stop, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(tabbedPane_main)));
      layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
            .createSequentialGroup()
            .addComponent(panel_logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                  javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_start_stop, javax.swing.GroupLayout.PREFERRED_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_tree, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addComponent(tabbedPane_main))));

      pack();

   }// </editor-fold>

   public PieDataset createPieDataset(double notice, double warning, double critical) {
      // TODO : Create Pie Dataset
      DefaultPieDataset dataset = new DefaultPieDataset();

      dataset.setValue("Critical", critical);
      dataset.setValue("Notice", notice);
      dataset.setValue("Warning", warning);

      return dataset;
   }

   public XYDataset createXYDataset() {
      // TODO : create XYDataset 무슨 데이터를 받아야할지 모르겠음

      Scanner scan = null;
      TimeSeries series = new TimeSeries("TrafficData");
      try {

         scan = new Scanner(new File("TrafficSample.txt"));
      } catch (FileNotFoundException ee) {
      }

      Minute min = new Minute();
      Day day = new Day();
      for (int i = 0; i < 1000; i++) {
         // series.add(day, Integer.parseInt(scan.next()));
         series.add(min, Integer.parseInt(scan.next()));
         min = (Minute) min.next();
         // day = (Day) day.next();
      }

      TimeSeriesCollection dataset = new TimeSeriesCollection(series);

      return dataset;
   }

   public JFreeChart drawPieChart(String title, PieDataset pieDataset) {
      // TODO : Draw PieChart
      JFreeChart chart = ChartFactory.createPieChart3D(title, pieDataset, true, true, false);

      PiePlot plot = (PiePlot) chart.getPlot();

      plot.setSectionPaint("Informaion", new Color(30, 144, 255));
      plot.setSectionPaint("Warning", new Color(50, 205, 50));
      plot.setSectionPaint("Notice", new Color(255, 127, 80));
      plot.setSectionPaint("critical", new Color(0xff, 0, 0));

      return chart;
   }

   public JFreeChart drawXYChart(String title, XYDataset dataset) {
      // TODO : draw XYChart
      JFreeChart chart = ChartFactory.createXYAreaChart(title, null, null, dataset, PlotOrientation.VERTICAL, false, // legend
            true, // tool tips
            false // URLs
      );
      XYPlot plot = chart.getXYPlot();

      ValueAxis domainAxis = new PeriodAxis("");
      plot.setDomainAxis(domainAxis);

      plot.setRangeGridlinePaint(new Color(0xff, 0x00, 0x00));
      plot.setDomainGridlinePaint(new Color(0xff, 0x00, 0x00));
      plot.getRenderer().setSeriesPaint(0, new Color(0x00, 0x00, 0xff));
      chart.removeLegend();

      return chart;
   }

   public void setInformation(String startTime, String durationTime, String type) {
      // TODO : setInformation

      label_info_startT_edit.setText(startTime);
      label_info_duT_edit.setText(durationTime);
      label_info_type_edit.setText(type);

   }

   public String getSelectedIp() {
      return selectedIp;
   }

   // TODO : setMutableTreeNode
   private DefaultMutableTreeNode setMutableTreeNode() {
      // TODO: set DefaultMutableTreeNode

      rootNode = new DefaultMutableTreeNode("All");

      //rootNode.add(new DefaultMutableTreeNode("All"));
      treeModel = new DefaultTreeModel(rootNode);
      tree_ip.setModel(treeModel);
      scrollPane_iptree.setViewportView(tree_ip);

      return rootNode;
      /*
       * treeNode1.add(treeNode2); treeNode2 = new
       * DefaultMutableTreeNode("168.10.0.1"); treeNode1.add(treeNode2);
       * 
       * DefaultMutableTreeNode treeNode = setMutableTreeNode();
       * 
       * tree_ip.setModel(new DefaultTreeModel(treeNode1));
       * scrollPane_iptree.setViewportView(tree_ip);
       */

   }

   // TODO : setTableObject
   public Object[][] setTableObject(ArrayList<Packet_Table> capturedPacket) {

      /*
       * Integer[][] objTest = new Integer[][] { { 1, 2, 3, 4, 5, 6 }, { 7, 8,
       * 9, 10, 11, 12 }, { 1, 2, 3, 4, 5, 6 }, { 7, 8, 9, 10, 11, 12 }, { 1,
       * 2, 3, 4, 5, 6 }, { 7, 8, 9, 10, 11, 12 }, { 1, 2, 3, 4, 5, 6 }, { 7,
       * 8, 9, 10, 11, 12 }, { 1, 2, 3, 4, 5, 6 }, { 7, 8, 9, 10, 11, 12 }, {
       * 7, 8, 9, 10, 11, 12 }, { 7, 8, 9, 10, 11, 12 }, { 7, 8, 9, 10, 11, 12
       * }, { 7, 8, 9, 10, 11, 12 }, { 7, 8, 9, 10, 11, 12 }, { 7, 8, 9, 10,
       * 11, 12 }, { 7, 8, 9, 10, 11, 12 },
       * 
       * };
       */

      String[][] tableObject = new String[capturedPacket.size()][6];

      for (int i = 0; i < capturedPacket.size(); i++) {
         // 0, 1, 2, 3, 4, 5, 6
         // 위험도, 시간, 프로토콜, 출발지, 도착지, 이벤트

         String tempPacket[] = { "1", "2", "3", "4", "5"
               // capturedPacket.get(i).getPacketResource("protocol_name"),
               // capturedPacket.get(i).getPacketResource("arrival_time"),
               // capturedPacket.get(i).getPacketResource("protocol_name"),
               // capturedPacket.get(i).getPacketResource("src_ip"),
               // capturedPacket.get(i).getPacketResource("dst_ip"),
               // capturedPacket.get(i).getPacketResource("protocol_name"),
         };
         tableObject[i] = tempPacket;
      }

      return (Object[][]) tableObject;
   }


   
   // TODO : 정적 변수 할당하는 곳
   static boolean check_start_stop = false;
   static String selectedIp = "";

   // Variables declaration - do not modify
   public javax.swing.JLabel label_analysis;
   public javax.swing.JLabel label_eventLog;
   public javax.swing.JLabel label_info_duT;
   public javax.swing.JLabel label_info_duT_edit;
   public javax.swing.JLabel label_info_startT;
   public javax.swing.JLabel label_info_startT_edit;
   public javax.swing.JLabel label_info_type;
   public javax.swing.JLabel label_info_type_edit;
   public javax.swing.JLabel label_information;
   public javax.swing.JLabel label_start_stop;
   public javax.swing.JLabel label_traffic;
   public javax.swing.JLabel logo_lebel;
   public javax.swing.JPanel panel_analysis;
   public javax.swing.JPanel panel_dashBoard;
   public javax.swing.JPanel panel_graph;
   public javax.swing.JPanel panel_information;
   public javax.swing.JPanel panel_logo;
   public javax.swing.JPanel panel_preferences;
   public javax.swing.JPanel panel_start_stop;
   public javax.swing.JPanel panel_traffic;
   public javax.swing.JPanel panel_tree;
   public javax.swing.JScrollPane scrollPane_eventLog;
   public javax.swing.JScrollPane scrollPane_iptree;
   public javax.swing.JTabbedPane tabbedPane_main;
   public javax.swing.JTable table_eventLog;
   public javax.swing.JLabel treeLabel;
   public javax.swing.JTree tree_ip;
   public javax.swing.table.DefaultTableModel tableModel;
   public DefaultMutableTreeNode rootNode;
   public DefaultTreeModel treeModel;
   public ChartPanel cp_analysis;
public JFreeChart chart_analysis;
public GroupLayout panel_analysisLayout;

   // End of variables declaration
}