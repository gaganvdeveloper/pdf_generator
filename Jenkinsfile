pipeline {
    agent any
    tools {
        maven 'apache-maven-3.9.10'   // Ensure this matches your Jenkins Maven installation name
        jdk 'Java 21'         // Ensure this matches your Jenkins JDK installation name
    }

    
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/gaganvdeveloper/pdf_generator.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -D skipTests'
            }
        }
    }
}
