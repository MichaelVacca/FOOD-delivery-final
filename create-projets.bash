#!/usr/bin/env bash

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=clients-service \
--package-name=com.fooddelivery.clientservice \
--groupId=com.fooddelivery.clientservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
clients-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=deliverydriver-service \
--package-name=com.fooddelivery.deliverydriverservice \
--groupId=com.fooddelivery.deliverydriverservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
deliverydriver-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=restaurant-service \
--package-name=com.fooddelivery.restaurantservice \
--groupId=com.fooddelivery.restaurantservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
restaurant-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=orders-service \
--package-name=com.fooddelivery.ordersservice \
--groupId=com.fooddelivery.ordersservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
orders-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.fooddelivery.apigateway \
--groupId=com.fooddelivery.apigateway \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
api-gateway

