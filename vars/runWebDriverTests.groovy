/**
 * This file is used to create Jenkins declarative pipeline, it has been added in Jenkins as a folder level shared library.
 * can access these files using `@Library('webdriver-common-library') _`.
 * We can call this file using filename e.g. runWebDriverTests( parameters like testEnvironment: 'production_global').
 *
 * @param userConfig an instance of Map contains parameters e.g. environment.
 * @return Jenkins declarative pipeline.
 */
library 'shared_lib'

def call(Map userConfig) {
    validate(userConfig)

    def podLabel = "webdriver-e2e-${userConfig.testEnvironment}-test-runner"
    def branchName = userConfig.getOrDefault('branch', 'master')
    def command_report = "${userConfig.command}"

    pipeline {
        agent {
            kubernetes {
                label podLabel
                yaml buildPodDefinitions()
            }
        }

        environment {
            HOME = "."
            LTI_ACCOUNT_ID =  "${userConfig.account}"
            LTI_ACCOUNT_KEY = "${userConfig.accountKey}"
            LTI_ACCOUNT_PASSWORD = "${userConfig.accountPassword}"
            LTI_HUB_URL = "${userConfig.seleniumGrid}"
            LTI_URL = "${userConfig.url}"
            LTI_ACCOUNT_EMAIL = "${userConfig.accountEmail}"
            LTI_TIMEZONE = "${userConfig.timezone}"
            BROWSER = "${userConfig.browser}"
            COMMAND = "${userConfig.command}"
            LTI_TEST_ENV="${userConfig.ltiTestEnv}"
        }

        parameters {
            string(name: 'Channel', defaultValue: 'ints-tfs-jenkins-jobs', description: 'Enter channel name')
        }


        stages {
            stage('Checkout') {
                steps {
                    git branch: branchName,
                            credentialsId: 'tiipghci_global_credentials',
                            url: 'https://ghe.iparadigms.com/Integrations/LTI_web_driver.git'

                }
            }

            stage('Decrypt secrets') {
                steps {
                    withCredentials([file(credentialsId: 'GITCRYPT_KEY', variable: 'GITCRYPT_KEY')]) {
                        sh "git-crypt unlock $GITCRYPT_KEY"
                    }
                }
            }

            stage('Test') {
                steps {
                    script {
                        container('javaqa') {
                            sh 'use_gradle_version 5.6.2'
                            ansiColor('xterm') {
                                script {
                                    def envName = "${userConfig.testEnvironment}"
                                    def tagName = "${userConfig.tag}"
                                    if (env.COMMAND == null) {
                                        env.COMMAND = "testFullSuite"
                                    }
                                    echo 'Running test for ' + envName
                                    sh './gradlew clean $COMMAND'
                                }
                            }
                        }

                    }

                }
            }
        }
        post {

            success {
                script {
                    if ( "${userConfig.command}" != 'testHeartBeat') {
                    slackSend(channel: params.Channel, color: '#00FF00', message: env.JOB_NAME + " on " + "${userConfig.testEnvironment}" + " environment looks good! :successkid:")
                    }
                }

            }

            failure {
                script {
                    slackSend (channel: params.Channel, color: '#FF0000', message: env.JOB_NAME +" failed on " + "${userConfig.testEnvironment}" + " environment :broken_heart: (" + env.BUILD_URL +")")
                }
            }

            always {
                container('jnlp') {
                    junit 'build/test-results/' + command_report + '/*.xml'
                    publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: false,
                            reportDir: 'build/reports/tests/' + command_report,
                            reportFiles: 'index.html',
                            reportName: 'HTML Report',
                            reportTitles: ''
                    ])
                }
            }
        }
    }
}

/**
 * Generate the YAML needed to run Kubernetes pods.
 **/
def buildPodDefinitions() {
    return """
apiVersion: v1
kind: Pod
spec:
  securityContext:
    runAsUser: 1000
  imagePullSecrets: ['appops-jenkins-ci']
  containers:
    - name: jnlp
      image: quay.io/turnitin/jenkins-jnlp-agent:latest
      tty: true
      securityContext:
        runAsUser: 10000
        allowPrivilegeEscalation: false
    - name: javaqa
      image: quay.io/turnitin/java-base:8
      tty: true
      securityContext:
        runAsUser: 0
        privileged: true
  """
}

def validate(Map config) {
    requiredParams = ['testEnvironment','account','accountKey','accountPassword','seleniumGrid','url','accountEmail','browser']
    for (String p : requiredParams) {
        if (isBlank(config[p])) {
            error("Missing required parameter '${p}'")
        }
    }
}

def isBlank(String s) {
    return s == null || s.toString().trim() == ""
}
