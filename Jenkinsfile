node {
    withMaven(maven:'maven_3.6.1') {
        stage('Checkout') {
            git url: 'https://github.com/mbiesenb/LiveLoc-Server.git', credentialsId: 'mbiesenb', branch: 'master'
        }
        stage('Build') {
            sh 'mvn clean install'
            def pom = readMavenPom file:'pom.xml'
            print pom.version
            env.version = pom.version
        }
        stage('Image') {
            dir ('discovery-service') {
                def app = docker.build "localhost:5000/liveloc-worker:${env.version}"
                app.push()
            }
        }
        stage ('Run') {
            docker.image("localhost:5000/liveloc-worker:${env.version}").run('-p 9000:9000 --name liveloc-worker')
        }
    }
}