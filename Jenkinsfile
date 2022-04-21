pipeline {
    agent any
     tools('Prepare tools') {
    maven 'localMaven'
  }
     parameters {
        // booleanParam(name: "TEST_BOOLEAN", defaultValue: true, description: "Sample boolean parameter")
        string(name: "URL", defaultValue: "Github URL", trim: true, description: "Github URL")
        // text(name: "TEST_TEXT", defaultValue: "Jenkins Pipeline Tutorial", description: "Sample multi-line text parameter")
        // password(name: "TEST_PASSWORD", defaultValue: "SECRET", description: "Sample password parameter")
        // choice(name: "TEST_CHOICE", choices: ["production", "staging", "development"], description: "Sample multi-choice parameter")
    }
    stages {

            stage('Clone Repo') {
                steps {
                git credentialsId: 'githubPK', url: ''
                }
            }
            stage('Build') {
                steps {
                sh 'mvn clean'
                }
            }
            stage('Teste') {
                steps {
                    sh ' mvn test'
                }
            }
            stage('Package') {
                steps {
                    sh ' mvn package'
                }
            }
            stage('Deploy') {
                steps {
                    sh ' mvn deploy'
                }
            }


    }
}