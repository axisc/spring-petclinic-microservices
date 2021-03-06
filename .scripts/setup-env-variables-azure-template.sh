#!/usr/bin/env bash

# ==== Resource Group ====
export SUBSCRIPTION=subscription-id # customize this
export RESOURCE_GROUP=resource-group-name # customize this
export REGION=westus2

# ==== Service and App Instances ====
export SPRING_CLOUD_SERVICE=azure-spring-cloud-name # customize this
export API_GATEWAY=api-gateway
export ADMIN_SERVER=admin-server
export CUSTOMERS_SERVICE=customers-service
export VETS_SERVICE=vets-service
export VISITS_SERVICE=visits-service
export COMMUNICATIONS_SERVICE=communications-service

# ==== JARS ====
export API_GATEWAY_JAR=spring-petclinic-api-gateway/target/spring-petclinic-api-gateway-2.2.1.jar
export ADMIN_SERVER_JAR=spring-petclinic-admin-server/target/spring-petclinic-admin-server-2.2.1.jar
export CUSTOMERS_SERVICE_JAR=spring-petclinic-customers-service/target/spring-petclinic-customers-service-2.2.1.jar
export VETS_SERVICE_JAR=spring-petclinic-vets-service/target/spring-petclinic-vets-service-2.2.1.jar
export VISITS_SERVICE_JAR=spring-petclinic-visits-service/target/spring-petclinic-visits-service-2.2.1.jar
export COMMUNICATIONS_SERVICE_JAR=spring-petclinic-visits-service/target/spring-petclinic-communications-service-2.2.1.jar

# ==== MYSQL INFO ====
export MYSQL_SERVER_NAME=mysql-servername # customize this
export MYSQL_SERVER_FULL_NAME=${MYSQL_SERVER_NAME}.mysql.database.azure.com
export MYSQL_SERVER_ADMIN_NAME=admin-name # customize this
export MYSQL_SERVER_ADMIN_LOGIN_NAME=${MYSQL_SERVER_ADMIN_NAME}\@${MYSQL_SERVER_NAME}
export MYSQL_SERVER_ADMIN_PASSWORD=SuperS3cr3t # customize this
export MYSQL_DATABASE_NAME=petclinic

# ==== SERVICE BUS INFO ====
export SERVICE_BUS_CONNECTION_STRING=connection-string-with-manage-permissions # customize this
export SERVICE_BUS_IDLE_TIMEOUT=20000 # customize this
export SERVICE_BUS_TOPIC_CLIENT_ID=test-application #customize this

# ==== KEY VAULT Info ====
export KEY_VAULT=your-keyvault-name # customize this

# ===== SENDGRID API ====
export SENDGRID_API_KEY=sendgrid-api-key #customize this

# ===== EMAIL OVERRIDES ====
export EMAIL_ACTIVE=false #pick true or false
export DEFAULT_RECIPIENT=john.doe@example.com #customize this

# ==== Application Insights ====
export APPLICATIONINSIGHTS_CONNECTION_STRING=InstrumentationKey=instrumentation-key # customize this


