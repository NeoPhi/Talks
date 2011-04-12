/*
 * Copyright (c) 2011 Daniel Rinehart <danielr@neophi.com> [http://danielr.neophi.com/]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.neophi.sample.akka;

import akka.actor.ActorRef;
import akka.actor.ActorRegistered;
import akka.actor.ActorUnregistered;
import akka.actor.Actors;
import akka.actor.UntypedActor;

@SuppressWarnings("unchecked")
public class LifecycleUntypedActor extends UntypedActor
{

    @Override
    public void postStop()
    {
        log().logger().info("Actor {} aka {} stopping", getContext().getUuid(), getContext().getId());
        super.postStop();
    }

    @Override
    public void preStart()
    {
        log().logger().info("Actor {} aka {} starting", getContext().getUuid(), getContext().getId());
        super.preStart();
    }

    @Override
    public void postRestart(Throwable reason)
    {
        log().logger().info("Actor {} aka {} restarted due to {}", new String[] {
                getContext().getUuid().toString(), getContext().getId(), reason.getMessage()
        });
        super.postRestart(reason);
    }

    @Override
    public void preRestart(Throwable reason)
    {
        log().logger().info("Actor {} aka {} about to restart due to {}", new String[] {
                getContext().getUuid().toString(), getContext().getId(), reason.getMessage()
        });
        super.preRestart(reason);
    }

    @Override
    public void onReceive(final Object message) throws Exception
    {
        log().logger().info("Actor {} aka {} onReceive", getContext().getUuid(), getContext().getId());
        if (message instanceof String)
        {
            log().logger().info("Received String message: {}", message);
        }
        else
        {
            throw new IllegalArgumentException("Unable to process message: " + message);
        }
    }

    public static void main(final String[] args) throws Exception
    {
        ActorRef listener = Actors.actorOf(ListenerUntypedActor.class);
        listener.start();
        Actors.registry().addListener(listener);
        ActorRef actorRef = Actors.actorOf(LifecycleUntypedActor.class);
        actorRef.start();
        actorRef.sendOneWay("Hello Actor!");
        actorRef.sendOneWay(Integer.valueOf(2));
        actorRef.sendOneWay("Goodbye Actor!");

        Actors.registry().shutdownAll();
    }

    public class ListenerUntypedActor extends UntypedActor
    {
        @Override
        public void onReceive(final Object message) throws Exception
        {
            if (message instanceof ActorRegistered)
            {
                log().logger().info("Adding actor: {}", ((ActorRegistered) message).actor().getUuid());
            }
            else if (message instanceof ActorUnregistered)
            {
                log().logger().info("Removing actor: {}", ((ActorUnregistered) message).actor().getUuid());
            }
            else
            {
                throw new IllegalArgumentException("Unable to process message: " + message);
            }
        }
    }
}
