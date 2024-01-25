# Файлы готовы
# Перейти в командной строке в текущую директорию
# Проверка на ошибки: helm lint .
# Проверка резултирующих файлов: helm template .
# Установка чарта: helm install user-app .
# При установке кубер может вернуть ошибку
# Например, если есть чарт с именем Chart.yaml->name, то будет:
# Error: INSTALLATION FAILED: cannot re-use a name that is still in use 
# Проверка существующих чартов: helm list
# Удалить чарт: helm delete user-app

# деплой в разные нэймспейсы
# создать нэймспейсы: kubectl create namespace dev
# установка: helm install user-app-dev . -f values-dev.yaml
# создать нэймспейсы: kubectl create namespace test
# установка: helm install user-app-test . -f values-test.yaml
