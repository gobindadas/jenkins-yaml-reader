package com.relevance.util
import org.ho.yaml.Yaml
import jenkins.model.*
public class jobdatareader {
	static def readDataFile(jobname){
		Yaml yml = new Yaml()
		def build = Thread.currentThread().executable
		def configyaml=build.workspace.toString()+"\\data\\" + jobname + ".yaml";
		println configyaml;

		InputStream istream =new FileInputStream( new File(configyaml));
		def jobSettings= yml.load(istream);
		println "${jobSettings.label}"
		return jobSettings
	}
}

