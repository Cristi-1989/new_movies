pipeline {
    agent any

    stages {
        stage('Lint java code by running gradlew checkstyle') {
             steps {
                 sh './gradlew checkstyleMain'
             }
        }

    }
}