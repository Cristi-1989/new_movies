pipeline {
    agent any

    tools {
        gradle "gradle-6.3"
        jdk 'jdk11'
    }
    stages {
        stage('Lint java code by running gradlew checkstyle') {
             steps {
                 sh './gradlew checkstyleMain '
             }
        }

    }
}