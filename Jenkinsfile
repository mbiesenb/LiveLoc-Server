pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'maven_3.6.1') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Deployment Stage') {
            steps {


                sh('rm -rf /home/martin/jenkins/codemwnci/kotlin-ws-chat/1.0-SNAPSHOT/*')
                //sh('sudo pkill -f *jar-with-dependencies.jar')
                withMaven(maven : 'maven_3.6.1') {
                    sh 'mvn deploy'
                }
                sh('mv /home/martin/jenkins/codemwnci/kotlin-ws-chat/1.0-SNAPSHOT/*dependencies.jar liveloc.jar')
                //sh('java -jar /home/martin/jenkins/codemwnci/kotlin-ws-chat/1.0-SNAPSHOT/*dependencies.jar')
                //sh('java -jar *jar-with-dependencies.jar &')

                //println stdout
            }
        }

        stage('Build image') {
            steps {
                echo 'Starting to build docker image'
                script {
                    def customImage = docker.build("my-image:${env.BUILD_ID}")
                        customImage.push()
                }
            }
        }
    }
}