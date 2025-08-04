def call(Map config = [:]) {
    def imageName = config.imageName ?: error("Missing 'imageName'")
    def dockerfile = config.dockerfile ?: 'Dockerfile'
    def context = config.context ?: '.'
    def tag = config.tag ?: 'latest'
    def registry = config.registry ?: ''
    def push = config.push ?: false

    def fullImageName = registry ? "${registry}/${imageName}:${tag}" : "${imageName}:${tag}"
    def dockerfilePath = "${context}/${dockerfile}"

    echo "🛠️ Building Docker image: ${fullImageName}"

    sh """
        docker build -f ${dockerfilePath} -t ${fullImageName} ${context}
    """

    if (push) {
        echo "📤 Pushing Docker image: ${fullImageName}"
        withCredentials([usernamePassword(credentialsId: config.credentialsId ?: 'dockerCred', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh "echo \$PASSWORD | docker login ${registry} -u \$USERNAME --password-stdin"
            sh "docker push ${fullImageName}"
            sh "docker logout ${registry}"
        }
    }

    return fullImageName
}


// dockerBuild(
//     imageName: 'my-frontend-app',
//     dockerfile: 'Dockerfile',
//     context: 'frontend',         // 👈 build context inside ./frontend
//     tag: 'latest',
//     registry: 'renish1111',
//     push: true,
//     credentialsId: 'dockerCred'
// )

