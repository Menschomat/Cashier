pipeline {
    agent none
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                echo "PATH = ${PATH}"
                echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage ('Build') {
            agent {
                label "arm64-docker"
            }
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }

            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}