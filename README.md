# Task Service

Стек: Java 21, Spring Boot, Spring Data JPA, PostgreSQL, Kafka, Docker

Как запустить:

docker compose up -d --build

API будет доступно на порту http://localhost:8080

Примеры запросов:
GET : /api/tasks?page=0&size=20   Список задач с пагинацией
GET : /api/tasks/{id} Получение задач по id
POST : /api/tasks Создание задачи
PUT : /api/tasks/{id}/assignee Назначение задачи исполнителю
PATCH : /api/tasks/{id}/status Смена статуса задачи (TODO, IN_PROGRESS, DONE)

Исполнители не добавлены через API.Чтобы добавить любого исполнителя, можно воспользоваться:

docker exec -i task-postgres psql -U taskuser -d taskdb -c "INSERT INTO users (name, email) VALUES ('Nikolay', 'nikolay1111@example.ru');"

События в Kafka:
task_created Задача создана
task_assigned Задача переназначена

NFT(10к исполнителей,100к задач)
- Решаю путем разбиения числа страниц с максимальным размером 20.
- Индексация assignee_id и status
- Использую @EntityGraph на чтение чтобы избежать N+1
- Read-only транзакции для запросов

События отправляются до фиксации транзакции. В продакшене можно использовать transactional outbox pattern.
