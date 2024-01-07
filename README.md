# Menu Application

## Overview

We are building a menu manager software application using Java Swing.

## Getting Started

### Dependencies

* JDK 17
* Gradle 8.3

### Execute
Clone the repo, `cd` to `javaswing-menu-app` 
```
gradle clean build run
```

### Features

* **Important note**
  
  **Order History**
  
  * For `pickup`: name and phone number are required
  * Phone number must be an 10-digit integer that starts with 04
  * For `delivery`: address is required
 
  **Admin Dashboard**
  
  * Price will be reset if user enter any string
  * If `Enter`, price will be updated immediately
  * Else, table view will be fixed after pressing `Save`
  * Price must be numeric and positive, ie: 9. is allowed
  * Row with empty food name and description will be filtered during `Save`

* **Login System**

  * Distinguish between admin and general users
  * Only admin can log in using an account
  * After logged in, Admin can sign up for another Admin
  * General users will sign in as guests.

* **Menu Ordering**

  * Select meals from menu
  * View item names, descriptions, and prices
  * Add items to order cart
  * Adjust item quantities
  * Remove items from the cart
  * Selecting delivery or pickup options and confirming the order
  * General users are not required to log in

* **Order History**

  * Only Admin can access order history
  * View order date, items ordered and total amount
  * Search orders based on order number

* **Admin Dashboard**

  * Only Admin can access admin dashboard
  * Overview of application's status (total orders proccessed)
  * Add new menu items (including a name, description and price for each item)
  * Update existing items (e.g. correcting prices or descriptions)
  * Remove outdated items from the menu


