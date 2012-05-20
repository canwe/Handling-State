package handlers;

import interfaces.INotifyHandler;
import model.NotifyMessage;
import model.xml.NotifyTemplate;
import obj.TestContext;
import obj.TestEntity;

public class NotifyHandler implements INotifyHandler<TestEntity, TestContext> {
    public String[] resolveAddress(String role, TestEntity entity, TestContext context) {
        return new String[] { role + "@mail.com" };
    }

    public void parseMessageTemplate(NotifyMessage message, NotifyTemplate template, TestEntity entity, TestContext context) {
        String subject = template.getSubject().replace("${Handler}", "handled " + context.Text);
        message.setSubject(subject);
        String body = template.getBody().replace("${Handler}", "handled " + context.Text );
        message.setBody(body);
    }
}
