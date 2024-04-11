package view;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import controller.Features;

/**
 * The GUIView class represents the graphical user interface for the investment application.
 * It provides methods to interact with the user interface, such as creating buttons, text fields,
 * labels, and handling various actions like buying stocks, selling stocks, saving portfolio, etc.
 * it implements the IViewGUI interface and provides methods to interact with.
 * graphical user interface of the investment application.
 */
public class GUIView extends JFrame implements IViewGUI {


  private Map<String, Double> shareDetails = new HashMap<>();
  private JFrame mainFrame;

  /**
   * this constructs a new GUIView object.
   */
  public GUIView() {
    mainFrame = new JFrame();
    mainFrame.setTitle("Investment Application");
    mainFrame.setSize(600, 600);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLayout(new BorderLayout());
    mainFrame.setLocationRelativeTo(null);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new GUIView();
      }
    });
  }

  /**
   * this method creates a JButton with specified label and customizes its appearance.
   * @param s the label text for button.
   * @return a customized JButton object.
   */
  private JButton createButton(String s) {
    JButton button = new JButton(s);
    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    button.setFont(new Font("Arial", Font.BOLD, 16));
    button.setPreferredSize(new Dimension(500, 50));
    return button;
  }

  /**
   * this method creates a JTextField with specified number of columns & customizes its appearance.
   * @param columns number of columns for the text field.
   * @return a customized JTextField object.
   */
  private JTextField createTextField(int columns) {
    JTextField textField = new JTextField(columns);
    textField.setFont(new Font("Arial", Font.BOLD, 14));

    textField.setForeground(Color.BLACK);

    textField.setBackground(Color.LIGHT_GRAY);

    textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    return textField;
  }

  /**
   * this method creates a JLabel with specified text and customizes its appearance.
   * @param s  text for the label.
   * @return a customized JLabel object.
   */
  private JLabel createLabel(String s) {
    JLabel label = new JLabel(s);
    label.setFont(new Font("Arial", Font.BOLD, 16));
    return label;
  }

  /**
   * this method adds features to the GUI interface.
   * @param features An instance of the Features class providing functionality for the application.
   */
  @Override
  public void addFeatures(Features features) {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.CENTER;


    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0; // Expand horizontally
    gbc.weighty = 1.0; // Expand vertically

    JButton portfolioButton = createButton("Create a flexible portfolio");
    portfolioButton.addActionListener(evt -> createPortfolio(features));

    JButton createStrategyButton = createButton("Create Portfolio with dollar-cost average " +
            "strategy");
    createStrategyButton.addActionListener(evt -> new CreateStrategyPage(mainFrame, this,
            features));

    JButton loadButton = createButton("Load a flexible portfolio");
    loadButton.addActionListener(evt -> loadPortfolio(features));

    JButton stockAnalysisButton = createButton("Get stock statistics ");
    stockAnalysisButton.addActionListener(evt -> stockAnalysis(features));


    panel.add(portfolioButton, gbc);
    gbc.gridy = 1;
    panel.add(loadButton, gbc);
    gbc.gridy = 2;
    panel.add(createStrategyButton, gbc);
    gbc.gridy = 3;
    panel.add(stockAnalysisButton, gbc);


    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);

  }

  /**
   * this method creates JComboBox (dropdown) with specified list of portfolio names.
   * @param listOfPortfolioNames array of portfolio names to be displayed in the dropdown.
   * @return  JComboBox object with the provided list of portfolio names.
   */
  private JComboBox<String> createDropdown(String[] listOfPortfolioNames) {
    return new JComboBox<String>(listOfPortfolioNames);
  }

  /**
   * displays buy page for purchasing stocks.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void showBuyPage(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;

    panel.add(createLabel("Select portfolio to buy stocks in:"), gbc);
    JComboBox<String> dropdown =
            createDropdown(features.getPortfolioNames().toArray(new String[0]));
    gbc.gridy++;
    panel.add(dropdown, gbc);
    gbc.gridy++;
    panel.add(createLabel("Enter the date of the purchase"), gbc);

    JDatePanelImpl datePanel = createDatePanel();
    JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    gbc.gridy++;
    panel.add(datePanel, gbc);
    final String[] date = {datePicker.getModel().getValue().toString()};

    datePicker.addActionListener(e -> {
      date[0] = getDate(datePicker);
    });

    final int[] choice = {0};

    dropdown.addActionListener(e -> {
      choice[0] = getDropdownChoice(e);
    });


    JLabel tickerLabel = createLabel("Enter the share name or ticker");
    gbc.gridy++;
    panel.add(tickerLabel, gbc);
    JTextField stringInputField = createTextField(20);
    gbc.gridy++;
    panel.add(stringInputField, gbc);
    JLabel quantityLabel = createLabel("Enter the quantity");
    gbc.gridy++;
    panel.add(quantityLabel, gbc);
    JTextField numberInputField = createTextField(10);
    gbc.gridy++;
    panel.add(numberInputField, gbc);

    JButton submitButton = createButton("Buy");
    gbc.gridy++;
    gbc.gridwidth = 2;
    panel.add(submitButton, gbc);

    submitButton.addActionListener(e -> {
      String shareName = stringInputField.getText().trim();
      String quantity = numberInputField.getText().trim();
      System.out.println(date[0]);
      features.buyStock(date[0], quantity, shareName, choice[0]);
      if (features.getErrorMessage() != null) {
        JOptionPane.showMessageDialog(panel, features.getErrorMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        numberInputField.setText("");
        stringInputField.setText("");
        dropdown.setSelectedIndex(0);
      } else {
        JOptionPane.showMessageDialog(panel, features.getSuccessMessage(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        showSecondMenu(features);
      }
    });

    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method displays sell page for selling stocks.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void showSellPage(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;

    panel.add(createLabel("Select portfolio to sell stocks in:"), gbc);
    JComboBox<String> dropdown =
            createDropdown(features.getPortfolioNames().toArray(new String[0]));
    gbc.gridy++;
    panel.add(dropdown, gbc);
    gbc.gridy++;
    panel.add(createLabel("Enter the date of the sale"), gbc);

    JDatePanelImpl datePanel = createDatePanel();
    JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    gbc.gridy++;
    panel.add(datePanel, gbc);
    final String[] date = {datePicker.getModel().getValue().toString()};

    datePicker.addActionListener(e -> {
      date[0] = getDate(datePicker);
    });

    final int[] choice = {0};

    dropdown.addActionListener(e -> {
      choice[0] = getDropdownChoice(e);
    });


    JLabel tickerLabel = createLabel("Enter the share name or ticker");
    gbc.gridy++;
    panel.add(tickerLabel, gbc);
    JTextField stringInputField = createTextField(20);
    gbc.gridy++;
    panel.add(stringInputField, gbc);
    JLabel quantityLabel = createLabel("Enter the quantity");
    gbc.gridy++;
    panel.add(quantityLabel, gbc);
    JTextField numberInputField = createTextField(10);
    gbc.gridy++;
    panel.add(numberInputField, gbc);

    JButton submitButton = createButton("Sell");
    gbc.gridy++;
    gbc.gridwidth = 2;
    panel.add(submitButton, gbc);

    submitButton.addActionListener(e -> {
      String shareName = stringInputField.getText().trim();
      String quantity = numberInputField.getText().trim();
      features.sellStock(date[0], quantity, shareName, choice[0]);
      if (features.getErrorMessage() != null) {
        JOptionPane.showMessageDialog(panel, features.getErrorMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        numberInputField.setText("");
        stringInputField.setText("");
        dropdown.setSelectedIndex(0);
      } else {
        JOptionPane.showMessageDialog(panel, features.getSuccessMessage(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        showSecondMenu(features);
      }
    });

    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method retrieves the index of the selected item from the dropdown.
   * @param e is the ActionEvent triggered by the dropdown selection.
   * @return index of the selected item in the dropdown.
   */
  private int getDropdownChoice(ActionEvent e) {
    JComboBox<String> cb = (JComboBox<String>) e.getSource();
    return cb.getSelectedIndex();
  }

  /**
   * this method retrieves the selected date from the JDatePicker.
   * @param datePicker the JDatePicker object containing selected date.
   * @return string representation of the selected date.
   */
  String getDate(JDatePicker datePicker) {
    Date selectedDate = (Date) datePicker.getModel().getValue();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(selectedDate);
  }

  /**
   * this method creates a JDatePanelImpl (date picker panel) with default properties.
   * @return a JDatePanelImpl object representing date picker panel.
   */
  JDatePanelImpl createDatePanel() {
    UtilDateModel model = new UtilDateModel();
    model.setValue(null);
    Calendar calendar = Calendar.getInstance();
    model.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    model.setSelected(true);

    Properties properties = new Properties();
    properties.put("text.today", "Today");
    properties.put("text.month", "Month");
    properties.put("text.year", "Year");

    return new JDatePanelImpl(model, properties);
  }


  /**
   * this method displays the save page for exporting the portfolio to a CSV file.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void save(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;

    panel.add(createLabel("Select portfolio to save:"), gbc);
    JComboBox<String> dropdown =
            createDropdown(features.getPortfolioNames().toArray(new String[0]));
    gbc.gridy++;
    panel.add(dropdown, gbc);
    gbc.gridy++;

    final int[] choice = {0};

    dropdown.addActionListener(e -> {
      choice[0] = getDropdownChoice(e);
    });

    JLabel csvLabel = createLabel("Select CSV file to save portfolio:");
    gbc.gridy++;
    panel.add(csvLabel, gbc);

    JButton browseButton = createButton("Browse Files");
    gbc.gridy++;
    panel.add(browseButton, gbc);

    JTextField filePathField = createTextField(20);
    filePathField.setEditable(false);
    gbc.gridy++;
    //panel.add(filePathField, gbc);

    browseButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Enter CSV file name to save portfolio at particular location");
      int userSelection = fileChooser.showSaveDialog(mainFrame);

      if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();
        if (!filePath.endsWith(".csv")) {
          filePath += ".csv"; // Ensuring the file extension is .csv
        }
        filePathField.setText(filePath);
      }
    });

    JButton submitButton = createButton("Save Portfolio");
    gbc.gridy++;
    gbc.gridwidth = 2;
    panel.add(submitButton, gbc);
    submitButton.addActionListener(e -> {
      String selectedPortfolio = (String) dropdown.getSelectedItem();
      String filePath = filePathField.getText();
      if (selectedPortfolio != null && !filePath.isEmpty()) {
        features.export(choice[0], filePath);
        if (features.getErrorMessage() != null) {
          JOptionPane.showMessageDialog(panel, features.getErrorMessage(),
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          dropdown.setSelectedIndex(0);
          filePathField.setText("");
        } else {
          JOptionPane.showMessageDialog(panel, "Portfolio saved successfully",
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);
          showSecondMenu(features);
        }
      } else {
        JOptionPane.showMessageDialog(panel, "Please select a portfolio and CSV file",
                "Error",
                JOptionPane.ERROR_MESSAGE);
      }
    });

    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }


  /**
   * this method displays create portfolio page for creating a new flexible portfolio.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void createPortfolio(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel heading = createLabel("Create New Flexible Portfolio");
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

  /**
   * this method displays second menu with various options for user, such as creating portfolios,
   * loading portfolios, getting portfolio composition, buying stocks, selling stocks, getting
   * cost basis, getting total value, saving portfolios, investing with Dollar Cost Averaging (DCA)
   * strategy, and getting stock analysis.
   * @param features An instance of the Features class providing functionality for the application.
   */
  void showSecondMenu(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.NORTH;


    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0; // Expand horizontally
    gbc.weighty = 2.0; // Ex

    JButton button = createButton("Create Flexible Portfolio");
    button.setPreferredSize(new Dimension(500, 50));
    button.addActionListener(evt -> createPortfolio(features));

    JButton createStrategyButton = createButton("Create Portfolio with dollar-cost average " +
            "strategy");
    button.setPreferredSize(new Dimension(500, 50));
    createStrategyButton.addActionListener(evt -> new CreateStrategyPage(mainFrame, this, features));

    JButton loadingButton = createButton("Load Flexible Portfolio");
    loadingButton.setPreferredSize(new Dimension(500, 50));
    loadingButton.addActionListener(evt -> loadPortfolio(features));

    JButton compositionButton = createButton("Get composition of the portfolio");
    compositionButton.setPreferredSize(new Dimension(500, 50));
    compositionButton.addActionListener(evt -> composition(features));

    JButton buyStockButton = createButton("Buy Stocks");
    buyStockButton.setPreferredSize(new Dimension(500, 50));
    buyStockButton.addActionListener(evt -> showBuyPage(features));

    JButton sellStockButton = createButton("Sell Stocks");
    sellStockButton.setPreferredSize(new Dimension(500, 50));
    sellStockButton.addActionListener(evt -> showSellPage(features));

    JButton costBasisButton = createButton("Get Cost Basis for a Portfolio");
    costBasisButton.setPreferredSize(new Dimension(500, 50));
    costBasisButton.addActionListener(evt -> showCostBasisPage(features));

    JButton totalValueButton = createButton("Get Total Value of a Portfolio");
    totalValueButton.setPreferredSize(new Dimension(500, 50));
    totalValueButton.addActionListener(evt -> showTotalValuePage(features));

    JButton saveButton = createButton("Save Flexible Portfolio");
    saveButton.setPreferredSize(new Dimension(500, 50));
    saveButton.addActionListener(evt -> save(features));

//    JButton dollarCostButton = createButton("Create Portfolio Using Dollar-Cost Averaging");
//    dollarCostButton.setPreferredSize(new Dimension(500, 50));
//    panel.add(dollarCostButton);
//    dollarCostButton.addActionListener(evt -> dollarCostPortfolio(features));

    JButton investButton = createButton("Investment in a Portfolio with Dollar cost Average " +
            "Strategy");
    investButton.setPreferredSize(new Dimension(500, 50));
    investButton.addActionListener(evt -> DCAInvestment(features));

    JButton stockAnalysis = createButton("Get Stock Analysis");
    stockAnalysis.setPreferredSize(new Dimension(500, 50));
    stockAnalysis.addActionListener(evt -> stockAnalysis(features));


    panel.add(button, gbc);
    gbc.gridy = 1;
    panel.add(loadingButton, gbc);
    gbc.gridy++;
    panel.add(createStrategyButton, gbc);
    gbc.gridy++;
    panel.add(compositionButton);
    gbc.gridy++;
    panel.add(buyStockButton, gbc);
    gbc.gridy++;
    panel.add(sellStockButton, gbc);
    gbc.gridy++;
    panel.add(costBasisButton, gbc);
    gbc.gridy++;
    panel.add(totalValueButton, gbc);
    gbc.gridy++;
    panel.add(saveButton, gbc);

    gbc.gridy++;
    panel.add(investButton, gbc);
    gbc.gridy++;
    panel.add(stockAnalysis, gbc);

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    mainFrame.add(scrollPane);

    revalidate();
    repaint();
    mainFrame.setVisible(true);
  }

  /**
   * this method perform Dollar Cost Averaging (DCA) investment strategy.
   * Displays a form for user to select a portfolio, date of investment, and weight for each stock.
   * Submits the investment request to Features object.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void DCAInvestment(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel choicePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;

    gbc.gridx = 0;
    gbc.gridy = 0;
    choicePanel.add(createLabel("Select portfolio to invest in:"), gbc);
    ArrayList<String> options = features.getPortfolioNames();
    options.add(0, "Select a portfolio");
    JComboBox<String> dropdown = createDropdown(options.toArray(new String[0]));
    gbc.gridy++;
    dropdown.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXX");
    choicePanel.add(dropdown, gbc);
    gbc.gridy++;
    choicePanel.add(createLabel("Select the date of investment:"), gbc);

    JDatePanelImpl datePanel = createDatePanel();
    JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    gbc.gridy++;
    choicePanel.add(datePanel, gbc);

    final String[] date = {LocalDate.now().minusDays(1) + ""};
    JPanel formPanel = new JPanel(new GridBagLayout());

    final int[] choice = {0};
    dropdown.addActionListener(e -> {
      Map<String, Double> composition = features.examineComposition( dropdown.getSelectedIndex() - 1,
              date[0]);
      if (features.getErrorMessage() != null) {
        JOptionPane.showMessageDialog(formPanel, features.getErrorMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        showSecondMenu(features);
        return;
      } else {
        choice[0] = dropdown.getSelectedIndex() - 1;
        updateForm(composition, formPanel, features, date[0], choice[0]);
      }
    });

    datePicker.addActionListener(e -> {
      date[0] = getDate(datePicker);
      Map<String, Double> composition = features.examineComposition(choice[0],
              date[0]);
      if (features.getErrorMessage() != null) {
        JOptionPane.showMessageDialog(formPanel, features.getErrorMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        DCAInvestment(features);
      } else {
        updateForm(composition, formPanel, features, date[0], choice[0]);
      }
    });

    mainPanel.add(choicePanel, BorderLayout.NORTH);


    // Add components to the main panel
    JScrollPane scrollPane = new JScrollPane(formPanel);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    mainFrame.add(mainPanel);
    mainFrame.revalidate();
    mainFrame.repaint();
    mainFrame.setVisible(true);

  }

  /**
   * this method update form with composition details of the selected portfolio.
   * Allows the user to enter the weight for each stock and the total investment amount.
   *
   * @param stringDoubleMap  map containing the composition of the portfolio.
   * @param formPanel      panel containing the form elements.
   * @param features       Features object containing the investment features.
   * @param date            date of investment.
   * @param choice         index of the selected portfolio.
   */
  private void updateForm(Map<String, Double> stringDoubleMap, JPanel formPanel,
                          Features features, String date, int choice) {
    formPanel.removeAll();
    formPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
//    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;
    gbc.gridy = 0;

    for (Map.Entry<String, Double> entry : stringDoubleMap.entrySet()) {


      JPanel rowPanel = new JPanel(new GridBagLayout());
      gbc.gridwidth = 1;

      JLabel quantityLabel = createLabel("Enter the weight for " + entry.getKey());
      JTextField quantity = createTextField(10);

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
          String quantityText = quantity.getText();
          if (!quantityText.isEmpty()) {
            double quantity = Double.parseDouble(quantityText);
            shareDetails.put(entry.getKey(), quantity);
          }
        }
      });
      rowPanel.add(quantityLabel, gbc);
      gbc.gridx = 1;
      rowPanel.add(quantity, gbc);

      gbc.gridy++;

      gbc.gridx = 0;
      gbc.gridwidth = 2;
      formPanel.add(rowPanel, gbc);
    }
    JLabel totalAmountLabel = createLabel("Total Investment Amount:");
    JTextField totalAmountField = createTextField(10);
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

    JButton submitButton = new JButton("Confirm");
    submitButton.setPreferredSize(new Dimension(250, 50));


    submitButton.addActionListener(e -> {
      if (totalAmountField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(formPanel, "Amount cannot be blank",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        DCAInvestment(features);
      }
      Double amount = Double.parseDouble(totalAmountField.getText());
      features.investWithDCAStrategy(choice, date,
              amount, shareDetails);

      if (features.getErrorMessage() != null) {
        JOptionPane.showMessageDialog(formPanel, features.getErrorMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        shareDetails = new HashMap<>();
      } else {
        JOptionPane.showMessageDialog(formPanel, features.getSuccessMessage(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        showSecondMenu(features);

      }
    });

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 1;
    formPanel.add(totalAmountLabel, gbc);

    gbc.gridx = 1;
    formPanel.add(totalAmountField, gbc);
    gbc.gridy++;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(submitButton, gbc);
    formPanel.revalidate();
    formPanel.repaint();
  }


  /**
   * this method displays page for calculating cost basis of a portfolio.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void showCostBasisPage(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;

    panel.add(createLabel("Select portfolio to get cost basis of:"), gbc);
    JComboBox<String> dropdown =
            createDropdown(features.getPortfolioNames().toArray(new String[0]));
    gbc.gridy++;
    panel.add(dropdown, gbc);
    gbc.gridy++;
    panel.add(createLabel("Select the date you want the cost basis till"), gbc);

    JDatePanelImpl datePanel = createDatePanel();
    JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    gbc.gridy++;
    panel.add(datePanel, gbc);
    final String[] date = {datePicker.getModel().getValue().toString()};

    datePicker.addActionListener(e -> {
      date[0] = getDate(datePicker);
    });

    final int[] choice = {0};

    dropdown.addActionListener(e -> {
      choice[0] = getDropdownChoice(e);
    });

    JButton submitButton = createButton("Submit");
    gbc.gridy++;
    gbc.gridwidth = 2;
    panel.add(submitButton, gbc);

    submitButton.addActionListener(e -> {
      features.getCostBasis(choice[0], date[0]);
      if (features.getErrorMessage() != null) {
        JOptionPane.showMessageDialog(panel, features.getErrorMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        dropdown.setSelectedIndex(0);
      } else {
        JOptionPane.showMessageDialog(panel, features.getSuccessMessage(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        showSecondMenu(features);
      }
    });

    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method display page for calculating the total value of a portfolio.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void showTotalValuePage(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;

    panel.add(createLabel("Select portfolio to get total value of:"), gbc);
    JComboBox<String> dropdown =
            createDropdown(features.getPortfolioNames().toArray(new String[0]));
    gbc.gridy++;
    panel.add(dropdown, gbc);
    gbc.gridy++;
    panel.add(createLabel("Select the date you want the total value for"), gbc);

    JDatePanelImpl datePanel = createDatePanel();
    JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    gbc.gridy++;
    panel.add(datePanel, gbc);
    final String[] date = {datePicker.getModel().getValue().toString()};

    datePicker.addActionListener(e -> {
      date[0] = getDate(datePicker);
    });

    final int[] choice = {0};

    dropdown.addActionListener(e -> {
      choice[0] = getDropdownChoice(e);
    });

    JButton submitButton = createButton("Get total Value");
    gbc.gridy++;
    gbc.gridwidth = 2;
    panel.add(submitButton, gbc);

    submitButton.addActionListener(e -> {
      System.out.println(date[0]);
      features.getTotalValue(choice[0], date[0]);
      if (features.getErrorMessage() != null) {
        JOptionPane.showMessageDialog(panel, features.getErrorMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        dropdown.setSelectedIndex(0);
      } else {
        JOptionPane.showMessageDialog(panel, features.getSuccessMessage(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        showSecondMenu(features);
      }
    });

    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }


  /**
   * this method display an error message in a dialog box.
   * @param error the error message to be displayed
   */
  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(null, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * this method print a message to the console.
   * @param message the message to be printed
   */
  @Override
  public void print(String message) {
    System.out.println(message);
  }


  /**
   * this method display stock analysis menu with various options.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void stockAnalysis(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.NORTH;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0; // Expand horizontally
    gbc.weighty = 1.0; // Ex

    JButton gainButton = createButton("Gain or Lose");
    gainButton.setPreferredSize(new Dimension(500, 50));
    panel.add(gainButton);
    gainButton.addActionListener(evt -> gainOrLose(features));

    JButton periodButton = createButton("Gain or Lose Over Period");
    periodButton.setPreferredSize(new Dimension(500, 50));
    panel.add(periodButton);
    periodButton.addActionListener(evt -> gainOrLoseOverPeriod(features));

    JButton xDayButton = createButton("X-Day Moving Average");
    xDayButton.setPreferredSize(new Dimension(500, 50));
    panel.add(xDayButton);
    xDayButton.addActionListener(evt -> xMovingAvg(features));

    JButton crossoverButton = createButton("Get Crossover Period");
    crossoverButton.setPreferredSize(new Dimension(500, 50));
    panel.add(crossoverButton);
    crossoverButton.addActionListener(evt -> crossoverPeriod(features));

    JButton movingButton = createButton("Get Moving Crossover Period");
    movingButton.setPreferredSize(new Dimension(500, 50));
    panel.add(movingButton);
    movingButton.addActionListener(evt -> movingCrossover(features));

    panel.add(gainButton, gbc);
    gbc.gridy = 1;
    panel.add(periodButton, gbc);
    gbc.gridy = 2;
    panel.add(xDayButton, gbc);
    gbc.gridy = 3;
    panel.add(crossoverButton, gbc);
    gbc.gridy = 4;
    panel.add(movingButton, gbc);

    mainFrame.add(panel);

    revalidate();
    repaint();
    mainFrame.setVisible(true);
  }

  /**
   * this method display the moving crossover analysis form.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void movingCrossover(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel heading = createLabel("Get Moving Crossover days for a Stock.");
    heading.setFont(new Font("Arial", Font.BOLD, 24));
    panel.add(heading, gbc);


    JLabel label = createLabel("Enter stock name or ticker symbol");
    JTextField textBox = createTextField(15);
    label.setLabelFor(textBox);
    JLabel dateLabel = createLabel("Select start date:");
    JDatePanelImpl datePanel1 = createDatePanel();
    JDatePicker datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
    final String[] date1 = {datePicker1.getModel().getValue().toString()};

    datePicker1.addActionListener(e -> {
      date1[0] = getDate(datePicker1);
    });
    JLabel dateLabel1 = createLabel("Select end date");

    JDatePanelImpl datePanel2 = createDatePanel();
    JDatePicker datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
    final String[] date2 = {datePicker2.getModel().getValue().toString()};

    datePicker2.addActionListener(e -> {
      date2[0] = getDate(datePicker2);
    });

    dateLabel1.setLabelFor(datePanel1);
    JLabel xLabel = createLabel("Enter X number of days (shorter days) for moving average.");
    JTextField textBox3 = createTextField(15);
    xLabel.setLabelFor(textBox3);
    JLabel xLabel1 = createLabel("Enter Y number of days (larger duration) for moving average.");
    JTextField textBox4 = createTextField(15);
    xLabel1.setLabelFor(textBox4);
    JButton submitButton = createButton("Submit");
    submitButton.setPreferredSize(new Dimension(200, 35));
    submitButton.addActionListener(evt -> {
      String inputText = textBox.getText();
      String startDate = date1[0];
      String endDate = date2[0];
      String xValue = textBox3.getText();
      String yValue = textBox4.getText();
      if (inputText != null && startDate != null && endDate != null
              && xValue != null && yValue != null) {
        TreeMap<String, String> result = features.movingCrossoversOverPeriod(startDate, endDate,
                xValue, yValue, inputText);

        if (features.getErrorMessage() != null) {
          String error = features.getErrorMessage();
          JOptionPane.showMessageDialog(panel, error,
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          showSecondMenu(features);
        } else {
          StringBuilder messageBuilder = new StringBuilder();
          for (Map.Entry<String, String> entry : result.entrySet()) {
            messageBuilder.append(entry.getKey()).append(": ")
                    .append(entry.getValue()).append("\n");
          }
          String message = messageBuilder.toString();
          JOptionPane.showMessageDialog(panel, message,
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);

          showSecondMenu(features);
        }
      }
    });

    panel.add(label, gbc);
    panel.add(textBox, gbc);
    panel.add(dateLabel, gbc);
    panel.add(datePanel1, gbc);
    panel.add(dateLabel1, gbc);
    panel.add(datePanel2, gbc);
    panel.add(xLabel, gbc);
    panel.add(textBox3, gbc);
    panel.add(xLabel1, gbc);
    panel.add(textBox4, gbc);
    panel.add(submitButton, gbc);

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Add the panel to the frame
    mainFrame.add(scrollPane);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method clears the main frame content.
   * prepares a panel for getting crossover days for a stock.
   * and sets up UI components for user input.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void crossoverPeriod(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel heading = createLabel("Get Crossover days if any for a Stock.");
    heading.setFont(new Font("Arial", Font.BOLD, 24));
    panel.add(heading, gbc);


    JLabel label = createLabel("Enter stock name or ticker symbol");
    JTextField textBox = createTextField(15);
    label.setLabelFor(textBox);
    JLabel dateLabel = createLabel("Select start date:");
    JDatePanelImpl datePanel1 = createDatePanel();
    JDatePicker datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
    final String[] date1 = {datePicker1.getModel().getValue().toString()};

    datePicker1.addActionListener(e -> {
      date1[0] = getDate(datePicker1);
    });
    JLabel dateLabel1 = createLabel("Select end date:");

    JDatePanelImpl datePanel2 = createDatePanel();
    JDatePicker datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
    final String[] date2 = {datePicker2.getModel().getValue().toString()};

    datePicker2.addActionListener(e -> {
      date2[0] = getDate(datePicker2);
    });
    JButton submitButton = createButton("Submit");
    submitButton.setPreferredSize(new Dimension(200, 35));
    submitButton.addActionListener(evt -> {
      String inputText = textBox.getText();
      String startDate = date1[0];
      String endDate = date2[0];
      if (inputText != null && startDate != null && endDate != null) {
        TreeMap<String, String> result = features.crossoverOverPeriod(startDate, endDate, inputText);
        if (features.getErrorMessage() != null) {
          String error = features.getErrorMessage();
          JOptionPane.showMessageDialog(panel, error,
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          showSecondMenu(features);
        } else {

          StringBuilder messageBuilder = new StringBuilder();
          for (Map.Entry<String, String> entry : result.entrySet()) {
            messageBuilder.append(entry.getKey()).append(": ")
                    .append(entry.getValue()).append("\n");
          }
          String message = messageBuilder.toString();
          JOptionPane.showMessageDialog(panel, message,
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);

          showSecondMenu(features);
        }
      }
    });

    panel.add(label, gbc);
    panel.add(textBox, gbc);
    panel.add(dateLabel, gbc);
    panel.add(datePanel1, gbc);
    panel.add(dateLabel1, gbc);
    panel.add(datePanel2, gbc);
    panel.add(submitButton, gbc);

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Add the panel to the frame
    mainFrame.add(scrollPane);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method clears main frame content.
   * prepares a panel for obtaining X-Day Moving Date Average for a stock,
   * and sets up UI components for user input.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void xMovingAvg(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel heading = createLabel("Get X-Day Moving Date Average for a Stock.");
    heading.setFont(new Font("Arial", Font.BOLD, 24));
    panel.add(heading, gbc);


    JLabel label = createLabel("Enter stock name or ticker symbol:");
    JTextField textBox = createTextField(15);
    label.setLabelFor(textBox);
    JLabel xLabel = createLabel("Enter x number of day for getting average:");
    JTextField textBox2 = createTextField(15);
    xLabel.setLabelFor(textBox2);
    JLabel dateLabel = createLabel("Select start date:");
    JDatePanelImpl datePanel = createDatePanel();
    JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    final String[] date = {datePicker.getModel().getValue().toString()};

    datePicker.addActionListener(e -> {
      date[0] = getDate(datePicker);
    });
    JButton submitButton = createButton("Submit");
    submitButton.setPreferredSize(new Dimension(200, 35));
    submitButton.addActionListener(evt -> {
      String inputText = textBox.getText();
      String xValue = textBox2.getText();
      String inputDate = date[0];
      if (inputText != null && inputDate != null && xValue != null) {
        features.xDayMovingAvg(inputText, xValue, inputDate);

        if (features.getErrorMessage() != null) {
          String error = features.getErrorMessage();
          JOptionPane.showMessageDialog(panel, error,
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          showSecondMenu(features);
        } else {
          String message = features.getSuccessMessage();
          JOptionPane.showMessageDialog(panel, message,
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);

          showSecondMenu(features);
        }
      }
    });

    panel.add(label, gbc);
    panel.add(textBox, gbc);
    panel.add(xLabel, gbc);
    panel.add(textBox2, gbc);
    panel.add(dateLabel, gbc);
    panel.add(datePanel, gbc);
    panel.add(submitButton, gbc);

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Add the panel to the frame
    mainFrame.add(scrollPane);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);

  }

  /**
   * this method clears main frame content.
   * prepares a panel for obtaining gain or loss of a stock over a period of time,
   * and sets up UI components for user input.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void gainOrLoseOverPeriod(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel heading = createLabel("Get Gain or Lose of a Stock Over a Period of Time");
    heading.setFont(new Font("Arial", Font.BOLD, 24));
    panel.add(heading, gbc);


    JLabel label = createLabel("Enter stock name or ticker symbol");
    JTextField textBox = createTextField(15);
    label.setLabelFor(textBox);
    JLabel dateLabel = createLabel("Select start date:");
    JDatePanelImpl datePanel1 = createDatePanel();
    JDatePicker datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
    final String[] date1 = {datePicker1.getModel().getValue().toString()};

    datePicker1.addActionListener(e -> {
      date1[0] = getDate(datePicker1);
    });
    JLabel dateLabel1 = createLabel("Select end date");

    JDatePanelImpl datePanel2 = createDatePanel();
    JDatePicker datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
    final String[] date2 = {datePicker2.getModel().getValue().toString()};

    datePicker2.addActionListener(e -> {
      date2[0] = getDate(datePicker2);
    });
    JButton submitButton = createButton("Submit");
    submitButton.setPreferredSize(new Dimension(200, 35));
    submitButton.addActionListener(evt -> {
      String inputText = textBox.getText();
      String startDate = date1[0];
      String endDate = date2[0];
      if (inputText != null && startDate != null && endDate != null) {
        features.gainOrLoseOverPeriod(startDate, endDate, inputText);

        if (features.getErrorMessage() != null) {
          String error = features.getErrorMessage();
          JOptionPane.showMessageDialog(panel, error,
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          showSecondMenu(features);
        } else {
          String message = features.getSuccessMessage();
          JOptionPane.showMessageDialog(panel, message,
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);

          showSecondMenu(features);
        }
      }
    });

    panel.add(label, gbc);
    panel.add(textBox, gbc);
    panel.add(dateLabel, gbc);
    panel.add(datePanel1, gbc);
    panel.add(dateLabel1, gbc);
    panel.add(datePanel2, gbc);
    panel.add(submitButton, gbc);

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Add the panel to the frame
    mainFrame.add(scrollPane);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method clears main frame content.
   * prepares a panel for obtaining gain or loss of a stock on a particular date,
   * and sets up UI components for user input.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void gainOrLose(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel heading = createLabel("Get Gain or Lose of a Stock on a Particular Date");
    heading.setFont(new Font("Arial", Font.BOLD, 24));
    panel.add(heading, gbc);


    JLabel label = createLabel("Enter stock name or ticker symbol");
    JTextField textBox = createTextField(15);
    label.setLabelFor(textBox);
    JLabel dateLabel = createLabel("Select the date:");
    JDatePanelImpl datePanel = createDatePanel();
    JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    final String[] date = {datePicker.getModel().getValue().toString()};

    datePicker.addActionListener(e -> {
      date[0] = getDate(datePicker);
    });

    JButton submitButton = createButton("Submit");
    submitButton.setPreferredSize(new Dimension(200, 35));
    submitButton.addActionListener(evt -> {
      String inputText = textBox.getText();
      String inputDate = date[0];
      if (inputText != null && inputDate != null) {
        features.gainOrLose(inputDate, inputText);
        if (features.getErrorMessage() != null) {
          String error = features.getErrorMessage();
          JOptionPane.showMessageDialog(panel, error,
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          showSecondMenu(features);
        } else {
          String message = features.getSuccessMessage();
          JOptionPane.showMessageDialog(panel, message,
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);

          showSecondMenu(features);
        }
      }
    });

    panel.add(label, gbc);
    panel.add(textBox, gbc);
    panel.add(dateLabel, gbc);
    panel.add(datePanel, gbc);
    panel.add(submitButton, gbc);

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Add the panel to the frame
    mainFrame.add(scrollPane);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method clears main frame content.
   * prepares a panel for loading a portfolio from a CSV file,
   * and sets up UI components for user input.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void loadPortfolio(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel heading = createLabel("Load Portfolio ");
    heading.setFont(new Font("Arial", Font.BOLD, 24));
    panel.add(heading, gbc);

    JLabel nameLabel = createLabel("Enter the name of portfolio to be loaded:");
    JTextField nameField = createTextField(15);
    nameLabel.setLabelFor(nameField);

    JLabel fileLabel = createLabel("Select CSV file:");
    JButton browseButton = createButton("Browse");
    browseButton.setPreferredSize(new Dimension(200, 35));
    JTextField filePathField = createTextField(15);
    filePathField.setEditable(false);

    browseButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Choose a CSV file to load portfolio");
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        filePathField.setText(selectedFile.getAbsolutePath());
      }
    });

    JButton submitButton = createButton("Submit");
    submitButton.setPreferredSize(new Dimension(200, 35));
    submitButton.addActionListener(evt -> {
      String name = nameField.getText();
      String filePath = filePathField.getText();
      if (!name.isEmpty() && !filePath.isEmpty()) {
        features.loadPortfolio(name, filePath);
        if (features.getErrorMessage() != null) {
          JOptionPane.showMessageDialog(panel, features.getErrorMessage(),
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(panel, features.getSuccessMessage(),
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);
          showSecondMenu(features);
        }
      } else {
        JOptionPane.showMessageDialog(panel, "Please enter portfolio name and select a CSV file",
                "Error",
                JOptionPane.ERROR_MESSAGE);
      }
    });

    panel.add(nameLabel, gbc);
    panel.add(nameField, gbc);
    panel.add(fileLabel, gbc);
    //panel.add(filePathField, gbc);
    panel.add(browseButton, gbc);
    panel.add(submitButton, gbc);

    // Add the panel to the frame
    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method clears the main frame content.
   * prepares a panel for obtaining the composition of a portfolio,
   * and sets up UI components for user input.
   * @param features An instance of the Features class providing functionality for the application.
   */
  private void composition(Features features) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;

    panel.add(createLabel("Select portfolio for which you want to get composition:"), gbc);
    JComboBox<String> dropdown =
            createDropdown(features.getPortfolioNames().toArray(new String[0]));
    gbc.gridy++;
    panel.add(dropdown, gbc);
    gbc.gridy++;
    panel.add(createLabel("Select the date for which you want to get composition"), gbc);

    JDatePanelImpl datePanel = createDatePanel();
    JDatePicker datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    gbc.gridy++;
    panel.add(datePanel, gbc);
    final String[] date = {datePicker.getModel().getValue().toString()};

    datePicker.addActionListener(e -> {
      date[0] = getDate(datePicker);
    });
    final int[] choice = {0};

    dropdown.addActionListener(e -> {
      choice[0] = getDropdownChoice(e);
    });

    JButton submitButton = createButton("Submit");
    gbc.gridy++;
    gbc.gridwidth = 2;
    panel.add(submitButton, gbc);

    submitButton.addActionListener(e -> {
      if (date[0] != null) { //date[0] != null
        Map<String, Double> compositionResult = features.examineComposition(choice[0], date[0]);

        if (features.getErrorMessage() != null) {
          JOptionPane.showMessageDialog(panel, features.getErrorMessage(),
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          dropdown.setSelectedIndex(0);
          showSecondMenu(features);
        } else {
//          JOptionPane.showMessageDialog(panel, features.getSuccessMessage(),
//                  "Success",
//                  JOptionPane.INFORMATION_MESSAGE);
//          showSecondMenu(features);
          displayCompositionResult(features, compositionResult);
        }
      } else {
        JOptionPane.showMessageDialog(panel, "Please select portfolio and date",
                "Error",
                JOptionPane.ERROR_MESSAGE);
      }
    });

    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();

    mainFrame.setVisible(true);
  }

  /**
   * this method displays the composition result of a portfolio in a table format.
   * @param features An instance of the Features class providing functionality for application.
   * @param compositionResult  map containing the composition result of  portfolio.
   */
  private void displayCompositionResult(Features features, Map<String, Double> compositionResult) {
    mainFrame.getContentPane().removeAll();
    JPanel panel = new JPanel(new BorderLayout());

    JTable table = createCompositionTable(compositionResult);
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    JButton backButton = createButton("Go Back to Main Menu");
    panel.add(backButton, BorderLayout.SOUTH);

    backButton.addActionListener(e -> {
      showSecondMenu(features);
    });

    mainFrame.add(panel);
    mainFrame.repaint();
    mainFrame.revalidate();
    mainFrame.setVisible(true);
  }

  /**
   * this method creates a JTable to display the composition result of a portfolio.
   * @param compositionResult  map containing the composition result of the portfolio.
   * @return  JTable displaying the composition result.
   */
  private JTable createCompositionTable(Map<String, Double> compositionResult) {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Ticker Symbol");
    model.addColumn("Quantity");

    for (Map.Entry<String, Double> entry : compositionResult.entrySet()) {
      model.addRow(new Object[]{entry.getKey(), entry.getValue()});
    }

    JTable table = new JTable(model);
    table.setFillsViewportHeight(true);
    return table;
  }

  /**
   * Custom formatter class for formatting dates in UI.
   */
  static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    @Override
    public Object stringToValue(String text) {
      return null; // Not used
    }

    /**
     * this method overrides valueToString method of AbstractFormatter class.
     * to format dates as strings.
     * If provided value is an instance of Date, it formats the date using the "yyyy-MM-dd" format.
     * Otherwise, it returns an empty string.
     * @param value value to be formatted.
     * @return formatted string representation of the date, or an empty string if value is not Date.
     */
    @Override
    public String valueToString(Object value) {
      if (value instanceof Date) {
        String dateFormat = "yyyy-MM-dd";
        return new java.text.SimpleDateFormat(dateFormat).format(value);
      } else {
        return ""; // Or return any appropriate default value
      }
    }
  }
}
