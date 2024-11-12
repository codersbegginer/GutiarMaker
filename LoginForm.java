import encapsulation.AccountCredentials;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame implements ActionListener{

    //LOGIN FORM
    JLabel        titleLabel = new JLabel("TitleProduct" ),
                   nameLabel = new JLabel("Username: "   ),
                   passLabel = new JLabel("Password: "   ),

                invalidLabel = new JLabel("Invalid Credentials"),
             maxAttemptLabel = new JLabel("Maximum Attempts Reached"),
                counterLabel = new JLabel("Please wait for: "),
                attemptLabel = new JLabel("Attempts Made: "),

                      advert = new JLabel(new ImageIcon("C:\\Users\\cesar\\OneDrive\\Desktop\\Case Study\\src\\Images\\advert.jpg")),
                        logo = new JLabel(new ImageIcon("C:\\Users\\cesar\\OneDrive\\Desktop\\Case Study\\src\\Images\\sampleLogo.jpg"));

    JButton     forgotButton = new JButton("Forgot Password?"),
                 loginButton = new JButton("LOG IN"),
                signUpButton = new JButton("SIGN UP"),
              showPassButton = new JButton("X"),
                   clickHere = new JButton("Click Here");

    JTextField      username = new JTextField();
    JPasswordField  password = new JPasswordField();

    //UTILITIES
    AccountCredentials verification = new AccountCredentials();

    boolean   passVisibility = false,
                  maxCounter = false;

    int         errorCounter = 0,
                 maxAttempts = 0,
                   countdown = 0,
                 windowState = 0;

    //LOGIN FORM
    JPanel                    loginPanel = new JPanel();
    //SIGN UP FORM
    SignUpForm             signUpHandler = new SignUpForm();
    //FORGOT PASSWORD FORM
    ForgotPasswordForm forgotPassHandler = new ForgotPasswordForm();

    LoginForm() {
        //Main Frame
        setTitle("Login Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        add(loginPanel);
        add(signUpHandler);
        add(forgotPassHandler);

        signUpHandler.backtoLogin.addActionListener(this);
        signUpHandler.confirm.addActionListener(this);
        forgotPassHandler.backtoLogin.addActionListener(this);
        forgotPassHandler.validateButton.addActionListener(this);
        forgotPassHandler.confirmButton.addActionListener(this);

        loginPanel.setLayout(null);
        //Fonts
        forgotButton   .setFont(new Font("Times New Roman", Font.BOLD, 15));
        loginButton    .setFont(new Font("Times New Roman", Font.BOLD, 20));
        signUpButton   .setFont(new Font("Times New Roman", Font.BOLD, 20));
        showPassButton .setFont(new Font("Times New Roman", Font.BOLD, 20));

        titleLabel     .setFont(new Font("Times New Roman", Font.BOLD, 45));
        nameLabel      .setFont(new Font("Times New Roman", Font.BOLD, 20));
        passLabel      .setFont(new Font("Times New Roman", Font.BOLD, 20));

        invalidLabel   .setFont(new Font("Times New Roman", Font.BOLD, 18));
        maxAttemptLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        counterLabel   .setFont(new Font("Times New Roman", Font.BOLD, 18));
        attemptLabel   .setFont(new Font("Times New Roman", Font.BOLD, 18));
        clickHere      .setFont(new Font("Times New Roman", Font.BOLD, 20));

        username       .setFont(new Font("Times New Roman", Font.BOLD, 15));
        password       .setFont(new Font("Times New Roman", Font.BOLD, 15));

        //Bounds
        loginPanel     .setBounds(  0,   0,1000, 700);
        forgotButton   .setBounds(700, 320, 200,  50);
        loginButton    .setBounds(700, 370, 200,  50);
        signUpButton   .setBounds(700, 440, 200,  50);
        showPassButton .setBounds(875, 275,  50,  40);

        titleLabel     .setBounds(710,  75, 500,  50);
        nameLabel      .setBounds(675, 160, 500,  50);
        passLabel      .setBounds(675, 235, 500,  50);

        invalidLabel   .setBounds(725, 305, 500,  50);
        maxAttemptLabel.setBounds(685, 305, 500,  50);
        counterLabel   .setBounds(695, 325, 500,  50);
        attemptLabel   .setBounds(725, 325, 500,  50);
        clickHere      .setBounds(700, 395, 200,  50);

        advert         .setBounds(  0, -30, 600, 730);
        logo           .setBounds(645,  75,  50,  50);

        username       .setBounds(675, 200, 250,  40);
        password       .setBounds(675, 275, 200,  40);

        //Additionals
        forgotButton   .setVisible(true );
        loginButton    .setVisible(true );
        signUpButton   .setVisible(true );
        showPassButton .setVisible(true );
        titleLabel     .setVisible(true );
        nameLabel      .setVisible(true );
        passLabel      .setVisible(true );

        invalidLabel   .setVisible(false);
        maxAttemptLabel.setVisible(false);
        counterLabel   .setVisible(false);
        attemptLabel   .setVisible(false);
        clickHere      .setVisible(false);
        signUpHandler  .setVisible(false);

        advert         .setVisible(true );
        logo           .setVisible(true );
        username       .setVisible(true );
        password       .setVisible(true );

        forgotButton   .setFocusPainted(false);
        loginButton    .setFocusPainted(false);
        signUpButton   .setFocusPainted(false);
        clickHere      .setFocusPainted(false);
        showPassButton .setFocusPainted(false);

        forgotButton   .addActionListener(this);
        loginButton    .addActionListener(this);
        signUpButton   .addActionListener(this);
        showPassButton .addActionListener(this);
        clickHere      .removeActionListener(this);

        forgotButton   .setForeground(Color.BLUE);
        forgotButton   .setBorderPainted(false);
        forgotButton   .setContentAreaFilled(false);

        username       .setEditable(true);
        password       .setEditable(true);
        password       .setEchoChar('*');

        loginPanel.add(forgotButton   );
        loginPanel.add(loginButton    );
        loginPanel.add(signUpButton   );
        loginPanel.add(showPassButton );
        loginPanel.add(titleLabel     );
        loginPanel.add(nameLabel      );
        loginPanel.add(passLabel      );
        loginPanel.add(invalidLabel   );
        loginPanel.add(maxAttemptLabel);
        loginPanel.add(counterLabel   );
        loginPanel.add(attemptLabel   );
        loginPanel.add(clickHere      );
        loginPanel.add(advert         );
        loginPanel.add(logo           );
        loginPanel.add(username       );
        loginPanel.add(password       );

        LoginLayout();
    }

    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if(windowState==0){LoginFunctions(event);}
        else if (windowState==1){SignUpFunctions(event);}
        else if (windowState==2){ForgotPassFunctions(event);}
    }

    void LoginLayout(){
        loginPanel.setVisible(true);
        signUpHandler.setVisible(false);
        forgotPassHandler.setVisible(false);
        windowState  = 0;
        errorCounter = 0;
    }
    void SignUpLayout(){
        windowState = 1;
        signUpHandler.setVisible(true);
        loginPanel.setVisible(false);
    }
    void ForgotPassLayout(){
        windowState = 2;
        loginPanel.setVisible(false);
        forgotPassHandler.setVisible(true);
    }
    void LoginFunctions(Object event){
        if(event == showPassButton) {
            if (!passVisibility) {
                showPassButton.setText("0");
                passVisibility = true;
                password.setEchoChar((char) 0);
            } else {
                showPassButton.setText("X");
                passVisibility = false;
                password.setEchoChar('*');
            }
        }
        if(event == forgotButton) {ForgotPassLayout();}
        if(event == loginButton ) {verify();         }
        if(event == signUpButton) {SignUpLayout();   }

        if(event == clickHere){
            countdown --;
            counterLabel.setText("Please wait for " + countdown    + " clicks");
            if(maxCounter && countdown==0){
                maxCounter = false;
                errorCounter = 0;
                LoginLayout();}
        }
    }
    void SignUpFunctions(Object event){
        if(event == signUpHandler.backtoLogin){LoginLayout();}
        if(event == signUpHandler.confirm){addAccount();}
    }
    void ForgotPassFunctions(Object event){
        if(event == forgotPassHandler.backtoLogin){LoginLayout();}
        if(event == forgotPassHandler.validateButton){
            verification.checkEmail(forgotPassHandler.emailAddress.getText());
            if(verification.existingEmail){
                JOptionPane.showMessageDialog(this,"Email successfully validated.");
                activation();
            }
        }
        if(event == forgotPassHandler.confirmButton){
            String[] answers = {forgotPassHandler.answer1.getText(),
                                forgotPassHandler.answer2.getText(),
                                forgotPassHandler.answer3.getText()};
            String[] data = verification.checkStoredAnswers(verification.x,answers);
            if(verification.confirmation){
                JOptionPane.showMessageDialog(this,"Username: " + data[0] + "\nPassword: " + data[1]);
                data[0] = "";
                data[1] = "";
            }
            else{
                JOptionPane.showMessageDialog(this,"Invalid Answers.");
                forgotPassHandler.answer1.setText("");
                forgotPassHandler.answer2.setText("");
                forgotPassHandler.answer3.setText("");
            }
        }
    }
    void verify(){
        boolean verify = false;
        String[][] credentials = verification.getStoredData();
        for (String[] credential : credentials) {
            if (credential[0].equals(username.getText()) && credential[1].equals(new String(password.getPassword()))) {
                verify = true;
            }
        }
        if(!verify){error();}
        else{
            //OPEN MAIN SHOPPING FRAME
        }
    }
    void error(){
        if      (errorCounter<2){errorCounter++;}
        else if (!maxCounter   ){
            maxCounter = true;
            maxAttempts++;
            countdown = maxAttempts * 3;
            loginButton.removeActionListener(this);
            clickHere.addActionListener(this);}

        errorLayout();
    }
    void errorLayout(){
        forgotButton   .setBounds(700, 350, 200, 50);
        loginButton    .setBounds(700, 400, 200, 50);
        signUpButton   .setBounds(700, 470, 200, 50);

        loginButton    .setVisible(!maxCounter);
        clickHere      .setVisible(maxCounter);

        invalidLabel   .setVisible(!maxCounter);
        attemptLabel   .setVisible(!maxCounter);

        maxAttemptLabel.setVisible(maxCounter);
        counterLabel   .setVisible(maxCounter);

        attemptLabel.setText("Attempts Made: "  + errorCounter            );
        counterLabel.setText("Please wait for " + countdown    + " clicks");
    }
    void addAccount(){
        String display = "";
        if (new String(signUpHandler.password    .getPassword()).isEmpty() ||
            new String(signUpHandler.confpassword.getPassword()).isEmpty() ||
                       signUpHandler.username    .getText()     .isEmpty() ||
                       signUpHandler.emailAddress.getText()     .isEmpty() ||
                       signUpHandler.answer1     .getText()     .isEmpty() ||
                       signUpHandler.answer2     .getText()     .isEmpty() ||
                       signUpHandler.answer3     .getText()     .isEmpty()  )
            {display = "Credentials should not be empty.";}

        else if(!new String(signUpHandler.password.getPassword()).equals(new String(signUpHandler.confpassword.getPassword()))){
            display = "Passwords do not match.";}
        else {
        verification.checkExistingData(signUpHandler.username.getText(),signUpHandler.emailAddress.getText());
        if(verification.existingEmail||verification.existingName){
            display = "Account Credentials already exists";}
        else{
            String[] data    = {username.getText(),new String(password.getPassword())};
            String[] answers = {signUpHandler.answer1.getText(),
                                signUpHandler.answer2.getText(),
                                signUpHandler.answer3.getText()};
            verification.setStoredData(data,signUpHandler.emailAddress.getText(),answers);
            display = "Account successfully signed up.";}
        }
        JOptionPane.showMessageDialog(this,display);
    }
    void activation(){
        forgotPassHandler.answer1.setEditable(true);
        forgotPassHandler.answer2.setEditable(true);
        forgotPassHandler.answer3.setEditable(true);
        forgotPassHandler.confirmButton.setEnabled(true);
    }
    public static void main(String[] args) {new LoginForm();}
}





