#  Project Manager -FSD SBA project

This project Front end was generated with [Angular CLI](https://github.com/angular/angular-cli) version 7.3.0. Backend from Spring Initializr for Spring Boot.

# FrontEnd

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. 

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

# Backend 

Run TaskTrackerApplication as Java Application. Backend is mappend to port 8083, i.e.,`http://localhost:8083/`.

Run project as 'mvn install' to regenerate the .war files. Test reports are accessible in /target folder. 
For component level test reports run the classes in src/test/java individually.

# CICD

# Jenkins pipeline will execute in pipeline order as :
        Checkout from git
        Build FrontEnd Applilcation (Angular)
        Build Backend Application (Spring Boot)
        Test FrontEnd (Angular)
        Maven Install Backend (Spring Boot)
# To bring the Application up and running from Docker Containers:
        Angular : Navigate to FinalFrontEnd
        $ docker build -t .
        Spring Boot: Navigate to Backend/TaskTracker
        $ docker-compose up -- build
