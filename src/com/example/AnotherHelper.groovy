package com.example

class AnotherHelper {

    static void dockerLogin(String username, String password, String registry) {
        sh """
            echo "${password}" | docker login ${registry} -u ${username} --password-stdin
        """
    }

    static void notifySlack(String channel, String message) {
        echo "[SLACK] Sending notification to ${channel}: ${message}"
        // Slack integration code can be added here
    }
}
