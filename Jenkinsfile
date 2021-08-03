pipeline {
    agent {
        label "maven"
    }
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
            steps {
                sh 'mvn install'
            }

            post {
                always {
                    archiveArtifacts artifacts: 'target/*.jar, target/*.exe', fingerprint: true
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}