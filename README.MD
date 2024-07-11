---
# Инструкция по запуску приложения

1. **Выполнить команду создания контейнера для Docker**
   ```bash
   docker run --rm --name clevertec-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres
   ```

2. **Клонировать проект**
   ```bash
   git clone https://github.com/poslannikdyni/ClevertecCheck.git
   ```

3. **Перейти в папку с исходными файлами (ClevertecCheck) и запустить консоль**
   ```bash
   cd ClevertecCheck
   ```

4. **Переключиться на ветку** `feature/entry-database`
   ```bash
   git checkout feature/entry-database
   ```

5. **В терминале ввести команду компиляции:**
   ```bash
   .\gradlew jar
   ```

6. **Запустить приложение с заданными параметрами :**
   `java -jar clevertec-check.jar 3-1 2-5 5-1 discountCard=xxxx balanceDebitCard=xxxx saveToFile=xxxx datasource.url=xxxx datasource.username=xxxx datasource.password=xxxx`
   где
   1. id - идентификатор товара
   2. quantity - количество товара
   3. discountCard=xxxx - название и номер дисконтной карты
   4. balanceDebitCard=xxxx - баланс на дебетовой карте
   5. saveToFile=xxxx - переопределение пути к файлу result.csv
   6. datasource.[url, username, password]=xxxx - параметры подключения к базе данных

   Пример команды
   
   ```bash
   java -jar clevertec-check.jar 3-1 2-5 5-1 5-2 discountCard=1111 balanceDebitCard=100 saveToFile=./result.csv datasource.url=jdbc:postgresql://localhost:5432/postgres datasource.username=postgres datasource.password=postgres
   ```

7. **Изучить вывод в консоле и файле.**
   > Консольная версия чека будет содержать дополнительную информацию о том какая скидка была применена к конкретной позиции.
---