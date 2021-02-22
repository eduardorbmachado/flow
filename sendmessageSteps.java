package StepDefinitions;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import cucumber.api.java.en.*;
import flow.Engine;
import flow.IAdapter;
import flow.IAgent;
import flow.IApp;
import flow.samples.TextAdapter;
import flow.samples.TextAgent;
import flow.samples.TextApp;

public class sendmessageSteps {
	
	private Engine engine;
	private TextAgent Agent;
	private TextAdapter EmailClient;
	private TextApp EmailServer;
	private Map<String, IAdapter> messages = new HashMap();
	

	@Given("^A User sends an \"([^\"]*)\" message$")
	public void a_User_sends_an_message(String strClientMessage) throws Throwable {
	
		Agent = new TextAgent(strClientMessage);
		EmailClient = new TextAdapter();
		messages.put("TEXT", EmailClient);
		
	}

	@When("^The message is converted by the Adapter$")
	public void the_message_is_converted_by_the_Adapter() throws Throwable {
	    		
		EmailServer = new TextApp();
		engine = new Engine(Agent, messages, EmailServer);
		engine.run();
	}

	@Then("^The Message server should contain the \"([^\"]*)\" message in the queue$")
	public void the_Message_server_should_contain_the_message_in_the_queue(String strExpServerMessage) throws Throwable {
	    
		Assert.assertEquals("ACK:"+strExpServerMessage, EmailServer.popResponse());

	}
	
}
