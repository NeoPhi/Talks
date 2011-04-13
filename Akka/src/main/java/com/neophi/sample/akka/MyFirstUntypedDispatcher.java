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
import akka.actor.Actors;
import akka.routing.UntypedDispatcher;

@SuppressWarnings("unchecked")
public class MyFirstUntypedDispatcher extends UntypedDispatcher
{
    private ActorRef pingRef = Actors.actorOf(Ping.class).start();

    private ActorRef pongRef = Actors.actorOf(Pong.class).start();

    @Override
    public ActorRef route(final Object message)
    {
        if ("ping".equals(message))
        {
            return pingRef;
        }
        if ("pong".equals(message))
        {
            return pongRef;
        }
        throw new IllegalArgumentException("I can't route" + message);
    }

    public static void main(final String[] args)
    {
        ActorRef dispatcherRef = Actors.actorOf(MyFirstUntypedDispatcher.class).start();
        dispatcherRef.sendOneWay("ping");
        dispatcherRef.sendOneWay("pong");
        dispatcherRef.sendOneWay("rally-on");
    }
}
