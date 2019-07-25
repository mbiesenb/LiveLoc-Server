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
                def killProcess = sh('pkill -f *jar-with-dependencies.jar').stdout

                def removeFiles =  sh('rm -rf /home/martin/jenkins/codemwnci/kotlin-ws-chat/1.0-SNAPSHOT/*').stdout

                withMaven(maven : 'maven_3.6.1') {
                    sh 'mvn deploy'
                }

                def runApp =  sh('java -jar *jar-with-dependencies.jar &').stdout
            }
        }
    }
}