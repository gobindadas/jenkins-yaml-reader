@Grab(group = 'org.jyaml', module = 'jyaml', version = '1.3')
import org.ho.yaml.Yaml

Yaml yml = new Yaml()
def build = Thread.currentThread().executable
def fileName = build.buildVariableResolver.resolve("fileName")
def configyaml = build.workspace.toString() + "/yaml-files/"+fileName;
def iStream = streamFileFromWorkspace(configyaml);
def jobSettings = yml.load(iStream);
  
  job(jobSettings.name) {
    description(jobSettings.description)
    scm {
        git(jobSettings.gitUrl)
    }

    (jobSettings.parameters.each { param->
      if (param.type == 'string') {
         parameters {
            stringParam(param.name, param.value)
         }
      }else if(param.type == 'boolean'){
         parameters {
            booleanParam(param.name, param.value)
         }
      }
            
    })
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
  }