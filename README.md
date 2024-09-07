# Утилита фильтрации содержимого файлов
### Версия Java "22.0.2"
### Система сборки: Maven "4.0.0"
### Сборка утилиты
* `mvn package`
### Запуск утилиты
* `java -jar .\target\TestTaskCFT-1.0.jar {Необходимые параметры}`
* Например `java -jar .\target\TestTaskCFT-1.0.jar -o /abc -p res_ -f in1.txt in2.txt`
### Особенности реализации
* Неверные параметры игнорируются. Программа продолжает работу будто бы данные параметры не вводились.
* При повторном вводе одного и того же параметра используется первый правильно введенный.
* При реализации сбора статистики были использованы классы BigInteger и BigDecimal для возможности работы с числами, выходящими за размерность примитивных типов и для более точного вычисления вещественных чисел.
