package com.example


pipeline {
  agent any

  stages {
    stage('Stage1') {
      steps {
        script {
          def datas = readYaml file: ‘config.yml '
          echo "Got version as ${datas.version} "
        }
        echo "Deploying to ${params.DEPLOY_ENV} with debug=${params.DEBUG_BUILD}"
      }
    }

    stages {
        stage('
          Build ') {
            steps {
                // Setup the database
                script {
                    withCredentials([string(credentialsId: '
          jenkins - database - username ', variable: '
          DATABASE_USERNAME ')]) {
                        withCredentials([string(credentialsId: '
          jenkins - database - password ', variable: '
          DATABASE_PASSWORD ')]) {
                            def test_database_credentials = buildTestMySQLDatabase {
                                dbUser = env.DATABASE_USERNAME
                                dbPass = env.DATABASE_PASSWORD
                            }
                            echo '
          Test Database Name: ' + test_database_credentials.dbName
                            echo '
          Test Username: ' + test_database_credentials.testUsername
                            echo '
          Test User Password: ' + test_database_credentials.testUserPassword
                        }
                    }
                }
            }
        }
    }
}


pipeline {
  agent any
  stages {
    stage('
          Slack Message ') {
      steps {
        slackSend channel: '#jenkins ',
            color: '
          good ',
            message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}"
      }
    }
  }
}'
