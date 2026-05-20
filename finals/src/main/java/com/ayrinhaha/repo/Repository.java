package com.ayrinhaha.repo;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic repository for managing data collections.
 *
 * @param <T> The type of item stored in the repository.
 * @author ayrinhaha
 */
public class Repository<T> {

    private List<T> items = new ArrayList<>();

    /**
     * Adds an item to the repository.
     *
     * @param item The item to add.
     */
    public void add(T item) {
        items.add(item);
    }

    /**
     * Retrieves all items in the repository.
     *
     * @return A list of all stored items.
     */
    public List<T> getAll() {
        return items;
    }

    /**
     * Removes a specific item from the repository.
     *
     * @param item The item to remove.
     */
    public void remove(T item) {
        items.remove(item);
    }
}