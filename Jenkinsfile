pipeline {
    agent any

    stages {
        stage('Lint java code') {
             steps {
                 sh './gradlew checkstyleMain'
             }
        }
        stage('BUILD') {
            steps {
                sh './gradlew build'
            }
        }

    }
}