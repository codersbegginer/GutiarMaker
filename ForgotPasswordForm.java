import javax.swing.*;
import java.awt.*;

public class ForgotPasswordForm extends JPanel {

    JLabel        titleLabel = new JLabel("TitleProduct"),
                  emailLabel = new JLabel("Email Address:"),
           secQuestionsLabel = new JLabel("Security Questions"),
                  ques1Label = new JLabel("What is your mother's maiden name?"),
                  ques2Label = new JLabel("What's the name of your first pet?"),
                  ques3Label = new JLabel("Where's your father?"),
                      advert = new JLabel(new ImageIcon("C:\\Users\\cesar\\OneDrive\\Desktop\\Case Study\\src\\Images\\advert.jpg")),
                        logo = new JLabel(new ImageIcon("C:\\Users\\cesar\\OneDrive\\Desktop\\Case Study\\src\\Images\\sampleLogo.jpg"));


    JPanel   questionsPanel = new JPanel();
    JTextField emailAddress = new JTextField(),
                    answer1 = new JTextField(),
                    answer2 = new JTextField(),
                    answer3 = new JTextField();

    JButton    backtoLogin  = new JButton("< BACK TO LOGIN"),
             validateButton = new JButton("Validate"),
              confirmButton = new JButton ("Confirm");

    ForgotPasswordForm(){
        setBounds(0,0,1000,700);
        setLayout(null);
        setVisible(false);
        ForgotPassLayout();
    }

    void ForgotPassLayout(){
        //BOUNDS
        logo          .setBounds( 45,  75,  50,  50);
        advert        .setBounds(400, -30, 600, 730);
        backtoLogin   .setBounds(  1,   1, 200,  50);

        titleLabel    .setBounds(110,  75, 500,  50);
        emailLabel    .setBounds( 30, 150, 250,  50);
        emailAddress  .setBounds( 30, 200, 200,  50);

        validateButton.setBounds(230, 200, 140,  50);
        confirmButton .setBounds(130, 590, 150,  50);

        //FONT
        backtoLogin   .setFont(new Font("Times New Roman", Font.BOLD, 15));
        titleLabel    .setFont(new Font("Times New Roman", Font.BOLD, 45));
        emailLabel    .setFont(new Font("Times New Roman", Font.BOLD, 20));
        emailAddress  .setFont(new Font("Times New Roman", Font.BOLD, 20));
        validateButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        confirmButton .setFont(new Font("Times New Roman", Font.BOLD, 15));

        //ADDITIONALS
        backtoLogin.setFocusPainted(false);
        backtoLogin.setBorderPainted(false);
        backtoLogin.setContentAreaFilled(false);
        backtoLogin.setForeground(Color.BLUE);
        backtoLogin.setOpaque(false);
        confirmButton.setEnabled(false);

        add(questionsPanel);
        add(titleLabel    );
        add(logo          );
        add(advert        );
        add(backtoLogin   );
        add(emailLabel    );
        add(emailAddress  );
        add(validateButton);
        add(confirmButton );

        questionsPanel();
    }
    void questionsPanel(){
        //PANEL
        questionsPanel.setLayout(null);
        questionsPanel.setBounds(30,275,340,300);
        questionsPanel.setBackground(Color.LIGHT_GRAY);

        //BOUNDS
        secQuestionsLabel.setBounds(75,   0, 300, 50);
        ques1Label       .setBounds(10,  50, 300, 40);
        ques2Label       .setBounds(10, 130, 300, 40);
        ques3Label       .setBounds(10, 210, 300, 40);
        answer1          .setBounds(10,  90, 320, 40);
        answer2          .setBounds(10, 170, 320, 40);
        answer3          .setBounds(10, 250, 320, 40);

        //FONT
        secQuestionsLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        ques1Label       .setFont(new Font("Times New Roman", Font.BOLD, 18));
        ques2Label       .setFont(new Font("Times New Roman", Font.BOLD, 18));
        ques3Label       .setFont(new Font("Times New Roman", Font.BOLD, 18));

        answer1          .setFont(new Font("Times New Roman", Font.BOLD, 15));
        answer2          .setFont(new Font("Times New Roman", Font.BOLD, 15));
        answer3          .setFont(new Font("Times New Roman", Font.BOLD, 15));

        //ADDITIONALS
        answer1.setEditable(false);
        answer2.setEditable(false);
        answer3.setEditable(false);

        questionsPanel   .add(secQuestionsLabel);
        questionsPanel   .add(ques1Label);
        questionsPanel   .add(ques2Label);
        questionsPanel   .add(ques3Label);
        questionsPanel   .add(answer1);
        questionsPanel   .add(answer2);
        questionsPanel   .add(answer3);
    }
    public static void main(String[] args){new ForgotPasswordForm();}
}
