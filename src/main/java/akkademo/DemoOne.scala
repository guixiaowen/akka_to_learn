package akkademo

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

object DemoOne extends App {

  class MyActor extends Actor {

    val log = Logging(context.system, this)

    override def receive: Receive = {
      case "test" => log.info("received test")
      case _ => log.info("received unknown message")
    }
  }
  // 创建actorSystem对象
  val system = ActorSystem("MyActorSystem")
  // 返回actorSystem对象的logging
  val systemLog = system.log

  val myactor = system.actorOf(Props[MyActor], name = "myactor")

  systemLog.info("准备向myActor发送消息")
  // 向myactor发送消息
  myactor!"test"
  myactor!123

//  system.shutdown()

}
