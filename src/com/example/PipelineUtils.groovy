package com.example

class PipelineUtils {
    static void printMessage(String message) {
        echo "[INFO] ${message}"
    }

    static void handleError(String errorMessage) {
        echo "[ERROR] ${errorMessage}"
        error(errorMessage)
    }
}
