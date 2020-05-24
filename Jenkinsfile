pipeline {
    agent any

    tools {
        jdk 'jdk8'
    }
    stages {
        stage('Lint java code by running gradlew checkstyle') {
             steps {
                 sh './gradlew checkstyleMain '
             }
        }

    }
}