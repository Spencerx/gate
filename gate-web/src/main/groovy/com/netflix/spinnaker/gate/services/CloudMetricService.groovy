/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.gate.services

import com.netflix.spinnaker.gate.services.commands.HystrixFactory
import com.netflix.spinnaker.gate.services.internal.ClouddriverService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@CompileStatic
@Component
class CloudMetricService {

  private static final String GROUP = "cloudMetrics"

  @Autowired
  ClouddriverService clouddriverService

  List<Map> findAll(String cloudProvider, String account, String region, Map<String, String> filters) {
    HystrixFactory.newListCommand(GROUP, "$GROUP:$account:$region:findAll") {
      clouddriverService.findAllCloudMetrics(cloudProvider, account, region, filters)
    } execute()
  }

  Map getStatistics(String cloudProvider, String account, String region, String metricName,
                    Long startTime, Long endTime, Map<String, String> filters) {
    HystrixFactory.newMapCommand(GROUP, "$GROUP:$account:$region:getStatistics") {
      clouddriverService.getCloudMetricStatistics(cloudProvider, account, region, metricName, startTime, endTime, filters)
    } execute()
  }
}
