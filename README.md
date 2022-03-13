Мое практическое обучение по созданию ktor сервера. Сейчас готов функционал для аутентификации:

• проверка пары "телефон/пароль", выдача jwt-токена при соответствии;

• проверка юзера по айди и jwt-токену.

• проверка наличия номера телефона в БД;

• отправка и проверка "кода ОТП";

• создание нового юзера;

• смена пароля юзера.




Для запуска сервера необходимо настроить Run Configuration:

![auth_serv_config](https://user-images.githubusercontent.com/79571688/158054676-ca1dedaf-ca55-4fca-a1f3-0302a90f92e7.png)

Evnvironment variables: KTOR_CARS_PARK_JWT_SECRET=password;HASH_SECRET_KEY=password;KTOR_DB_PW=password 

Вместо password - можно использовать свои данные.

Так же необходимо создать свой sql server с двумя таблицами со следующими полями:

Otp:

![mysql_table_otp](https://user-images.githubusercontent.com/79571688/158055015-ebbcc82f-203c-487d-abeb-491796d5397f.png)

Users:

![mysql_table_users](https://user-images.githubusercontent.com/79571688/158055018-19c687bb-9d19-46e2-ace4-150b150508da.png)

IP-адрес и название БД устанавливается в Constants проекта.

Приложение, которое работает с этим сервером: 
https://github.com/veygard/ktor_client_login_jwt
