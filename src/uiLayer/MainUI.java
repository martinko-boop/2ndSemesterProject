/**
 * This class is a part of the Booking System
 * developed for International House North Denmark.
 * It contains the MainUI which represents the
 * way the User interacts with the system.
 */

package uiLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.border.EmptyBorder;

import config.Config;
import databaseLayer.DBConnection;
import modelLayer.LogEntry;
import modelLayer.User;
import modelLayer.User.UserType;
import databus.Databus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

@SuppressWarnings("serial")
public class MainUI extends JFrame
{

    private Config config;
    private JPanel contentPane;
    private CardLayout cards;
    private BookingPanel bookingPanel;
    private JPanel mainPanel;
    private JButton bookingButton;
    private JButton usersButton;
    private JButton roomsButton;
    private JButton selectedPageButton;
    private static JTextArea logTextArea;

    /**
     * Create the frame.
     * 
     * @throws SQLException
     */
    public MainUI(User loggedUser) throws SQLException
    {
        config = new Config();
        Locale.setDefault(Locale.UK);
        bookingPanel = new BookingPanel(loggedUser);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 871, 500);
        contentPane = new JPanel();
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{ 0, 0 };
        gbl_contentPane.rowHeights = new int[]{ 0 };
        gbl_contentPane.columnWeights = new double[]{ 0.1, 1.0 };
        gbl_contentPane.rowWeights = new double[]{ Double.MIN_VALUE };
        contentPane.setLayout(gbl_contentPane);

        this.addWindowListener(new WindowAdapter()
        {
            // Close the connection when app is closed. (logout triggers windownClosed)
            @Override
            public void windowClosing(WindowEvent e)
            {
                DBConnection.getInstance().disconnect();
            }
        });

        // Creates the sidebar containing the menu
        JPanel sidebarPanel = new JPanel();
        GridBagConstraints gbc_sidebarPanel = new GridBagConstraints();
        sidebarPanel.setBackground(config.getBlueColorDefault());
        sidebarPanel.setPreferredSize(new Dimension(140, 0));

        gbc_sidebarPanel.fill = GridBagConstraints.BOTH;
        gbc_sidebarPanel.gridx = 0;
        gbc_sidebarPanel.gridy = 0;
        contentPane.add(sidebarPanel, gbc_sidebarPanel);
        GridBagLayout gbl_sidebarPanel = new GridBagLayout();
        gbl_sidebarPanel.columnWidths = new int[]{ 0, 0, 0, 0 };
        gbl_sidebarPanel.rowHeights = new int[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_sidebarPanel.columnWeights = new double[]{ 0.1, 1.0, 0.0, Double.MIN_VALUE };
        gbl_sidebarPanel.rowWeights = new double[]{ 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.4, Double.MIN_VALUE };
        sidebarPanel.setLayout(gbl_sidebarPanel);

        // Set the image icon of the button and scale is to 50x50
        URL url = getClass().getResource("images/Icon.png");
        ImageIcon icon = new ImageIcon(url, "YOU");
        icon = new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JButton avatarButton = new JButton(icon);
        GridBagConstraints gbc_avatarButton = new GridBagConstraints();
        avatarButton.setContentAreaFilled(false);
        avatarButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        avatarButton.setFocusable(false);
        gbc_avatarButton.insets = new Insets(10, 10, 10, 10);
        gbc_avatarButton.fill = GridBagConstraints.BOTH;
        gbc_avatarButton.gridheight = 2;
        gbc_avatarButton.gridx = 0;
        gbc_avatarButton.gridy = 0;
        sidebarPanel.add(avatarButton, gbc_avatarButton);

        // User name label
        JLabel userNameLabel = new JLabel("User Name");
        userNameLabel.setText(loggedUser.getName());
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setFont(new Font("Roboto", Font.BOLD, 15));
        GridBagConstraints gbc_userNameLabel = new GridBagConstraints();
        gbc_userNameLabel.insets = new Insets(25, 0, 0, 0);
        gbc_userNameLabel.anchor = GridBagConstraints.SOUTHWEST;
        gbc_userNameLabel.gridx = 1;
        gbc_userNameLabel.gridy = 0;
        sidebarPanel.add(userNameLabel, gbc_userNameLabel);

        // User type label
        JLabel userTypeLabel = new JLabel("User Type");
        userTypeLabel.setText(loggedUser.getUserType().getType());
        userTypeLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
        userTypeLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc_userTypeLabel = new GridBagConstraints();
        gbc_userTypeLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_userTypeLabel.gridx = 1;
        gbc_userTypeLabel.gridy = 1;
        sidebarPanel.add(userTypeLabel, gbc_userTypeLabel);

        // Logout button
        JButton logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                LoginDialog.main(null);
            }
        });
        formatSidebarButton(logOutButton);
        GridBagConstraints gbc_logOutButton = new GridBagConstraints();
        gbc_logOutButton.anchor = GridBagConstraints.WEST;
        gbc_logOutButton.insets = new Insets(20, 0, 20, 10);
        gbc_logOutButton.gridheight = 2;
        gbc_logOutButton.fill = GridBagConstraints.VERTICAL;
        gbc_logOutButton.gridx = 2;
        gbc_logOutButton.gridy = 0;
        sidebarPanel.add(logOutButton, gbc_logOutButton);

        // Line that separates User section from Pages
        JSeparator separator = new JSeparator();
        GridBagConstraints gbc_separator = new GridBagConstraints();
        separator.setForeground(config.getSeparatorColor());
        separator.setBackground(config.getSeparatorColor());
        separator.setOpaque(true);
        gbc_separator.gridwidth = 3;
        gbc_separator.gridx = 0;
        gbc_separator.gridy = 2;
        gbc_separator.fill = GridBagConstraints.BOTH;
        sidebarPanel.add(separator, gbc_separator);

        // Booking page button
        bookingButton = new JButton("Booking");
        bookingButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                selectPage("Bookings");
            }
        });
        formatSidebarButton(bookingButton);
        bookingButton.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_bookingButton = new GridBagConstraints();
        gbc_bookingButton.insets = new Insets(20, 20, 5, 60);
        gbc_bookingButton.gridwidth = 3;
        gbc_bookingButton.fill = GridBagConstraints.BOTH;
        gbc_bookingButton.gridx = 0;
        gbc_bookingButton.gridy = 3;
        sidebarPanel.add(bookingButton, gbc_bookingButton);

        // User page button
        usersButton = new JButton("Users");
        if (loggedUser.getUserType() == UserType.DEFAULT)
        {
            usersButton.setVisible(false);
        }
        usersButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                selectPage("Users");
            }
        });
        GridBagConstraints gbc_usersButton = new GridBagConstraints();
        gbc_usersButton.insets = new Insets(0, 20, 5, 60);
        gbc_usersButton.gridwidth = 3;
        gbc_usersButton.fill = GridBagConstraints.BOTH;
        formatSidebarButton(usersButton);
        usersButton.setHorizontalAlignment(SwingConstants.LEFT);
        gbc_usersButton.gridx = 0;
        gbc_usersButton.gridy = 4;
        sidebarPanel.add(usersButton, gbc_usersButton);

        // Room page button
        roomsButton = new JButton("Rooms");
        if (loggedUser.getUserType() == UserType.DEFAULT)
        {
            roomsButton.setVisible(false);
        }
        roomsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                selectPage("Rooms");
            }
        });
        GridBagConstraints gbc_roomsButton = new GridBagConstraints();
        gbc_roomsButton.insets = new Insets(0, 20, 5, 60);
        gbc_roomsButton.anchor = GridBagConstraints.NORTH;
        gbc_roomsButton.gridwidth = 3;
        gbc_roomsButton.fill = GridBagConstraints.HORIZONTAL;
        formatSidebarButton(roomsButton);
        roomsButton.setHorizontalAlignment(SwingConstants.LEFT);
        gbc_roomsButton.gridx = 0;
        gbc_roomsButton.gridy = 5;
        sidebarPanel.add(roomsButton, gbc_roomsButton);

        // Line that separates Pages section from Logs
        JSeparator logSeparator = new JSeparator();

        if (loggedUser.getUserType() == UserType.DEFAULT)
        {
            logSeparator.setVisible(false);
        }
        GridBagConstraints gbc_logSeparator = new GridBagConstraints();
        gbc_logSeparator.anchor = GridBagConstraints.SOUTH;
        logSeparator.setForeground(config.getSeparatorColor());
        logSeparator.setOpaque(true);
        logSeparator.setPreferredSize(new Dimension(sidebarPanel.getWidth(), 4));
        gbc_logSeparator.fill = GridBagConstraints.HORIZONTAL;
        gbc_logSeparator.gridwidth = 3;
        gbc_logSeparator.gridx = 0;
        gbc_logSeparator.gridy = 6;
        sidebarPanel.add(logSeparator, gbc_logSeparator);

        JPanel logPanel = new JPanel();
        if (loggedUser.getUserType() == UserType.DEFAULT)
        {
            logPanel.setVisible(false);
        }
        GridBagConstraints gbc_logPanel = new GridBagConstraints();
        logPanel.setBackground(config.getBlueColorDefault());
        gbc_logPanel.gridwidth = 3;
        gbc_logPanel.fill = GridBagConstraints.BOTH;
        gbc_logPanel.gridx = 0;
        gbc_logPanel.gridy = 7;
        sidebarPanel.add(logPanel, gbc_logPanel);
        GridBagLayout gbl_logPanel = new GridBagLayout();
        gbl_logPanel.columnWidths = new int[]{ 0 };
        gbl_logPanel.rowHeights = new int[]{ 0, 0, 0 };
        gbl_logPanel.columnWeights = new double[]{ 1.0 };
        gbl_logPanel.rowWeights = new double[]{ 0.0, 1.0, Double.MIN_VALUE };
        logPanel.setLayout(gbl_logPanel);

        JLabel logLabel = new JLabel("LOG");
        logLabel.setFont(config.getLabelTitleFont());
        logLabel.setForeground(Color.WHITE);
        logLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_logLabel = new GridBagConstraints();
        gbc_logLabel.insets = new Insets(5, 0, 0, 0);
        gbc_logLabel.gridx = 0;
        gbc_logLabel.gridy = 0;
        logPanel.add(logLabel, gbc_logLabel);

        logTextArea = new JTextArea();
        logTextArea.setWrapStyleWord(true);
        logTextArea.setLineWrap(true);
        logTextArea.setForeground(config.getFrontPanelDefaultColor());
        logTextArea.setFont(config.getLabelTitleFont());
        logTextArea.setEditable(false);
        logTextArea.setBackground(config.getBlueColorDefault());

        JScrollPane scrollPanel = new JScrollPane(logTextArea);
        scrollPanel.setBorder(config.getEmptyBorderZeros());
        GridBagConstraints gbc_scrollPanel = new GridBagConstraints();
        gbc_scrollPanel.insets = new Insets(5, 10, 0, 10);
        gbc_scrollPanel.fill = GridBagConstraints.BOTH;
        gbc_scrollPanel.gridx = 0;
        gbc_scrollPanel.gridy = 1;
        logPanel.add(scrollPanel, gbc_scrollPanel);

        mainPanel = new JPanel();
        GridBagConstraints gbc_mainPanel = new GridBagConstraints();
        gbc_mainPanel.fill = GridBagConstraints.BOTH;
        gbc_mainPanel.gridx = 1;
        gbc_mainPanel.gridy = 0;
        contentPane.add(mainPanel, gbc_mainPanel);
        cards = new CardLayout(0, 0);
        mainPanel.setLayout(cards);
        mainPanel.add(bookingPanel, "Bookings");
        formatSelectedSidebarButton(bookingButton);
        selectedPageButton = bookingButton;

        updateLog();
    }

    /**
     * A method to select a page when using the sidebar buttons
     * @param page
     */
    private void selectPage(String page)
    {
        formatSidebarButton(selectedPageButton);
        switch (page)
        {
        case "Bookings":
            cards.show(mainPanel, page);
            selectedPageButton = bookingButton;
            break;
        case "Users":
            cards.show(mainPanel, page);
            selectedPageButton = usersButton;
            break;
        case "Rooms":
            cards.show(mainPanel, page);
            selectedPageButton = roomsButton;
            break;
        }
        formatSelectedSidebarButton(selectedPageButton);
    }

    /**
     * A method to format the sidebar buttons
     * @param button
     */
    private void formatSidebarButton(JButton button)
    {
        button.setForeground(config.getButtonDefaultForeground());
        button.setBackground(config.getBlueColorDefault());
        button.setBorder(config.getSidebarButtonBorder());
        button.setFocusable(false);
        button.setFont(config.getButtonDefaultFont());
    }

    /**
     * A method to format the selected format button
     * @param button
     */
    private void formatSelectedSidebarButton(JButton button)
    {
        button.setForeground(config.getBlueColorDefault());
        button.setBackground(config.getSelectedSidebarButtonColor());
        button.setOpaque(true);
    }

    /**
     * A method to update the log text area
     * @throws SQLException
     */
    public static void updateLog() throws SQLException
    {
        logTextArea.setText("");
        ArrayList<LogEntry> logs = Databus.getInstance().getLogs();
        logs.parallelStream().forEach(log -> logTextArea.append(" -" + log.getAction() + "\n"));
        Databus.getInstance().queryLogs();
    }
}
