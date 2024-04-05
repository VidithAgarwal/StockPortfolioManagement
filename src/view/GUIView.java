package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import controller.Features;
import controller.StockControllerImplGUI;

public class GUIView extends JFrame implements IViewGUI {

  private JTextArea outputArea;
  private JFrame mainFrame;
  private JButton createPortfolioButton;
  private JButton loadPortfolioButton;
  private JButton stockStatisticsButton;

  private JButton gainOrLoseButton;
  private JButton gainOrLoseOverPeriodButton;
  private JButton xdaymovingavgButton;
  private JButton crossoverperiodButton;
  private JButton movingcrossoverperiodButton;
  private JPanel additionalButtonPanel;

  private JButton createButton(String s) {
    JButton button = new JButton(s);
    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    button.setFont(new Font("Arial", Font.BOLD, 16));
    button.setPreferredSize(new Dimension(500, 50));
    return button;
  }

  private JTextField createTextField(int columns) {
    JTextField textField = new JTextField(columns);
    textField.setFont(new Font("Arial", Font.BOLD, 14));

    textField.setForeground(Color.BLACK);

    textField.setBackground(Color.LIGHT_GRAY);

    textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    return textField;
  }

  private JLabel createLabel(String s) {
    JLabel label = new JLabel(s);
    label.setFont(new Font("Arial", Font.BOLD, 16));
    return label;
  }



  public GUIView() {
    mainFrame = new JFrame();
    mainFrame.setTitle("Investment Application");
    mainFrame.setSize(600, 400); // increased width for the image
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLayout(new BorderLayout());
    mainFrame.setLocationRelativeTo(null);
//    // Image label
//    ImageIcon icon = new ImageIcon("ass6.png");
//    JLabel imageLabel = new JLabel(icon);
//    add(imageLabel, BorderLayout.WEST);
//
//    // Button panel
//    JPanel buttonPanel = new JPanel();
//    buttonPanel.setLayout(new GridLayout(5, 1));
//
//    createPortfolioButton = new JButton("Create Portfolio");
//    createPortfolioButton.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        // Create and display a custom dialog for entering the portfolio name
//        JTextField portfolioNameField = new JTextField();
//        JPanel dialogPanel = new JPanel(new GridLayout(2, 1));
//        dialogPanel.add(new JLabel("Enter Portfolio Name:"));
//        dialogPanel.add(portfolioNameField);
//
//        JLabel statusLabel = new JLabel(""); // JLabel to display status message
//        dialogPanel.add(statusLabel);
//
//        int result = JOptionPane.showConfirmDialog(null, dialogPanel,
//                "Create Portfolio", JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//          String portfolioName = portfolioNameField.getText();
//              // Call the method to create flexible portfolio
//              stockGUI.createFlexiblePortfolioV(portfolioName);
//
//          if (stockGUI.getErrorMessage() != null) {
//            statusLabel.setText("<html><font color='red'>" + stockGUI.getErrorMessage() + "</font></html>");
//          } else {
//            statusLabel.setText("<html><font color='green'>" + stockGUI.getSuccessMessage() + "</font></html>");
//          }
//
//
//        }
//      }
//    });
//    buttonPanel.add(createPortfolioButton);
//
//    loadPortfolioButton = new JButton("Load Portfolio");
//    buttonPanel.add(loadPortfolioButton);
//
//    stockStatisticsButton = new JButton("Stock Statistics");
//    stockStatisticsButton.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        showAdditionalButtons();
//      }
//    });
//    buttonPanel.add(stockStatisticsButton);
//
//    add(buttonPanel, BorderLayout.NORTH);
  }

  @Override
  public void addFeatures(Features features) {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.NORTH;



    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0; // Expand horizontally
    gbc.weighty = 1.0; // Expand vertically

    JButton portfolioButton = createButton("Create a flexible portfolio");
    portfolioButton.addActionListener(evt -> createPortfolio(features));

    JButton loadButton = createButton("Load a flexible portfolio");
    loadButton.addActionListener(evt -> loadPortfolio(features));

    JButton stockAnalysisButton = createButton("Analyse a stock");
    stockAnalysisButton.addActionListener(evt -> stockAnalysis(features));


    panel.add(portfolioButton, gbc);
    gbc.gridy = 1;
    panel.add(loadButton, gbc);
    gbc.gridy = 2;
    panel.add(stockAnalysisButton, gbc);



    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);

  }

  private void stockAnalysis(Features features) {
  }

  private void loadPortfolio(Features features) {
  }

  private void createPortfolio(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel heading = createLabel("Create A new Flexible Portfolio");
    heading.setFont(new Font("Arial", Font.BOLD, 24));
    panel.add(heading, gbc);



    JLabel label = createLabel("Enter the name of the portfolio");
    JTextField textBox = createTextField(15);
    label.setLabelFor(textBox);
    JButton submitButton = createButton("Submit");
    submitButton.setPreferredSize(new Dimension(200, 35));
    submitButton.addActionListener(evt -> {
            String inputText = textBox.getText();
            if (inputText != null) {
              features.createFlexiblePortfolio(inputText);
              if (features.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(panel, "Portfolio with this name already exists",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                textBox.setText("");
                textBox.requestFocus();
              } else {
                JOptionPane.showMessageDialog(panel, "Portfolio created successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                showSecondMenu(features);
              }
            }
    });

    panel.add(label, gbc);
    panel.add(textBox, gbc);
    panel.add(submitButton, gbc);

    // Add the panel to the frame
    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  private void showSecondMenu(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    JButton button = createButton("Create Portfolio");
    panel.add(button);
    button.addActionListener(evt -> createPortfolio(features));
    mainFrame.add(panel);

    revalidate();
    repaint();
    mainFrame.setVisible(true);
  }

  private void showAdditionalButtons() {
    if (additionalButtonPanel == null) {
      additionalButtonPanel = new JPanel();
      additionalButtonPanel.setLayout(new GridLayout(5, 1));

      gainOrLoseButton = new JButton("Gain or Lose");
      gainOrLoseButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Handle Gain or Lose button clickSa@090701
        }
      });
      additionalButtonPanel.add(gainOrLoseButton);

      gainOrLoseOverPeriodButton = new JButton("Gain or Lose Over Period");
      gainOrLoseOverPeriodButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Handle Gain or Lose Over Period button click
        }
      });
      additionalButtonPanel.add(gainOrLoseOverPeriodButton);

      xdaymovingavgButton = new JButton("X-Day Moving Average");
      xdaymovingavgButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Handle X-Day Moving Average button click
        }
      });
      additionalButtonPanel.add(xdaymovingavgButton);

      crossoverperiodButton = new JButton("Crossover Period");
      crossoverperiodButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Handle Crossover Period button click
        }
      });
      additionalButtonPanel.add(crossoverperiodButton);

      movingcrossoverperiodButton = new JButton("Moving Crossover Period");
      movingcrossoverperiodButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Handle Moving Crossover Period button click
        }
      });
      additionalButtonPanel.add(movingcrossoverperiodButton);

      add(additionalButtonPanel, BorderLayout.CENTER);
      revalidate();
      repaint();
    } else {
      remove(additionalButtonPanel);
      additionalButtonPanel = null;
      revalidate();
      repaint();
    }
  }

  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void print(String message) {
    System.out.println(message);
  }

  @Override
  public String getPortfolioName() {
    return null;
  }

  @Override
  public String getPositiveInput() {
    return null;
  }

  @Override
  public int[] inputDate() {
    return new int[0];
  }

  @Override
  public int selectPortfolio(Map<String, String> listOfPortfolios) {
    return 0;
  }

  @Override
  public String getPath() {
    return null;
  }

  @Override
  public String getShareName() {
    return null;
  }

//  @Override
//  public void addFeatures(Features.java features) {
//    // Implement if needed
//  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new GUIView();
      }
    });
  }
}
