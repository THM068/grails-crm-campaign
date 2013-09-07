/*
 * Copyright (c) 2013 Goran Ehrsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import grails.plugins.crm.campaign.CampaignArtefactHandler
import grails.plugins.crm.campaign.GrailsCampaignClass
import grails.spring.BeanBuilder

class CrmCampaignGrailsPlugin {
    // Dependency group
    def groupId = "grails.crm"
    // the plugin version
    def version = "1.2.0-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    def loadAfter = ['crmTags']
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/domain/test/**",
            "grails-app/views/error.gsp"
    ]
    def watchedResources = [
            "file:./grails-app/campaigns/**/*Campaign.groovy",
            "file:./plugins/*/grails-app/campaigns/**/*Campaign.groovy"
    ]
    def artefacts = [new CampaignArtefactHandler()]

    def title = "Grails CRM Campaign Plugin" // Headline display name of the plugin
    def author = "Goran Ehrsson"
    def authorEmail = "goran@technipelago.se"
    def description = '''\
Campaign Management for Grails CRM
'''
    def documentation = "http://grails.org/plugin/crm-campaign"
    def license = "APACHE"
    def organization = [name: "Technipelago AB", url: "http://www.technipelago.se/"]
    def issueManagement = [system: "github", url: "https://github.com/goeh/grails-crm-campaign/issues"]
    def scm = [url: "https://github.com/goeh/grails-crm-campaign"]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // Configure campaign handlers
        def campaignClasses = application.campaignClasses
        campaignClasses.each { campaignClass ->
            "${campaignClass.propertyName}"(campaignClass.clazz) { bean ->
                bean.autowire = "byName"
                //bean.scope = "prototype"
            }
        }
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        println "Installed campaign handlers ${application.campaignClasses*.propertyName}"
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
        if (application.isCampaignClass(event.source)) {
            log.debug "Campaign ${event.source} modified!"

            def context = event.ctx
            if (!context) {
                log.debug("Application context not found - can't reload.")
                return
            }

            // Make sure the new selection class is registered.
            def campaignClass = application.addArtefact(GrailsCampaignClass.TYPE, event.source)

            // Create the selection bean.
            def bb = new BeanBuilder()
            bb.beans {
                "${campaignClass.propertyName}"(campaignClass.clazz) { bean ->
                    bean.autowire = "byName"
                    //bean.scope = "prototype"
                }
            }
            bb.registerBeans(context)
        }
    }
}
