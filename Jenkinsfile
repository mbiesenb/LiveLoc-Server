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

                withMaven(maven : 'maven_3.6.1') {
                    sh 'mvn deploy'
                }

                def stdout = sh(script: 'java -jar *jar-with-dependencies.jar &', returnStdout: true)
                println stdout
            }
        }
    }
}