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

        stage('Build Image Stage') {
            steps {
                //sh 'docker stop $(docker ps -q --filter ancestor=liveloc-worker )'
                echo "docker container stop ${dockerName} txt"
                sh "docker container stop ${dockerName}"
                script {
                    def customImage = docker.build(myVar)
                }
            }
        }
        stage('Start Container Stage') {
            steps {
                sh "docker run ${myVar} --name ${dockerName}"
            }

        }
    }
}