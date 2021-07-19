package com.gmail.murtazinram.validator;

import com.gmail.murtazinram.beans.Role;
import com.gmail.murtazinram.beans.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public interface ValidateUser {
    String EMAIL_PATTERN = "\\w+@\\w+.\\w+";
    String PHONE_PATTERN = "375\\d{9}";

    Collection<String> apply(User user);

    default Collection<String> usernameValidate(User user) {
        var result = new ArrayList<String>(1);
        int size = user.getName().trim().length();
        if (size < 3 || size > 30)
            result.add("Username error. Too short or too long name");
        return result;
    }

    default Collection<String> surnameValidate(User user) {
        var result = new ArrayList<String>(1);
        int size = user.getSurname().trim().length();
        if (size < 3 || size > 30)
            result.add("Surname error. Too short or too long surname");
        return result;
    }

    default Collection<String> emailValidate(User user) {
        var pattern = Pattern.compile(EMAIL_PATTERN);
        var result = new ArrayList<String>(1);
        if (!pattern.matcher(user.getEmail()).matches()) {
            result.add("Incorrect email!");
        }
        return result;
    }

    default Collection<String> phoneListValidate(User user) {
        var pattern = Pattern.compile(PHONE_PATTERN);
        var result = new ArrayList<String>(1);
        if (user.getPhonesList().size() > 3) {
            result.add("Too much more phone numbers! Can`t add more than 3 numbers");
        }
        user.getPhonesList().stream()
                .filter(phone -> !pattern.matcher(phone).matches())
                .map(phone -> "Incorrect phone number!").forEach(result::add);
        return result;
    }

    default Collection<String> roleValidate(User user) {
        var result = new ArrayList<String>(1);
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
        public Collection<String> apply(User user) {
            Collection<String> strings = usernameValidate(user);
            strings.addAll(surnameValidate(user));
            strings.addAll(emailValidate(user));
            strings.addAll(phoneListValidate(user));
            strings.addAll(roleValidate(user));
            return strings;
        }
    }
}
