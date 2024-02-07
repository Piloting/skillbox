# Файлы готовы
# Перейти в командной строке в текущую директорию
# Проверка на ошибки: helm lint .
# Проверка резултирующих файлов: helm template .
# Установка чарта: helm install postgres .
# При установке кубер может вернуть ошибку
# Например, если есть чарт с именем Chart.yaml->name, то будет:
# Error: INSTALLATION FAILED: cannot re-use a name that is still in use 
# Проверка существующих чартов: helm list
# Удалить чарт: helm delete postgres
# Доступ снаружи: в hosts добавить preprod.postgres.local. Из idea: jdbc:postgresql://preprod.postgres.local/postgres
