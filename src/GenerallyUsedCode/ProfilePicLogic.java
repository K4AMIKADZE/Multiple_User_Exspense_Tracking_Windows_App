package GenerallyUsedCode;

import java.util.ArrayList;

import ItemClases.GetCurrentUser;

public class ProfilePicLogic {



    public static String GetUserIcon(){
        ArrayList<GetCurrentUser> CurrentUser = Logic.ReadCurrentUsersInfoToTxtFile();
        int iconID = CurrentUser.get(0).getProfilepicId();

        String sendPath = "";
        switch (iconID) {

            case 42069:
                sendPath =  "aImages/manager.png";
                break;
            
             case 0:
                sendPath =  "aImages/user6.png";
                break;

            case 1:
                sendPath =  "aImages/user1.png";
                break;

                 case 2:
                sendPath =  "aImages/user2.png";
                break;

                 case 3:
                sendPath =  "aImages/user3.png";
                break;

                 case 4:
                sendPath =  "aImages/user4.png";
                break;

                 case 5:
                sendPath =  "aImages/user5.png";
                break;

                 

                
        
            default:
                break;
        }

        return sendPath;

    }

}
