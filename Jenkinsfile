pipeline {
    agent any
    stage ('Build Maven Project') {
        echo "Building"
        sh "docker build -t liveloc-worker ."
    }
}