package com.expensetracker.service;

import com.expensetracker.model.Category;
import com.expensetracker.model.CategoryType;
import com.expensetracker.model.User;
import com.expensetracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll(User user) {
        return categoryRepository.findByUser(user);
    }

    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getByType(User user, CategoryType type) {
        return categoryRepository.findByUserAndType(user, type);
    }
}
