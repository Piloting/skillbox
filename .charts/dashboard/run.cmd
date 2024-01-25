# Включить в docker-desktop кубер (для Крыма нужен ВПН)
# Установить дашборд - https://artifacthub.io/packages/helm/k8s-dashboard/kubernetes-dashboard
# helm repo add kubernetes-dashboard https://kubernetes.github.io/dashboard/
# helm upgrade --install kubernetes-dashboard kubernetes-dashboard/kubernetes-dashboard --create-namespace --namespace kubernetes-dashboard

# открыть порты (выполнять после рестарта)
# получить имя поды дашборда
# kubectl get pods --namespace "kubernetes-dashboard"
# kubectl port-forward --namespace "kubernetes-dashboard" kubernetes-dashboard-798dd48467-kwwlv 8443:8443
# проверить https://localhost:8443/ Должна открыться страница авторизации. Нужен токен

# Создание токена (выполнять после рестарта) - https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md
# накатить на кубер dashboard-ClusterRoleBinding.yaml и dashboard-ServiceAccount.yaml :
# kubectl apply -f . 
# создать токен
# kubectl -n kubernetes-dashboard create token admin-user
# Результат eyJhbGciOiJSUzI1NiIsImtpZCI6InU3YjQ5cHZmV3Y3RXZ2WWZlWlNBZFktd1g3R3daSWhTMGJ4STItY3poc1EifQ.eyJhdWQiOlsiaHR0cHM6Ly9rdWJlcm5ldGVzLmRlZmF1bHQuc3ZjLmNsdXN0ZXIubG9jYWwiXSwiZXhwIjoxNzA1NjQ4Mjc5LCJpYXQiOjE3MDU2NDQ2NzksImlzcyI6Imh0dHBzOi8va3ViZXJuZXRlcy5kZWZhdWx0LnN2Yy5jbHVzdGVyLmxvY2FsIiwia3ViZXJuZXRlcy5pbyI6eyJuYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsInNlcnZpY2VhY2NvdW50Ijp7Im5hbWUiOiJhZG1pbi11c2VyIiwidWlkIjoiMTAwZjBmYTctM2YzZC00N2YwLWFhMmYtOTU1OGNiYjRhMDkxIn19LCJuYmYiOjE3MDU2NDQ2NzksInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDprdWJlcm5ldGVzLWRhc2hib2FyZDphZG1pbi11c2VyIn0.fh7T_Y04T0ZvKShX7CcNEBXP97iwCvDoGctIJoE3R5WJ6za6GbK0djvSk8AwsiBw-3TDCtMD_x6u1HywcTfO3rF3EBSnMVL7pelgvmSB1rHgJlAZVN8AAKmS90mVm8798RjEjYThNHtsxcNUiMHclzhujmPnSa9PkNbKiXhRqLGGefhF7lXol6vRDg82414tw8dCKOlzCYKS7qFOurkaJXPLMDxdvuE_t5NSHlt00wpyyRPNTgRUqVxPXc36O0wA5ndU0qgRt6SWDO5nd86uVYrT5f4ASCnO2FZi3TK9ZR7E_QC9WNP_tXBAnLuom7_on5e99uvb-b3jCrn9OSfKFA

# установка ingress
# helm repo add nginx-stable https://helm.nginx.com/stable
# helm install nginx-ingress nginx-stable/nginx-ingress
