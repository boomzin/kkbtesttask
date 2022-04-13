[Test task - Kuban Kredit Bank](https://github.com/boomzin/kkbtesttask)

Сервис для трекера задач.

Основные функции сервиса:

- просмотр списка задач;
- просмотр задачи по номеру;
- создание новой задачи;
- редактирование задачи;
- удаление задачи;


#### База данных H2 в памяти с таблицей:
- **TASK** - содержит сущности задач
    - соответствует классу `Task.class`

Порядок запуска из репозитория:
<pre>git clone https://github.com/boomzin/kkbtesttask
cd kkbtesttask/
mvn package
java -jar target/kkbtesttask-0.0.1-SNAPSHOT.jar
</pre>

Порт приложения по умолчанию - 8087

После запуска будут доступен endpoint:
<pre>http://localhost:8085/api/tasks</pre>

И документация к API:
<pre>http://localhost:8087/swagger-ui/index.html</pre>



