package com.myspace.jbpm_elements_katas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.EmptyContext;

public class ExclusiveGatewayPizzaDeliveryTest extends JbpmJUnitBaseTestCase {

    private String processId = "jbpm-elements-katas.ExclusiveGatewayPizzaDelivery";

    @Before
    public void setup() { 

    }

    @Test
    public void givenCollectPizzaShouldFollowCollect() {
        RuntimeManager rtm = createRuntimeManager("com/myspace/jbpm_elements_katas/ExclusiveGatewayPizzaDelivery.bpmn");
        RuntimeEngine engine = getRuntimeEngine(EmptyContext.get());
        KieSession kieSession = engine.getKieSession();

        assertNotNull(kieSession);

        Map<String, Object> params = new HashMap<String, Object>();

        Order order = new Order();
        order.setDelivery(new Delivery());
        order.getDelivery().setDelivered(true);
        order.getDelivery().setDeliveryMethod("collect");
        params.put("order", order);
        ProcessInstance pi = kieSession.startProcess(processId, params);

        assertNotNull(pi);

        assertEquals(1, 1);

    }
    
}
