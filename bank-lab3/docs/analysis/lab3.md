# Лабораторна робота 3 — CQS (Command-Query Separation)

## 1. Що змінилося порівняно з лабораторною 2

У лабораторній роботі 2 використовувалась шарова архітектура з Application Service (`AccountService`), який поєднував операції читання і запису (create, deposit, withdraw, get).

У лабораторній роботі 3 було реалізовано підхід **CQS (Command-Query Separation)**:

- операції запису (write) → `Command + CommandHandler`
    - приклади: `CreateAccountCommand`, `DepositCommand`, `WithdrawCommand`
- операції читання (read) → `Query + QueryHandler`
    - приклад: `GetAccountQuery`

### Було:
Controller → AccountService → Domain → Repository

### Стало:
Controller → CommandHandler → Domain → Repository  
Controller → QueryHandler → Repository → DTO

Таким чином, логіка читання і запису була розділена.

---

## 2. Переваги CQS

### 2.1 Розділення відповідальностей

Кожен клас відповідає за одну операцію:
- `DepositHandler` — поповнення рахунку
- `WithdrawHandler` — зняття коштів
- `GetAccountHandler` — отримання даних

Це робить код простішим і зрозумілішим.

---

### 2.2 Краще тестування

Тести логічно розділяються:

- **Command (unit)** — без БД, через `FakeAccountRepository`
    - приклад: `WithdrawHandlerTest` перевіряє, що не можна зняти більше коштів, ніж є
- **Query (integration)** — через HTTP (MockMvc)
    - приклад: `AccountControllerIT` перевіряє повний flow (create → deposit → withdraw → get)

У лабораторній 2 це теж було можливо, але в CQS це розділення закладене в самій архітектурі.

---

### 2.3 Розширюваність

Додавання нової операції:

- у CQS → новий handler (наприклад, `TransferHandler`)
- у Service → зміна існуючого класу

Це зменшує ризик зламати вже написаний код.

---

### 2.4 Контролери

`AccountController` не містить бізнес-логіки, а лише:
- приймає HTTP-запит
- створює Command або Query
- викликає відповідний handler

---

## 3. Недоліки CQS

### 3.1 Більше коду

З’являються додаткові класи:
- Command
- Query
- Handler

---

### 3.2 Складніша структура

Код розподілений по пакетах (`command`, `query`, `domain`, `presentation`), що ускладнює навігацію.

---

### 3.3 Маппінг

Потрібно перетворювати:
Account (domain) → AccountResponse (DTO)

Це додає додатковий код, але дозволяє не використовувати домен напряму в API.

---

## 4. Command/Query Handler vs Service

У лабораторній 2 `AccountService` виконував всі операції:
- createAccount
- deposit
- withdraw
- getAccount

У лабораторній 3:
- `CreateAccountHandler` — створення
- `DepositHandler` — поповнення
- `WithdrawHandler` — зняття

Це відповідає принципу Single Responsibility.

---

## 5. Query vs Domain модель

У проєкті запити повертають **DTO (`AccountResponse`)**, а не доменні моделі (`Account`).

Наприклад:
- `Account` містить `Money` і бізнес-логіку
- `AccountResponse` містить тільки дані для API

Це дозволяє:
- не розкривати домен
- змінювати внутрішню логіку без впливу на API

---

## 6. Тестування

### 6.1 Тести команд (unit)

- без БД
- використовують `FakeAccountRepository`
- перевіряють:
    - бізнес-логіку
    - інваріанти (баланс не може бути від’ємним)
    - помилки

---

### 6.2 Тести запитів (integration)

- через HTTP (MockMvc)
- з реальною БД
- перевіряють:
    - API
    - DTO
    - фінальний результат

---

## 7. Що стало простіше / складніше

### Простішим стало:
- тестування бізнес-логіки
- додавання нових операцій
- розуміння відповідальності класів

---

### Складнішим стало:
- структура проєкту
- кількість класів
- маппінг між шарами

---

## Висновок

Підхід CQS дозволив розділити логіку читання і запису, що зробило систему більш зрозумілою, тестованою та гнучкою.  
Хоча коду стало більше, така архітектура краще підходить для масштабованих систем.