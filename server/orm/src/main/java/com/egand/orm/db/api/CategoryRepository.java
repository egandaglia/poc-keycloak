package com.egand.orm.db.api;

import com.egand.orm.db.entities.Category;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CategoryRepository extends Repository<Category, Integer> {

    @Query("select * from t_category")
    List<Category> findAll();
}
