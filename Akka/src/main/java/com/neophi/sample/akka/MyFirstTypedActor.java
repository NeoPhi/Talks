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

import java.util.ArrayList;
import java.util.List;

import scala.Option;
import akka.actor.TypedActor;
import akka.dispatch.Future;

@SuppressWarnings("unchecked")
public class MyFirstTypedActor extends TypedActor implements MyTypedInterface
{
    public void echo(final String message)
    {
        log().logger().debug(message);
    }

    public List<String> reverse(final List<String> data)
    {
        final List<String> result = new ArrayList<String>(data.size());
        for (String string : data)
        {
            result.add(new StringBuilder(string).reverse().toString());
        }
        return result;
    }

    public Future<MyTypedMessage> send(final MyTypedMessage myMessage)
    {
        return future(new MyTypedMessage(myMessage.getFrom(), myMessage.getTo()));
    }

    public static void main(final String args[])
    {
        MyTypedInterface actorRef = TypedActor.newInstance(MyTypedInterface.class, MyFirstTypedActor.class, 1000);

        actorRef.echo("Hello There");

        List<String> sample = new ArrayList<String>();
        sample.add("Hello");
        sample.add("There");
        List<String> result = actorRef.reverse(sample);
        for (String string : result)
        {
            System.out.println(string);
        }

        // Error checking ignored
        Future<MyTypedMessage> future = actorRef.send(new MyTypedMessage("Bob", "Alice"));
        future.await();
        Option<MyTypedMessage> response = future.result();
        System.out.println("Response: " + response);

        TypedActor.stop(actorRef);
    }
}
