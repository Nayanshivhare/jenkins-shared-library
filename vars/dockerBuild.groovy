def call(String imagename, String dockerrepo) {
    sh "docker image build -t ${dockerrepo}/${imagename} . "
    sh "docker tag ${dockerrepo}/${imagename} ${dockerrepo}/${imagename}:${ImageTag}"
    sh "docker tag ${dockerrepo}/${imagename} ${dockerrepo}/${imagename}:latest"
    withCredentials([usernamePassword(
            credentialsId: "docker_cred",
            usernameVariable: "USER",
            passwordVariable: "PASS"
    )]) {
        sh "docker login -u '$USER' -p '$PASS'"
    }
    sh "docker image push ${dockerrepo}/${imagename}:${ImageTag}"
    sh "docker image push ${dockerrepo}/${imagename}:latest"
}
