package com.gmail.murtazinram.validator;

import com.gmail.murtazinram.beans.Role;
import com.gmail.murtazinram.beans.User;

import java.util.*;
import java.util.regex.Pattern;

public interface ValidateUser {
    String EMAIL_PATTERN = "\\w+@\\w+.\\w+";
    String PHONE_PATTERN = "375\\d{9}";

    List<String> apply(User user);

    default List<String> usernameValidate(User user) {
        ArrayList<String> result = new ArrayList<>(1);
        int size = user.getName().trim().length();
        if (size < 3 || size > 30)
            result.add("Username error. Too short or too long name");
        return result;
    }

    default List<String> surnameValidate(User user) {
        ArrayList<String> result = new ArrayList<>(1);
        int size = user.getSurname().trim().length();
        if (size < 3 || size > 30)
            result.add("Surname error. Too short or too long surname");
        return result;
    }

    default List<String> emailValidate(User user) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        ArrayList<String> result = new ArrayList<>(1);
        if (!pattern.matcher(user.getEmail()).matches()) {
            result.add("Incorrect email!");
        }
        return result;
    }

    default List<String> phoneListValidate(User user) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        ArrayList<String> result = new ArrayList<>(1);
        if (user.getPhonesList().size() > 3) {
            result.add("Too much more phone numbers! Can`t add more than 3 numbers");
        }
        user.getPhonesList().stream()
                .filter(phone -> !pattern.matcher(phone).matches())
                .map(phone -> "Incorrect phone number!").forEach(result::add);
        return result;
    }

    default List<String> roleValidate(User user) {
        ArrayList<String> result = new ArrayList<>(1);
        if (user.getRoleList().size() > 2) {
            result.add("you can't have more than 2 roles at the same time");
        }
        Map<Integer, String> rolesMap = new HashMap<>();
        for (Role role : user.getRoleList()) {
            if (!canAddRole(role, rolesMap)) {
                result.add("Can`t add this role");
            }
            rolesMap.put(role.getLevel(), role.name());
        }
        return result;
    }

    private boolean canAddRole(Role role, Map<Integer, String> rolesMap) {
        if (rolesMap.containsKey(role.getLevel()) ||
                rolesMap.containsValue(Role.SUPER_ADMIN.name())) {
            return false;
        }
        return rolesMap.size() == 0;
    }

    class Default implements ValidateUser {
        @Override
        public List<String> apply(User user) {
            List<String> strings = usernameValidate(user);
            strings.addAll(surnameValidate(user));
            strings.addAll(emailValidate(user));
            strings.addAll(phoneListValidate(user));
            strings.addAll(roleValidate(user));
            return strings;
        }
    }
}
