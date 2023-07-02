<br/>
<p align="center">
  <h3 align="center">Финансовый помощник</h3>

  <p align="center">
    Контроль над счетами и тратами
    <br/>
    <br/>
  </p>
</p>

## Использованные технологии/фреймворки:

Spring boot 2.6.6
Spring security for UI
Spring Data JPA for Database (Postgres 14)
Spring Web (Controllers, RestControllers)
Spring Test + JUnit + Mockito for testing (H2 Database)

```

### Установка

Установить локальные переменные среды jdbcUser; jdbcPassword; jdbcUrl для Вашей БД и запустить приложение.

## Использование

Имеет несколько интерфейсов для взаимодействия:

1)Консоль (реализовано через Spring CommandLineRunner)

2)REST API (RestControllers Spring Web):
   Список URL HTTP-методов:
      POST /api/user/register - добавление пользователя
      Параметры запроса передаются в теле запроса в виде JSON-объекта со следующими атрибутами:
         name - имя;
         email - эл.почта (по ней осуществляется авторизация);
         password - пароль;
      Pезультаты:
         JSON-объект со статусом.

      POST /api/account(type)/(add, delete, update) - работа со счетами пользователя и типами транзакций (перечислений денег)
      Параметры: //в скобках указаны команды, для которых в JSON необходимо включить параметры
         id - номер счёта (из результатов /api/account(type)/list) (update, delete)
         name - название счёта/типа (add)
         balance - начальный баланс счёта (число строкой, если некорректный ввод/отрицательное - получим JSON с результатом false)(add)
         param - что именно меняем для СЧЁТА (NAME-название, BALANCE-баланс), является enum.(update)

      GET /api/account(type)/list - вывод всех счетов пользователя (или типов транзакций) в виде JSON-объекта со списком внутри.

      POST /transaction/add - добавление транзакции. как между счетами пользователя, так и поступление извне/расход вовне приложения.
      Параметры:
         idSource - номер счёта списания (выбирается из результатов /api/account/list или 0 для расхода из приложения)
         idTarget - номер счёта зачисления (все аналогично source)
         amount - сумма денег (передается строкой, в случае некорректной /отрицательной - получим JSON с результатом false)
         date - время проведения (строка в формате dd-MM-yyyy hh:mm или 0 для проставления текущей даты/времени)
         description - комментарий
         int type - тип транзакции (аналогично номерам счетов, из /api/type/list)
      Результаты: JSON-объект со статусом (true/false)

3) UI в браузере по "/" -реализован Spring security, неавторизованный доступ возможен только по /login и /register
