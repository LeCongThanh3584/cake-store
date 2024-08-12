CREATE DATABASE sweet_cake;
use sweet_cake;

CREATE TABLE `sweet_cake`.`category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(145) NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
  
  
  CREATE TABLE `sweet_cake`.`material` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(145) NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);

CREATE TABLE `sweet_cake`.`cake` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_category` BIGINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  `weight` INT NOT NULL,
  `image` VARCHAR(145) NULL,
  `color` VARCHAR(45) NOT NULL,
  `size` VARCHAR(45) NOT NULL,
  `height` INT NOT NULL,
  `length` INT NOT NULL,
  `description` VARCHAR(145) NULL,
  `status` INT NOT NULL,
  `deleted_at` DATETIME NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `created_by` VARCHAR(45) NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_CAKE_CATEGORY_idx` (`id_category` ASC) VISIBLE,
  CONSTRAINT `FK_CAKE_CATEGORY`
    FOREIGN KEY (`id_category`)
    REFERENCES `sweet_cake`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `sweet_cake`.`material_cake` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_material` BIGINT NOT NULL,
  `id_cake` BIGINT NOT NULL,
  `description` VARCHAR(45) NULL,
  `weight` VARCHAR(45) NOT NULL,
    `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_MC_CAKE_idx` (`id_cake` ASC) VISIBLE,
  CONSTRAINT `FK_MC_CAKE`
    FOREIGN KEY (`id_cake`)
    REFERENCES `sweet_cake`.`cake` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_MC_MATERIAL`
    FOREIGN KEY (`id_material`)
    REFERENCES `sweet_cake`.`material` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `sweet_cake`.`store` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `image` VARCHAR(145) NULL,
  `address` VARCHAR(145) NULL,
  `phone` VARCHAR(45) NOT NULL,
  `status` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);


CREATE TABLE `sweet_cake`.`cake_store` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_store` BIGINT NOT NULL,
  `id_cake` BIGINT NOT NULL,
  `price` DECIMAL NOT NULL,
  `quantity` VARCHAR(45) NOT NULL,
  `production_date` VARCHAR(45) NOT NULL,
  `expiration_date` VARCHAR(45) NOT NULL,
  `status` INT NULL,
  `created_at` DATETIME NULL,
  `created_by` VARCHAR(45) NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_CS_CAKE_idx` (`id_cake` ASC) VISIBLE,
  CONSTRAINT `FK_CS_CAKE`
    FOREIGN KEY (`id_cake`)
    REFERENCES `sweet_cake`.`cake` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CS_S`
    FOREIGN KEY (`id_store`)
    REFERENCES `sweet_cake`.`store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `sweet_cake`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `full_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(145) NOT NULL,
  `role` INT NOT NULL,
  `status` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);


CREATE TABLE `sweet_cake`.`notification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_user` BIGINT NOT NULL,
  `message` VARCHAR(145) NOT NULL,
  `type` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_NOTI_USER_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `FK_NOTI_USER`
    FOREIGN KEY (`id_user`)
    REFERENCES `sweet_cake`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `sweet_cake`.`address` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_user` BIGINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `province` VARCHAR(45) NOT NULL,
  `district` VARCHAR(45) NOT NULL,
  `ward` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `created_at` VARCHAR(45) NOT NULL,
  `created_by` VARCHAR(45) CHARACTER SET 'binary' NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_ADDRESS_USER_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `FK_ADDRESS_USER`
    FOREIGN KEY (`id_user`)
    REFERENCES `sweet_cake`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `sweet_cake`.`product_cart` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_user` BIGINT NOT NULL,
  `id_cake_store` BIGINT NOT NULL,
  `quantity` BIGINT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_CART_USER_idx` (`id_user` ASC) VISIBLE,
  INDEX `FK_CART_CAKE_STORE_idx` (`id_cake_store` ASC) VISIBLE,
  CONSTRAINT `FK_CART_USER`
    FOREIGN KEY (`id_user`)
    REFERENCES `sweet_cake`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CART_CAKE_STORE`
    FOREIGN KEY (`id_cake_store`)
    REFERENCES `sweet_cake`.`cake_store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `sweet_cake`.`order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_store` BIGINT NOT NULL,
  `id_admin` BIGINT NOT NULL,
  `id_shipper` BIGINT NULL,
  `id_user` BIGINT NULL,
  `code` VARCHAR(45) NOT NULL,
  `reciver` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NOT NULL,
  `address` VARCHAR(145) NOT NULL,
  `total_money` DECIMAL NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_ORDER_STORE_idx` (`id_store` ASC) VISIBLE,
  INDEX `FK_ORDER_admin_idx` (`id_admin` ASC) VISIBLE,
  INDEX `FK_ORDER_USER_idx` (`id_user` ASC) VISIBLE,
  INDEX `FK_ORDER_SHIPPER_idx` (`id_shipper` ASC) VISIBLE,
  CONSTRAINT `FK_ORDER_STORE`
    FOREIGN KEY (`id_store`)
    REFERENCES `sweet_cake`.`store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDER_admin`
    FOREIGN KEY (`id_admin`)
    REFERENCES `sweet_cake`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDER_USER`
    FOREIGN KEY (`id_user`)
    REFERENCES `sweet_cake`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDER_SHIPPER`
    FOREIGN KEY (`id_shipper`)
    REFERENCES `sweet_cake`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `sweet_cake`.`order_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_order` BIGINT NOT NULL,
  `description` VARCHAR(145) NULL,
  `status` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_ORHIS_ORDER_idx` (`id_order` ASC) VISIBLE,
  CONSTRAINT `FK_ORHIS_ORDER`
    FOREIGN KEY (`id_order`)
    REFERENCES `sweet_cake`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `sweet_cake`.`order_detail` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `id_cake_store` BIGINT NOT NULL,
  `id_order` BIGINT NOT NULL,
  `quantity` BIGINT NOT NULL,
  `price` DECIMAL NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `deleted_at` DATETIME NULL,
  `deleted_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_ORDER_idx` (`id_cake_store` ASC) VISIBLE,
  INDEX `FK_ORDE_idx` (`id_order` ASC) VISIBLE,
  CONSTRAINT `FK_ORDER`
    FOREIGN KEY (`id_cake_store`)
    REFERENCES `sweet_cake`.`cake_store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDE`
    FOREIGN KEY (`id_order`)
    REFERENCES `sweet_cake`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
