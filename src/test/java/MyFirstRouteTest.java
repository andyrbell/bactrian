import com.bactrian.WorkflowMain;
import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = WorkflowMain.class)
@MockEndpoints
public class MyFirstRouteTest {


    @Autowired
    @Qualifier("myContextId")
    CamelContext camelContext;

    //@Produce(ref="testExchange")
    //@Produce(uri="testExchange")
    @Produce(uri="direct:start")
    ProducerTemplate template;

//    @EndpointInject(ref="outQueue")
    @EndpointInject(uri="mock:rabbitmq:localhost:5671/myExchange_OUT")
    MockEndpoint outQueue;


    private String DUMMY_MSG = "{\n" +
            "  \"foo\": {\n" +
            "    \"bar\": \"fizzbuzz\"\n" +
            "  }\n" +
            "}\n";

    @Test
    public void foo() throws Exception {
        outQueue.expectedMessageCount(1);

        template.sendBody(DUMMY_MSG);

        MockEndpoint.assertIsSatisfied(camelContext);

    }
}
