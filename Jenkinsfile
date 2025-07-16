pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-1'
        ECR_REPO = '314175685418.dkr.ecr.${AWS_REGION}.amazonaws.com/Kuddle-auth'
        IMAGE_TAG = "latest"
    }

    tools {
        maven 'Maven 3.8.5'
    }

    stages {
        stage('Check and Install Java') {
            steps {
                script {
                    def javaInstalled = sh(script: 'java -version', returnStatus: true) == 0
                    if (javaInstalled) {
                        echo "Java is already installed. Skipping installation."
                    } else {
                        echo "Java not found. Proceeding with installation..."
                        sh '''
                            sudo apt update -y
                            sudo apt install openjdk-11-jdk -y
                        '''
                    }
                }
            }
        }
        
        stage('Check and Install Maven') {
            steps {
                script {
                    def mavenInstalled = sh(script: 'which mvn', returnStatus: true) == 0
                    if (mavenInstalled) {
                        echo "Maven is already installed. Skipping installation."
                    } else {
                        echo "Maven not found. Proceeding with installation..."
                        sh '''
                            chmod +x maven.sh
                            ./maven.sh
                        '''
                    }
                }
            }
        }

        stage('Check and Install Git') {
            steps {
                script {
                    def gitInstalled = sh(script: 'which git', returnStatus: true) == 0
                    if (gitInstalled) {
                        echo "Git is already installed. Skipping installation."
                    } else {
                        echo "Git not found. Proceeding with installation..."
                        sh '''
                            sudo apt update -y
                            sudo apt install git -y
                        '''
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                git url: 'https://github.com/nhnaveen/Kuddle-auth.git', branch: 'main'
            }
        }

        stage('Build and package with Maven') {
            steps {
                sh '''
                  mvn clean install
                  mvn clean package
                '''
            }
        }
    }
}
