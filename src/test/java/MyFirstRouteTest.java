import com.bactrian.WorkflowMain;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = WorkflowMain.class)
//@ContextConfiguration
public class MyFirstRouteTest {


    @Autowired
    CamelContext camelContext;


    @EndpointInject(ref="testExchange")
    Endpoint trigger;

    @Test
    public void foo() throws Exception {
        Assert.assertNotNull(trigger);

    }
}
