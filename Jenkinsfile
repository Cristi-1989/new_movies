pipeline {
    agent any

    environment {
        APP_NAME = "new-movies"
        REGISTRY = "crististoica/${APP_NAME}"
        IMAGE = "${REGISTRY}:v${BUILD_NUMBER}"
        VERSION = "v${BUILD_NUMBER}"
        DOCKER_CREDS = 'cristi_docker'
        AWS_REGION = 'us-west-2'
        AWS_CREDENTIALS = "cristi_aws"
        EKS_CLUSTER = "my-cluster"
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
                sh 'echo "Building image ${IMAGE}"'

                script {
                    dockerImage = docker.build REGISTRY + ":v$BUILD_NUMBER"
                }
            }
        }
        stage('Push our image') {
            steps {
                script {
                    docker.withRegistry('', DOCKER_CREDS) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Configure kubeconfig') {
            steps {
                withAWS(credentials: "${AWS_CREDENTIALS}", region: "${AWS_REGION}") {
                    sh 'aws eks update-kubeconfig --name $EKS_CLUSTER --kubeconfig ~/kubeconfig'
                }
            }
        }

        stage('Get BLUE version') {
            steps {
                withAWS(credentials: "${AWS_CREDENTIALS}", region: "${AWS_REGION}") {
                    script {
                        env.BLUE_VERSION=sh(script:  "/bin/bash -c 'kubectl --kubeconfig ~/kubeconfig get service $APP_NAME-service -o=jsonpath=\'{.spec.selector.version}\''", returnStdout: true)
                    }
                }
            }
        }
        stage('Deploy Green (current) version') {
            steps {
                withAWS(credentials: "${AWS_CREDENTIALS}", region: "${AWS_REGION}") {
                    script {
                        sh(script:  "/bin/bash -c 'kubectl --kubeconfig ~/kubeconfig get deployment $APP_NAME-deployment-$BLUE_VERSION -o=yaml | sed -e \"s/$BLUE_VERSION/$VERSION/g\" | kubectl --kubeconfig ~/kubeconfig apply -f -'", returnStdout: true)
                    }
                }
            }
        }
        stage('Check rollout status') {
            steps {
                withAWS(credentials: "${AWS_CREDENTIALS}", region: "${AWS_REGION}") {
                    sh 'kubectl --kubeconfig ~/kubeconfig rollout status deployment/$APP_NAME-deployment-$VERSION'
                }
            }
        }
        stage('Deploy new service') {
            steps {
                withAWS(credentials: "${AWS_CREDENTIALS}", region: "${AWS_REGION}") {
                    script {
                        sh(script:  "/bin/bash -c 'kubectl --kubeconfig ~/kubeconfig get service ${APP_NAME}-service -o=yaml | sed -e \"s/$BLUE_VERSION/$VERSION/g\" | kubectl --kubeconfig ~/kubeconfig apply -f -'", returnStdout: true)
                    }
                }
            }
        }
        stage('Delete BLUE version') {
            steps {
                withAWS(credentials: "${AWS_CREDENTIALS}", region: "${AWS_REGION}") {
                    sh 'kubectl --kubeconfig ~/kubeconfig delete deployment $APP_NAME-deployment-$BLUE_VERSION'
                }
            }
        }
    }
}