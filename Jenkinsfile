pipeline {
    agent any

    environment {
        APP_NAME = "new_movies"
//        IMAGE_TAG = "${APPNAME}:${env.BUILD_NUMBER}"
        REGISTRY = "crististoica/${APPNAME}"
        DOCKER_CREDS = 'cristi_docker'
        AWS_REGION = 'us-west-2'
        AWS_CREDENTIALS = "cristi_aws"
    }

    stages {
        stage('Lint java code') {
            steps {
                sh './gradlew checkstyleMain'
            }
        }
        stage('build app') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Build image') {
            steps {
                sh """
                echo "Cleaning up all images and containers ${IMAGE_TAG}"
                """
            }
            script {
                dockerImage = docker.build REGISTRY + ":$BUILD_NUMBER"
            }
        }
        stage('Deploy our image') {
            steps {
                script {
                    docker.withRegistry('', DOCKER_CREDS) {
                        dockerImage.push()
                    }
                }
            }
        }
    }
}