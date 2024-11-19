To run Bookstore locally:
--------------------------------------
1. Navigate to folder src/main/resources 
2. Create application-dev.properties file based on application.properties file
3. Run spring boot with active profiles dev
--------------------------------------

To start SonarQube, Grafana and Prometheus containers:
--------------------------------------
1. Navigate to folder bookstore-backend/monitoring
2. Run docker command to start the containers: docker compose up --detach
3. Prometheus will start at: http://localhost:9090/
4. Grafana will start at: http://localhost:3000/
5. SonarQube will start at: http://localhost:9004/
--------------------------------------

To run Sonar:
--------------------------------------
1. Navigate to SonarQube http://localhost:9004/
2. Create a new Project and keep note of the projectKey
3. Create sonar login token for the created project
4. Replace from the following command {PROJECT_KEY} and {LOGIN_TOKEN} with the ones created on the above steps and run it: `mvn clean verify sonar:sonar -Dsonar.projectKey={PROJECT_KEY} -Dsonar.host.url=http://localhost:9004 -Dsonar.login={LOGIN_TOKEN}`
-------------------------------------