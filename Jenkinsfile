dockerName = 'liveloc-worker'

pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
                echo 'Starting to compile java files'
                withMaven(maven : 'maven_3.6.1') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Deployment Stage') {
            steps {
                echo 'Starting to deploy java files'
                withMaven(maven : 'maven_3.6.1') {
                    sh 'mvn deploy'
                }
            }
        }
        // ${dockerName}
        stage('Build Image Stage') {
            steps {
                script {
                    // Kill if exists
                    def statusStop = sh "docker container stop ${dockerName} || true"
                    def statusRm = sh "docker container rm ${dockerName} || true"

                    // Build image
                    def customImage = docker.build("${dockerName}")
                }
            }
        }
        stage('Start Container Stage') {
            steps {
                sh "docker run --name liveloc-worker -p 9000:9000 ${dockerName} &"
            }

        }
    }
}