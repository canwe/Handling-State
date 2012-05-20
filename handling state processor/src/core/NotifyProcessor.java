package core;

import interfaces.INotifyHandler;
import model.NotifyMessage;
import model.xml.Notification;
import model.xml.NotifyTemplate;
import model.xml.Recipient;

import java.util.Hashtable;

public class NotifyProcessor<T, C> {
    Hashtable _to;
    Hashtable _cc;

    Notification _notification;
    NotifyTemplate _template;
    T _entity;
    C _ctx;
    INotifyHandler<T, C> _handler;

    public NotifyProcessor(T entity,
                           C context,
                           Notification n,
                           NotifyTemplate t,
                           INotifyHandler<T, C> handler) {
        _to = new Hashtable();
        _cc = new Hashtable();
        _notification = n;
        _template = t;
        _entity = entity;
        _ctx = context;
        _handler = handler;
    }

    public NotifyMessage Process() {
        for (Recipient r : _notification.getTo()) AddTo(r);
        if (_to.size() == 0) return null;
        for (Recipient r : _notification.getCc()) AddCc(r);
        return InternalParse(GetTo(), GetCc(), _template);
    }

    private String[] InternalResolveAddress(String role) {
        if (_handler != null) return _handler.resolveAddress(role, _entity, _ctx);
        else return new String[]{};
    }

    private NotifyMessage InternalParse(String to, String cc, NotifyTemplate template) {
        NotifyMessage message = new NotifyMessage(to, cc, null, null);
        if (_handler != null)
            _handler.parseMessageTemplate(message, template, _entity, _ctx);
        else {
            message.setSubject(template.getSubject());
            message.setBody(template.getBody());
        }
        return message;
    }

    private String GetCc() {
        StringBuilder s = new StringBuilder();
        for (Object key : _cc.keySet()) {
            if (!_to.containsKey(key)) {
                //s.append(String.format("{%s}; ", _cc.get(key)));
                s.append(String.format("%s; ", _cc.get(key)));
            }
        }
        return s.toString();
    }

    private String GetTo() {
        StringBuilder s = new StringBuilder();
        for (Object key : _to.keySet()) {
            //s.append(String.format("{%s}; ", _to.get(key)));
            s.append(String.format("%s; ", _to.get(key)));
        }
        return s.toString();
    }


    void AddTo(Recipient rec) {
        AddRecipient(_to, rec);
    }

    void AddCc(Recipient rec) {
        AddRecipient(_cc, rec);
    }

    void AddRecipient(Hashtable source, Recipient rec) {

        if (!(null == rec.getRole() || rec.getRole().isEmpty())) {
            for (String addr : InternalResolveAddress(rec.getRole()))
                source.put(addr, addr);
        } else if (!(null == rec.getAddress() || rec.getAddress().isEmpty())) {
            source.put(rec.getAddress(), rec.getAddress());
        }
    }
}
