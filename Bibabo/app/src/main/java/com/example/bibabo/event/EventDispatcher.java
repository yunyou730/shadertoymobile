package com.example.bibabo.event;

import android.media.metrics.Event;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventDispatcher {

    public enum EventType
    {
        ReceiveShaderCode,
        ShaderCodeError,
        ReceiveTextureFile,
    }

    HashMap<EventType,ArrayList<WizardEvent>> mEventMap = new HashMap<EventType, ArrayList<WizardEvent>>();
    HashMap<EventType,ArrayList<EventListener>> mEventListeners = new HashMap<EventType,ArrayList<EventListener>>();

    public EventDispatcher()
    {
    }

    public void DispatchEvent(EventType type,WizardEvent event)
    {
        if(!mEventMap.containsKey(type))
        {
            mEventMap.put(type,new ArrayList<WizardEvent>());
        }
        mEventMap.get(type).add(event);
    }

    public void SendToListeners()
    {
        for(EventType eventKey : mEventMap.keySet())
        {
            // get events
            ArrayList<WizardEvent> events = mEventMap.get(eventKey);
            if(events.size() == 0)
            {
                continue;
            }

            // get listeners
            ArrayList<EventListener> listeners = null;
            if(mEventListeners.containsKey(eventKey))
            {
                listeners = mEventListeners.get(eventKey);
            }

            // send event to listeners
            if(listeners != null)
            {
                for (EventListener listener : listeners) {
                    for(WizardEvent evt : events)
                    {
                        listener.onEvent(eventKey,evt);
                    }
                }
            }
        }
    }

    public void ClearAllEvents()
    {
        for(ArrayList<WizardEvent> events : mEventMap.values())
        {
            events.clear();
        }
    }

    public void RegisterEvent(EventType eventType,EventListener listener)
    {
        if(mEventListeners.containsKey(eventType))
        {
            mEventListeners.get(eventType).add(listener);
        }
        else
        {
            ArrayList<EventListener> listeners = new ArrayList<EventListener>();
            listeners.add(listener);
            mEventListeners.put(eventType,listeners);
        }
    }

    public void UnRegisterEvent(EventType eventType,EventListener listener)
    {
        if(mEventListeners.containsKey(eventType))
        {
            ArrayList<EventListener> listeners = mEventListeners.get(eventType);
            // @miao @todo

        }
    }

    public static abstract class WizardEvent
    {
    }

    public static class ShaderCodeChangeEvent extends WizardEvent
    {
        public String mVertCode;
        public String mFragCode;

        public ShaderCodeChangeEvent(String vertCode,String fragCode)
        {
            super();
            mVertCode = vertCode;
            mFragCode = fragCode;
        }
    }

    public static class ShaderCodeErrorEvent extends WizardEvent
    {
        public String mError;

        public ShaderCodeErrorEvent(String error)
        {
            mError = error;
        }
    }
}



