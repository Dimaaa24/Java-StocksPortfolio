package isp.lab9.exercise1.ui;
import isp.lab9.exercise1.services.UserPortfolio;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuyJPanel extends JPanel {
    private StockMarketJFrame frame;

    private UserPortfolio portfolio;

    public BuyJPanel(StockMarketJFrame frame)
    {
        portfolio=frame.getPortfolio();
        this.frame = frame;
        initComponents();
    }

    private void initComponents(){
        setLayout(new GridLayout(2, 2));

        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(10, 2));

        JLabel availableFundsLabel = new JLabel("Available funds:");
        JTextField availableFundsTextField = new JTextField(portfolio.getCash().toPlainString() + " $");
        availableFundsTextField.setEditable(false);

        JLabel symbolLabel = new JLabel("Symbol:");
        JComboBox<String> symbolComboBox = new JComboBox<>();
        symbolComboBox.setModel(new DefaultComboBoxModel(frame.getMarketService().getSymbols()));

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityTextField = new JTextField();

        JLabel costLabel = new JLabel("Total cost:");
        JTextField costTextField = new JTextField();
        costTextField.setEditable(false);

        JButton costButton = new JButton("Get cost");
        costButton.addActionListener(e ->
                calculateTotalCostActionPerformed(symbolComboBox, quantityTextField, costTextField));

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(e ->
                buyStocks(symbolComboBox,costTextField,quantityTextField));
        // todo: add event listener to 'Buy' button


        buyPanel.add(availableFundsLabel);
        buyPanel.add(availableFundsTextField);
        buyPanel.add(new JPanel()); // empty cell in the grid
        buyPanel.add(new JPanel()); // empty cell in the grid
        buyPanel.add(symbolLabel);
        buyPanel.add(symbolComboBox);
        buyPanel.add(new JPanel()); // empty cell in the grid
        buyPanel.add(new JPanel()); // empty cell in the grid
        buyPanel.add(quantityLabel);
        buyPanel.add(quantityTextField);
        buyPanel.add(new JPanel()); // empty cell in the grid
        buyPanel.add(new JPanel()); // empty cell in the grid
        buyPanel.add(costLabel);
        buyPanel.add(costTextField);
        buyPanel.add(new JPanel()); // empty cell in the grid
        buyPanel.add(new JPanel()); // empty cell in the grid
        buyPanel.add(costButton);
        buyPanel.add(buyButton);
        add(buyPanel);
        add(new JPanel()); // empty cell in the grid
        add(new JPanel()); // empty cell in the grid
        add(new JPanel()); // empty cell in the grid

    }

    /**
     * Calculates the total transaction cost
     */
    private void buyStocks(JComboBox<String> symbolComboBox,
                           JTextField totalCost,
                           JTextField quantity)
    {

        String symbol=(String) symbolComboBox.getSelectedItem();
        int stock_quant=Integer.parseInt(quantity.getText());
        int total_cost=Integer.parseInt(totalCost.getText());
        UserPortfolio temp=frame.getPortfolio();
        //create a temp portfolio;
        int wallet=temp.getCash().intValue();
        if(wallet<=total_cost)
        {
            temp.addShares(symbol,stock_quant);
            wallet-=total_cost;
            BigDecimal money=new BigDecimal(wallet);
            temp.setCash(money);
        }
        frame.setPortfolio(temp);

    }
    private void calculateTotalCostActionPerformed(JComboBox<String> symbolComboBox,
                                                   JTextField quantityTextField,
                                                   JTextField totalCostTextField) {
        try {
            String symbol = (String) symbolComboBox.getSelectedItem();
            BigDecimal stockPrice = frame.getMarketService().getStockPrice(symbol);

            try {
                int quantity = Integer.parseInt(quantityTextField.getText());
                DecimalFormat formatter = new DecimalFormat("#,##0.##");
                totalCostTextField.setText(
                        formatter.format(stockPrice.multiply(new BigDecimal(quantity))));
            } catch (NumberFormatException e) {
                totalCostTextField.setText("Invalid quantity value!");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(StockMarketJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
