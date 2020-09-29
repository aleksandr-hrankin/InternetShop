CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internet_shop`.`products` (
  `id_product` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL DEFAULT 'default_product',
  `price` DOUBLE NOT NULL DEFAULT 0.00,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_product`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`users` (
  `id_user` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NULL,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(225) NULL,
  `salt` BLOB NULL,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

insert into users (name, login, password) values ('Carl', 'admin', 1);

CREATE TABLE `internet_shop`.`roles` (
  `id_role` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id_role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

insert into roles (name) values ('ADMIN');
insert into roles (name) values ('USER');

CREATE TABLE `internet_shop`.`users_roles` (
  `id_user` BIGINT(11) NULL,
  `id_role` BIGINT(11) NULL,
  INDEX `users_roles_fk_idx` (`id_user` ASC) VISIBLE,
  INDEX `roles_fk_idx` (`id_role` ASC) VISIBLE,
  CONSTRAINT `users_fk`
    FOREIGN KEY (`id_user`)
    REFERENCES `internet_shop`.`users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `roles_fk`
    FOREIGN KEY (`id_role`)
    REFERENCES `internet_shop`.`roles` (`id_role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

insert into users_roles (id_user, id_role) values (1, 2);

CREATE TABLE `internet_shop`.`orders` (
  `id_order` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `id_user` BIGINT(11) NULL,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_order`),
  INDEX `orders_user_fk_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `orders_user_fk`
    FOREIGN KEY (`id_user`)
    REFERENCES `internet_shop`.`users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`orders_products` (
  `id_order` BIGINT(11) NULL,
  `id_product` BIGINT(11) NULL,
  INDEX `order_fk_idx` (`id_order` ASC) VISIBLE,
  INDEX `product_fk_idx` (`id_product` ASC) VISIBLE,
  CONSTRAINT `order_fk`
    FOREIGN KEY (`id_order`)
    REFERENCES `internet_shop`.`orders` (`id_order`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_fk`
    FOREIGN KEY (`id_product`)
    REFERENCES `internet_shop`.`products` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`shopping_carts` (
  `id_shopping_cart` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `id_user` BIGINT(11) NULL,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_shopping_cart`),
  INDEX `shopping_carts_users_fk_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `shopping_carts_users_fk`
    FOREIGN KEY (`id_user`)
    REFERENCES `internet_shop`.`users` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`shopping_carts_products` (
  `id_shopping_cart` BIGINT(11) NULL,
  `id_product` BIGINT(11) NULL,
  INDEX `shopping_cart_fk_idx` (`id_shopping_cart` ASC) VISIBLE,
  INDEX `product_fk_idx` (`id_product` ASC) VISIBLE,
  CONSTRAINT `shopping_cart_fk`
    FOREIGN KEY (`id_shopping_cart`)
    REFERENCES `internet_shop`.`shopping_carts` (`id_shopping_cart`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `shopping_cart_product_fk`
    FOREIGN KEY (`id_product`)
    REFERENCES `internet_shop`.`products` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
