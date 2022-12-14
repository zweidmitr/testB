# test Application
О проекте: сервис для синхронизации цен с биржей

Основные шаги:
1. Парсинг html
2. Интеграция по api с внешним сервисом
3. Отдача по api методов на фронт


Автоматический сбор информации о компаниях с первой страницы https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap
(Ticker, Company, Sector, Industry), сбор данных по ценам с yahoo-finance

### api
/api/companies?page=0&size=20 - получить список компаний постранично с page и size

/api/companies/{ticker}/prices - получить всю историю изменения цены по одной акции



## Архитектура

1. Модели данных(entities)

1.1 [Share](src/main/java/com/zwei/testb/entities/Share.java) 
Модель для хранения акций.

1.2 [DataSet](src/main/java/com/zwei/testb/entities/DataSet.java)
Модель для хранения изменений цены по акциям.

2. Контроллеры (endpoint)

2.1 [ShareController](src/main/java/com/zwei/testb/endpoint/ShareController.java)
Контроллер по работе с акциями

2.2 [DataSetController](src/main/java/com/zwei/testb/endpoint/DataSetController.java)
Контроллер по работе с историей изменений цены

3. Сервисы (services)

3.1 [services](src/main/java/com/zwei/testb/services/ShareService.java)
Сервис по обработке акций

3.2 [DataSetService](src/main/java/com/zwei/testb/services/DataSetService.java)
Сервис по обработки изменений цены


4. Хранилища (repository)

4.1 [ShareRepository](src/main/java/com/zwei/testb/repository/ShareRepository.java)
Модель для хранения акций

4.2 [DataSetRepository](src/main/java/com/zwei/testb/repository/DataSetRepository.java)
Модель для хранения истории изменений

5. Объекты для передачи (dto)

5.1 [ShareDto](src/main/java/com/zwei/testb/dto/ShareDto.java)
Объекты для передачи на основе модели акций

5.2 [DataSetDto](src/main/java/com/zwei/testb/dto/DataSetDto.java)
Объекты для передачи на основе модели изменений цены

6. Обработчик исключений (handlers)

6.1 [GlobalExceptionHandler](src/main/java/com/zwei/testb/handlers/GlobalExceptionHandler.java)
Обработчик исключений во всех контроллерах (ControllerAdvise)
