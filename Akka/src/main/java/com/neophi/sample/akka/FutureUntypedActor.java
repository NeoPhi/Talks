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

import scala.Option;
import akka.actor.ActorRef;
import akka.actor.Actors;
import akka.actor.UntypedActor;
import akka.dispatch.Future;

@SuppressWarnings("unchecked")
public class FutureUntypedActor extends UntypedActor
{
    @Override
    public void onReceive(final Object message) throws Exception
    {
        log().logger().info("Received: {} From: {}", message, getContext().getSender());
        if ("Wait".equals(message))
        {
            Thread.sleep(250);
        }
        getContext().replyUnsafe("Got it");
    }

    public static void main(final String[] args) throws Exception
    {
        ActorRef actorRef = Actors.actorOf(FutureUntypedActor.class);
        actorRef.start();
        sendToTheFuture(actorRef, "Hello");
        sendToTheFuture(actorRef, "Wait");
        actorRef.stop();
    }

    private static void sendToTheFuture(final ActorRef actorRef, final String message)
    {
        Future<?> future = actorRef.sendRequestReplyFuture(message, 100, actorRef);
        try
        {
            future.await();
        }
        catch (Exception exception)
        {
            System.out.println("Future await exception" + exception.getMessage());
        }
        if (future.isCompleted())
        {
            Option<?> option = future.result();
            if (option.isDefined())
            {
                System.out.println(message + " Response: " + option.get());
            }
            else
            {
                System.out.println(message + " Response undefined");
            }
        }
        else
        {
            System.out.println(message + " Future not complete.");
        }
    }
}
