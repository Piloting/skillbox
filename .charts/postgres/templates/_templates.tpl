{{- define "postgres-deployment" -}}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .appName }}-deployment
  namespace: {{ .namespace }}
  labels:
    app: {{ .appName }}
spec:
  replicas: {{ .replicasCount }}
  selector:
    matchLabels:
      app: {{ .appName }}
  template:
    metadata:
      labels:
        app: {{ .appName }}
    spec:
      containers:
      - name: {{ .appName }}
        image: {{ .container.image }}:{{ .container.version }}
        ports:
        - containerPort: {{ .container.port }}
        resources:
          requests:
            memory: 256M
            cpu: 100m
          limits:
            memory: 512M
            cpu: 200m
{{- if .container.env }}
        env:
{{ toYaml .container.env | indent 8 }}
{{- end }}
{{- end }}

{{- define "postgres-service" -}}
{{- if .service }}
apiVersion: v1
kind: Service
metadata:
  name: {{ .appName }}-service
  namespace: {{ .namespace }}
spec:
  {{ if .service.type }}type: {{ .service.type }}{{ end }}
  selector:
    app: {{ .appName }}
  ports:
  - protocol: TCP
    port: {{ .service.port }}
    targetPort: {{ .service.targetPort }}
    {{ if .service.nodePort }}nodePort: {{ .service.nodePort }}{{ end }}
{{- end }}
{{- end }}