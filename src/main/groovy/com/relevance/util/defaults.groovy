package com.relevance.util
public class defaults
{
	static void setParameters(ctx,jobSettings){		
		ctx.with{
			parameters {
				(jobSettings.parameters.each { p->
                  if(p.type == 'string'){                    
					stringParam(p.name,p.value)
                  }
				})
			}
		}
	}
}