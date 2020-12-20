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
// import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.EmptyContext;

public class ExclusiveGatewayPizzaDeliveryTest extends JbpmJUnitBaseTestCase {

    private String processId = "jbpm-elements-katas.ExclusiveGatewayPizzaDelivery";
    // private RuntimeManager rtm;
    private RuntimeEngine engine;
    private KieSession kieSession;

    public ExclusiveGatewayPizzaDeliveryTest() {
        super();
    }

    @Before
    public void setup() { 
        createRuntimeManager("com/myspace/jbpm_elements_katas/ExclusiveGatewayPizzaDelivery.bpmn");
        engine = getRuntimeEngine(EmptyContext.get());
        kieSession = engine.getKieSession();
    }

    @Test
    public void givenCollectPizzaShouldFollowCollect() {
        assertNotNull(kieSession);
        Map<String, Object> params = new HashMap<String, Object>();
        Order order = createOrder("collect");
        params.put("order", order);
        ProcessInstance pi = kieSession.startProcess(processId, params);
        assertNotNull(pi);
        assertEquals("Collected", order.getActions().get(0));
    }

    @Test
    public void givenHomeDeliveryPizzaShouldFollowHomeDelivery() {
        assertNotNull(kieSession);
        Map<String, Object> params = new HashMap<String, Object>();
        Order order = createOrder("homeDelivery");
        params.put("order", order);
        ProcessInstance pi = kieSession.startProcess(processId, params);
        assertNotNull(pi);
        assertEquals("Home Delivered", order.getActions().get(0));
    }

    @Test
    public void givenNoDeliveySelectedPizzaShouldFollowCreateVoucher() {
        assertNotNull(kieSession);
        Map<String, Object> params = new HashMap<String, Object>();
        Order order = createOrder(null);
        params.put("order", order);
        ProcessInstance pi = kieSession.startProcess(processId, params);
        assertNotNull(pi);
        assertEquals("Voucher Created", order.getActions().get(0));
    }

    private Order createOrder(String deliveryMethod) {
        Order order = new Order();
        order.setDelivery(new Delivery());
        order.getDelivery().setDelivered(true);
        if (deliveryMethod!=null)
            order.getDelivery().setDeliveryMethod(deliveryMethod);
        return order;
    }
    
}
