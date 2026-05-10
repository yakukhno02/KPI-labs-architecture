# Лабораторна робота 5 — Modular Monolith та Bounded Contexts

## 1. Що змінилося порівняно з лабораторною 4

У лабораторній 4 система вже мала:
- layered architecture
- CQRS
- events
- async communication

Але весь код все ще був одним великим модулем.

У лабораторній 5 система була розділена на два bounded contexts:

```text
core
analytics
```

Тепер кожен модуль має:
- власну структуру
- власну модель
- власну відповідальність

---

## 2. Core context

Модуль `core` містить:
- `Account`
- `Money`
- handlers
- repositories
- основну бізнес-логіку

Структура:

```text
core
 ├── application
 ├── domain
 ├── infrastructure
 └── api
```

У `core.api` були винесені integration events:

```text
MoneyDepositedEvent
```

Це стало публічним контрактом для інших модулів.

---

## 3. Analytics context

Було створено окремий модуль:

```text
analytics
```

який відповідає тільки за аналітику.

Він містить:
- listener
- ACL
- власну модель

```text
TransactionAnalytics
```

Analytics не використовує:
- `Account`
- `Money`

і не залежить від внутрішньої моделі `core`.

---

## 4. ACL (Anti-Corruption Layer)

Для інтеграції між модулями було створено:

```text
AnalyticsTranslator
```

Він перетворює:

```text
MoneyDepositedEvent
    ↓
TransactionAnalytics
```

Тому зовнішня модель не потрапляє всередину analytics.

---

## 5. Комунікація між модулями

Після `deposit`:

```text
DepositHandler
```

публікує:

```text
MoneyDepositedEvent
```

Analytics підписується на подію через:

```text
AnalyticsListener
```

Схема:

```text
DepositHandler
    -> publishEvent()
        -> AnalyticsListener
```

Listener працює через:

```java
@Async
@EventListener
```

тому analytics оновлюється асинхронно.

---

## 6. Strong Consistency та Eventual Consistency

### Strong Consistency

У `core` баланс оновлюється одразу після операції.

Це важливо, тому що баланс рахунку не може бути неправильним навіть тимчасово.

---

### Eventual Consistency

Між:
- `core`
- `analytics`

використовується eventual consistency.

Analytics оновлюється після отримання події, тому може трохи відставати від основної системи.

Для аналітики це нормально, тому що затримка не впливає на бізнес-логіку.

---

## 7. Перехід до мікросервісів

Завдяки:
- bounded contexts
- events
- ACL
- public contracts

модуль `analytics` можна винести в окремий сервіс майже без змін архітектури.

Потрібно буде тільки:
- замінити Spring Events на Kafka або RabbitMQ
- додати окрему базу даних

---

## 8. Ретроспектива курсу

### Лабораторна 1

У першій лабораторній використовувалась MVC-архітектура з великою кількістю сутностей та тісною зв’язаністю між компонентами.

Проєкт був функціонально більшим, ніж у наступних лабораторних, тому повний рефакторинг такого коду був би занадто великим і не дав би кращого розуміння архітектури.

---

### Лабораторна 2

Було введено layered architecture:
- presentation
- application
- domain
- infrastructure

Бізнес-логіка була перенесена в:
- `Account`
- `Money`

З’явились:
- Rich Domain Model
- інваріанти
- domain factory

---

### Лабораторна 3

Було реалізовано CQRS.

`AccountService` був розбитий на окремі handlers:
- `DepositHandler`
- `WithdrawHandler`
- `GetAccountQueryHandler`

Операції читання і запису були розділені.

---

### Лабораторна 4

Було додано:
- events
- async listeners
- `AuditService`

Система почала використовувати event-driven communication.

---

### Лабораторна 5

Було реалізовано:
- modular monolith
- bounded contexts
- ACL
- public contracts
- eventual consistency між модулями

---

## 9. Trade-offs

### Що стало краще
- модулі стали ізольованими
- зменшилась зв’язаність
- analytics більше не залежить від core domain
- систему простіше масштабувати

### Що стало складніше
- збільшилась кількість пакетів
- з’явились ACL та public contracts
- архітектура стала складнішою

---

## Висновок

У лабораторній роботі було реалізовано modular monolith із двома bounded contexts:
- `core`
- `analytics`

Модулі взаємодіють через integration events та public API, а ACL ізолює внутрішні моделі модулів одна від одної.

У результаті система стала більш гнучкою та ближчою до реальної enterprise-архітектури.