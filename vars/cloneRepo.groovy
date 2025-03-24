def call() {
    script {
        withCredentials([string(credentialsId: 'github-pat', variable: 'GITHUB_TOKEN')]) {
            echo "Cloning repository from GitHub"

            try {
                sh '''
                    # Clean up any previous workspace
                    rm -rf workspace

                    # Clone the repository
                    if ! git clone -b ${GIT_BRANCH} https://x-access-token:${GITHUB_TOKEN}@github.com/${REPO_NAME}.git workspace; then
                        echo "Failed to clone the repository. Please check the branch and repository name."
                        exit 1
                    fi

                    cd workspace

                    # Configure Git user
                    git config --global user.email "msuryaprasad11@gmail.com"
                    git config --global user.name "MSR"
                '''
                echo "Repository cloned and Git configured successfully!"
            } catch (Exception e) {
                echo "Error occurred: ${e.getMessage()}"
                error "Failed to clone repository."
            }
        }
    }
}
