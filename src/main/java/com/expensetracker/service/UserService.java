package com.expensetracker.service;

import com.expensetracker.model.Category;
import com.expensetracker.model.DefaultCategory;
import com.expensetracker.model.User;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.DefaultCategoryRepository;
import com.expensetracker.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DefaultCategoryRepository defaultCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void saveUser(User user) {

        User savedUser = userRepository.save(user);

        List<DefaultCategory> defaults =
                defaultCategoryRepository.findAll();

        List<Category> categories = defaults.stream()
                .map(dc -> {
                    Category c = new Category();
                    c.setName(dc.getName());
                    c.setType(dc.getType());
                    c.setUser(savedUser);
                    return c;
                })
                .toList();

        categoryRepository.saveAll(categories);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}