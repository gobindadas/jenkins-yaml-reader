
import org.ho.yaml.Yaml
import jenkins.model.*

Yaml yml = new Yaml()
		def build = Thread.currentThread().executable
		def configyaml=build.workspace.toString()+"\\data\\d4d.yaml"; 

		InputStream istream =new FileInputStream( new File(configyaml));
		def jobSettings= yml.load(istream);

job('bluenode3') {	
    scm {
        git(jobSettings.giturl)
    }   
   parameters {
				(jobSettings.parameters.each { p->
                  if(p.type == 'string'){                    
					stringParam(p.name,p.value)
                  }
				})
			}
    steps {
        maven('-e clean test')
    }
}