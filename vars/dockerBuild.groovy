def call(String image-name, String docker-repo) {
    sh "docker image build -t ${docker-repo}/${image-name} https://github.com/LondheShubham153/reddit-clone-k8s-ingress.git"
    sh "docker tag ${docker-repo}/${image-name} ${docker-repo}/${image-name}:${ImageTag}"
    sh "docker tag ${docker-repo}/${image-name} ${docker-repo}/${image-name}:latest"
    withCredentials([usernamePassword(
            credentialsId: "docker_cred",
            usernameVariable: "USER",
            passwordVariable: "PASS"
    )]) {
        sh "docker login -u '$USER' -p '$PASS'"
    }
    sh "docker image push ${docker-repo}/${image-name}:${ImageTag}"
    sh "docker image push ${docker-repo}/${image-name}:latest"
}
