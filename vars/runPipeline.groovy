def call(String language) {
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
