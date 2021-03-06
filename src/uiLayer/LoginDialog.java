package uiLayer;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import config.Config;
import controlLayer.UserController;
import modelLayer.User;
import databus.Databus;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog
{
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private final Config config;
    private UserController userController;
    private JPanel errorPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
    	try 
    	{
    		Databus.getInstance().startUp();
		} 
    	catch (SQLException e1) 
    	{
			e1.printStackTrace();
		}
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    // Remove the system's scale factor on the UI elements
                    System.setProperty("sun.java2d.uiScale", "1.0");
                    LoginDialog dialog = new LoginDialog();
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                    // Centres the dialog
                    dialog.setLocationRelativeTo(null);
                    URL url = getClass().getResource("images/ihndLogo.png");
                    ImageIcon icon = new ImageIcon(url);
                    dialog.setIconImage(icon.getImage());
                    dialog.setTitle("Login - IHND Booking System");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the dialog.
     * 
     * @throws SQLException
     */
    public LoginDialog()
    {
        try
        {
            userController = new UserController();
        }
        catch (SQLException error)
        {
            error.printStackTrace();
        }

        config = new Config();

        setBounds(100, 100, 931, 601);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{ 0, 0 };
        gridBagLayout.rowHeights = new int[]{ 43, 0 };
        gridBagLayout.columnWeights = new double[]{ 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]{ 0.0, 1.0 };
        getContentPane().setLayout(gridBagLayout);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(config.getBlueColorDefault());
        GridBagConstraints gbc_titlePanel = new GridBagConstraints();
        gbc_titlePanel.insets = new Insets(0, 0, 5, 0);
        gbc_titlePanel.fill = GridBagConstraints.BOTH;
        gbc_titlePanel.gridx = 0;
        gbc_titlePanel.gridy = 0;
        getContentPane().add(titlePanel, gbc_titlePanel);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titlePanel.add(titleLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc_mainPanel = new GridBagConstraints();
        gbc_mainPanel.insets = new Insets(30, 30, 80, 30);
        gbc_mainPanel.fill = GridBagConstraints.BOTH;
        gbc_mainPanel.gridx = 0;
        gbc_mainPanel.gridy = 1;
        getContentPane().add(mainPanel, gbc_mainPanel);
        GridBagLayout gbl_mainPanel = new GridBagLayout();
        gbl_mainPanel.columnWidths = new int[]{ 0, 0, 0 };
        gbl_mainPanel.rowHeights = new int[]{ 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_mainPanel.columnWeights = new double[]{ 0.6, 0.6, 0.6 };
        gbl_mainPanel.rowWeights = new double[]{ 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, Double.MIN_VALUE };
        mainPanel.setLayout(gbl_mainPanel);

        errorPanel = new JPanel();
        errorPanel.setVisible(false);
        errorPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc_errorPanel = new GridBagConstraints();
        gbc_errorPanel.insets = new Insets(0, 0, 5, 5);
        gbc_errorPanel.anchor = GridBagConstraints.NORTH;
        gbc_errorPanel.fill = GridBagConstraints.BOTH;
        gbc_errorPanel.gridx = 0;
        gbc_errorPanel.gridy = 1;
        gbc_errorPanel.gridwidth = 3;
        mainPanel.add(errorPanel, gbc_errorPanel);

        JPanel errorFrame = new JPanel();
        errorFrame.setBorder(new LineBorder(Color.red));
        errorFrame.setBackground(Color.WHITE);
        errorPanel.add(errorFrame);

        JLabel errorLabel = new JLabel("The email or the password is incorrect!");
        errorLabel.setForeground(Color.RED);
        errorFrame.add(errorLabel);

        JLabel emailLabel = new JLabel("Email");
        GridBagConstraints gbc_emailLabel = new GridBagConstraints();
        gbc_emailLabel.anchor = GridBagConstraints.WEST;
        gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
        gbc_emailLabel.gridx = 1;
        gbc_emailLabel.gridy = 2;
        mainPanel.add(emailLabel, gbc_emailLabel);

        emailTextField = new JTextField();
        emailTextField.setForeground(config.getLabelDefaultForeground());
        emailTextField.setFont(config.getLogSize());
        emailTextField.setBorder(BorderFactory.createLineBorder(new Color(212, 212, 212), 1));
        GridBagConstraints gbc_emailTextField = new GridBagConstraints();
        gbc_emailTextField.insets = new Insets(0, 0, 5, 5);
        gbc_emailTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_emailTextField.gridx = 1;
        gbc_emailTextField.gridy = 3;
        mainPanel.add(emailTextField, gbc_emailTextField);
        emailTextField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password");
        GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
        gbc_passwordLabel.anchor = GridBagConstraints.WEST;
        gbc_passwordLabel.insets = new Insets(30, 0, 5, 5);
        gbc_passwordLabel.gridx = 1;
        gbc_passwordLabel.gridy = 4;
        mainPanel.add(passwordLabel, gbc_passwordLabel);

        passwordTextField = new JPasswordField();
        passwordTextField.setForeground(config.getLabelDefaultForeground());
        passwordTextField.setFont(config.getLogSize());
        passwordTextField.setBorder(BorderFactory.createLineBorder(new Color(212, 212, 212), 1));
        GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
        gbc_passwordTextField.insets = new Insets(0, 0, 5, 5);
        gbc_passwordTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordTextField.gridx = 1;
        gbc_passwordTextField.gridy = 5;
        mainPanel.add(passwordTextField, gbc_passwordTextField);
        passwordTextField.setColumns(10);

        JButton loginButton = new JButton("Login");
        loginButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                loginButton.setBackground(config.getBlueColorDefault().brighter());
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                loginButton.setBackground(config.getBlueColorDefault());
            }
        });
        this.getRootPane().setDefaultButton(loginButton);
        loginButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                openMainUI();
            }
        });
        
        loginButton.setBorder(new EmptyBorder(8, 50, 8, 50));
        loginButton.setFocusable(false);
        loginButton.setBackground(config.getBlueColorDefault());
        loginButton.setFont(config.getButtonDefaultFont());
        loginButton.setForeground(config.getButtonDefaultForeground());
        loginButton.setOpaque(true);
        GridBagConstraints gbc_loginButton = new GridBagConstraints();
        gbc_loginButton.insets = new Insets(0, 0, 0, 5);
        gbc_loginButton.gridx = 1;
        gbc_loginButton.gridy = 6;
        mainPanel.add(loginButton, gbc_loginButton);
    }

    /**
     * A method to check if the login credentials are right and open the main application
     */
    private void openMainUI()
    {
        User loggedUser;

        try
        {
            loggedUser = userController.getUser(emailTextField.getText(), String.valueOf(passwordTextField.getPassword()));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return;
        }

        if (loggedUser == null)
        {
            passwordTextField.setBorder(new LineBorder(config.getErrorMessageColor()));
            emailTextField.setBorder(new LineBorder(config.getErrorMessageColor()));
            errorPanel.setVisible(true);
            return;
        }

        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    MainUI frame = new MainUI(loggedUser);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                    URL url = getClass().getResource("images/ihndLogo.png");
                    ImageIcon icon = new ImageIcon(url);
                    frame.setIconImage(icon.getImage());
                    frame.setTitle("IHND Booking System");
                    dispose();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    
   /**
    *  Unused method. Later can be used
    */
    /*
      private static byte[] encryptionForLife(String password) {
		String newPWD = "";
		int firstLetter = 25;
		boolean wasThereLetter = false;
		int numberCount = 0;
		for (int e = 0 ; e < password.length() ; e++)
		{
			if ((int) password.charAt(e) >= 97 && (int) password.charAt(e) <= 122)
			{
				if (firstLetter == 0 || wasThereLetter == false)
				{
					firstLetter = password.charAt(e);
				}
				if ((((int) password.charAt(e)) - 34) < 65)
				{
					newPWD += (char) ((((((int) password.charAt(e)) - 7)) - ((((int) 'A')) - (((int) password.charAt(e)) - 34))));
				}
				else
				{
					newPWD += (char) (((int) password.charAt(e)) - 34);
				}

			}
			else if ((int) password.charAt(e) >= 65 && (int) password.charAt(e) <= 90)
			{
				if (firstLetter == 0 || wasThereLetter == false)
				{
					firstLetter = password.charAt(e);
				}
				if ((((int) password.charAt(e)) + 34) > 122)
				{
					newPWD += (char) ((((((int) password.charAt(e)) + 7)) - ((((int) password.charAt(e)) - 34) - (((int) 'a')))));
				}
				else
				{
					newPWD += (char) (((int) password.charAt(e)) + 34);
				}
			}
			else if (Character.isDigit(password.charAt(e)))
			{
				newPWD += (Character.getNumericValue(password.charAt(e)) + 1) * (Character.getNumericValue(password.charAt(e)) + 1);
				newPWD += (char) ++numberCount;
				newPWD += (Character.getNumericValue(password.charAt(e)) + 1) * (Character.getNumericValue(password.charAt(e)) + 1);
			}
			else
			{
				newPWD += (char) (((int) password.charAt(e) + firstLetter));
				wasThereLetter = true;
			}
		}
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
		} catch (NoSuchAlgorithmException e) {}
		return md.digest(newPWD.getBytes(StandardCharsets.UTF_8));

	}
	*/
}
