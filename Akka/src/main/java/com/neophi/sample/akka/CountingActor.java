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
import akka.actor.UntypedActor;
import akka.stm.Atomic;
import akka.stm.Ref;

@SuppressWarnings("unchecked")
public class CountingActor extends UntypedActor
{
    private final Ref<Integer> counter = new Ref<Integer>(0);

    @Override
    public void onReceive(final Object message) throws Exception
    {
        int newCount = new Atomic<Integer>()
        {
            public Integer atomically()
            {
                int newCount = counter.get() + ((Integer) message).intValue();
                counter.set(newCount);
                return newCount;
            }
        }.execute();
        log().logger().info("Count now: {}", newCount);
    }

    public static void main(final String[] args) throws Exception
    {
        ActorRef actorRef = Actors.actorOf(CountingActor.class);
        actorRef.start();
        for (int i = 1; i <= 10; i++)
        {
            actorRef.sendOneWay(Integer.valueOf(i));
            if ((i % 5) == 0)
            {
                actorRef.sendOneWay(String.valueOf(i));
            }
        }
        System.out.println("Done adding messages");
        // how to safely shutdown?
    }
}
