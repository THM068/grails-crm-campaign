package grails.plugins.crm.campaign

import grails.plugins.crm.core.TenantUtils

/**
 * Campaign Tags.
 */
class CrmCampaignTagLib {

    static namespace = "crm"

    def grailsApplication
    def crmCampaignService

    def campaign = { attrs, body ->
        def tenant = attrs.tenant ?: (TenantUtils.tenant ?: grailsApplication.config.crm.content.include.tenant)
        if (attrs.campaign) {
            def resource = TenantUtils.withTenant(tenant) {
                def crmCampaign = crmCampaignService.findByCode(attrs.campaign)
                if (crmCampaign) {
                    return crmCampaignService.getCampaignResource(crmCampaign, attrs.template)
                }
                null
            }
            if (resource) {
                attrs.template = resource
                out << crm.render(attrs, body)
                return
            }
        }

        out << crm.render(attrs, body)
    }
}
