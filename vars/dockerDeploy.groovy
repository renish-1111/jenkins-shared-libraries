def call(Map config = [:]) {
    def composeFile = config.composeFile ?: 'docker-compose.yml'
    def workingDir = config.workingDir ?: '.'

    echo "Deploying using Docker Compose"

    dir(workingDir) {
        sh "docker compose -f ${composeFile} down"
        sh "docker compose -f ${composeFile} up -d"
    }
}

// dockerComposeDeploy(
//                         composeFile: 'docker-compose.yml',
//                         workingDir: '.' // or 'backend' or 'deploy' folder
// )
