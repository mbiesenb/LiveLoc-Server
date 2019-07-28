node {
    try {
        stage('Handle running container') {
            try {
                echo "Stopping Container"
                sh "docker stop liveloc-worker"
                echo "Removing Container"
                sh "docker rm -f liveloc-worker"
            } catch (e) {
                echo "there was no existing container to stop, this may can lead to problems later in the pipeline: {$e}"
            }
        }
        stage('Dockerimage - Build') {
            echo "Building"
            sh "docker build -t liveloc-worker ."
        }
        stage('Deploy Dockerimage -> Server') {
            echo "deploy to server .."
            sh "docker run \
                    -p 9000:9000 \
                    --name liveloc-worker \
                    -d liveloc-worker   "
            echo "deployed and started successfully to server!"
        }
        stage('Clean deprecated docker images') {
            sh "docker image prune -a -f"
            echo "Build Server is successfully cleaned"
        }
        stage('Cleanup workspace'){
            echo "Cleanup workspace..."
            deleteDir()
            echo "Cleanup workspace successfully!"
        }
    } catch (e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {

    }
}
