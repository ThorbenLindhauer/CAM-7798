/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.example.spring.servlet.pa;

import java.util.Set;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.application.ProcessApplicationManager;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.context.Context;

public class WorkaroundPlugin implements ProcessEnginePlugin {

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    processEngineConfiguration.setProcessApplicationManager(new ProcessApplicationManager() {
      @Override
      protected void createJobExecutorRegistrations(Set<String> deploymentIds) {
        try {
          Set<String> registeredDeployments = Context.getProcessEngineConfiguration().getRegisteredDeployments();
          registeredDeployments.addAll(deploymentIds);
        }
        catch (Exception e) {
          throw LOG.exceptionWhileRegisteringDeploymentsWithJobExecutor(e);
        }
      }
    });
  }

  @Override
  public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
  }

  @Override
  public void postProcessEngineBuild(ProcessEngine processEngine) {

  }
}
