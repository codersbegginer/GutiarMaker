import javax.swing.*;
import java.awt.*;

public class SignUpForm extends JPanel{

    JLabel           nameLabel = new JLabel("Username:"),
                    emailLabel = new JLabel("Email Address:"),
                     passLabel = new JLabel("Password"),
                 confPassLabel = new JLabel("Confirm Password"),
             secQuestionsLabel = new JLabel("Security Questions"),
                    ques1Label = new JLabel("Question 1: What is your mother's maiden name?"),
                    ques2Label = new JLabel("Question 2: What's the name of your first pet?"),
                    ques3Label = new JLabel("Question 3: Where's your father?");
    JTextField        username = new JTextField(),
                  emailAddress = new JTextField(),
                       answer1 = new JTextField(),
                       answer2 = new JTextField(),
                       answer3 = new JTextField();
    JPasswordField    password = new JPasswordField(),
                  confpassword = new JPasswordField();
    JButton            confirm = new JButton("Confirm"),
                   backtoLogin = new JButton("< BACK TO LOGIN");

    SignUpForm(){
        //SignUp Main Panel Layout
        setBounds(0,0,1000,700);
        setLayout(null);
        setVisible(false);
        SignUpLayout();
    }
    void SignUpLayout(){

        //BOUNDS
        nameLabel        .setBounds( 25, 100, 500, 50);
        emailLabel       .setBounds( 25, 200, 500, 50);
        passLabel        .setBounds( 25, 300, 500, 50);
        confPassLabel    .setBounds( 25, 400, 500, 50);

        secQuestionsLabel.setBounds(500, 100, 500, 50);
        ques1Label       .setBounds(500, 200, 500, 50);
        ques2Label       .setBounds(500, 300, 500, 50);
        ques3Label       .setBounds(500, 400, 500, 50);

        answer1          .setBounds(500, 175, 250, 50);
        answer2          .setBounds(500, 275, 250, 50);
        answer3          .setBounds(500, 375, 250, 50);

        username         .setBounds( 25, 150, 250, 50);
        emailAddress     .setBounds( 25, 250, 250, 50);
        password         .setBounds( 25, 350, 250, 50);
        confpassword     .setBounds( 25, 450, 250, 50);
        confirm          .setBounds( 25, 550, 200, 50);
        backtoLogin      .setBounds(  1,   1, 200, 50);

        //Fonts
        nameLabel        .setFont(new Font("Times New Roman",Font.BOLD,20));
        emailLabel       .setFont(new Font("Times New Roman",Font.BOLD,20));
        passLabel        .setFont(new Font("Times New Roman",Font.BOLD,20));
        confPassLabel    .setFont(new Font("Times New Roman",Font.BOLD,20));

        secQuestionsLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
        ques1Label       .setFont(new Font("Times New Roman",Font.BOLD,20));
        ques2Label       .setFont(new Font("Times New Roman",Font.BOLD,20));
        ques3Label       .setFont(new Font("Times New Roman",Font.BOLD,20));

        answer1          .setFont(new Font("Times New Roman",Font.BOLD,20));
        answer2          .setFont(new Font("Times New Roman",Font.BOLD,20));
        answer3          .setFont(new Font("Times New Roman",Font.BOLD,20));

        username         .setFont(new Font("Times New Roman",Font.BOLD,25));
        emailAddress     .setFont(new Font("Times New Roman",Font.BOLD,25));
        password         .setFont(new Font("Times New Roman",Font.BOLD,25));
        confpassword     .setFont(new Font("Times New Roman",Font.BOLD,25));
        confirm          .setFont(new Font("Times New Roman",Font.BOLD,25));
        backtoLogin      .setFont(new Font("Times New Roman",Font.BOLD,15));

        //ADDITIONALS
        backtoLogin.setFocusPainted(false);
        backtoLogin.setBorderPainted(false);
        backtoLogin.setContentAreaFilled(false);
        backtoLogin.setForeground(Color.BLUE);
        backtoLogin.setOpaque(false);

        add(nameLabel);
        add(emailLabel);
        add(passLabel);
        add(confPassLabel);
        add(secQuestionsLabel);
        add(ques1Label);
        add(ques2Label);
        add(ques3Label);
        add(answer1);
        add(answer2);
        add(answer3);
        add(username);
        add(emailAddress);
        add(password);
        add(confpassword);
        add(confirm);
        add(backtoLogin);
    }
}