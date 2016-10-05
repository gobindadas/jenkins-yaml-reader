@Grab(group = 'org.jyaml', module = 'jyaml', version = '1.3')
import org.ho.yaml.Yaml

Yaml yml = new Yaml()
def build = Thread.currentThread().executable
def fileName = build.buildVariableResolver.resolve("fileName")
def jobName = build.buildVariableResolver.resolve("jobName")
def configyaml = build.workspace.toString() + "/yaml-files/"+fileName;
def iStream = streamFileFromWorkspace(configyaml);
def jobSettings = yml.load(iStream);

  // Create job with given name
  job(jobName) {
    if(jobSettings.description){
      description(jobSettings.description)
    }
    scm {
        git(jobSettings.gitUrl,jobSettings.branchName)
    }
    if(jobSettings.parameters && jobSettings.parameters.length){
    (jobSettings.parameters.each { param->
      
      switch(param.type){
        case 'string':
         parameters {
            stringParam(param.name, param.value, param.description)
         }
        break;
        case 'boolean':
         parameters {
            booleanParam(param.name, param.value, param.description)
         }
        break;
      }     
    })
  }
    if(jobSettings.mavenCMD){
      steps{
        maven(jobSettings.mavenCMD)
      }
    }
    if(jobSettings.shellCMD){
      steps{
        shell(jobSettings.shellCMD)
      }
    }
    if(jobSettings.assignedNode){
      steps{
        label(jobSettings.assignedNode)
      }
    }
    if(jobSettings.recipients){
      publishers {
        extendedEmail {
            recipientList(jobSettings.recipients)
        }
      }
    }
    if(jobSettings.schedule){
      triggers {
        scm(jobSettings.schedule)
      }
    }
  }