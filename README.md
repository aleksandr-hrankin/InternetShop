# internet-shop
Internet-shop with basic operations. 

In this project used N-tier architecture with DB layer, DAO layer, Service layer, Controllers layer and View layer. <br>
Project was developed according to SOLID principles with authorization and authentication by RBAC filter strategy.

#### Diagram that describes the structure of the project
<img src="https://github.com/aleksandr-hrankin/pictures/blob/main/project_internet_shop_structure_diagram.png" alt="project_internet_shop_structure_diagram" width="520"/>

#### UML diagram that describes the relationship between the entities.
<img src="https://github.com/aleksandr-hrankin/pictures/blob/main/project_internet-shop-uml.png" alt="project_internet_shop_uml" width="520"/>

One user can have multiple roles. <br>
##### No role: <br>
  - Registration
  - Authorization
  - View products
##### User: <br>
  - View products
  - Add / remove products to shopping cart
  - Make an order
  - Logout
##### Admin: <br>
  - View / delete users
  - View / add / remove products
  - View / delete user orders.
  - Logout

# Technologies used <br>
**backend:** Java, Servlets, Tomcat, JDBC <br>
**frontend:** HTML, CSS, Bootstrap, JSP, JSTL <br>
**database:** MySQL <br>
**tools:** lombok <br>

# To start the project you need: <br>

1) *Download and install* the [JDK](https://www.oracle.com/java/technologies/javase-downloads.html, "Download JDK") <br>
2) *Download and install* servlet container (for example Apache [Tomcat](https://tomcat.apache.org/download-90.cgi, "Download Tomcat")) <br>
3) *Download and install* [MySQL Server](https://dev.mysql.com/downloads/)<br>
+ Setup new connection with <br>
  + user: *"your username"* <br>
  + password: *"your password"*<br>
  + url: jdbc:mysql://*"your host name"*:*"your port"*/*"your name db"*?useUnicode=true&serverTimezone=UTC <br>
+ Create schema internet_shop <br>
+ Create tables using commands from init_db.sql under the path src/main/resources/ <br>

# Available for admin (login: admin, password: admin)
