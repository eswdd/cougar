/*
 * Copyright 2014, The Sporting Exchange Limited
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

package uk.co.exemel.disco.core.impl.kpi;

import com.betfair.tornjak.kpi.KPIMonitor;
import com.betfair.tornjak.kpi.repeater.KPIRepeater;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ConfigurableKPIRepeater extends KPIRepeater implements BeanFactoryAware, InitializingBean {

    private BeanFactory beanFactory;
    private Map<String, Boolean> kpiMonitors;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setKpiMonitors(Map<String, Boolean> kpiMonitors) {
        this.kpiMonitors = kpiMonitors;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<KPIMonitor> monitors = new ArrayList<KPIMonitor>();
        for (String beanName : kpiMonitors.keySet()) {
            if (kpiMonitors.get(beanName)) {
                monitors.add((KPIMonitor) beanFactory.getBean(beanName));
            }
        }
        setMonitors(monitors);
    }
}
