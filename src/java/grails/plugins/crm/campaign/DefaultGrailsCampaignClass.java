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

package grails.plugins.crm.campaign;

import groovy.lang.Closure;

import org.codehaus.groovy.grails.commons.AbstractInjectableGrailsClass;

import java.util.Map;

/**
 * @author Goran Ehrsson
 */
public class DefaultGrailsCampaignClass extends AbstractInjectableGrailsClass implements GrailsCampaignClass {

    public DefaultGrailsCampaignClass(Class clazz) {
        super(clazz, GrailsCampaignClass.TYPE);
    }

    public void configure(Object campaign, Closure dsl) {
        getMetaClass().invokeMethod(getReferenceInstance(), GrailsCampaignClass.CONFIGURE, new Object[]{campaign, dsl});
    }

    public void configure(Object campaign, Map<String, Object> params) {
        getMetaClass().invokeMethod(getReferenceInstance(), GrailsCampaignClass.CONFIGURE, new Object[]{campaign, params});
    }

    public void process(Object data) {
        getMetaClass().invokeMethod(getReferenceInstance(), GrailsCampaignClass.PROCESS, new Object[]{data});
    }

}
