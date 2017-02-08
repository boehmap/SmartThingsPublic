/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Alfred Workflow
 *
 *  Author: SmartThings
 */

definition(
    name: "Turn On At Time If Switch On",
    namespace: "boomtown.alarms",
    author: "Andrew Boehm",
    description: "This SmartApp allows you to activate a switch at a certain time only if a different switch is on. The indicator switch is turned off after running.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet-luminance.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet-luminance@2x.png"
)

preferences {
    input "theTime", "time", title: "Time to execute every day"
    section("Turn on these things......") {
        input "switches", "capability.switch", title: "Which Switches?", multiple: true, required: false
        input "controllers", "capability.sessionStatus", title: "Which Session To Start?", multiple: true, required: false
    }
    section("If this switch is on at execution time....") {
        input "toggle", "capability.switch", title: "Which Switch", multiple: false, required: true
    }
}

def initialize() {
    schedule(theTime, handler)
}

// called every day at the time specified by the user
def handler() {
    if(toggle.switch == "on"){
        log.debug "handler called at ${new Date()}"
        controllers.start()
        switches.on()
        toggle.off()
    }
    else
    {
        log.debug "handler skipped at ${new Date()}"
    }
}
