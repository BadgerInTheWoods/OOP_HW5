package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;
import notebook.util.UserValidator;

import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run() {
        Commands com;
        showCommands();
        while (true) {
            while (true) {
                String command = userController.prompt("Введите команду: ");
                try {
                    com = Commands.valueOf(command);
                    break;
                } catch (Exception e) {
                    System.out.println("Error: wrong command " + command);
                }
            }

            if (com == Commands.EXIT) return;
            switch (com) {
                case CREATE:
                    User u = userController.createUser();
                    userController.saveUser(u);
                    break;
                case READ:
                    try {
                        User user = userController.readUser(Long.parseLong(typeUserId()));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case LIST:
                    System.out.println(userController.readAll());
                    break;
                case UPDATE:
                    userController.updateUser(typeUserId(), userController.createUser());
                    break;
                case DELETE:
                    userController.deleteUser(typeUserId());
                    break;
                case SAVEALL:
                    userController.saveAllUsers();
                    break;

            }
        }
    }

    public String typeUserId() {
        String userId = userController.prompt("Enter user id: ");
        return userId;
    }

    public void showCommands() {
        System.out.println("The commands are :");
        for (Commands c : Commands.values()) {
            System.out.print(c + " ");
        }
        System.out.println();
    }


}
