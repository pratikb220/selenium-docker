pipeline{
    agent any

    stages{
        stage('Build Jar'){
            steps{
                bat "mvn clean package -DskipTests"
            }
        }
        stage('Build Image'){
            steps{
                bat "docker build -t=pratikbhusari220/selenium ."
            }
        }
        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('dockerhub-creds')
            }
            steps{
                bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
                bat "docker push pratikbhusari220/selenium"
            }
        }
    }
    post{
        always{
            bat "docker logout"
        }
    }
}
