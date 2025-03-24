@Library('jenkins-shared-library') _

pipeline {
    agent any

    environment {
        LANGUAGES = "go,html"  // Comma-separated as environment variables don't support lists
    }

    stages {
        stage('Clone Repo') {
            steps {
                script {
                    cloneRepo(repoName: 'mokadi-suryaprasad/jenkins-shared-library', branch: 'main')
                }
            }
        }

        stage('Run Pipelines for Both Go and HTML') {
            parallel {
                stage('Backend Go Pipeline') {
                    steps {
                        script {
                            catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                                runPipeline('go')
                            }
                        }
                    }
                }
                stage('Frontend HTML Pipeline') {
                    steps {
                        script {
                            catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                                runPipeline('html')
                            }
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
                if (currentBuild.result == 'FAILURE') {
                    def failedStages = findFailedStages()
                    sendNotification(success: false, language: failedStages)
                }
            }
        }
    }
}

// Function to Run the Pipeline
def runPipeline(String language) {
    stage("Run Tests for ${language}") {
        runTests(language: language)
    }

    stage("SonarQube Analysis for ${language}") {
        sonarQubeAnalysis(language: language)
    }

    stage("Sonar Quality Gate for ${language}") {
        sonarQualityGate()
    }

    stage("Build Code for ${language}") {
        buildCode(language: language)
    }

    stage("Build Docker Image for ${language}") {
        buildDockerImage(language: language)
    }

    stage("Trivy Scan for ${language}") {
        trivyScan(language: language)
    }

    stage("Push Docker Image for ${language}") {
        pushDockerImage(language: language)
    }

    stage("Update Kubernetes for ${language}") {
        updateKubernetes(language: language)
    }
}

// Helper Function to Detect Failed Stages
def findFailedStages() {
    def failedLanguages = []
    if (currentBuild.rawBuild.getActions(org.jenkinsci.plugins.workflow.support.steps.StageStepExecution.class).any { it.displayName.contains('Go') && it.result == Result.FAILURE }) {
        failedLanguages.add('go')
    }
    if (currentBuild.rawBuild.getActions(org.jenkinsci.plugins.workflow.support.steps.StageStepExecution.class).any { it.displayName.contains('HTML') && it.result == Result.FAILURE }) {
        failedLanguages.add('html')
    }
    return failedLanguages.join(',')
}
