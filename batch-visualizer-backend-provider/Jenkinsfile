/* pipeline 변수 설정 */
def DOCKER_IMAGE_NAME = "twofootdog/project-repo"           // 생성하는 Docker image 이름
def DOCKER_IMAGE_TAGS = "batch-visualizer-backend-provider"  // 생성하는 Docker image 태그
def DOCKER_CONTAINER_NAME = "backend-provider-container"    // 생성하는 Docker Container 이름
def NAMESPACE = "ns-project"
def SLACK_CHANNEL = "#backend-provider"
def VERSION = "${env.BUILD_NUMBER}"
def DATE = new Date();

def notifyStarted(slack_channel) {
    slackSend (channel: "${slack_channel}", color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}
def notifySuccessful(slack_channel) {
    slackSend (channel: "${slack_channel}", color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}
def notifyFailed(slack_channel) {
  slackSend (channel: "${slack_channel}", color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}

podTemplate(label: 'builder',
            containers: [
                containerTemplate(name: 'gradle', image: 'gradle:5.6-jdk8', command: 'cat', ttyEnabled: true),
                containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
                containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.15.3', command: 'cat', ttyEnabled: true)
            ],
            volumes: [
                hostPathVolume(mountPath: '/home/gradle/.gradle', hostPath: '/home/admin/k8s/jenkins/.gradle'),
                hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
                //hostPathVolume(mountPath: '/usr/bin/docker', hostPath: '/usr/bin/docker')
            ]) {
    node('builder') {
        try {
            stage('Start') {
                /* Slack 메시지 전송 */
                notifyStarted(SLACK_CHANNEL)
            }
            stage('Checkout'){
                 checkout scm   // gitlab으로부터 소스 다운
            }
            stage('Test') {
                container('gradle') {
                    sh "gradle clean test -Dspring.profiles.active=dev"
                }
            }
            stage('Build') {
                container('gradle') {
                    /* 도커 이미지를 활용하여 gradle 빌드를 수행하여 ./build/libs에 jar파일 생성 */
                    /* sh """gradle -x test build sonarqube \
                                 -Dsonar.projectKey=batch-visualizer-backend-provider \
                                 -Dsonar.host.url=http://66.42.43.41:30002/sonar \
                                 -Dsonar.login=1c9a8c93a20ba4be5c542640cf5e5c4fb3e51a73
                    """
                    */
                    sh "gradle -x test build sonarqube"
                }
            }
            stage('Docker build') {
                container('docker') {
                    withCredentials([usernamePassword(
                        credentialsId: 'docker_hub_auth',
                        usernameVariable: 'USERNAME',
                        passwordVariable: 'PASSWORD')]) {
                            /* ./build/libs 생성된 jar파일을 도커파일을 활용하여 도커 빌드를 수행한다 */
                            sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAGS} ."
                            sh "docker login -u ${USERNAME} -p ${PASSWORD}"
                            sh "docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAGS}"
                    }
                }
            }
            stage('Run kubectl') {
                container('kubectl') {
                    withCredentials([usernamePassword(
                        credentialsId: 'docker_hub_auth',
                        usernameVariable: 'USERNAME',
                        passwordVariable: 'PASSWORD')]) {
                            /* secret 존재여부 확인. 미존재시 secret 생성성 */
                            sh """
                                kubectl get secret my-secret -n ${NAMESPACE} || \
                                kubectl create secret docker-registry my-secret \
                                --docker-server=https://index.docker.io/v1/ \
                                --docker-username=${USERNAME} \
                                --docker-password=${PASSWORD} \
                                --docker-email=ekfrl2815@gmail.com \
                                -n ${NAMESPACE}
                            """
                            /* namespace 존재여부 확인. 미존재시 namespace 생성 */
                            sh "kubectl get ns ${NAMESPACE}|| kubectl create ns ${NAMESPACE}"

                            /* k8s-deployment.yaml 의 env값을 수정해준다(VERSION과 DATE로). 배포시 수정을 해주지 않으면 변경된 내용이 정상 배포되지 않는다. */
                            sh "echo ${VERSION}"
                            sh "sed -i.bak 's#VERSION_STRING#${VERSION}#' ./k8s/k8s-deployment.yaml"
                            sh "echo ${DATE}"
                            sh "sed -i.bak 's#DATE_STRING#${DATE}#' ./k8s/k8s-deployment.yaml"

                            /* yaml파일로 배포를 수행한다 */
                            sh "kubectl apply -f ./k8s/k8s-deployment.yaml -n ${NAMESPACE}"
                            sh "kubectl apply -f ./k8s/k8s-service.yaml -n ${NAMESPACE}"

                    }
                }
            }
            notifySuccessful(SLACK_CHANNEL)
        } catch(e) {
        /* 배포 실패 시 */
            currentBuild.result = "FAILED"
            notifyFailed(SLACK_CHANNEL)
        }
    }
}