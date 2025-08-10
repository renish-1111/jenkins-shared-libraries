def call(String url, String branch, String credentialsId = null) {
    if (credentialsId) {
        git url: "${url}", branch: "${branch}", credentialsId: credentialsId
    } else {
        git url: "${url}", branch: "${branch}"
    }
}
