package org.jayhenri.cloud9.interfaces.service.other;

import org.jayhenri.cloud9.exception.OutOfStockException;
import org.jayhenri.cloud9.model.item.Item;

import java.util.List;
import java.util.UUID;

/**
 * The interface Inventory service i.
 *
 * @param <T> the type parameter
 */
public interface InventoryServiceI<T> {

    /**
     * Add.
     *
     * @param item     the item
     * @param quantity the quantity
     * @param price    the price
     */
    void add(Item item, int quantity, double price);

    /**
     * Update.
     *
     * @param t the t
     */
    void update(T t);

    /**
     * Delete.
     *
     * @param t the t
     */
    void delete(T t);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Exists by item name boolean.
     *
     * @param itemName the item name
     * @return the boolean
     */
    boolean existsByItemName(String itemName);

    /**
     * Gets by item name.
     *
     * @param itemName the item name
     * @return the by item name
     */
    T getByItemName(String itemName);

    /**
     * Exists by id boolean.
     *
     * @param uuid the u
     * @return the boolean
     */
    boolean existsById(UUID uuid);

    /**
     * Gets by id.
     *
     * @param uuid the u
     * @return the by id
     */
    T getById(UUID uuid);

    /**
     * Update.
     *
     * @param t        the t
     * @param quantity the quantity
     * @return the boolean
     */
    boolean canPurchase(T t, int quantity);

    /**
     * Update.
     *
     * @param t        the t
     * @param quantity the quantity
     * @throws OutOfStockException the out of stock exception
     */
    void purchase(T t, int quantity) throws OutOfStockException;
}