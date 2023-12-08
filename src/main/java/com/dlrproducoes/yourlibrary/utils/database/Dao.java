package com.dlrproducoes.yourlibrary.utils.database;

import com.dlrproducoes.yourlibrary.model.tables.Polo;

import java.util.List;

public interface Dao<T> {
    List<T> consultAll();
    T consult(T ob);
    void save(T ob);
    void update(T ob);
    void delete(T ob);
}
