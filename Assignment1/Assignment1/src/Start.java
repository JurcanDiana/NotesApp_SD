import presentation.LoginView;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Start {

    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException {
        LoginView loginView = new LoginView();
        //AdminView adminView = new AdminView();
        //NewNoteView newNoteView = new NewNoteView();
        //UserView userView = new UserView();
    }
}
