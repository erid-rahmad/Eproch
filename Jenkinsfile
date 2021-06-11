pipeline {
  	agent {
    	label 'bhp-dwh-prd'
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
                		sh "chmod +x mvnw"
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
		
	stage('Restart Eproc Demo') {
        steps {
                script {
						sh "chmod +x script/demoeproc"
       					sh "sudo /usr/bin/monit restart eprocdemo"
                    }
            }
        }	
   
    stage('Remove Failed Container') {
        steps {
                script {
                		sh "chmod +x removenoneimage.sh"
       					sh "sh removenoneimage.sh"
                    }
            }
        }
  }
}