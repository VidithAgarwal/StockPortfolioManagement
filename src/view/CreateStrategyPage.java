package view;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;



import controller.Features;

/**
 * CreateStrategyPage class represents page for creating an investment strategy.
 * It allows users to input details such as portfolio name, stock detail, investment frequency, etc.
 */
public class CreateStrategyPage {

  private final JFrame mainFrame;
  private final JPanel mainPanel;
  private JPanel inputPanel;

  private String portfolioName;

  private Map<String, Double> shareDetails = new HashMap<>();

  GUIView mainView;
  Features features;

  /**
   * the constructor CreateStrategyPage object, used to initialise frame, view and features.
   * @param mainFrame  main JFrame of the application.
   * @param view       GUIView instance.
   * @param features  Features instance for interacting with the application's features.
   */
  public CreateStrategyPage(JFrame mainFrame, GUIView view, Features features) {
    mainView = view;
    this.features = features;
    this.mainFrame = mainFrame;
    mainFrame.getContentPane().removeAll();
    mainFrame.repaint();
    mainFrame.revalidate();

    mainPanel = new JPanel(new BorderLayout());
    mainFrame.add(mainPanel);

    createPortfolioWithStrategy();
  }

  /**
   * Displays panel for creating a portfolio with an investment strategy.
   */
  private void createPortfolioWithStrategy() {
    mainPanel.removeAll();
    inputPanel = new JPanel(new GridBagLayout());

    JLabel nameLabel = new JLabel("Enter the name of the portfolio:");
    JTextField nameField = new JTextField(15);

    JLabel numberLabel = new JLabel("Enter number of stocks you want to make the strategy "
            + "for:");
    JTextField numberField = new JTextField(15);

    numberField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!(Character.isDigit(c)
                || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
          e.consume();
        }
      }
    });
    JButton submitButton = new JButton("Submit");

    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int n = Integer.parseInt(numberField.getText().trim());
        portfolioName = nameField.getText().trim();
        features.createFlexiblePortfolio(portfolioName);

        if (features.getErrorMessage() != null) {
          JOptionPane.showMessageDialog(inputPanel, features.getErrorMessage(),
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          numberField.setText("");
          nameField.setText("");
          nameField.requestFocus();
        } else {
          takeStrategyInputs(n);
        }
      }
    });

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(5, 5, 5, 5);

    inputPanel.add(nameLabel, gbc);
    gbc.gridx = 1;
    inputPanel.add(nameField, gbc);
    gbc.gridy = 1;
    gbc.gridx = 0;
    inputPanel.add(numberLabel, gbc);

    gbc.gridx = 1;
    inputPanel.add(numberField, gbc);

    gbc.gridy = 2;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    inputPanel.add(submitButton, gbc);

    JScrollPane scrollPane = new JScrollPane(inputPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    mainPanel.add(scrollPane, BorderLayout.CENTER);

    mainFrame.revalidate();
    mainFrame.repaint();
    mainFrame.setVisible(true);
  }

  /**
   * this method displays panel for taking strategy inputs.
   * @param n is number of stocks for which strategy inputs are required.
   */
  private void takeStrategyInputs(int n) {
    mainFrame.getContentPane().removeAll();
    inputPanel = new JPanel();
    inputPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(5, 5, 5, 5);


    for (int i = 0; i < n; i++) {
      JPanel rowPanel = new JPanel(new GridBagLayout());
      JLabel shareNameLabel = new JLabel("Enter the name of stock " + (i + 1) + ":");
      JTextField shareName = new JTextField(10);

      JLabel quantityLabel = new JLabel("Enter the quantity:");
      JTextField quantity = new JTextField(10);
      shareName.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
          String share = shareName.getText();
          String quantityText = quantity.getText();
          if (! quantityText.isEmpty()) {
            double quantity = Double.parseDouble(quantityText);
            shareDetails.put(share, quantity);
          }
        }
      });

      quantity.addKeyListener(new KeyAdapter() {

        int flag = 0;
        @Override
        public void keyTyped(KeyEvent e) {
          char c = e.getKeyChar();
          if (!(Character.isDigit(c) || c == '.'
                  || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            e.consume();
          } else {
            if (c == '.') {
              if (flag >= 1) {
                e.consume();
              }
              flag++;
            }
          }
        }

        @Override
        public void keyReleased(KeyEvent e) {
          String share = shareName.getText();
          String quantityText = quantity.getText();
          if (!quantityText.isEmpty()) {
            double quantity = Double.parseDouble(quantityText);
            shareDetails.put(share, quantity);
          }
        }
      });

      rowPanel.add(shareNameLabel, gbc);
      gbc.gridx = 1;
      rowPanel.add(shareName, gbc);
      gbc.gridx = 0;
      gbc.gridy++;
      rowPanel.add(quantityLabel, gbc);
      gbc.gridx = 1;
      rowPanel.add(quantity, gbc);

      gbc.gridx = 0;
      gbc.gridy++;

      gbc.gridwidth = 2;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      rowPanel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

      gbc.gridy++;
      inputPanel.add(rowPanel, gbc);
      gbc.gridwidth = 1;
    }

    JLabel totalAmountLabel = new JLabel("Total Amount:");
    JTextField totalAmountField = new JTextField(10);

    gbc.gridx = 0;
    gbc.gridy++;
    inputPanel.add(totalAmountLabel, gbc);

    gbc.gridx = 1;
    inputPanel.add(totalAmountField, gbc);

    // Add a text field for frequency days
    JLabel frequencyDaysLabel = new JLabel("Frequency Days:");
    JTextField frequencyDaysField = new JTextField(10);

    gbc.gridx = 0;
    gbc.gridy++;
    inputPanel.add(frequencyDaysLabel, gbc);

    gbc.gridx = 1;
    inputPanel.add(frequencyDaysField, gbc);

    // Add date pickers for start and end dates
    JLabel startDateLabel = new JLabel("Start Date:");
    JDatePanelImpl startDatePanel = mainView.createDatePanel();
    JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel,
            new GUIView.DateLabelFormatter());
    String[] startDate = {null};
    startDatePicker.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        startDate[0] = displaySelectedDate(startDatePicker);
      }
    });

    JLabel endDateLabel = new JLabel("End Date:");
    JDatePanelImpl endDatePanel = mainView.createDatePanel();
    JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel,
            new GUIView.DateLabelFormatter());

    String[] endDate = {null};
    endDatePicker.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        endDate[0] = displaySelectedDate(endDatePicker);

      }
    });

    frequencyDaysField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
          e.consume();
        }
      }
    });

    totalAmountField.addKeyListener(new KeyAdapter() {
      int flag = 0;
      @Override
      public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!(Character.isDigit(c) || c == '.'
                || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
          e.consume();
        } else {
          if (c == '.') {
            if (flag >= 1) {
              e.consume();
            }
            flag++;
          }
        }
      }
    });

    gbc.gridx = 0;
    gbc.gridy++;
    inputPanel.add(startDateLabel, gbc);

    gbc.gridx = 1;
    inputPanel.add(startDatePicker, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    inputPanel.add(endDateLabel, gbc);

    gbc.gridx = 1;
    inputPanel.add(endDatePicker, gbc);

    JButton confirmButton = new JButton("Confirm");
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        int frequency = Integer.parseInt(frequencyDaysField.getText());
        Double amount = Double.parseDouble(totalAmountField.getText());
        features.createPortfolioWithStrategy(portfolioName, startDate[0], endDate[0], frequency,
                amount, shareDetails);

        if (features.getErrorMessage() != null) {
          JOptionPane.showMessageDialog(inputPanel, features.getErrorMessage(),
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          shareDetails = new HashMap<>();
          takeStrategyInputs(n);
        } else {
          JOptionPane.showMessageDialog(inputPanel, features.getSuccessMessage(),
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);
          mainView.showSecondMenu(features);
        }
      }
    });

    gbc.gridy++;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    inputPanel.add(confirmButton, gbc);

    JScrollPane scrollPane = new JScrollPane(inputPanel);

    mainFrame.add(scrollPane);

    mainFrame.revalidate();
    mainFrame.setVisible(true);
  }


  /**
   * this method formats and displays selected date.
   * @param datePicker  JDatePickerImpl object containing the selected date.
   * @return  selected date in "yyyy-MM-dd" format.
   */
  private String displaySelectedDate(JDatePickerImpl datePicker) {
    Date selectedDate = (Date) datePicker.getModel().getValue();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    JFormattedTextField textField = datePicker.getJFormattedTextField();
    textField.setText(sdf.format(selectedDate));
    return sdf.format(selectedDate);
  }

}
