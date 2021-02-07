package com.AlbertAbuav.db;

import com.AlbertAbuav.utils.DBUtils;

public class DatabaseManager {
    /**
     * This class holds the database "Url" "Username" and "Password".
     */
    public static final String url = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=UTC";
    public static final String username = "root";
    public static final String password = "1234";
    /**
     * This class implement DDL query's (the creation and dropping of "Schemas" and "Tables" in the database SQL).
     */
    private static final String CREATE_SCHEMA = "CREATE SCHEMA `couponsystem`";
    private static final String DROP_SCHEMA = "DROP SCHEMA `couponsystem`";
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `couponsystem`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(80) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`))";
    private static final String DROP_TABLE_COMPANIES = "DROP TABLE `couponsystem`.`companies`";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `couponsystem`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(80) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`))";
    private static final String DROP_TABLE_CUSTOMERS = "DROP TABLE `couponsystem`.`customers`";
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `couponsystem`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`))";
    private static final String DROP_TABLE_CATEGORIES = "DROP TABLE `couponsystem`.`categories`";
    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `couponsystem`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(80) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `couponsystem`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "    FOREIGN KEY (`category_id`)\n" +
            "    REFERENCES `couponsystem`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";
    private static final String DROP_TABLE_COUPONS = "DROP TABLE `couponsystem`.`coupons`";
    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE `couponsystem`.`customers_vs_coupons` (\n" +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `couponsystem`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `couponsystem`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";
    private static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE `couponsystem`.`customers_vs_coupons`";

    public static void createSchema() {
        DBUtils.runQuery(CREATE_SCHEMA);
    }

    public static void dropSchema() {
        DBUtils.runQuery(DROP_SCHEMA);
    }

    public static void createTableCompanies() {
        DBUtils.runQuery(CREATE_TABLE_COMPANIES);
    }

    public static void dropTableCompanies() {
        DBUtils.runQuery(DROP_TABLE_COMPANIES);
    }

    public static void createTableCustomers() {
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS);
    }

    public static void dropTableCustomers() {
        DBUtils.runQuery(DROP_TABLE_CUSTOMERS);
    }

    public static void createTableCategories() {
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES);
    }

    public static void dropTableCategories() {
        DBUtils.runQuery(DROP_TABLE_CATEGORIES);
    }

    public static void createTableCoupons() {
        DBUtils.runQuery(CREATE_TABLE_COUPONS);
    }

    public static void dropTableCoupons() {
        DBUtils.runQuery(DROP_TABLE_COUPONS);
    }

    public static void createTableCustomersVsCoupons() {
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
    }

    public static void dropTableCustomersVsCoupons() {
        DBUtils.runQuery(DROP_TABLE_CUSTOMERS_VS_COUPONS);
    }
}
