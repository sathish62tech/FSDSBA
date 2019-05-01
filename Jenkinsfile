pipeline {
  agent any
  stages {
    stage('Build Frontend Application') {
      steps {
        echo 'Building Frontend..'
        bat 'cd FinalFrontEnd &&npm i && npm run-script build'
      }
    }
    stage('Build Backend Application') {
      steps {
        echo 'Building Backend..'
        bat 'cd Backend && cd TaskTracker && mvn clean'
      }
    }
    stage('Test Frontend Application') {
      steps {
        echo 'Testing Frontend..'
        bat 'cd FinalFrontEnd && npm i && npm test'
      }
    }
    stage('Test and install Backend Application') {
      steps {
        echo 'Testing Backend..'
        bat 'cd Backend && cd TaskTracker && mvn install'
      }
    }
  }
  tools {
    maven 'M2_HOME'
    jdk 'JAVA_HOME'
  }
}