node {
    stage('checkout') {
        checkout scm
    }

    stage('clean') {
        sh "./gradlew clean"
    }

    stage('backend tests') {
        try {
            sh "./gradlew test"
        } catch(err) {
            throw err
        } finally {
            step([$class: 'JUnitResultArchiver', testResults: '**/build/**/TEST-*.xml'])
        }
    }

    stage('packaging') {
        sh "./gradlew bootRepackage -Pprod -x test"
    }
}
