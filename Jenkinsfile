pipeline {
    agent {
        kubernetes {
          label 'sakai-tca-automations-build-pod'
          yamlFile 'JenkinsBuildPod.yaml'
        }
    }

    parameters {
        string(name: 'environment', defaultValue: 'uscalp', description: 'Environments')
        string(name: 'course', defaultValue: 'Test TCA', description: 'Enter course name')
        string(name: 'assignment', defaultValue: 'Test automation assignment', description: 'Enter assignment name')
        string(name: 'Channel', defaultValue: 'ints-tfs-jenkins-jobs', description: 'Enter channel name')
    }

    environment {
        HOME = "."
    }
    triggers {
            cron('0 5 * * 1-7')
        }
    stages {
        stage('Test') {
            steps {
                container('javaqa') {
                   sh 'use_gradle_version 5.6.2'
                    ansiColor('xterm') {
                        script{
                            echo 'Running tests against ' + params.environment
                            sh 'gradle clean test -Denvironment=' + params.environment + ' -Dcourse="' + params.course + '" -Dassignment="' + params.assignment +'"'
                        }
                    }
                }
            }
        }
    }

    post {

       success {
           slackSend color: 'good',
           channel: params.Channel,
           message: " :grin::tada::thumbsup::skin-tone-2:SUCCESS:thumbsup::skin-tone-2::tada::grin: " + env.JOB_NAME + "\n" + env.BUILD_URL
       }

       failure {
         script {
             slackSend color: 'danger',

             channel: params.Channel,
             message: ":sob::thumbsdown::skin-tone-2:FAILURE - @here:thumbsdown::skin-tone-2::sob: " + env.JOB_NAME + "\n" + env.BUILD_URL

         }
       }

      always {
        container('jnlp') {
          publishHTML(target: [
             allowMissing         : false,
             alwaysLinkToLastBuild: true,
             includes             : '**/*',
             keepAll              : true,
             reportDir            : 'target/site/serenity',
             reportFiles          : 'index.html',
             reportName           : 'Serenity'
                      ]
          )
        }
      }
    }

}
