# Для подключения локального кубера к гитлабу нужно установить в свой кубер агент гитЛаба 
# Дока: https://docs.gitlab.com/ee/user/clusters/agent/install/index.html
# 1. Добавить в проект пустой файл .gitlab/agents/k8s-agent/config.yaml. Вкомитить
# 2. Зайти в гитЛаб Operate->Kubernetes Cluster. Тут нажать "Connect a cluster". Выбрать из списка k8s-agent (гитЛаб проверяет main ветку, 
# точнее default. Чтобы увидел мой файл k8s-agent/config.yaml я переключил в настройках гитЛаба default ветку на master)
# 3. Запустить, то, что предлагает гитЛаб:
#    helm repo add gitlab https://charts.gitlab.io
#    helm repo update
#    helm upgrade --install k8s-agent gitlab/gitlab-agent \
#        --namespace gitlab-agent-k8s-agent \
#        --create-namespace \
#        --set image.tag=v16.9.2 \
#        --set config.token=glagent-... \
#        --set config.kasAddress=wss://kas.gitlab.com
# 4. После этого в гитЛабе Operate->Kubernetes Cluster должен появиться статус Connected (через некоторое время)
# 5. Дописать в файл k8s-agent/config.yaml доступ агента к репо 
#    ci_access:
#      groups:
#        - id: Piloting1/skillbox
#