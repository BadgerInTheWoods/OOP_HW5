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
                String command = prompt("Введите команду: ");
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
                    User u = createUser();
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
                    userController.updateUser(typeUserId(), createUser());
                    break;
                case DELETE:
                    userController.deleteUser(typeUserId());
                    break;

            }
        }
    }

    public String typeUserId() {
        String userId = prompt("Enter user id: ");
        return userId;
    }

    public void showCommands() {
        System.out.println("The commands are :");
        for (Commands c : Commands.values()) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private User createUser() {
        String firstName = prompt("Имя: ");
        String lastName = prompt("Фамилия: ");
        String phone = prompt("Номер телефона: ");

        UserValidator validator = new UserValidator();

        return validator.validate(new User(firstName, lastName, phone));
    }
}
