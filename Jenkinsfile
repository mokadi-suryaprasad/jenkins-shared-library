@Library('jenkins-shared-library') _
pipeline {
    agent any
    environment {
        LANGUAGES = ['go', 'html']
    }
    stages {
        stage('Clone Repo') {
            steps {
                script {
                    cloneRepo()
                }
            }
        }
        stage('Run Pipelines for Both Go and HTML') {
            parallel {
                stage('Backend Go Pipeline') {
                    steps {
                        script {
                            runPipeline('go')
                        }
                    }
                }
                stage('Frontend HTML Pipeline') {
                    steps {
                        script {
                            runPipeline('html')
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            script {
                sendNotification(success: true, language: 'all')
            }
        }
        failure {
            script {
                sendNotification(success: false, language: 'all')
            }
        }
    }
}
