def d_work = "/app/jenkins/workspace/eproc" 

pipeline {
  	agent {
    	label 'bhp-dataflow-dev'
  	}
 
  	stages {
      	stage('Checkout') {
      					steps {
        					checkout scm
      					}
    	}
    	
    stage('Clean') {
        steps {
                script {
                		sh "chmod +x ${d_work}/mvnw"
       					sh "./mvnw clean"
                    }
            }
        }
    	
    stage('Packaging & Create Image') {
     steps {
                script {
                		sh "./mvnw package -Pprod -DskipTests verify jib:dockerBuild"
                    }
            }
        }
   
    stage('Remove Failed Container') {
        steps {
                script {
                		sh "chmod +x ${d_work}/removenoneimages/bin/removenoneimage.sh"
       					sh "${d_work}/removenoneimages/bin/removenoneimage.sh"
                    }
            }
        }
  }
}