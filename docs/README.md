Кратко про арихитектуру

File Storing Service (порт 8081)
---
– загружает и хранит текстовые файлы, сохраняет их в локальной папке и метаданные в H2.

– REST-контроллер POST /files/upload и GET /files/{id}.

– Swagger UI по адресу http://localhost:8081/swagger-ui.html.


File Analysis Service (порт 8082)
---
– скачивает текст по fileId, считает абзацы, слова, символы и MD5-хеш, сохраняет результат в H2.

– методы POST /analysis/analyze, GET /analysis/{fileId} и POST /analysis/compare.

– Swagger UI по адресу http://localhost:8082/swagger-ui.html.

API Gateway (порт 8080)
---
– единая точка входа, проксирует /files/** → хранилище (8081) и /analysis/** → анализ (8082).

---
Если мы хотим пользоваться UI, То делаем вот так

http://localhost:8081/swagger-ui.html - заходим сюда, нажимаем post, загружаем нужный нам файл, запоминаем его хеш

http://localhost:8082/swagger-ui.html - потом заходим сюда, нажимаем analyze, вставляем туда наш хеш

Чтобы сравнить два файла, нужно сделать алгоритм выше для каждого из них, потом зайти в compare и вставить их хеши, если будет true, то это плагиат

Если мы пользуемся API и консоллью, то вот так:
````
curl -v -F "file=@path\file" http://localhost:8080/files/upload

curl -v -X POST http://localhost:8080/analysis/analyze ^
-H "Content-Type: application/json" ^
-d "{\"fileId\":\"<FILE_ID>\"}"

curl -v http://localhost:8080/analysis/<FILE_ID>

curl -v -X POST http://localhost:8080/analysis/compare ^
-H "Content-Type: application/json" ^
-d "{\"fileId1\":\"<FILE_ID>\",\"fileId2\":\"<FILE_ID_OR_SECOND_ID>\"}"

Если хотим скачать:
curl -v http://localhost:8080/files/<FILE_ID> --output downloaded.txt
