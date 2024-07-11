---
# Инструкция по запуску приложения

1. **Клонировать проект**
   ```bash
   git clone https://github.com/poslannikdyni/ClevertecCheck.git
   ```

2. **Перейти в папку с исходными файлами (ClevertecCheck) и запустить консоль**
   ```bash
   cd ClevertecCheck
   ```

3. **Переключиться на ветку** `feature/entry-core`
   ```bash
   git checkout feature/entry-core
   ```

4. **В терминале ввести команду компиляции:**

   ```bash
   javac -sourcepath ./src ./src/main/java/ru/clevertec/check/*.java
   ```

5. **Запустить приложение с заданными параметрами :**
   java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx
   где 
   1. id - идентификатор товара
   2. quantity - количество товара
   3. discountCard=xxxx - название и номер дисконтной карты
   4. balanceDebitCard=xxxx - баланс на дебетовой карте

   Пример команды

   ```bash
   java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 3-1 2-5 5-1 5-2 discountCard=1111 balanceDebitCard=100.11
   ```

6. **Изучить вывод в консоле и файле.**
   > Консольная версия чека будет содержать дополнительную информацию о том какая скидка была применена к конкретной позиции.
---



