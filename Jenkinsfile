pipeline{
    agent any

    stages{
        stage('Build Jar'){
            steps{
                sh "mvn clean package -DskipTests"
            }
        }
        stage('Build Image'){
            steps{
                sh "docker build -t=pratikbhusari220/selenium ."
            }
        }
        stage('Push Image'){
            steps{
                echo "docker push pratikbhusari220/selenium"
            }
        }
    }
}
