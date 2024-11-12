    package encapsulation;

class StoredCredentials {
    //Main Credentials                        //Account0                            //Account1           <-Visuals
    private String[][]       storedData = { {"USERNAME","PASSW0RD"}          , {"USERNAME2","p4ssworD"} };
    private String[]        storedEmail = { "email@gmail.com"                , "email2@gmail.com"       };
    private String[][]    storedAnswers = { {"Answer1", "Answer2", "Answer3"}, {"Ans1", "Ans2", "Ans3"} };

    public boolean  confirmation,
                    existingEmail,
                    existingName;

    public int x = 0;
    //Getter Method - For Forgotten Credentials
    public  String [][] getStoredData     (){return storedData     ;}

    //Setter Method - For Sign-Up
    public void setStoredData(String[] data, String email, String[] answers){

        //Hold Current Data to utilities
        String[][] utilityData = this.storedData;
        String[] utilityEmail = this.storedEmail;
        String[][] utilityAnswers = this.storedAnswers;

        //Expand length of main credentials variables for new account
        this.storedData       = new String [utilityData.length   + 1][2];
        this.storedEmail      = new String [utilityEmail.length   + 1]   ;
        this.storedAnswers    = new String [utilityAnswers.length   + 1][3];

        //Return the Current Data from utility to main
        for(int x = 0; x < utilityData.length; x++){
            for(int y = 0; y<2; y++){
                this.storedData     [x][Math.min(y,1)] = utilityData[x][y];
                this.storedEmail    [x]                = utilityEmail[x]   ;
                this.storedAnswers  [x][y]             = utilityAnswers[x][y];}
        }
        //Sets the new Data from account creation
        this.storedEmail[utilityEmail.length] = email  ;
        for(int x = 0; x<2; x++){
            this.storedData     [utilityData.length][Math.min(x,1)] = data     [Math.min(x,1)];
            this.storedAnswers  [utilityAnswers.length][x]             = answers  [x]            ;}
    }

    //Check Method - For Unique Account Creation
    public void checkExistingData(String username, String email){
        existingEmail = false;
        existingName  = false;
        for(String emails : this.storedEmail){
            if (email.equals(emails)){existingEmail = true;break;}
        }
        for (String[] usernames : this.storedData) {
            if (username.equals(usernames[0])) {existingName = true;break;}
        }
    }

    //Forgot Password Email Validation
    public void checkEmail(String email){
        existingEmail = false;
        x = 0;
        for(String emails: this.storedEmail){
            if (email.equals(emails)){existingEmail = true;break;}
            x++;
        }
    }
    public String[] checkStoredAnswers(int x, String[] ans){
        confirmation = true;
        for(int i = 0; i<3; i++){
            if (!this.storedAnswers[x][i].equals(ans[i])) {
                confirmation = false;
                break;
            }
        }
        return new String[]{this.storedData[x][0], this.storedData[x][1]};

    }
}
public class AccountCredentials extends StoredCredentials{}
