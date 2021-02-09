package com.AlbertAbuav.dao;

import com.AlbertAbuav.beans.Category;

import java.util.List;

public interface CategoriesDAO {

    void addCategories();

    List<Category> getAllCategories();
}
