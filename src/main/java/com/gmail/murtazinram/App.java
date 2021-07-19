package com.gmail.murtazinram;

import com.gmail.murtazinram.beans.Role;
import com.gmail.murtazinram.beans.User;
import com.gmail.murtazinram.console.Menu;
import com.gmail.murtazinram.console.MenuEntry;
import com.gmail.murtazinram.validator.ValidateUser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private static final String FILE_NAME = "./src/main/resources/users.txt";

    public static void main(String[] args) {
        User user = new User();
        List<User> userList = new ArrayList<>();

        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            Menu menu = new Menu(reader);
            createUser(user, userList, menu);
            editUser(userList, menu);
            printUsers(userList, menu);
            deleteUser(userList, menu);
            writeToFile(userList, menu);
            menu.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(List<User> userList, Menu menu) {
        menu.addEntry(new MenuEntry("save users to file") {
            @Override
            public void run() {
                try (var writer = new FileWriter(FILE_NAME, true)) {
                    for (User user : userList) {
                        writer.append(user.toString());
                        writer.append('\n');
                    }
                    writer.flush();
                    out("write complete!");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    private static void deleteUser(List<User> userList, Menu menu) {
        menu.addEntry(new MenuEntry("delete user") {
            @Override
            public void run() {
                try {
                    out("please select the user id to delete");
                    String line = menu.getReader().readLine();
                    var idForDelete = Integer.parseInt(line);
                    userList.remove(idForDelete);
                } catch (IOException | IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private static void editUser(List<User> userList, Menu menu) {
        menu.addEntry(new MenuEntry("edit user") {
            @Override
            public void run() {
                out("please select the user id to edit");
                try {
                    String line = menu.getReader().readLine();
                    int id = Integer.parseInt(line);
                    User temp = new User();

                    addUserOrEdit(menu, temp);

                    var apply = new ValidateUser.Default().apply(temp);
                    if (!apply.isEmpty()) {
                        apply.forEach(System.out::println);
                        throw new IllegalArgumentException("---!!!please add correct data!!!---");
                    } else userList.set(id, temp);

                } catch (IllegalArgumentException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private static void printUsers(List<User> userList, Menu menu) {
        menu.addEntry(new MenuEntry("print users") {
            @Override
            public void run() {
                for (int i = 0; i < userList.size(); i++) {
                    System.out.println(i + ". " + userList.get(i));
                }
            }
        });
    }

    private static void createUser(User user, List<User> userList, Menu menu) {
        menu.addEntry(new MenuEntry("create user") {
            @Override
            public void run() {
                try {
                    addUserOrEdit(menu, user);

                    var apply = new ValidateUser.Default().apply(user);
                    if (!apply.isEmpty()) {
                        apply.forEach(System.out::println);
                        throw new IllegalArgumentException("---!!!please add correct data!!!---");
                    } else userList.add(user);

                } catch (IllegalArgumentException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private static void addUserOrEdit(Menu menu, User user) throws IOException {
        out("add name");
        String line = menu.getReader().readLine();
        user.setName(line);

        out("add surname");
        line = menu.getReader().readLine();
        user.setSurname(line);

        out("add email");
        line = menu.getReader().readLine();
        user.setEmail(line);

        out("add phone(you can add up to three phones separated by a space)");
        line = menu.getReader().readLine();
        user.setPhonesList(Arrays.asList(line.split(" ")));

        out("add role(you can add up to two role separated by a space)");
        line = menu.getReader().readLine();
        String[] roles = line.toUpperCase().split(" ");
        List<Role> collectRole = Arrays.stream(roles)
                .map(Role::valueOf)
                .collect(Collectors.toList());
        user.setRoleList(collectRole);
    }

    private static void out(String s) {
        System.out.println(s);
    }
}
